
<#assign compplugin="p"+pluginName>
<#assign cmp = PluginsPackages.get(pluginName) >
${cmp} ${compplugin} = new ${cmp}();
JComponent ${id}=${compplugin}.createComponent(ctx);

