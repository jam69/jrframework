package com.jrsolutions.framework.core.utils.tabledefinition;

import java.util.ArrayList;


public class GroupDefinition  implements CGDefinition{

	private String description;
	private boolean expandable = false;
	private boolean expanded = true;
	private boolean alwaysShowGroupColumn = false;
	private String columnId;
	private ArrayList<CGDefinition> alElements = new ArrayList<CGDefinition>();
	
	public boolean isColumnDefinition(){return false;}
	public boolean isGroupDefinition(){return true;}

    public void addElement(ColumnDefinition c){
        alElements.add(c);
    }
    
    public void addElement(GroupDefinition c){
        alElements.add(c);
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isAlwaysShowGroupColumn() {
        return alwaysShowGroupColumn;
    }

    public void setAlwaysShowGroupColumn(boolean alwaysShowGroupColumn) {
        this.alwaysShowGroupColumn = alwaysShowGroupColumn;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnID) {
        this.columnId = columnID;
    }

    public ArrayList<CGDefinition> getAlElements() {
        return alElements;
    }

    public void setAlElements(ArrayList<CGDefinition> alElements) {
        this.alElements = alElements;
    }
	
    public String toString(){
    	return "GroupDefinition: ColumnID="+columnId+" desc="+description+" expandable="+expandable+" expanded="+expanded+" isAlwaysShow="+alwaysShowGroupColumn;    	
    }
    
    // Metodos de utilidad
    public ArrayList<ColumnDefinition> allColumns(){
    	ArrayList<ColumnDefinition> res=new ArrayList<ColumnDefinition>();
   		allColumns(res);
    	return res;
    }
    
    private void allColumns(ArrayList<ColumnDefinition> res){
    	for(CGDefinition obj:alElements){
    		if(obj instanceof ColumnDefinition){
    			res.add((ColumnDefinition)obj);
    		}
    		if(obj instanceof GroupDefinition){
    			((GroupDefinition)obj).allColumns(res);
    		}
    	}    	
    }
}
	
