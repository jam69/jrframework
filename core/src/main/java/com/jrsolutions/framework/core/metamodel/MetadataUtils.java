package com.jrsolutions.framework.core.metamodel;


public class MetadataUtils {

////    static protected MetaEntityFactory factory = new MultiTypeInfoFactory(
////            new MetaEntityFactory[]{
////        new IntrospectorFactory(), new Repos()
////    });
//     static protected MetaInfoFactory factory = new IntroInfoFactory();
//
//    public static Object createEntity(String tipo, String[] params) {
//        MetaEntity info = factory.getTypeInfo(tipo);
//        if (info == null) {
//            log.error("No conozco el tipo '" + tipo + "'");
//            return null;
//        }
//        Object ent = (Object) info.newInstance();
//        MetaAttribute[] desc = info.getAttributesA();
//        for (int i = 0; i < desc.length; i++) {
//            if (params == null) {
//                setProperty(ent, desc[i].getName(), createAttr(desc[i].getClassName(), null));
//            } else {
//                if (i >= params.length) {
//                    setProperty(ent, desc[i].getName(), createAttr(desc[i].getClassName(), null));
//                } else {
//                    setProperty(ent, desc[i].getName(),
//                            createAttr(
//                            AnalizaStringEditors.command(params[i]),
//                            AnalizaStringEditors.parameters(params[i])));
//                }
//
//            }
//        }
//        return ent;
//    }
//
//    private static void setProperty(Object obj, String name, Object value) {
//        if (obj instanceof Entity) {
//            Entity ent = (Entity) obj;
//            ent.setProperty(name, value);
//        } else {
//            try {
//                BeanUtils.setProperty(obj, name, value);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static Entity createEntityData(String tipo, String[] data) {
//
//        Repos repos = new Repos();
//        MetaEntity info = repos.getTypeInfo(tipo);
//        if (info == null) {
//            log.error("No conozco el tipo '" + tipo + "'");
//            return null;
//        }
//        Entity ent = (Entity) info.newInstance();
//        MetaAttribute[] desc = info.getMetaAttributes();
//        for (int i = 0; i < desc.length && i < data.length; i++) {
//            //ent.setProperty(desc[i].getName(),setAttr(desc[i].getClassName(),null));
//            if (desc[i].getClassName().equals("java.lang.String")) {
//                desc[i].setValue(ent, data[i]);
//            } else if (desc[i].getClassName().equals("java.lang.Integer")) {
//                desc[i].setValue(ent, new Integer(data[i]));
//            } else if (desc[i].getClassName().equals("java.lang.Long")) {
//                desc[i].setValue(ent, new Long(data[i]));
//            } else if (desc[i].getClassName().equals("java.lang.Short")) {
//                desc[i].setValue(ent, new Short(data[i]));
//            } else if (desc[i].getClassName().equals("java.lang.Double")) {
//                desc[i].setValue(ent, new Double(data[i]));
//            } else if (desc[i].getClassName().equals("java.lang.Float")) {
//                desc[i].setValue(ent, new Float(data[i]));
//            } else if (desc[i].getClassName().equals("java.lang.Boolean")) {
//                desc[i].setValue(ent, new Boolean(data[i]));
//            } else {
//                System.out.println("No se como convertir de String a " + desc[i].getClassName());
//            }
//        }
//        return ent;
//    }
//
//    public static Object createAttr(String tipo, String[] param) {
//        Object obj = DataCreatorRegister.create(tipo, param);
//        if (obj == null) {
//            return createEntity(tipo, null);
//        } else {
//            return obj;
//        }
//    }
//
//    public static Entity cloneEntity(Entity entidad) {
//        return (Entity) entidad.clone();
//    }
//
//    public static boolean isModified(Entity a, Entity b) {
//        if (a.equals(b)) {
//            return false;
//        } else {
//            if (a.getKeys().size() != b.getKeys().size()) {
//                return true;
//            } else {
//                Iterator itA = a.getKeys().iterator();
//                while (itA.hasNext()) {
//                    String key = (String) itA.next();
//
//                    if (!b.getKeys().contains(key)) {
//                        return true;
//                    } else if (!a.getProperty(key).equals(b.getProperty(key))) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//    }
//
//    public static Entity setProperty(Entity entidad, String prop_name, Object value) {
//        entidad.setProperty(prop_name, value);
//        return entidad;
//    }

}
