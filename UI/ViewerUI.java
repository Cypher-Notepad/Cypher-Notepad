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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import File.FileManager;
import VO.MemoVO;

public class ViewerUI extends JFrame implements UI {

	Container container;

	public ViewerUI() {
		//draw();
	}

	@Override
	public void draw() {
		setTitle("**Crypto Viewer**");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.container = getContentPane();

		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		fl.setHgap(10);

		this.container.setLayout(fl);
		this.container.setBackground(Color.WHITE);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel();
		title.setText("--VIEWER--");
		title.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		titlePanel.add(title);
		
		JPanel statePanel = new JPanel();
		
		ButtonGroup modeGroup = new ButtonGroup();
		JRadioButton editorBtn = new JRadioButton("Editor");
		JRadioButton viewerBtn = new JRadioButton("Viewer");
		viewerBtn.setSelected(true);
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
		// nameTextField.setText(name);
		namePanel.add(nameTextField);

		JPanel contentPanel = new JPanel();
		//contentPanel.setBackground(Color.BLUE);
		contentPanel.setLayout(fl);
		contentPanel.add(new JLabel("Content : "));
		JTextArea contentTextArea = new JTextArea(10, 30);
		// contentTextArea.setText(content);
		contentTextArea.setLineWrap(true);
		contentTextArea.setEditable(false);
		contentTextArea.setBackground(Color.lightGray);
		contentPanel.add(contentTextArea);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1, 0, 20));
		JButton openBtn = new JButton("Open");
		JButton editBtn = new JButton("Edit"); 
		openBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent act) {
				MemoVO memo = FileManager.getInstance().loadMemo(nameTextField.getText());
				contentTextArea.setText(memo.getContent());
				System.out.println(memo.getContent());
			}
			
		});
		editBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UIManager.getInstance().setUI(new EditorUI(nameTextField.getText(), contentTextArea.getText()));
			}
			
		});
		buttonPanel.add(openBtn);
		buttonPanel.add(editBtn);
		contentPanel.add(buttonPanel);

		container.add(titlePanel);
		container.add(statePanel);
		container.add(namePanel);
		container.add(contentPanel);

		setSize(550, 500);
		setVisible(true);

	}

	@Override
	public void erase() {
		this.dispose();
	}
}
