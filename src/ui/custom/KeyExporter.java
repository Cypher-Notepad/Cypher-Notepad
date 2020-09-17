package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import config.Language;
import config.Property;
import file.FileManager;
import ui.NotepadUI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class KeyExporter extends JDialog {
	
	private static final long serialVersionUID = -7169483905654387775L;
	
	private static final int SHORT_HEIGHT = 390;
	private static final int LONG_HEIGHT = 450;
	
	public static final int EXPORT_OPTION = 1;
	public static final int CANCEL_OPTION = 0;
	public static final int CLOSED_OPTION = -1;
	
	private final JPanel contentPanel = new JPanel();
	private JButton btnSave, btnCopy, btnCancel;
	private JTextArea txtKey;

	private Clipboard clipboard;
	private JFileChooser fc;
	private CopyThread copyThread;
	
	private int result = CLOSED_OPTION;
	private JCheckBox chckbxDelete;
	
	private NotepadUI frame = null;

	private Language lang;
	
	public KeyExporter() {
		lang = Property.getLanguagePack();
		
		setBounds(100, 100, 520, LONG_HEIGHT);
		
		this.setMinimumSize(new Dimension(520,370));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle(lang.keTitle);

		JLabel lblKey = new JLabel(lang.keKey + " : ");

		txtKey = new JTextArea();
		txtKey.setText("");
		txtKey.setEditable(false);
		txtKey.setLineWrap(true);
		txtKey.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(txtKey);

		JTextPane txtpnWarn = new JTextPane();
		txtpnWarn.setText(lang.keWarn);
		txtpnWarn.setFont(txtpnWarn.getFont().deriveFont(12f));
		txtpnWarn.setBackground(new Color(0xF0F0F0));
		txtpnWarn.setFocusable(false);
		
		JTextPane txtpnWarn2 = new JTextPane();
		txtpnWarn2.setText(lang.keWarn2);
		txtpnWarn2.setFont(txtpnWarn.getFont().deriveFont(12f));
		txtpnWarn2.setBackground(new Color(0xF0F0F0));
		txtpnWarn2.setFocusable(false);
		
		chckbxDelete = new JCheckBox(lang.keDeleteKey);
		
		chckbxDelete.addItemListener(e -> {
			if(chckbxDelete.isSelected()) {
				txtKey.setBackground(new Color(0xE8E8E8));
				txtKey.setForeground(txtKey.getDisabledTextColor());
				txtKey.setText("N/A for security reason.");
				btnCopy.setEnabled(false);
				btnCopy.setForeground(txtKey.getDisabledTextColor());
				txtpnWarn2.setVisible(true);
				this.setSize(this.getWidth(), LONG_HEIGHT);
			} else {
				txtKey.setBackground(Color.WHITE);
				txtKey.setForeground(Color.BLACK);
				txtKey.setText(FileManager.getInstance().getCurKey());
				btnCopy.setEnabled(true);
				btnCopy.setForeground(Color.BLACK);
				txtpnWarn2.setVisible(false);
				this.setSize(this.getWidth(), SHORT_HEIGHT);
			}
		});

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtpnWarn2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
						.addComponent(txtpnWarn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
						.addComponent(lblKey, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
						.addComponent(chckbxDelete, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
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
					.addComponent(txtpnWarn, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxDelete, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnWarn2, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnSave = new JButton(lang.keSave);
			{
				btnCopy = new JButton(lang.keCopy);
				getRootPane().setDefaultButton(btnCopy);
			}
			{
				btnCancel = new JButton(lang.btnCancel);
			}
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			buttonPane.add(btnSave);
			buttonPane.add(btnCopy);
			buttonPane.add(btnCancel);

		}

		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("PEM File (*.pem)", "pem"));
		fc.setAcceptAllFileFilterUsed(false);
		btnSave.addActionListener(e -> {
			String filePath = null;
			boolean selectAgain = true;
			
			while(selectAgain) {
				int userSelection = fc.showSaveDialog(this);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					filePath = fc.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".pem")) {
						filePath += ".pem";
					}
					
					File file = new File(filePath);
					if(!((new File(filePath).exists()) && (frame.showOverwritingDialog(file.getName())!=1))) {
						if(chckbxDelete.isSelected()) {
							FileManager.getInstance().deleteCurrentKey();
							frame.saveSavedMemo(true);
						}
						
						FileManager.getInstance().exportKey(filePath, FileManager.getInstance().getCurKey());
						selectAgain = false;
						result = EXPORT_OPTION;
						
						setVisible(false);
					}
				} else {
					selectAgain = false;
				}
			}
		});

		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		copyThread = null;
		btnCopy.addActionListener(e -> {
			StringSelection content = new StringSelection(txtKey.getText());
			clipboard.setContents(content, null);
			if(copyThread != null) {
				copyThread.extendThread();
			} else {
				copyThread = new CopyThread();
				copyThread.start();
			}
			

		});
		
		btnCancel.addActionListener(e->{
			result = CANCEL_OPTION;
			setVisible(false);
		});

		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}

	public int showDialog(NotepadUI frame) {
		result = CLOSED_OPTION;
		this.frame = frame;
		
		if(FileManager.getInstance().isTemporary()) {
			chckbxDelete.setEnabled(false);
			chckbxDelete.setSelected(true);
		} else {
			chckbxDelete.setSelected(true);
		}
		
		String keyFileName = frame.directory + FileManager.SEPARATOR + frame.fileName;
		int fileExtensionIdx = keyFileName.lastIndexOf('.');
		if(fileExtensionIdx == -1) {
			fileExtensionIdx = keyFileName.length();
		}
		keyFileName = keyFileName.substring(0, fileExtensionIdx) + ".pem";
		fc.setSelectedFile(new File(keyFileName));

		setLocationRelativeTo(frame);
		setVisible(true);
		
		return result;
	}

	private class CopyThread extends Thread {
		private int sleepCnt = 3;

		public void run() {
			btnCopy.setText(lang.keCopied);
			btnCopy.setForeground(Color.red);

			for (; sleepCnt > 0; sleepCnt--) {
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (btnCopy.isShowing()) {
				btnCopy.setText(lang.keCopy);
				if(btnCopy.isEnabled()) {
					btnCopy.setForeground(Color.black);	
				} else {
					btnCopy.setForeground(txtKey.getDisabledTextColor());
				}
			}
			// Remove reference.
			copyThread = null;
		}

		public void extendThread() {
			sleepCnt = 3;
		}
	}
}