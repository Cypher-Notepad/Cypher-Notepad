package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;

public class KeyImporter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KeyExporter dialog = new KeyExporter();
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
		setBounds(100, 100, 520, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Export key");
		
		JLabel lblKey = new JLabel("Key : ");
		
		JTextArea txtKey = new JTextArea();
		txtKey.setText("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALPNwzWNbVH8kV8CHq8x1jIVn7aX6MAPWIV+NlWnH8ATOHE2nAcjc9Qw8CFzqnchbXyPD2HgCujLCY7hpzAkXLD116t2Cv3FRNPpcOsXxXjF8iuKG49RJfWcnWjBavN2cIw8+0JsJeskGGqiaov1/X05n9Dxu2+3tPF6NbgCtOIZAgMBAAECgYAR0b+Z7BU8fdpuXwhpdnfy6L+2WeHAPwUX3cVGwdRctcrvNWlLL9FH4z25Ivxu6AowwQDWQ1zxa0XcOjLWi5P/7wcuKSltJMFWfhhSrc1l1fwusPBWg0NSTo+c6VsjVrJeiWL645pe+jR0RfLVcZ4DAPY9uH5UpEpXtm6bJ4vB8QJBAOeL+MT6KkgY3HLOq5zlXlZBga4uujeqAkXEJO6eRXiFb+D4jO/Om55zqhQn5slhM00keayYDi5uajZv17eVdGUCQQDGyuHvyomKFLO7QzAkg6ZsTLhR0GV9/7E0o8KfXHAd71GodIdEt2GhVdMasQfuKMdNv4qPMmSL9NIzj5QjRxmlAkEAqRJNzhcVNJvirHo4WVIqdjVS6cr48phTHHpCtXIgLAbTUKRs1NY6T5MJh7ozDKzK9vNBXUOSZ1j8eU9lZonc+QJBAMRSBINdkCsverhLCDZnVWnK8pTJrBGc/JIxz7i1/3twp0Inopb4S5CbQ5oujthiqUFdEieM6sNVCmolN3UHeIkCQAv50JG7zDq3xe1+wB2xB7NJhKD5Q4NqW34/twkvsFekUyFij8tbAxl0ebs390QSUix2PUWNUMYBBIBhanr2YGk=");
		txtKey.setEditable(false);
		txtKey.setLineWrap(true);
		txtKey.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(txtKey);

		
		JTextPane txtpnThisthisth = new JTextPane();
		txtpnThisthisth.setText("1. 암호키를 추출하여 키파일에 저장하면 파일무효화에 영향받지 않습니다.\n" + 
				"2. 키파일을 갖고 있다면 암호화된 파일을 다른 기기에서도 복호화 할 수 있습니다.\n" + 
				"3. 파일을 저장한 후 키파일을 생성해야 올바른 키파일이 생성됩니다.");
		txtpnThisthisth.setFont(txtpnThisthisth.getFont().deriveFont(12f));
		txtpnThisthisth.setBackground(new Color(0xF0F0F0));
		txtpnThisthisth.setFocusable(false);
		
		//txtpnThisthisth.setFont(new Font("Consolas", Font.PLAIN, 55));
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnThisthisth, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
						.addComponent(lblKey, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
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
					.addComponent(txtpnThisthisth, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnNewButton = new JButton("Import");
			{
				okButton = new JButton("Open");
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}
	
	public void showDialog() {
	
		setVisible(true);
	}
}
