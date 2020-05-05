package ui.custom;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.Language;
import config.Property;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class KeyImporter extends JDialog {
	private static final long serialVersionUID = -426831946642003239L;
	
	public static final int IMPORT_OPTION = 1;
	public static final int CANCEL_OPTION = 0;
	public static final int CLOSED_OPTION = -1;
	
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	
	private int result = CLOSED_OPTION;

	private Language lang = null;
	
	public KeyImporter() {
		lang = Property.getLanguagePack();
		
		setTitle(lang.kiTitle);
		setBounds(100, 100, 500, 340);
		this.setMinimumSize(new Dimension(500,340));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblDoYouReally = new JLabel(lang.kiMainContent);
		
		JTextPane txtpnAsd = new JTextPane();
		txtpnAsd.setText(lang.kiSubContent);
		txtpnAsd.setEditable(false);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnAsd, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
						.addComponent(lblDoYouReally, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDoYouReally, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnAsd, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton(lang.kiImport);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton(lang.btnCancel);
				buttonPane.add(cancelButton);
			}
		}
		
		okButton.addActionListener(e->{
			result = IMPORT_OPTION;
			setVisible(false);
		});
		
		cancelButton.addActionListener(e->{
			result = CANCEL_OPTION;
			setVisible(false);
		});
		
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}
	
	public int showDialog(JFrame frame) {
		result = CLOSED_OPTION;
		setLocationRelativeTo(frame);

		setVisible(true);
		return result;
	}
	
}
