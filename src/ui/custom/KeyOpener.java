package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.Language;
import config.Property;
import file.FileDrop;
import file.FileManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

public class KeyOpener extends JDialog {
	
	private static final long serialVersionUID = 949855548485976167L;
	
	public static final int DECRYPT_OPTION = 1;
	public static final int CANCEL_OPTION = 0;
	
	private final JPanel contentPanel = new JPanel();
	private JButton btnOpen, btnDecrypt;
	private JTextArea txtKey;

	private JFileChooser fc;
	private JLabel lblArrow;
	
	private int result = CANCEL_OPTION;
	
	private Language lang = null;
	
	public KeyOpener() {
		lang = Property.getLanguagePack();
		
		setBounds(100, 100, 520, 300);
		this.setMinimumSize(new Dimension(520,300));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setFocusable(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle(lang.koTitle);

		JLabel lblKey = new JLabel(lang.koMainContent);

		txtKey = new JTextArea();
		txtKey.setEditable(true);
		txtKey.setLineWrap(true);
		txtKey.setWrapStyleWord(true);
		txtKey.setBackground(new Color(0xE8E8E8));
		txtKey.setForeground(txtKey.getDisabledTextColor());
		txtKey.setText(lang.txtBoxDrag);
		txtKey.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				erasePlaceholder();
			}

			@Override
			public void focusLost(FocusEvent e) {
				setPlaceholder();
			}
			
			public void setPlaceholder() {
				txtKey.setBackground(new Color(0xE8E8E8));
				txtKey.setForeground(txtKey.getDisabledTextColor());
				if(txtKey.getText().equals("")) {
					txtKey.setText(lang.txtBoxDrag);
				}
			}

			public void erasePlaceholder() {
				txtKey.setBackground(Color.WHITE);
				txtKey.setForeground(Color.BLACK);
				txtKey.setText("");	
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(txtKey);

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblKey, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblKey)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(95, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnOpen = new JButton(lang.koOpen);
			btnDecrypt = new JButton(lang.koDecrypt);
			lblArrow = new JLabel("");
			
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			buttonPane.add(btnOpen);
			buttonPane.add(lblArrow);
			buttonPane.add(btnDecrypt);
		}
		
		Thread tr_arrow = new Thread() {
			private int count = 12;
			int i, j;
			private String lblStr = "\u27A4            ";

			public void run() {
				try {
					lblArrow.setText(lblStr);
					while (true) {
						if (lblArrow.isShowing()) {
							for (i = 0; i < count; i++) {
								lblStr = "";
								for (j = 0; j < i; j++) {
									lblStr += " ";
								}
								lblStr += "\u27A4";
								for (j = 0; j < count - i; j++) {
									lblStr += " ";
								}
								Thread.sleep(70);
								lblArrow.setText(lblStr);
							}
							Thread.sleep(350);
						} else {
							Thread.sleep(1000);
						}
					}
				} catch (Exception e) {/* do nothing */}
			}
		};
		tr_arrow.start();
		
		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("pem File (*.pem)", "pem"));
		fc.setAcceptAllFileFilterUsed(true);
		btnOpen.addActionListener(e -> {
			boolean toBeSelected = true;
			while(toBeSelected) {
				int response = fc.showOpenDialog(this);
				if (response == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile().exists()) {						
						boolean isLoaded = loadPEMFile(fc.getSelectedFile());
						if(isLoaded) {
							toBeSelected = false;
						}
					} else {
						toBeSelected = true;
						JOptionPane.showMessageDialog(this,
								lang.fileNotExist, lang.cypherNotepad,
								JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					toBeSelected = false;
				}
			}
		});

		new FileDrop(txtKey, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				@SuppressWarnings("unused")
				boolean isDraged = loadPEMFile(files[0]);
			}
		});

		btnDecrypt.addActionListener(e->{
			result = DECRYPT_OPTION;
			setVisible(false);
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				e.getComponent().requestFocusInWindow();
			}
		});
		
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}

	private boolean loadPEMFile(File pem) {
		boolean isSucceed = false;
		String loadedKey = FileManager.getInstance().loadPEMFile(pem);
		if (loadedKey != null) {
			txtKey.setText(loadedKey);
			btnDecrypt.requestFocusInWindow();
			isSucceed = true;
		} else {
			JOptionPane.showMessageDialog(this,
					lang.fileFormat_PEM, lang.cypherNotepad,
					JOptionPane.ERROR_MESSAGE);
		}
		
		return isSucceed;
	}
	
	public int showDialog(JFrame frame) {
		result = CANCEL_OPTION;
		setLocationRelativeTo(frame);

		setVisible(true);
		return result;
	}
	
	public String getEnteredKey() {
		if(result == DECRYPT_OPTION) {
			return txtKey.getText();
		}
		return null;
	}
}