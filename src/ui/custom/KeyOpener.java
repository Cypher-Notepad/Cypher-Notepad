package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.Property;
import file.FileManager;
import ui.NotepadUI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

public class KeyOpener extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnSave, btnCancel;
	private JTextArea txtKey;

	private JFileChooser fc;
	private JLabel lblNewLabel;

	public KeyOpener() {
		setBounds(100, 100, 520, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Enter the key");

		JLabel lblKey = new JLabel("Enter the key to use for decryption : ");

		txtKey = new JTextArea();
		txtKey.setText("");
		txtKey.setEditable(false);
		txtKey.setLineWrap(true);
		txtKey.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(txtKey);

		JTextPane txtpnWarn = new JTextPane();
		txtpnWarn.setText("sample text");
		txtpnWarn.setFont(txtpnWarn.getFont().deriveFont(12f));
		txtpnWarn.setBackground(new Color(0xF0F0F0));
		txtpnWarn.setFocusable(false);

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnWarn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING)
						.addComponent(lblKey, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblKey)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnWarn, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnSave = new JButton("Save");
			{
				btnCancel = new JButton("Cancel");
				btnCancel.setActionCommand("Cancel");
			}
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			buttonPane.add(btnSave);
			
			lblNewLabel = new JLabel("    ->    ");
			buttonPane.add(lblNewLabel);
			buttonPane.add(btnCancel);

		}

		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("pem File (*.pem)", "pem"));
		fc.setAcceptAllFileFilterUsed(false);
		btnSave.addActionListener(e -> {
			String filePath = null;
			int userSelection = fc.showSaveDialog(this);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				filePath = fc.getSelectedFile().getAbsolutePath();
				if (!filePath.endsWith(".pem")) {
					filePath += ".pem";
				}
				FileManager.getInstance().exportKey(filePath, txtKey.getText());
				setVisible(false);
			} else if (userSelection == JFileChooser.CANCEL_OPTION) {/* do nothing.*/}
			else if (userSelection == JFileChooser.ERROR_OPTION) {/* do nothing.*/}
		});

		btnCancel.addActionListener(e->{
			setVisible(false);
		});

	}

	public void showDialog(NotepadUI frame) {

		/*
		txtKey.setText(FileManager.getInstance().getCurKey());
		String keyFileName = frame.directory + FileManager.SEPARATOR + frame.fileName;
		keyFileName = keyFileName.substring(0, keyFileName.lastIndexOf('.')) + ".pem";
		fc.setSelectedFile(new File(keyFileName));
		 */
		
		setVisible(true);
	}
}