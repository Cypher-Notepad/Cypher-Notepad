package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class KeyExporter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	private JTextArea txtKey;
	private JFileChooser fc;

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
	public KeyExporter() {
		setBounds(100, 100, 520, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Export key");
		
		JLabel lblKey = new JLabel("Key : ");
		
		txtKey = new JTextArea();
		txtKey.setText("");
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
		JButton btnNewButton;
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			btnNewButton = new JButton("Save");
			{
				okButton = new JButton("Copy");
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
			}
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			buttonPane.add(btnNewButton);
			buttonPane.add(okButton);
			buttonPane.add(cancelButton);
			
			
		}
		
		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("pem File (*.pem)", "pem"));
		fc.setAcceptAllFileFilterUsed(false);
		btnNewButton.addActionListener(e->{
			String filePath = null;
			//Filemanger에서 pem파일 저장하는 거 잘연결 시키기
			
			boolean rtn = false;
			int userSelection = fc.showSaveDialog(this);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				filePath = fc.getSelectedFile().getAbsolutePath();
				if (!filePath.endsWith(".pem")) {
					filePath += ".pem";
				}
				//directory = fc.getCurrentDirectory();
				//saveMemo();
				FileManager.getInstance().exportKey(filePath, txtKey.getText());
				rtn = true;
			} else if (userSelection == JFileChooser.CANCEL_OPTION) {
				//do nothing.
			} else if (userSelection == JFileChooser.ERROR_OPTION) {
				//do nothing.
			}
			//return rtn;
		});
		
	}
	
	public void showDialog(NotepadUI frame) {
		txtKey.setText(FileManager.getInstance().getCurKey());	
		//fc.setCurrentDirectory(new File(frame.directory + FileManager.SEPARATOR + frame.fileName));
		String keyFileName = frame.directory + FileManager.SEPARATOR + frame.fileName;
		keyFileName = keyFileName.substring(0, keyFileName.lastIndexOf('.')) + ".pem";
		fc.setSelectedFile(new File(keyFileName));
		
		
		setVisible(true);
	}
}