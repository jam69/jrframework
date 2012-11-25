/*
 * ImageLoader.java
 *
 * Created on 11 de febrero de 2005, 13:44
 */

package com.jrsolutions.framework.core.utils;

import java.net.URL;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import javax.swing.ImageIcon;


/**
 * Clase de utilidad para leer iconos desde el ClassPath.
 *
 */
public class ImageLoader {

	private static Logger log= Logger.getLogger(ImageLoader.class.getName());

    private static Map<String,ImageIcon> images=new WeakHashMap<String,ImageIcon>();

    private static ImageIcon def;
    static {
    	def=loadIcon("default.gif"); //$NON-NLS-1$
    }

    /** Creates a new instance of ImageLoader */
    public ImageLoader() {
    }

    /**
     * metodo que lee las imagenes.
     *
     * <ol>
     * <li> Primero lo busca en su propio cache, por si la cargo antes.</li>
     * <li> Luego la busca en el classpath (a�adiendo la '/' de raiz)</li>
     * <li> Luego en el classpath pero debajo de icons( a�adiendo '/icons/' )</li>
     * <li> Luego en el proyecto (como si el proyecto fuera el directorio actual)</li>
     * <li> Luego en el proyecto bajo el directorio '/icons'</li>
     * <li> Si todo falla, pone una imagen por defecto ('default.gif')</li>
     * </ul>
     */
  public static ImageIcon loadIcon(String s) {
	  ImageIcon imageIcon = (ImageIcon) images.get(s);
	  if (imageIcon == null) {
		  URL url = ImageLoader.class.getResource("/" + s); //$NON-NLS-1$
		  if (url != null) {
			  imageIcon = new ImageIcon(url);
		  }
		  else {
			  url = ImageLoader.class.getResource("/icons/" + s); //$NON-NLS-1$
			  if (url != null) {
				  imageIcon = new ImageIcon(url); //$NON-NLS-1$
			  }
			  else {
//				  try {
//					url = new URL(AppCodeBase.getCodeBase(),"icons/"+s); //$NON-NLS-1$
//					  imageIcon = new ImageIcon(url);
//				} catch (MalformedURLException e) {
					log.severe("No encuentro el icono:"+s); //$NON-NLS-1$
//				}
			  }
		  }
	  }

	  if (imageIcon == null) {
		  log.severe("No encuentro el icono:" + s); //$NON-NLS-1$
		  imageIcon = def;
	  }else{
		  images.put(s, imageIcon);
	  }
	  return imageIcon;
  }

  /** M�todo para leer las im�genes de la documentaci�n */
  public static ImageIcon loadDocIcon(String s) {
  	ImageIcon imageIcon = null;
	  URL url = ImageLoader.class.getResource("/doc/icons/" + s); //$NON-NLS-1$
	  if (url != null) {
		  imageIcon = new ImageIcon(url);
	  }
	  else {
		  url = ImageLoader.class.getResource("/config/doc/icons/" + s); //$NON-NLS-1$
		  if (url != null) {
			  imageIcon = new ImageIcon(url);
		  }
		  else {
		  	log.severe("No encuentro el icono: " + s); //$NON-NLS-1$
		  }
	  }

	  if (imageIcon == null) {
		  log.severe("No encuentro el icono:" + s); //$NON-NLS-1$
		  imageIcon = def;
	  }
	  return imageIcon;
  }
}
