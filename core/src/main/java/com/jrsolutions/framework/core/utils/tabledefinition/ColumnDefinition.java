package com.jrsolutions.framework.core.utils.tabledefinition;

import java.util.ArrayList;



public class ColumnDefinition  implements CGDefinition{

	private String id;
	private boolean hidden = false;
	private boolean visible = true;
	private boolean editable = false;
	private boolean sortable = true;
	private boolean filtrable = true;
	private boolean beanValue = true;
	private boolean functionValue = false;
	private String attribute;
	private String attrDescription;
	private String funcDescription;
	private String function;
	private int prefSize = 75;
	private boolean useDefaultEditor = true;
	private String customEditor;
	private String editableDefinition = "";
	private ArrayList<CGDefinition> attributeDefinition;

	public boolean isColumnDefinition(){return true;}
	public boolean isGroupDefinition(){return false;}

	public String getId() { return id; }
	public void setId(String id) {
		this.id = id;
	}

	public boolean isHidden() { return hidden; }
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isVisible() { return visible; }
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEditable() { return editable; }
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getEditableDefinition() { return editableDefinition; }
	public void setEditableDefinition(String editableDefinition) {
		this.editableDefinition = editableDefinition;
	}

	public boolean isSortable() { return sortable; }
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isFiltrable() { return filtrable; }
	public void setFiltrable(boolean filtrable) {
		this.filtrable = filtrable;
	}

	public boolean isBeanValue() { return beanValue; }
	public void setBeanValue(boolean beanValue) {
		this.beanValue = beanValue;
	}

	public boolean isFunctionValue() { return functionValue; }
	public void setFunctionValue(boolean functionValue) {
		this.functionValue = functionValue;
	}

	public String getAttribute() { return attribute; }
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttrDescription() { return attrDescription; }
	public void setAttrDescription(String attrDescription) {
		this.attrDescription = attrDescription;
	}

	public String getFuncDescription() { return funcDescription; }
	public void setFuncDescription(String funcDescription) {
		this.funcDescription = funcDescription;
	}

	public String getFunction() { return function; }
	public void setFunction(String function) {
		this.function = function;
	}

	public int getPrefSize() { return prefSize; }
	public void setPrefSize(int prefSize) {
		this.prefSize = prefSize;
	}

	public boolean isUseDefaultEditor() { return useDefaultEditor; }
	public void setUseDefaultEditor(boolean useDefaultEditor) {
		this.useDefaultEditor = useDefaultEditor;
	}

	public String getCustomEditor() { return customEditor; }
	public void setCustomEditor(String customEditor) {
		this.customEditor = customEditor;
	}
	public ArrayList<CGDefinition> getAttributeDefinition() {
		return attributeDefinition;
	}
	public void setAttributeDefinition(ArrayList<CGDefinition> attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}

	public String toString(){
		return "ColumnDefinition: attribute="+attribute+" Desc="+attrDescription+" id="+id+" attDef="+attributeDefinition;
	}
}
