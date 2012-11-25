package com.jrsolutions.framework.generator.internacionalizer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;


public class ComponentsInternationalizer {
	
	public static void cambiaLengua(String ventana, JComponent jp) {
		 GestorMultilingualidad gestor = new GestorMultilingualidad();
		
//		if (jp instanceof TablePanel)
//		{
//			JTableHeader j = null;
//			if (((TablePanel) jp).getCenterTable() != null)
//			{
//				j= ((TablePanel) jp).getCenterTable().getTableHeader();
//				for (int pp = 0; pp < j.getColumnModel().getColumnCount(); ++pp)
//				{
//					j.getColumnModel().getColumn(pp).setHeaderValue(gestor.buscar(ventana, (String)j.getColumnModel().getColumn(pp).getHeaderValue()));	
//				}
//			}
//			if (((TablePanel) jp).getLeftTable() != null)
//			{
//				j= ((TablePanel) jp).getLeftTable().getTableHeader();
//			for (int pp = 0; pp < j.getColumnModel().getColumnCount(); ++pp)
//				{	j.getColumnModel().getColumn(pp).setHeaderValue(gestor.buscar(ventana, (String)j.getColumnModel().getColumn(pp).getHeaderValue()));
//				}
//			}
//			if (((TablePanel) jp).getRightTable() != null)
//			{
//				j= ((TablePanel) jp).getRightTable().getTableHeader();
//				for (int pp = 0; pp < j.getColumnModel().getColumnCount(); ++pp)
//				{	j.getColumnModel().getColumn(pp).setHeaderValue(gestor.buscar(ventana, (String)j.getColumnModel().getColumn(pp).getHeaderValue()));
//				}
//			}
//			
//		}
		
		if ((jp.getName() != null) && (jp instanceof JComponent)) {
			gestor.buscar(ventana, jp.getName());
			
		}
		for (int k = 0; k < jp.getComponentCount(); ++k) {
			try {

				if ((jp.getComponent(k) instanceof JPanel) || ((jp.getComponent(k) instanceof JRootPane)) || ((jp.getComponent(k) instanceof JLayeredPane))) {
					cambiaLengua(ventana, (JComponent) jp.getComponent(k));
			}
				//JSCROLLPANE Y JVIEWPORT
				if (jp.getComponent(k) instanceof JViewport)
				{
					for (int i = 0; i < ((JViewport)jp.getComponent(k)).getComponentCount(); ++i)
					{
						cambiaLengua(ventana, (JComponent)((JViewport)jp.getComponent(k)).getComponent(i));
					}
				}
				if (jp.getComponent(k) instanceof JScrollPane) {
					if(((JScrollPane)jp.getComponent(k)).getComponentCount()>0)
					{
						cambiaLengua(ventana,(JViewport)((JScrollPane)jp.getComponent(k)).getComponent(0) );
					}
					if (((JScrollPane) jp.getComponent(k)).getToolTipText() != null
							&& ((JScrollPane) jp.getComponent(k)).getToolTipText()
									.equalsIgnoreCase("") == false 	&& ((JScrollPane) jp.getComponent(k)).getToolTipText().indexOf("$") !=0) {
						gestor.buscar(((JScrollPane) jp.getComponent(k))
								.getToolTipText());
						if (ventana != null
								&& ventana.equalsIgnoreCase("") == false && ventana.indexOf("$") != 0) {
							gestor.buscar(ventana,
									 ((JScrollPane) jp.getComponent(k)).getToolTipText());
						}

					}
				}
// LABEL
				if (jp.getComponent(k) instanceof JLabel) {
					if (((JLabel) jp.getComponent(k)).getText() != null) {
						((JLabel) jp.getComponent(k)).setText(
								gestor.buscar(ventana, ((JLabel) jp.getComponent(k))
										.getText()));
					}

					if (((JLabel) jp.getComponent(k)).getToolTipText() != null) {
						((JLabel) jp.getComponent(k)).setToolTipText(
								gestor.buscar(ventana, ((JLabel) jp.getComponent(k))
										.getToolTipText()));
					}
				}
				
//CHECKBOX
				if (jp.getComponent(k) instanceof JCheckBox) {
					if (((JCheckBox) (jp.getComponent(k))).getToolTipText() != null) {

						((JCheckBox) (jp.getComponent(k)))
								.setToolTipText(gestor.buscar(ventana,
										((JCheckBox) (jp.getComponent(k)))
												.getToolTipText()));
					}
				}

// COMBO BOXES
				if (jp.getComponent(k) instanceof JComboBox) {
					if (((JComboBox) (jp.getComponent(k))).getToolTipText() != null) {

						((JComboBox) (jp.getComponent(k)))
								.setToolTipText(gestor.buscar(ventana,
										((JComboBox) (jp.getComponent(k)))
												.getToolTipText()));
					}
				}
// AREA DE TEXTO
				if (jp.getComponent(k) instanceof JTextArea) {
					if (((JTextArea) (jp.getComponent(k))).getToolTipText() != null) {

						((JTextArea) (jp.getComponent(k)))
								.setToolTipText(gestor.buscar(ventana,
										((JTextArea
												) (jp.getComponent(k)))
												.getToolTipText()));
					}
				}
				
				
				if (jp.getComponent(k) instanceof JTextPane) {
					if (((JTextPane) (jp.getComponent(k))).getToolTipText() != null) {

						((JTextPane) (jp.getComponent(k)))
								.setToolTipText(gestor.buscar(ventana,
										((JTextPane) (jp.getComponent(k)))
												.getToolTipText()));
					}
				}
// AREA HTML
//				if (jp.getComponent(k) instanceof JREditorHTML) {
//					if (((JREditorHTML) (jp.getComponent(k))).getToolTipText() != null) {
//
//						((JREditorHTML) (jp.getComponent(k)))
//								.setToolTipText(gestor.buscar(ventana,
//										((JREditorHTML) (jp.getComponent(k)))
//												.getToolTipText()));
//					}
//				}
// CAMPOS DE TEXTO
				if (jp.getComponent(k) instanceof JTextField) {
					if (((JTextField) (jp.getComponent(k))).getToolTipText() != null) {

						((JTextField) (jp.getComponent(k)))
								.setToolTipText(gestor.buscar(ventana,
										((JTextField) (jp.getComponent(k)))
												.getToolTipText()));
					}
				}
//LISTAS
				if (jp.getComponent(k) instanceof JList) {
					if (((JList) (jp.getComponent(k))).getToolTipText() != null) {

						((JList) (jp.getComponent(k))).setToolTipText(gestor
								.buscar(ventana, ((JList) (jp.getComponent(k)))
										.getToolTipText()));
					}
				}
//�RBOL POPUP
//				if (jp.getComponent(k) instanceof TreeComboSelector) {
//					if (((TreeComboSelector) (jp.getComponent(k)))
//							.getToolTipText() != null) {
//
//						((TreeComboSelector) (jp.getComponent(k)))
//								.setToolTipText(gestor.buscar(ventana,
//										((TreeComboSelector) (jp
//												.getComponent(k)))
//												.getToolTipText()));
//					}
//				}
//SPINNERS
				if (jp.getComponent(k) instanceof JSpinner) {
					if (((JSpinner) (jp.getComponent(k))).getToolTipText() != null) {

						((JSpinner) (jp.getComponent(k))).setToolTipText(gestor
								.buscar(ventana, ((JSpinner) (jp
										.getComponent(k))).getToolTipText()));
					}
				}

				if (jp.getComponent(k) instanceof JButton) {
//HYPERLABEL
//					if (jp.getComponent(k) instanceof TransparentLabeledButton) {
//						if (((TransparentLabeledButton) (jp.getComponent(k)))
//								.getLabel() != null) {
//
//							((TransparentLabeledButton) (jp.getComponent(k)))
//									.setLabel(gestor.buscar(ventana,
//											((TransparentLabeledButton) (jp
//													.getComponent(k)))
//													.getLabel()));
//
//						}
//						if (((TransparentLabeledButton) (jp.getComponent(k)))
//								.getToolTipText() != null) {
//
//							((TransparentLabeledButton) (jp.getComponent(k)))
//									.setToolTipText(gestor.buscar(ventana,
//											((TransparentLabeledButton) (jp
//													.getComponent(k)))
//													.getToolTipText()));
//
//						}
//					}
////					OTRO BOT�N QUE NO SEA EL RADIO
//					else { 
						if (((JButton) (jp.getComponent(k))).getText() != null) {

							((JButton) (jp.getComponent(k))).setText(gestor
									.buscar(ventana, ((JButton) (jp
											.getComponent(k))).getText()));

						}
						if (((JButton) (jp.getComponent(k))).getToolTipText() != null) {

							((JButton) (jp.getComponent(k)))
									.setToolTipText(gestor.buscar(ventana,
											((JButton) (jp.getComponent(k)))
													.getToolTipText()));
						}
					}
//				}
				//RADIO BUTTON
				if (jp.getComponent(k) instanceof JRadioButton) {
					if (((JRadioButton) (jp.getComponent(k))).getText() != null) {

						((JRadioButton) (jp.getComponent(k))).setText(gestor
								.buscar(ventana, ((JRadioButton) (jp
										.getComponent(k))).getText()));

					}
					if (((JRadioButton) (jp.getComponent(k))).getToolTipText() != null) {

						((JRadioButton) (jp.getComponent(k)))
								.setToolTipText(gestor.buscar(ventana,
										((JRadioButton) (jp.getComponent(k)))
												.getToolTipText()));
					}
				}
				//RTF
				if (jp.getComponent(k) instanceof JEditorPane) {
					if (((JEditorPane) (jp.getComponent(k))).getToolTipText() != null) {

						((JEditorPane) (jp.getComponent(k))).setToolTipText(gestor
								.buscar(ventana, ((JEditorPane) (jp
										.getComponent(k))).getToolTipText()));
					}
				}
				
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
				return;
			}

		}
	}
}
