
package com.jrsolutions.framework.core.metamodel.intro;

import com.jrsolutions.framework.core.metamodel.MetaAttribute;
import com.jrsolutions.framework.core.metamodel.MetaEntity;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 *  Define una clase a partir de su introspeccion.
 * 
 */
public class IntroInfoEntity implements MetaEntity {

    private final BeanInfo beanInfo;
    private final String className;
    private List<IntroInfoAttribute> ad; // <IntroInfoAttribute>

    public IntroInfoEntity(String name) throws ClassNotFoundException,IntrospectionException {
        className = name;
        Class c;
//        try {
            c = Class.forName(name);
            beanInfo = Introspector.getBeanInfo(c);
//        } catch (IntrospectionException e) {
//            e.printStackTrace();
//        }
    }

    public String getName() {
        return beanInfo.getBeanDescriptor().getName();
    }

    public String getDisplayName() {
        return beanInfo.getBeanDescriptor().getDisplayName();
    }

    public Object newInstance() {
        try {
            Class c=Class.forName(className);
            //if(!c.isPrimitive())
                return c.newInstance();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List getAttributes() {
        if (ad == null) {
            leeAttrDescriptors();
        }
        return ad;
    }
    
    public MetaAttribute[] getAttributesA() {
        if (ad == null) {
            leeAttrDescriptors();
        }
        MetaAttribute [] res=new MetaAttribute[ad.size()];
        for(int i=0;i<ad.size();i++){
            res[i]=(MetaAttribute)ad.get(i);
        }
        return res;
    }
    public String getAttributeNames(){
    	 if (ad == null) {
             leeAttrDescriptors();
         }
		StringBuffer ret=new StringBuffer();
		boolean first=true;
		for(MetaAttribute a:ad){
			if(first){
				first=false;
			}else{
				ret.append(",");
			}
			ret.append(a.getName());			
		}
		return ret.toString();
	}
    public MetaAttribute getAttribute(String name) {
        if (ad == null) {
            leeAttrDescriptors();
        }
        Iterator<IntroInfoAttribute> it = ad.iterator();
        while (it.hasNext()) {
            IntroInfoAttribute a = (IntroInfoAttribute) it.next();
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public void leeAttrDescriptors() {
        ArrayList<IntroInfoAttribute> props = new ArrayList<IntroInfoAttribute>();
        boolean fg = true;
        PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < pd.length; i++) {
            if (pd[i].isHidden()) {
                continue;
            }
            if (pd[i].getReadMethod() != null && pd[i].getWriteMethod() != null) {
                if (!(pd[i] instanceof JRPropertyDescriptor)) {
                    fg = false;
                }
                IntroInfoAttribute a = new IntroInfoAttribute(pd[i]);
                props.add(a);
            }
        }
        if (fg) {
            Collections.sort(props, new Comparator<IntroInfoAttribute>() {

                public int compare(IntroInfoAttribute arg0, IntroInfoAttribute arg1) {                   
                    return arg0.getOrden() - arg1.getOrden();
                }
            });
        }
        ad = props;
    }
}
