package com.jrsolutions.framework.core.utils.constrainedpanel;

/**
 * Clase que contiene las variables que se utilizan para configurar los paneles 
 * <code>VerticalPanel</code> y <code>HorizontalPanel</code>
 *
 * @author Luis Manuel Parrondo Merino, UF515621
 * @since 1.0
 * @version $Revision: 1.1 $
 * <br/>Revisi�n javadoc: 1.1
 * <br/>Fecha de la �ltima revisi�n: $Date: 2009/02/09 15:28:03 $
 */
public class ConstraintsDefinition {

	private boolean withTitle = true;
	private boolean expandable = true;
	private boolean scrollable = false;
	private boolean minSizeSet = false;
	private boolean minSizePreferred = true;
	private boolean minSizeFixed = false;
	private int minSizeFixedHeight = 0;
	private boolean normalSizePreferred = true;
	private boolean normalSizeFixed = false;
	private boolean normalSizeRelative = false;
	private int normalSizeFixedHeight = 0;
	private double normalSizeRelativeHeight = 50d;
	private boolean maxSizeSet = false;
	private boolean maxSizePreferred = true;
	private boolean maxSizeFixed = false;
	private int maxSizeFixedHeight = 0;
	
	/** Indica si hay que mostrar la barra de t�tulo */
	public boolean isWithTitle() { return withTitle; }
	/** Establece si hay que mostrar la barra de t�tulo */
	public void setWithTitle(boolean withTitle) { this.withTitle = withTitle; }
	
	/** Indica si la barra de t�tulo es expandible */
	public boolean isExpandable() { return expandable; }
	/** Establece si la barra de t�tulo es expandible */
	public void setExpandable(boolean expandable) { this.expandable = expandable; }
	
	/** Indica si hay que poner el scroll */
	public boolean isScrollable() { return scrollable; }
	/** Establece si hay que poner el scroll */
	public void setScrollable(boolean scrollable) { this.scrollable = scrollable; }
	
	/** Indica si se ha establecido el tama�o m�nimo */
	public boolean isMinSizeSet() { return minSizeSet; }
	/** Establece si se ha establecido el tama�o m�nimo */
	public void setMinSizeSet(boolean minSizeSet) { this.minSizeSet = minSizeSet; }
	
	/** Indica si el tama�o m�nimo es el preferido */
	public boolean isMinSizePreferred() { return minSizePreferred; }
	/** Establece si el tama�o m�nimo es el preferido */
	public void setMinSizePreferred(boolean minSizePreferred) { this.minSizePreferred = minSizePreferred; }
	
	/** Indica si el tama�o m�nimo es absoluto */
	public boolean isMinSizeFixed() { return minSizeFixed; }
	/** Establece si el tama�o m�nimo es absoluto */
	public void setMinSizeFixed(boolean minSizeFixed) { this.minSizeFixed = minSizeFixed; }
	
	/** Devuelve el n�mero de p�xeles si el tama�o m�nimo es absoluto */
	public int getMinSizeFixedHeight() { return minSizeFixedHeight; }
	/** Establece el n�mero de p�xeles si el tama�o m�nimo es absoluto */
	public void setMinSizeFixedHeight(int minSizeFixedHeight) { this.minSizeFixedHeight = minSizeFixedHeight; }
	
	/** Indica si el tama�o normal es el preferido */
	public boolean isNormalSizePreferred() { return normalSizePreferred; }
	/** Establece si el tama�o normal es el preferido */
	public void setNormalSizePreferred(boolean normalSizePreferred) { this.normalSizePreferred = normalSizePreferred; }
	
	/** Indica si el tama�o normal es absoluto */
	public boolean isNormalSizeFixed() { return normalSizeFixed; }
	/** Establece si el tama�o normal es absoluto */
	public void setNormalSizeFixed(boolean normalSizeFixed) { this.normalSizeFixed = normalSizeFixed; }
	
	/** Devuelve el n�mero de p�xeles si el tama�o normal es absoluto */
	public int getNormalSizeFixedHeight() { return normalSizeFixedHeight; }
	/** Establece el n�mero de p�xeles si el tama�o normal es absoluto */
	public void setNormalSizeFixedHeight(int normalSizeFixedHeight) { this.normalSizeFixedHeight = normalSizeFixedHeight; }
	
	/** Indica si el tama�o normal es relativo */
	public boolean isNormalSizeRelative() { return normalSizeRelative; }
	/** Establece si el tama�o normal es relativo */
	public void setNormalSizeRelative(boolean normalSizeRelative) { this.normalSizeRelative = normalSizeRelative; }
	
	/** Devuelve el porcentaje si el tama�o normal es relativo */
	public double getNormalSizeRelativeHeight() { return normalSizeRelativeHeight; }
	/** Establece el porcentaje si el tama�o normal es relativo */
	public void setNormalSizeRelativeHeight(double normalSizeRelativeHeight) { this.normalSizeRelativeHeight = normalSizeRelativeHeight; }
	
	/** Indica si se ha establecido el tama�o m�ximo */
	public boolean isMaxSizeSet() { return maxSizeSet; }
	/** Establece si se ha establecido el tama�o m�ximo */
	public void setMaxSizeSet(boolean maxSizeSet) { this.maxSizeSet = maxSizeSet; }
	
	/** Indica si el tama�o m�ximo es el preferido */
	public boolean isMaxSizePreferred() { return maxSizePreferred; }
	/** Establece si el tama�o m�ximo es el preferido */
	public void setMaxSizePreferred(boolean maxSizePreferred) { this.maxSizePreferred = maxSizePreferred; }
	
	/** Indica si el tama�o m�ximo es absoluto */
	public boolean isMaxSizeFixed() { return maxSizeFixed; }
	/** Establece si el tama�o m�ximo es absoluto */
	public void setMaxSizeFixed(boolean maxSizeFixed) { this.maxSizeFixed = maxSizeFixed; }
	
	/** Devuelve el n�mero de p�xeles si el tama�o m�ximo es absoluto */
	public int getMaxSizeFixedHeight() { return maxSizeFixedHeight; }
	/** Establece el n�mero de p�xeles si el tama�o maximo es absoluto */
	public void setMaxSizeFixedHeight(int maxSizeFixedHeight) { this.maxSizeFixedHeight = maxSizeFixedHeight; }
	
}
