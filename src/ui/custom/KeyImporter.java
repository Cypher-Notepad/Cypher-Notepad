package ui.custom;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class KeyImporter extends JDialog {

	public static final int IMPORT_OPTION = 1;
	public static final int CANCEL_OPTION = 0;
	
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	
	
	private int result = CANCEL_OPTION;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KeyImporter dialog = new KeyImporter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public KeyImporter() {
		setTitle("Import key");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblDoYouReally = new JLabel("Do you really want to import the key?");
		
		JTextPane txtpnAsd = new JTextPane();
		txtpnAsd.setText("This process will store your key internally.\r\n\r\nWhen you open this file next time : \r\n  \u2022  Exported keyfile isn't needed anymore.\r\n  \u2022  The file will be decrypted automatically.\r\n\r\nWhen you invalidate keyfile on settings :\r\n  \u2022  The key must be saved before key invalidation.\r\n  \u2022  Or the file won't be able to be decrypted.");
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
				okButton = new JButton("Import the key...\r\n");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		okButton.addActionListener(e->{
			result = IMPORT_OPTION;
			setVisible(false);
		});
		
		cancelButton.addActionListener(e->{
			setVisible(false);
		});
		
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}
	
	public int showDialog(JFrame frame) {
		result = CANCEL_OPTION;
		setLocationRelativeTo(frame);
		
		setVisible(true);
		return result;
	}
	
}
