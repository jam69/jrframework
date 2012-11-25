
<#--
    Plantilla de Panel
    se llama desde la plantilla  'window.ftl'
    Recibe las  variables:
       panel:  Componente Panel a generar
       id:     variable que debe contener el panel
    Variables a usar en el codigo:
       ctx:    Contexto.
    Notas:
       como va a anidar llamadas a otros paneles copiamos panel e id para guardar
       su valor original. Y luego vamos seteando las variables y llamando a las
       templates 'hijas'
-->    

		// Dialog  ${dialog}
		<#assign id = dialog.name />	
		${id}=new JDialog();
		<#if dialog.title??>
		<#assign tituloDialogo= "tituloDialogo"+id>
		<#assign titulilloDialogillo=	dialog.title?j_string>
		String ${tituloDialogo}=GestorMultilingualidad.buscar("${titulilloDialogillo}");
		${id}.setTitle(${tituloDialogo});
		</#if> 
		${id}.setModal(Boolean.TRUE);
		((java.awt.Frame)${id}.getOwner()).setIconImage(app.getIconApp());
		<#if dialog.dismissOperation?? >
			${id}.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
					System.err.println("cerrado");
					 Action a= (Action)ctx.get("${dialog.dismissOperation}");
		 	         a.actionPerformed(new ActionEvent(e.getSource(),WindowEvent.WINDOW_CLOSED,null));
				}
			});
		</#if>
	<#--	<#assign id0 = id +"Panel" />
		<#assign panel = dialog.panel />
		<#import  "panels/"+panel+".ftl" as pm />
		<#assign pname="panel"+id>
		<@pm.macroPanel pname panel />  -->
		${id}.setContentPane(get${"Panel"+id}());
		${id}.pack();
		${id}.setLocationRelativeTo(JOptionPane.getFrameForComponent(app.getConversationState().getWorkPane()));  
		// End Dialog

		