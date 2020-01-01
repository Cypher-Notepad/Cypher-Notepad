package ui.custom;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import config.Language;
import config.Property;

public class KReplacer extends JDialog {

	private static final long serialVersionUID = 8578949730681943840L;
	private final JPanel contentPanel = new JPanel();
	private JButton findButton;
	private JButton replaceButton;
	private JTextField txtToFind;
	private String strToFind;
	private String strToReplace;
	private JTextArea textArea;
	private int findIdx;
	private int startToFind;
	private String direction;
	ButtonGroup bgDirection;
	private JTextField txtToReplace;
	
	private Language lang;
	
	public KReplacer(JTextArea textArea){
		lang = Property.getLanguagePack();
		
		this.textArea = textArea;
		findIdx = -1;
		strToFind = "";
		strToReplace = "";
		setTitle(lang.kreReplce);
		setBounds(100, 100, 620, 282);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			findButton = new JButton(lang.kfiFindNxt);
			findButton.addActionListener(e->find());
			getRootPane().setDefaultButton(findButton);
		}
		{
			replaceButton = new JButton(lang.kreReplce);
			replaceButton.addActionListener(e->replace());
		}
		
		JLabel lblNewLabel = new JLabel(lang.kfiFind);
		
		txtToFind = new JTextField();
		txtToFind.setColumns(10);
		txtToFind.getDocument().addDocumentListener(new DocumentListener() {
			private void update() {
				strToFind = txtToFind.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				update();
			}
			
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, lang.kfiDir, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JRadioButton rdbtnUp = new JRadioButton(lang.kfiDirUp);
		rdbtnUp.addActionListener(e->direction = "Up");
		JRadioButton rdbtnDown = new JRadioButton(lang.kfiDirDown);
		rdbtnDown.addActionListener(e->direction = "Down");
		rdbtnDown.setSelected(true);
		this.direction = "Down";
		bgDirection = new ButtonGroup();
		bgDirection.add(rdbtnUp);
		bgDirection.add(rdbtnDown);

		JCheckBox chckbxUpperlower = new JCheckBox(lang.kfiUppLow);
		chckbxUpperlower.setEnabled(false);
		
		JButton replaceAllButton = new JButton(lang.kreReplceAll);
		replaceAllButton.addActionListener(e->replaceAll());
		
		JButton cancelButton = new JButton(lang.btnCancel);
		cancelButton.addActionListener(e->setVisible(false));
		
		JLabel lblReplace = new JLabel(lang.krelblReplce);
		
		txtToReplace = new JTextField();
		txtToReplace.setColumns(10);
		txtToReplace.getDocument().addDocumentListener(new DocumentListener() {
			private void update() {
				strToReplace = txtToReplace.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				update();
			}
			
		});
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(chckbxUpperlower)
							.addGap(48)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(26))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblReplace)
									.addGap(22)))
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtToReplace)
								.addComponent(txtToFind, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(findButton, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addComponent(replaceButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(replaceAllButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtToFind, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel))
						.addComponent(findButton))
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(replaceButton)
								.addComponent(lblReplace)
								.addComponent(txtToReplace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(replaceAllButton)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
									.addComponent(chckbxUpperlower))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(18)
									.addComponent(cancelButton))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
					.addGap(35))
		);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addComponent(rdbtnUp)
					.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
					.addComponent(rdbtnDown)
					.addGap(42))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnUp)
						.addComponent(rdbtnDown))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(gl_contentPanel);
	}

	public void showDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void find() {
		if (!strToFind.equals("")) {
			String source = textArea.getText();
			if(direction.equals("Up")) {
				startToFind =  textArea.getSelectionStart();
				findIdx = source.lastIndexOf(strToFind, startToFind-1);
				if(findIdx > -1) {
					textArea.select(findIdx, findIdx + strToFind.length());
				}
				else { showInfoMessage("Cannot find \"" + strToFind +"\""); }
				
			}
			else if(direction.equals("Down")) {
				startToFind =  textArea.getSelectionEnd();
				findIdx = source.indexOf(strToFind, startToFind);
				if(findIdx > -1) {
					textArea.select(findIdx, findIdx + strToFind.length());
				}
				else { showInfoMessage("Cannot find \"" + strToFind +"\""); }
				
			}

		} else {
			if (!this.isVisible()) {
				showDialog();
			}
		}
	}
	
	private boolean replace() {
		boolean rtn = false;
		if (!strToFind.equals("")) {
			String source = textArea.getText();
			if(direction.equals("Up")) {
				
				startToFind =  textArea.getSelectionStart();
				findIdx = source.lastIndexOf(strToFind, startToFind-1);
				if(findIdx > -1) {
					textArea.select(findIdx, findIdx + strToFind.length());
					textArea.replaceSelection(strToReplace);
					rtn = true;
				}
				else { showInfoMessage("All is replaced."); }
				
			}
			else if(direction.equals("Down")) {
				startToFind =  textArea.getSelectionEnd();
				findIdx = source.indexOf(strToFind, startToFind);
				if(findIdx > -1) {
					textArea.select(findIdx, findIdx + strToFind.length());
					textArea.replaceSelection(strToReplace);
					rtn = true;
				}
				else { showInfoMessage("All is replaced."); }
				
			}

		} else {
			if (!this.isVisible()) {
				showDialog();
			}
		}
		return rtn;
	}
	
	private void replaceAll() {
		while(replace());
	}

	
	private void showInfoMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Crypto Notepad", JOptionPane.INFORMATION_MESSAGE);
	}
}
