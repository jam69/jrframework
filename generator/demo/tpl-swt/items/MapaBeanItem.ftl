<#-- 
 -  Recibe:
 -    cell como el CellSpecModel
 -    item como el item
 -->

<!-- Label -->
CELL: fila:${cell.@row} col:${cell.@col} colSpan=${cell.@colSpan} rowSpan=${cell.@rowSpan}
          insets: ${cell.@insets}
          Alignment: ${cell.@HAlignment} , ${cell.@VAlignment} 
          <#list cell.* as item>
             ITEM:  ${item?node_name}

             <#list item.@@ as attr>
              - ${attr?node_name} = ${attr}
             </#list>              
          </#list>
<!-- /Label -->