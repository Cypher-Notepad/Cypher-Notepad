package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import File.FileManager;
import VO.MemoVO;

public class EditorUI extends JFrame implements UI{
	
	Container container;
	String name = null;
	String content = null;
	
	public EditorUI() {
		this.name = "";
		this.content = "";
	}
	
	public EditorUI(String name, String content){
		this.name = name;
		this.content = content;
	}

	@Override
	public void draw() {
		draw(this.name, this.content);
	}
	
	public void draw(String name, String content) {
		setTitle("**Crypto Editor**");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		
		this.container = getContentPane();
		
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		fl.setHgap(10);
		this.container.setLayout(fl);
		
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel();
		title.setText("--EDITOR--");
		title.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		titlePanel.add(title);
		
		
		JPanel statePanel = new JPanel();
		ButtonGroup modeGroup = new ButtonGroup();
		JRadioButton editorBtn = new JRadioButton("Editor");
		JRadioButton viewerBtn = new JRadioButton("Viewer");
		editorBtn.setSelected(true);
		editorBtn.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent item) {
				if(item.getStateChange() == ItemEvent.SELECTED) {
					UIManager.getInstance().setUI(new EditorUI());
				}
				
			}
			
		});
		viewerBtn.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent item) {
				if(item.getStateChange() == ItemEvent.SELECTED) {
					UIManager.getInstance().setUI(new ViewerUI());
				}
			}
			
		});
		modeGroup.add(editorBtn);
		modeGroup.add(viewerBtn);
		statePanel.add(editorBtn);
		statePanel.add(viewerBtn);
		
		
		JPanel namePanel = new JPanel();
		//namePanel.setBackground(Color.RED);
		namePanel.setLayout(fl);
		namePanel.add(new JLabel("File : "));
		JTextField nameTextField = new JTextField(20);
		nameTextField.setText(name);
		namePanel.add(nameTextField);
		
		
		JPanel contentPanel = new JPanel();
		//contentPanel.setBackground(Color.BLUE);
		contentPanel.setLayout(fl);
		contentPanel.add(new JLabel("Content : "));
		JTextArea contentTextArea = new JTextArea(10, 30);
		contentTextArea.setLineWrap(true);
		contentTextArea.setText(content);
		contentPanel.add(contentTextArea);
	
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1,0,20));
		JButton saveBtn = new JButton("Save");
		JButton resetBtn = new JButton("Reset");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent act) {
				MemoVO memo = new MemoVO();
				memo.setContent(contentTextArea.getText());
				FileManager.getInstance().saveMemo(nameTextField.getText(), memo);
				
			}
			
		});
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nameTextField.setText("");
				contentTextArea.setText("");
			}
			
		});
		buttonPanel.add(saveBtn);
		buttonPanel.add(resetBtn);
		contentPanel.add(buttonPanel);
		
		container.add(titlePanel);
		container.add(statePanel);
		container.add(namePanel);
		container.add(contentPanel);
		
		setSize(550,500);
		setVisible(true);
	}

	@Override
	public void erase() {
		this.dispose();
	}

	
}