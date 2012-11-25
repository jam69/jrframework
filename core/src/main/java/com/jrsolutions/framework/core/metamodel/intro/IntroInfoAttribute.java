
package com.jrsolutions.framework.core.metamodel.intro;

import com.jrsolutions.framework.core.metamodel.MetaAttribute;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *  Describe un atributo a partir de la Introspeccion de su clase.
 */
public class IntroInfoAttribute implements MetaAttribute {

    private final PropertyDescriptor pd;
    private String editorName;

    public IntroInfoAttribute(PropertyDescriptor pd) {
        this.pd = pd;
    }

    public String getName() {
        return pd.getName();
    }

    public String getDisplayName() {
        if (pd.getDisplayName() != null) {
            return pd.getDisplayName();
        }
        return pd.getName();
    }

    public String getTypeName() {
        return pd.getPropertyType().getName();
    }

    public boolean isHidden() {
        return pd.isHidden();
    }

    public int getOrden() {
        if (pd instanceof JRPropertyDescriptor) {
            return ((JRPropertyDescriptor) pd).getOrden();
        } else {
            return 0;
        }
    }

    public boolean isNullable() {
        if (pd instanceof JRPropertyDescriptor) {
            return ((JRPropertyDescriptor) pd).isMandatory();
        } else {
            return false;
        }
    }

    public String getEditor() {
        if (editorName != null) {
            return editorName;
        } else {
            return getTypeName();
        }
    }

    public void setEditor(String str) {
        editorName = str;
    }

    public Object getValue(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            return ((Map) obj).get(getName());
        }
        Method m = pd.getReadMethod();
        try {
            Object val = m.invoke(obj,new Object[0]);
            return val;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR:" + e.getMessage());
            System.err.println("    m=" + m);
            System.err.println("    pd=" + pd);
            System.err.println("    obj=" + obj);
            e.printStackTrace();
        }
        return null;
    }

    public void setValue(Object obj, Object value) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Map) {
            ((Map) obj).put(getName(), value);
            return;
        }
        try {
            Object[] pars = new Object[1];
            pars[0] = value;
            Method m = pd.getWriteMethod();
            m.invoke(obj, pars);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    

}
