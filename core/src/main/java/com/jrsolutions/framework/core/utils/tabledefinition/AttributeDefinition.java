package com.jrsolutions.framework.core.utils.tabledefinition;

public class AttributeDefinition {

	private String attribute;
	private String dataType;
	private String customEditor;
	
	public AttributeDefinition(){
		
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getCustomEditor() {
		return customEditor;
	}
	public void setCustomEditor(String customEditor) {
		this.customEditor = customEditor;
	}
	
}
