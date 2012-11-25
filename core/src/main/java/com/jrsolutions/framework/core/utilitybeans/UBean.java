package com.jrsolutions.framework.core.utilitybeans;

import com.jrsolutions.framework.core.context.MInfoMeta;
import com.jrsolutions.framework.core.metamodel.Entity;
import com.jrsolutions.framework.core.metamodel.MetaAttribute;
import com.jrsolutions.framework.core.metamodel.MetaEntity;
import com.jrsolutions.framework.core.metamodel.MetaInfoFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * Clases de utilidad para el manejo de collecciones y entidades.
 * 
 * <p>No tienen acceso al contexto, as� que no lanzan eventos de modificaci�n.
 * Para ello debermos poner siempre un resultado en el m�todo.
 * 
 * <p>Si creamos un UBean, podemos asignar el lenguaje que usa.
 * <ul>
 * <li>    JavaScript (a partir de java 6.0) o incorparando Rhino.jar
 * <li>    BeanShell  (beanshell.jar)
 * <li>    EL, JEXL   (el lenguaje de los JSP) @{link http://commons.apache.org/jexl/reference/syntax.html}
 * <li>    otros scripts BSF (http: bsf.java.dev.net )
 * </ul>
 * <p> Los scripts van como string, pero si empiezan por '{' se buscan
 * como ficheros en el proyecto (no implementado).
 * TODO: �scripts en ficheros?
 * 
 * Abreviaturas:
 * <ul>
 * <li> c:  Collection</li>
 * <li> o:  Object</li>
 * <li> script: un script o referencia al script</li>
 * <li> n:  un numero entero</li>
 * <li> attrs: una lista de nombres de propiedades separados por comas</li>
 * <li> tm: TreeModel</li>
 * <li> type: nombre de la clase </li>
 * <li> a: un valor de atributo(primitiva o clase)</li>
 * <li> b: devuelve un boolean </li>
 * </ul>
 * 
 * Manejo de collecciones
 * <ul>
 * <li>c sort(c,"atrs"): ordena</li>
 * <li>c filter(c,script): copia los registros que script es true</li>
 * <li>c delete(c,script): borra los registros que script es true</li>
 * <li>c update(c,script): modifica el contenido</li>
 * <li>c copyRango(n0,n1): copia los registros indicados (-1 significa el ultimo,-n el n por el final)</li>
 * <li>o getRec(n): optiene un registro</li>
 * </ul>
 * Conversión a arbol
 * <ul>
 * <li>tm toTree(c,"attrs"): (para atributos repetidos)</li>
 * <li>tm toTree2(c,"attrId","attrPadre"): (con atributos de relacion)</li>
 * <ul>
 * Obtener desde ficheros
 * <ul>
 * <li>c fromURLCSV(url,type)</li>
 * <li>c fromURLXML(url)</li>
 * </ul>
 * Fusion de colecciones
 * <ul>
 * <li>c concat(c1,c2)</li>
 * <li>c join(..........)</li>
 * <li>c oneToMany(.......)</li>
 * <li>c tolizador(c,"attrs"): Los atributos no indicados quedan a null, los indicados quedan con tipo Double</li>
 * </ul>
 * Colecciones constantes
 * <ul>
 * <li>c rangoInt(n0,n1)</li>
 * <li>c rangoFloat(f0,f1,fStep)</li>
 * <li>c rangoDouble(d0,d1,dStep)</li>
 * <li>c lastYears(n): Los objetos son Integer</li>
 * <li>c nextYears(n): Los objetos son Integer</li>
 * <li>c WeekDaysNames(): Los objetos son String</li>
 * <li>c WeekDaysNamesLong(): Los objetos son String</li>
 * <li>c MonthNames(): Los objetos son String</li>
 * <li>c MonthNamesLong(): Los objetos son String</li>
 * </ul>
 * Gestion de Beans
 * <ul>
 * <li>o copy(o1): simplemente copia la referencia</li>
 * <li>a get(c,"attr_name"): devuelve el valor de un atributo</li>
 * <li>o set(o,"attr_name",v): cambia el atributo y pone el valor v</li>
 * <li>b equals(o1,o2): hace el o1.equals(o2)</li>
 * <li>o clone(o1): copia o1 y todos los atributos</li>
 * </ul>
 * Script en general
 * <ul>
 * <li>x script(script, varargs) <i>a partir JDK1.5</i> Ejecuta el script
 * y recibe como la variable 'arg[]' los argumentos que se le pasan.</li>
 * </ul>
 * 
 */
public class UBean implements MInfoMeta{

    private MetaInfoFactory factory;
    private RunnerBSF scriptRunner=new RunnerBSF("jexl");
    
    private String[] leePropsNames(Object o) {
        MetaEntity info = factory.getTypeInfo(getClassName(o));
        MetaAttribute[] a = info.getAttributesA();
        String[] res = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i].getName();
        }
        return res;
    }
    
    public void setInfoFactory(MetaInfoFactory f){
    	factory=f;
    }
    
    private String getClassName(Object o) {
		if (o instanceof Entity) {
			return ((Entity) o).getTypeName();
		} else {
        return o.getClass().getName();
		}
    }

    private MetaAttribute[] leeProps(Object o, String attrs) {
        return leeProps(getClassName(o), attrs);
    }

    private MetaAttribute[] leeProps(String cname, String attrs) {
        MetaEntity info = factory.getTypeInfo(cname);
        String a[] = attrs.split(",");
        MetaAttribute[] props = new MetaAttribute[a.length];
        for (int i = 0; i < a.length; i++) {
            props[i] = info.getAttribute(a[i]);
        }
        return props;
    }

    private Object getFirst(Collection c) {
        Iterator it = c.iterator();
        return it.next();
    }

    private Object clona(Object o) {
        try {
            Class c = o.getClass();
            Object r = c.newInstance();
            return r;
        } catch (Exception ex) {
            return null;
        }
    }

    private String buscaScript(String bsh) {
        if (bsh.startsWith("{")) {
            return bsh;
        } else {
            // deberiamos tener un cache aqu�
            try {
                char[] buff = new char[256];
                Reader r = new FileReader(bsh);
                StringWriter sw = new StringWriter();
                int n;
                while ((n = r.read(buff)) > 0) {
                    sw.write(buff, 0, n);
                }
                r.close();
                return sw.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Cambia el interprete de script.
     * Los interpretes posibles son:
     * <ul>
     * <li>JEXL: {@link http://commons.apache.org/jexl/reference/syntax.html} Por defecto. Incluido en el runner</li>
     * <li>JavaScript: {@link http://developer.mozilla.org/en/docs/Rhino_documentation } Incluido en el JDK1.6, Sino incluir jsRhino.jar</li>
     * <li>BeanShell: {@link http://www.beanshell.org/manual/} Debes incluir bsh.jar y bsh-engine.jar</li>
     * <li>JRuby,Jython,Jacl,OGNL,Groovy,....: ver BSF{https://scripting.dev.java.net/} <li>
     * </ul>
     * 
     * @param str
     */
    public void setLang(String str){
            RunnerBSF bsf=new RunnerBSF();
            bsf.setLanguage(str);
            scriptRunner=bsf;
    }

    /**
     * Ordena una colección, utilizando los atributos indicados.
     * Los valores null no se comparan (cualquier cosa es igual a null)
     *
     * Ejemplo: 
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * resultado: sort(c,"nombre,apellido");
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr> 
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>         
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * </table>
     * 
     * @param c
     *            Coleccion a ordenar, lanza el evento de modificado
     * @param attrs
     *            Lista de atributos separados por comas.
     */
    public Collection sort(List c, String attrs) {
        Object o = getFirst(c);
        if (o == null) {
            return c;
        }
        final MetaAttribute[] a = leeProps(o, attrs);
        Collections.sort(c, new Comparator() {

            public int compare(Object o1, Object o2) {
                for (int i = 0; i < a.length; i++) {
                    Comparable v1 = (Comparable) a[i].getValue(o1);
                    Comparable v2 = (Comparable) a[i].getValue(o2);
                    if (v1 == null || v2 == null) {
                        return 0;
                    }
                    int r = v1.compareTo(v2);
                    if (r != 0) {
                        return r;
                    }
                }
                return 0;
            }
        });
        return c;

    }

    /**
     * Genera una colleci�n copiando las entidades que cumplan la condici�n
     * 
     * Se pasa el registro al Script con nombre 'rec'.
     * 
     * Ejemplo: 
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * resultado: filter(c,"rec.get('nombre').starsWith('J')");
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * </table>
     * @param c     Colección origen
     * @param bsh   Script BeanShell que filtra.
     * @return Una nueva coleccion que contiene las entiedades que cumplen la
     *         condicion.
     */
    public Collection filter(Collection c, String bsh) {
        String script = buscaScript(bsh);
        ArrayList ret = new ArrayList();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object ent = it.next();
            try {
                Object q=scriptRunner.execute(ent, script);
                if (q != null && q instanceof Boolean) {
                    if (((Boolean) q).booleanValue()) {
                        ret.add(ent);
                    }
                }
            } catch (Exception e) {
                // no copiamos el registro
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Borra los elementos que cumplan la condici�n.
     * 
     * Ejemplo: 
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * resultado: delete(c,"rec.get('nombre').starsWith('J')");
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>        
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * @param c
     * @param bsh
     * @return
     */
    public Collection delete(Collection c, String bsh) {
 
        String script = buscaScript(bsh);

        ArrayList ret = new ArrayList();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object ent = it.next();
            try {
                Object q = scriptRunner.execute(ent,script);
                if (q != null && q instanceof Boolean) {
                    if (!((Boolean) q).booleanValue()) {
                        ret.add(ent);
                    }
                }
            } catch (Exception e) {
                // no copiamos el registro
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Ejecuta el script indicado sobre cada registro de la collecion para
     * modificar el contenido de sus columnas
     * 
     * Ejemplo: 
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * resultado: update(c,"if (nombre.starsWith('J') ) rec.setEdad(66)");
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>66</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>66</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>66</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * @param c
     *            Colecci�n a actualizar
     * @param bsh
     *            Script que se ejecuta sobre cada elemento.
     */
    public Collection update(Collection c, String bsh) {

        String script = buscaScript(bsh);

        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object ent = it.next();
            try {
                scriptRunner.execute(ent, script);
            } catch (Exception e) {
                // no copiamos el registro
                e.printStackTrace();
            }
        }
        return c;
    }

    /**
     * Devuelve un TreeModel agrupando los registros seg�n los atributos
     * indicados. La coleccion debe estar ordenada por los atributos indicados
     * 
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * 
     * <b>toTreeModel(c,"nombre,apellido")</b>
     * <pre>
     *  Juan - Martin  - 34
     *                 - 33
     *       - Lopez   - 32
     *  Lucas - Lopez - 32
     *  Pedro - Lopez - 39
     *  Alberto - Martin - 30
     * </pre>
     * @param c
     *            Colecci�n origen
     * @param attrs
     *            Lista de campos a utilizar como agrupadores, separados por
     *            comas
     * @return Devuelve un TreeModel con los datos de la colecci�n.
     */
    public TreeModel toTreeModel(Collection c, String attrs) {
        Object o = getFirst(c);
        MetaAttribute[] a = leeProps(o, attrs);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object ent = it.next();
            DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(ent);
            DefaultMutableTreeNode n = busca(root, a, ent, 0);
            if (n != null) {
                n.add(nuevo);
            }
        }
        return new DefaultTreeModel(root);
    }

    private DefaultMutableTreeNode busca(DefaultMutableTreeNode root,
            MetaAttribute[] a, Object obj, int level) {

        Object v = a[level].getValue(obj);
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(i);
            Object ent = n.getUserObject();
            Object v2 = a[level].getValue(ent);
            if (v.equals(v2)) {
                return busca(n, a, obj, level + 1);
            }

        }
        return root;
    }

    /**
     * Devuelve un TreeModel agrupando los registros utilizando un atributo como
     * ID y otro como PARENT. (Parecido al <i>select connected</i> de Oracle).
     * 
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th><th>jefe</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td><td>Luis<td></tr>
     * <tr><td>Luis</td><td>Martin</td><td>33</td><td>Mario<td></tr>
     * <tr><td>Mario</td><td>Lopez</td><td>32</td><td>Pedro<td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td><td>Luis<td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td><td><td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td><td><td></tr>
     * </table>
     * 
     * <b>toTreeModel2(c,"nombre","jefe")</b>
     * <pre>
     * Pedro
     *      - Mario
     *              - Luis
     *                     - Lucas
     *              - Juan
     * Alberto
     * </pre>
     * @param c
     *            Colleci�n origen
     * @param id
     *            Atributo usado como ID
     * @param padre
     *            Atributo usado como PARENT
     * @return Un TreeModel con los datos de la colleci�n.
     */
    public TreeModel toTreeModel2(Collection c, String id, String padre) {
        Object o = getFirst(c);
        MetaEntity info = factory.getTypeInfo(getClassName(o));
        MetaAttribute aID = info.getAttribute(id);
        MetaAttribute aPadre = info.getAttribute(padre);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        HashMap aux = new HashMap();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object ent = it.next();
            DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(ent);
            Object k = aID.getValue(ent);
            Object p = aPadre.getValue(ent);
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) aux.get(p);
            if (n != null) {
                n.add(nuevo);
            } else {
                root.add(nuevo);
            }
            aux.put(k, nuevo);
        }
        return new DefaultTreeModel(root);
    }

    /**
     * Devuelve una colleci�n con un elemento del mismo tipo que la colleci�n
     * origen, pero con la suma de los valores de los atributos indicados.
     * <table>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td>Juan</td><td>Martin</td><td>34</td></tr>
     * <tr><td>Juan</td><td>Martin</td><td>33</td></tr>
     * <tr><td>Juan</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Lucas</td><td>Lopez</td><td>32</td></tr>
     * <tr><td>Pedro</td><td>Lopez</td><td>39</td></tr>
     * <tr><td>Alberto</td><td>Martin</td><td>30</td></tr>
     * </table>
     * <b>r totalizador(c,"edad");</b>
     * <tr><th>nombre</th><th>apellido</th><th>edad</th></tr>
     * <tr><td></td><td></td><td>200.0d</td></tr>
     * </table> 
     * @param c
     *            Colecci�n origen.
     * @param attrs
     *            Atributos a sumar
     * @return Una Collection con 1 registro conteniendo los totales.
     */
    public Collection totalizador(Collection c, String attrs) {
        Object o = getFirst(c);
        MetaEntity info = factory.getTypeInfo(getClassName(o));
        String att[] = attrs.split(",");
        MetaAttribute[] a = new MetaAttribute[att.length];
        for (int i = 0; i < att.length; i++) {
            a[i] = info.getAttribute(att[i]);
        }
        double sum[] = new double[a.length];
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object e = it.next();
            for (int i = 0; i < a.length; i++) {
                Number n = (Number) a[i].getValue(e);
                sum[i] += n.doubleValue();
            }
        }
        Object proto = clona(o);
        for (int i = 0; i < a.length; i++) {
            a[i].setValue(proto, new Double(sum[i]));
        }
        ArrayList res = new ArrayList(1);
        res.add(proto);
        return res;
    }

    /**
     * Crea una nueva colleci�n uniendo las dos existentes. Las dos collecciones
     * deben tener elementos del mismo tipo.
     * 
     * @param c1
     *            Una colecci�n
     * @param c2
     *            Otra colleci�n con elementos del mismo tipo que la primera
     * @return La collecci�n destino.
     */
    public Collection concat2(Collection c1, Collection c2) {
        Collection ret = new ArrayList();
        if (c1 != null) {
            ret.addAll(c1);
        } // no comprobamos tipos ni nada
        if (c2 != null) {
            ret.addAll(c2);
        } // no comprobamos tipos ni nada
        return ret;
    }

    /**
     * A�ade los elementos de la segunda colleci�n a la primera. Los elementos
     * de ambas deben ser del mismo tipo.
     * 
     * @param c1
     *            una coleci�n (a la que se a�adiran los elemento de la segunda)
     * @param c2
     *            una colecci�n con los elementos a a�adir a la primera.
     */
    public void concat(Collection c1, Collection c2) {
        // pasamos de comprobar nada
        c1.addAll(c2);
    }

    /**
     * Crea una nueva colleci�n de tipo indicado tomando los atributos de los
     * elementos de la colleci�n.
     * 
     * @param type
     *            Nombre de las clase de los elementos destino.
     * @param c1
     *            Colleci�n origen.
     * @return Colleccion con los elementos de tipo type y los datos de la
     *         colleci�n origen.
     */
    public Collection newCollection(String type, Collection c2) {
        ArrayList ret = new ArrayList();
        MetaEntity info1 = factory.getTypeInfo(type);
        Object o = getFirst(c2);
        MetaEntity info2 = factory.getTypeInfo(getClassName(o));
        String[] attName = leePropsNames(o);
        ArrayList desc1 = new ArrayList(); // <MetaAttribute>
        ArrayList desc2 = new ArrayList(); // <MetaAttribute>
        for (int i = 0; i < attName.length; i++) {
            MetaAttribute d1 = info1.getAttribute(attName[i]);
            if (d1 != null) {
                desc1.add(d1);
                MetaAttribute d2 = info2.getAttribute(attName[i]);
                desc2.add(d2);
            }
        }
        Iterator it = c2.iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            Object o1 = info1.newInstance();
            for (int i = 0; i < desc1.size(); i++) {
                MetaAttribute d1 = (MetaAttribute) desc1.get(i);
                MetaAttribute d2 = (MetaAttribute) desc2.get(i);
                Object value = d2.getValue(o2);
                d1.setValue(o1, value);
            }
            ret.add(o1);
        }
        return ret;
    }

    /**
     * Devuelve una nueva colección con entidades del tipo indicado y copiando
     * los atributos segun los mappings que se indican a partir de una colección
     * de Object[].
     * 
     * @param type
     *            Nombre de la clase o tipo de las entidades creadas
     * @param c2
     *            Colecci�n origen
     * @param mappings
     *            Mapeo de atributos, separados por comas
     * @return Una colección con entidades del tipo indicado y el mismo numero de
     *         registros que la colección origen.
     */
    public Collection newCollectionFromArray(String type, Collection c2, String mappings) {
        ArrayList ret = new ArrayList();
        final MetaAttribute NullDesc = null; // CHECK:
        MetaEntity info1 = factory.getTypeInfo(type);
        ArrayList desc1 = new ArrayList(); // <MetaAttribute>
        String[] maps = mappings.split(",");
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].equalsIgnoreCase("IGNORE")) {
                desc1.add(NullDesc);
            } else {
                desc1.add(info1.getAttribute(maps[i]));
            }
        }
        Iterator it = c2.iterator();
        while (it.hasNext()) {
            Object[] o2 = (Object[]) it.next();
            Object o1 = info1.newInstance();
            for (int i = 0; i < desc1.size(); i++) {
                MetaAttribute d1 = (MetaAttribute) desc1.get(i);
                if (d1 != NullDesc) {
                    Object value = o2[i];
                    d1.setValue(o1, value);
                }
            }
            ret.add(o1);
        }
        return ret;
    }

    /**
     * Devuelve una nueva colecci�n con entidades del tipo indicado y copiando
     * los atributos segun los mappings que se indican.
     * 
     * @param type
     *            Nombre de la clase o tipo de las entidades creadas
     * @param c2
     *            Colecci�n origen
     * @param mappings
     *            Mapeo de atributos, separados por comas, del tipo
     *            'campo1=campo2' donde copia en el campo1 del resultado por el
     *            campo2 de la colecci�n origen.
     * @return Una colecci�n con entidades del tipo indicado y los mismos
     *         registros que la colecci�n origen.
     */
    public Collection newCollection(String type, Collection c2, String mappings) {
        ArrayList ret = new ArrayList();
        MetaEntity info1 = factory.getTypeInfo(type);
        Object o = getFirst(c2);
        MetaEntity info2 = factory.getTypeInfo(getClassName(o));
        ArrayList desc1 = new ArrayList(); // <MetaAttribute>
        ArrayList desc2 = new ArrayList(); // <MetaAttribute>
        String[] maps = mappings.split(",");
        for (int i = 0; i < maps.length; i++) {
            int p = maps[i].indexOf('=');
            if (p < 0) {
                desc1.add(info1.getAttribute(maps[i]));
                desc2.add(info2.getAttribute(maps[i]));
            } else {
                String campo1 = maps[i].substring(0, p);
                String campo2 = maps[i].substring(p + 1);
                desc1.add(info1.getAttribute(campo1));
                desc2.add(info2.getAttribute(campo2));
            }
        }
        Iterator it = c2.iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            Object o1 = info1.newInstance();
            for (int i = 0; i < desc1.size(); i++) {
                MetaAttribute d1 = (MetaAttribute) desc1.get(i);
                MetaAttribute d2 = (MetaAttribute) desc2.get(i);
                Object value = d2.getValue(o2);
                d1.setValue(o1, value);
            }
            ret.add(o1);
        }
        return ret;
    }

    /**
     * Crea una coleccion a partir de los datos de un fichero localizado por
     * la URL.(Fichero, FTP, HTTP, ....)
     * 
     * 
     * @param type Nombre de la clase a crear
     * @param path localizacion del fichero
     * @param sep separador
     * @param header Si la primera linea tiene los nombres de los atributos
     * @return
     */
    public Collection newCollectionFromURL(String type, String uri, String sep,
            boolean header) {
        if (sep == null) {
            sep = ";";
        }
        MetaEntity info = factory.getTypeInfo(type);
        try {
            URL url = new URL(uri);
            InputStream is = url.openStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            String line = reader.readLine();
            MetaAttribute[] a;
            if (header) {
                line = reader.readLine();
                String[] f = line.split(sep);
                a = new MetaAttribute[f.length];
                for (int i = 0; i < f.length; i++) {
                    a[i] = info.getAttribute(f[i]);
                }
            } else {
                a = info.getAttributesA();
            }
            ArrayList res = new ArrayList();
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(sep);
                Object o = info.newInstance();
                for (int i = 0; i < f.length; i++) {
                    a[i].setValue(o, f[i]);
                }
                res.add(o);
            }
            reader.close();
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * crea una coleccion a partir de los datos de un fichero
     * (no es recomedable ya que no sabemos donde está el fichero)
     * 
     * @param type Nombre de la clase a crear
     * @param path localizacion del fichero
     * @param sep separador
     * @param header Si la primera linea tiene los nombres de los atributos
     * @return
     */
    public Collection newCollectionFromFile(String type, String path,
            String sep, boolean header) {
        try {
            File f = new File(path);
            URL url = f.toURL();
            return newCollectionFromURL(type, url.toExternalForm(), sep, header);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve una coleccion de todas las entidades de tipo 'entName' que
     * encuentre en el fichero. Deben de ser tipo 'type'. Los atributos de esa
     * entidad son los atributos que se copian a los objetos creados.
     * 
     * @param uri
     *            Localizacion del fichero XML
     * @param type
     *            Nombre del tipo o clase a generar
     * @param entName
     *            Nombre de la entidad cuyos atributos se convierten:
     * 
     * <pre>
     *   &lt;entName nombre=&quot;n&quot; cod=&quot;342&quot; ape=&quot;aasf&quot; &gt;
     * </pre>
     * 
     * Se convierte en una entidad de tipo entName con los atributos nombre,cod
     * y ape.
     * 
     * @return
     */
    public Collection newCollectionFromXMLURL(String uri, String type,
            String entName) {
        MetaEntity info = factory.getTypeInfo(type);
        try {
            URL url = new URL(uri);
            InputStream is = url.openStream();
            MyHandler handler = new MyHandler(info, entName);
            parse(is, handler);
            return handler.res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void parse(InputStream is, DefaultHandler handler) {
        // get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            // get a new instance of parser
            SAXParser sp = spf.newSAXParser();
            // parse the file and also register this class for call backs
            sp.parse(is, handler);
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private class MyHandler extends DefaultHandler {

        private ArrayList res = new ArrayList();
        private String entName;
        private MetaEntity info;

        MyHandler(MetaEntity info, String entName) {
            this.info = info;
            this.entName = entName;
        }

        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase(entName)) {
                Object o = info.newInstance();
                int n = attributes.getLength();
                for (int i = 0; i < n; i++) {
                    String qname = attributes.getQName(i);
                    MetaAttribute a = info.getAttribute(qname);
                    a.setValue(o, attributes.getValue(i));
                }
                res.add(o);
            }
        }
    }

    /**
     * Devuelve un Map usando el campo 'attr' como clave.
     * 
     * @param c
     *            Colecci�n origen.
     * @param attr
     *            Nombre del atributo a usar como clave del Map
     * @return Un Map indexado por el campo indicado.
     */
    public Map toMap(Collection c, String attr) {
        Object o = getFirst(c);
        MetaEntity info = factory.getTypeInfo(getClassName(o));
        MetaAttribute a = info.getAttribute(attr);
        Map aux = new HashMap(c.size());
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object ent = it.next();
            Object key = a.getValue(ent);
            aux.put(key, ent);
        }
        return aux;
    }

    private interface Asignador {

        public void put(Object dest, Object origen);
    }

    // En un futuro habr� AsignadorFuncion o AsignadorExpresion
    private class AsignadorCampo implements Asignador {

        MetaAttribute dest;
        MetaAttribute orig;

        public AsignadorCampo(MetaAttribute dest, MetaAttribute origen) {
            this.dest = dest;
            this.orig = origen;
        }

        public void put(Object oDest, Object oOrigen) {
            Object v = orig.getValue(oOrigen);
            dest.setValue(oDest, v);
        }
    }

    /**
     * Hace el join de dos colecciones
     * 
     * <pre>
     *  C1:  id nombre apellido  tipo
     *        1  aaa   bbbb      A1
     *        2  ss    dfs      	A3
     *        3  sfs   afds      A1
     *        
     *  C2:  tipoX Desc
     *       A1   'Tipo basico'
     *       A3   'Tipo Extend'
     *       
     *  OneToMany(tipoC3,C1,tipo,C2,tipoX,&quot;id,nombre,ape=apellido,d=Desc&quot;)
     *  
     *  C3:  id nombre ape 	d
     *       1  aaa    bbbb  'Tipo basico'
     *       2  ss     dfs   'Tipo Extend'
     *       3  sfs    afds  'Tipo basico'
     * </pre>
     * 
     * @param tipoDest
     * @param c1
     * @param campo1
     * @param c2
     * @param key
     * @param mappings
     * @return
     */
    public Collection oneToMany(String tipoDest, Collection c1, String campo1,
            Collection c2, String key, String mappings) {
        MetaEntity info = factory.getTypeInfo(tipoDest);
        Object o = getFirst(c1);
        MetaEntity info1 = factory.getTypeInfo(getClassName(o));
        Object o2 = getFirst(c2);
        MetaEntity info2 = factory.getTypeInfo(getClassName(o2));
        MetaAttribute a = info.getAttribute(campo1);
        ArrayList asig1 = new ArrayList(); // <Asignador>
        ArrayList asig2 = new ArrayList(); // <Asignador>
        String[] maps = mappings.split(",");
        for (int i = 0; i < maps.length; i++) {
            int p = maps[i].indexOf('=');
            if (p < 0) {
                MetaAttribute d = info.getAttribute(maps[i]);
                MetaAttribute d1 = info1.getAttribute(maps[i]);
                if (d1 != null) {
                    asig1.add(new AsignadorCampo(d, d1));
                } else {
                    d1 = info2.getAttribute(maps[i]);
                    if (d1 != null) {
                        asig2.add(new AsignadorCampo(d, d1));
                    } else {
                        System.out.println("NO conozco ese campo");
                    }
                }
            } else {
                String ncampo = maps[i].substring(0, p);
                String ncampo1 = maps[i].substring(p + 1);
                MetaAttribute d = info1.getAttribute(ncampo);
                MetaAttribute d1 = info1.getAttribute(ncampo1);
                if (d1 != null) {
                    asig1.add(new AsignadorCampo(d, d1));
                } else {
                    d1 = info2.getAttribute(ncampo1);
                    if (d1 != null) {
                        asig2.add(new AsignadorCampo(d, d1));
                    } else {
                        System.out.println("NO conozco ese campo");
                    }
                }
            }
        }

        Map m = toMap(c2, key);
        ArrayList res = new ArrayList();
        Iterator it = c1.iterator();
        while (it.hasNext()) {
            Object obj1 = it.next();
            Object ent = info.newInstance();
            Object v = a.getValue(obj1);
            Object obj2 = m.get(v);
            // copiar valores 1
            Iterator it1 = asig1.iterator();
            while (it1.hasNext()) {
                Asignador asig = (Asignador) it1.next();
                asig.put(ent, obj1);
            }
            // copiar valores 2
            Iterator it2 = asig2.iterator();
            while (it2.hasNext()) {
                Asignador asig = (Asignador) it2.next();
                asig.put(ent, obj2);
            }
            res.add(ent);
        }
        return res;
    }

    /**
     * Retorna una colección con los nombres de los meses de 3 letras.
     * (no tiene internacionalización todavía)
     * <ul>
     * <li>Ene</li>
     * <li>Feb</li>
     * <li>Mar</li>
     * <li>Abr</li>
     * <li>May</li>
     * <li>Jun</li>
     * <li>Jul</li>
     * <li>Ago</li>
     * <li>Sep</li>
     * <li>Oct</li>
     * <li>Nov</li>
     * <li>Dic</li>
     * </ul>
     * @return
     */
    public Collection monthNames() {
        return list("Ene,Feb,Mar,Abr,May,Jun,Jul,Ago,Sep,Oct,Nov,Dic");
    }

    /**
     * Retorna una colección con los nombres de los meses.
     * (no tiene internacionalización todavía)
     * <ul>
     * <li>Enero</li>
     * <li>Febrero</li>
     * <li>Marzo</li>
     * <li>Abril</li>
     * <li>Mayo</li>
     * <li>Junio</li>
     * <li>Julio</li>
     * <li>Agosto</li>
     * <li>Septiembre</li>
     * <li>Octubre</li>
     * <li>Noviembre</li>
     * <li>Diciembre</li>
     * </ul>
     * @return
     */
    public Collection monthNamesLong() {
        return list("Enero,Febrero,Marzo,Abril,Mayo,Junio,Julio,Agosto,Septiembre,Octubre,Noviembre,Diciembre");
    }

    /**
     * Retorna una colección con los nombres de los días de la semana de tres letras.
     * Empezando por Lunes.(no tiene internacionalización todavía)
     * <ul>
     * <li>Lun</li>
     * <li>Mar</li>
     * <li>Mie</li>
     * <li>Jue</li>
     * <li>Vie</li>
     * <li>Sab</li>
     * <li>Dom</li>
     * </ul>
     * @return
     */
    public Collection weekDaysNames() {
        return list("Lun,Mar,Mie,Jue,Vie,Sab,Dom");
    }

    /**
     * Retorna una colección con los nombres de los días de la semana.
     * Empezando por Lunes. (no tiene internacionalización todavía)
     * <ul>
     * <li>Lunes</li>
     * <li>Martes</li>
     * <li>Miercoles</li>
     * <li>Jueves</li>
     * <li>Viernes</li>
     * <li>Sabado</li>
     * <li>Domingo</li>
     * </ul>
     * @return
     */
    public Collection weekDaysNamesLong() {
        return list("Lunes,Martes,Miercoles,Jueves,Viernes,Sabado,Domingo");
    }

    /**
     * Devuelve una colección con los elementos que forman el string 'items'
     * al separarlo por las comas.
     */
    public Collection list(String items) {
        return list(items, ",");
    }

    /**
     * Devuelve una colección con los elementos que forman el string 'items'
     * al separarlo con el separador 'sep'.
     * 
     * @see String.Split
     * 
     * @param items
     * @param sep
     * @return
     */
    public Collection list(String items, String sep) {
        String[] s = items.trim().split(sep);
        ArrayList ret = new ArrayList(s.length);
        for (int i = 0; i < s.length; i++) {
            ret.add(s[i]);
        }
        return ret;
    }

    /**
     * Devuelve una collección con los últimos N años.
     */
    public Collection lastYears(int n) {
        Calendar c = Calendar.getInstance();
        int a = c.get(Calendar.YEAR);
        return range(a - n, a);
    }

    /**
     * Devuelve una collección con los últimos N años.
     */
    public Collection nextYears(int n) {
        Calendar c = Calendar.getInstance();
        int a = c.get(Calendar.YEAR);
        return range(a, a + n);
    }

    /**
     * Devuelve una collecci�n con los posibles valores dentro del rango
     * indicado
     */
    public Collection range(int ini, int fin) {
        ArrayList ret = new ArrayList();
        int d;
        for (d = ini; d < fin; d++) {
            ret.add(new Integer(d));
        }
        ret.add(new Integer(d)); // ponemos el ultimo valor
        return ret;
    }

    /**
     * Devuelve una collecci�n con los posibles valores dentro del rango
     * indicado
     */
    public Collection range(int ini, int fin, int step) {
        ArrayList ret = new ArrayList();
        int d;
        for (d = ini; d < fin; d += step) {
            ret.add(new Integer(d));
        }
        ret.add(new Integer(d)); // ponemos el ultimo valor
        return ret;
    }

    /**
     * Devuelve una collecci�n con los posibles valores dentro del rango
     * indicado
     * 
     * @param ini
     * @param fin
     * @param step
     * @return
     */
    public Collection range(float ini, float fin, float step) {
        ArrayList ret = new ArrayList();
        float d;
        for (d = ini; d < fin; d += step) {
            ret.add(new Float(d));
        }
        ret.add(new Float(d)); // ponemos el ultimo valor
        return ret;
    }

    /**
     * Devuelve una collecci�n con los posibles valores dentro del rango
     * indicado
     * 
     * @param ini
     * @param fin
     * @param step
     * @return
     */
    public Collection range(double ini, double fin, double step) {
        ArrayList ret = new ArrayList();
        double d;
        for (d = ini; d < fin; d += step) {
            ret.add(new Double(d));
        }
        ret.add(new Double(d)); // ponemos el ultimo valor
        return ret;
    }
    
    
//     <li>o copy(o1): simplemente copia la referencia</li>
// * <li>a get(c,"attr_name"): devuelve el valor de un atributo</li>
// * <li>o set(o,"attr_name",v): cambia el atributo y pone el valor v</li>
// * <li>b equals(o1,o2): hace el o1.equals(o2)</li>
// * <li>o clone(o1): copia o1 y todos los atributos</li>
// * </ul>
// * Script en general
// * <ul>
// * <li>x script(script, varargs) <i>a partir JDK1.5</i></li>
// * <li>x script(script, a0) ejecut
    

    /** Simplemente Copia la referencia a un objeto */
    public Object copy(Integer o1){
    	return o1;
    }
    
    /** Simplemente Copia la referencia a un objeto */
    public Object copy(String o1){
    	return o1;
    }
    
    /** Simplemente Copia la referencia a un objeto */
    public Object copy(Object o1){
        return o1;
    }
    /** Simplemente Copia la referencia a un objeto */
    public Object copy(Double o1){
        return o1;
    }
    
    /** Devuelve el valor de un atributo */
    public Object get(Object c,String attrName){
        if(c==null)return null;
        MetaEntity info = factory.getTypeInfo(getClassName(c));
        MetaAttribute attr=info.getAttribute(attrName);
        if(attr==null){
            throw new IllegalArgumentException("La clase "+getClassName(c)+" no tiene el atributo '"+attrName+"'");            
        }
        return attr.getValue(c);
    }
    
    /** Cambia el valor de un atributo */
    public Object set(Object c,String attrName,Object value){
        if(c==null)return null;
        MetaEntity info = factory.getTypeInfo(getClassName(c));
        MetaAttribute attr=info.getAttribute(attrName);
        if(attr==null){
            throw new IllegalArgumentException("La clase "+getClassName(c)+" no tiene el atributo '"+attrName+"'");            
        }
        attr.setValue(c, value);
        return c;
    }
    
    /** Comprueba si dos objetos son iguales (devuelve un Boolean) */
    public Object equals(Object o1,Object o2){
        if(o1==null){
            return Boolean.valueOf(o2==null);
        }else{
            return Boolean.valueOf(o1.equals(o2));
        }
    }

    /** 
     * Ejecuta un script sobre los objetos que se le pasan
     * El script recibe los argumentos como un array con el nombre 'arg'
     * 
     * @param script  Script a ejecutar
     * @param args    argumentos (los que se quieran)
     * @return
     */
    public Object script(String script,Object... args){
        return scriptRunner.executeArgs(args, script);
    }
    
}
