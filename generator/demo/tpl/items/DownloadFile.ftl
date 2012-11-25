<#--
     Plantilla de componentes.
     es incluida por la plantilla "formulario.ftl"
     recibe como variables:
        item : el nodo del Formulario en XML (los atributos se obtienen con item.@nombre_attributo)
        id:  nombre de la variable que debemos crear con el componente
     En el codigo que genera puede usar:
         ctx : como Contexto   
-->
		// DownloadFile  
		 
		final JButton ${id}=new JButton();
		final javax.swing.filechooser.FileFilter fileFilter = null;
		${id}.setText("Download");
		${id}.setFont(RunnerLF.getFont("download.font"));
		${id}.setEnabled(Boolean.FALSE);
		<#assign fileChooser = "fileChooser"+id>
		final JFileChooser ${fileChooser} = new JFileChooser();
				
		<#if item.@urlVar[0]?? || item.@urlVar[0]!="null">
		<#assign urlVar = item.@urlVar>
			<#if urlVar?starts_with("$")>
		ctx.addListener("${urlVar?substring(1)}", new ContextListener() {
			public void valueChanged(String key, Object value) {
				if (value != null) {
					${id}.setEnabled(true);
				} else {
					${id}.setEnabled(false);

				}
			}
		});
			<#else>
		if(ctx.get("${urlVar}")!= null){
		<#assign urlComprueba="urlComprueba"+id>
		java.net.URL ${urlComprueba};
		<#assign path = "path">
			String ${path}= null;
			${id}.setEnabled(true);
			try {
				${path} = (String) ctx.get("${urlVar}");
				${urlComprueba}= new java.net.URL(${path});
			} catch (java.net.MalformedURLException ex) {
				${path} = "file:///" + ${path};
				try {
					${urlComprueba} = new java.net.URL(${path});
				} catch (java.net.MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					${id}.setEnabled(false);
				}
			}
		}
		ctx.addListener("${urlVar}", new ContextListener() {
			public void valueChanged(String key, Object value) {
			java.net.URL urlComprueba;
				if (value != null) {
					${id}.setEnabled(true);
					String ${path} = null;
					try {
						${path} = (String) ctx.get("${urlVar}");
						urlComprueba = new java.net.URL(${path});
					} catch (java.net.MalformedURLException exce) {
						${path} = "file:///" + ${path};
						try {
							urlComprueba = new java.net.URL(${path});
						} catch (java.net.MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							${id}.setEnabled(false);
						}
					}

				} else {
					${id}.setEnabled(false);

				}
			}
		});
			</#if>	
		</#if>
		
		${id}.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (isEnabled()) {
					<#if urlVar?starts_with("$")>
						if (fileFilter != null) {
							${fileChooser}.removeChoosableFileFilter(fileFilter);
							${fileChooser}.setAcceptAllFileFilterUsed(Boolean.TRUE);
						}
						int option = ${fileChooser}.showSaveDialog(JOptionPane
								.getFrameForComponent(${id}));
						
						if (option == JFileChooser.APPROVE_OPTION) {
							<#assign path = "path">
							String ${path}  = new String();
							${path} = ${fileChooser}.getSelectedFile().getPath();
							java.io.File file = new java.io.File(${path});
							boolean doWrite = true;
							if (file.exists()) {
								Icon icono = es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("confirmation.icon"));
								int confirm = JOptionPane.showConfirmDialog(JOptionPane
										.getFrameForComponent(${id}), "El fichero ya existe, ¿desea sobreescribirlo?", null,
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE, icono);
								doWrite = confirm == JOptionPane.YES_OPTION;
							}
							if (doWrite) {
								if (file.exists() && !file.canWrite()) {
									Icon icono = es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("error.icon"));
									JOptionPane.showMessageDialog(JOptionPane
											.getFrameForComponent(${id}), "No se puede escribir en el fichero. Compruebe que no está protegido contra escritura", null,
											JOptionPane.ERROR_MESSAGE, icono);
								} else {
									try {
										java.io.FileWriter writer= new java.io.FileWriter(file);
										writer.write(ctx.get("${urlVar?substring(1)}").toString());
										writer.close();
				
									} catch (java.io.IOException ex) {
										Icon icono = es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("error.icon"));
										JOptionPane.showMessageDialog(JOptionPane
												.getFrameForComponent(${id}),
												"Error al escribir el archivo", null,
												JOptionPane.ERROR_MESSAGE, icono);
									}
								}
							}
						}

					<#else>
						if (fileFilter != null) {
							${fileChooser}.removeChoosableFileFilter(fileFilter);
							${fileChooser}.setAcceptAllFileFilterUsed(Boolean.TRUE);
						}
						<#assign path = "path">
						String ${path} = null;
						java.net.URL url = null;
						try {
							${path} = (String) ctx.get("${urlVar}");
							url = new java.net.URL(${path});
						} catch (java.net.MalformedURLException ex) {
							${path} = "file:///" + ${path};
							try {
								url = new java.net.URL(${path});
							}catch (java.net.MalformedURLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						${path} = url.getPath();
						int index = path.lastIndexOf(".");
						if (index >= 0) {
							final String extension = ${path}.substring(index + 1);
							javax.swing.filechooser.FileFilter fileFilter = new javax.swing.filechooser.FileFilter() {
								public boolean accept(java.io.File f) {
									return f.isDirectory()
											&& f.getName().endsWith("." + extension);
								}
				
								public String getDescription() {
									return "." + extension;
								}
							};
							${fileChooser}.addChoosableFileFilter(fileFilter);
							${fileChooser}.setAcceptAllFileFilterUsed(false);
						}
						int option = ${fileChooser}.showSaveDialog(JOptionPane
								.getFrameForComponent(${id}));
						if (option == ${fileChooser}.APPROVE_OPTION) {
							<#assign rutaDestino = "rutaDestino">
							String ${rutaDestino} = new String();
							if(url.getPath().lastIndexOf('.') < 0)
								${rutaDestino} = ${fileChooser}.getSelectedFile().getPath();
							else
								${rutaDestino} = ${fileChooser}.getSelectedFile().getPath()
									+ url.getPath().substring(url.getPath().lastIndexOf('.'));
									
							<#assign file = "file">		
							java.io.File ${file} = new java.io.File(${rutaDestino});
							boolean doWrite = true;
							if (${file}.exists()) {
								Icon icono = es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("confirmation.icon"));
								int confirm = JOptionPane.showConfirmDialog(JOptionPane
										.getFrameForComponent(${id}), "El fichero ya existe, ¿desea sobreescribirlo?", null,
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE, icono);
				
								doWrite = confirm == JOptionPane.YES_OPTION;
							}
							if (doWrite) {
								if (${file}.exists() && !file.canWrite()) {
									Icon icono = es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(
											 RunnerLF.getLabel("error.icon"));
									JOptionPane.showMessageDialog(JOptionPane
											.getFrameForComponent(${id}), "No se puede escribir en el fichero. Compruebe que no está protegido contra escritura", null,
											JOptionPane.ERROR_MESSAGE, icono);
								} else {
									try {
										java.io.BufferedInputStream bis = new java.io.BufferedInputStream(url
												.openStream());
										java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(
												new java.io.FileOutputStream(${file}));
										int count;
										byte[] data = new byte[1024];
										while (bis.available() > 0) {
											count = bis.read(data);
											bos.write(data, 0, count);
										}
										bis.close();
										bos.close();
									} catch (java.io.IOException ex) {
										Icon icono = es.indra.humandev.runner.core.utils.ImageLoader.loadIcon(RunnerLF.getLabel("error.icon"));
										JOptionPane.showMessageDialog(JOptionPane
												.getFrameForComponent(${id}),
												"Error al escribir el archivo", null,
												JOptionPane.ERROR_MESSAGE, icono);
									}
								}
							}
						}
					</#if>
				}
			}
		});

		// DownloadFile
		
		