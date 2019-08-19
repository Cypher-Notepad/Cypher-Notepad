package ui.custom;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import thread.ThreadManager;
import thread.TrKsetInitialize;
import thread.TrKsetInvalidateFile;
import thread.TrKsetLanguage;

public class KSettings extends JDialog {

	private JPanel contentPane;
	JPanel panelForBtn;
	KButton btnInit, btnInvalidate, btnLang;
	public boolean isConfirmed;

	private TitledBorder selectedBorder;
	private Color selectedColorTrue = new Color(0xf0f0f0);
	private Color selectedColorfalse = new Color(0x68217A);
	private boolean selectLang = false;
	private boolean selectInvalidate = false;
	private boolean selectInit = false;
	private boolean langColored = false;
	private boolean invalColored = false;
	private boolean initColored = false;

	private ArrayList<Thread> toDoList;
	private Thread trLang;
	private Thread trInval;
	private Thread trInit;
	
	private ActionListener selectedAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent evt) {
			if (selectLang) {
				if (langColored) {
					langColored = false;
					btnLang.setkForeGround(selectedColorTrue);
				} else {
					langColored = true;
					btnLang.setkForeGround(selectedColorfalse);
				}
			}
			if (selectInvalidate) {
				if (invalColored) {
					invalColored = false;
					btnInvalidate.setkForeGround(selectedColorTrue);
				} else {
					invalColored = true;
					btnInvalidate.setkForeGround(selectedColorfalse);
				}
			}
			if (selectInit) {
				if (initColored) {
					initColored = false;
					btnInit.setkForeGround(selectedColorTrue);
				} else {
					initColored = true;
					btnInit.setkForeGround(selectedColorfalse);
				}
			}
			panelForBtn.repaint();
		}
	};
	private Timer selectedTimer;

	public KSettings() {
		// TODO Auto-generated method stub
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		selectedBorder = new TitledBorder("Reserved");
		selectedBorder.setBorder(new LineBorder(Color.BLACK, 3));
		selectedTimer = new Timer(650, selectedAction);

		setBounds(100, 100, 550, 644);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		this.setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x68217A));
		String context = "Crypto-Notepad\n\r" + "Version 2.0\n\r" + "";

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(e -> {
			System.out.println("OKAy - kSetting");
			isConfirmed = true;
			selectedTimer.stop();
			setVisible(false);
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			isConfirmed = false;
			selectedTimer.stop();
			setVisible(false);
		});

		panelForBtn = new JPanel();

		// list.
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(129)
						.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE).addGap(122)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(127, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(159)
						.addComponent(panelForBtn, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(159, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE).addGap(38)
						.addComponent(panelForBtn, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE).addGroup(gl_contentPane
								.createParallelGroup(Alignment.BASELINE).addComponent(btnOk).addComponent(btnCancel))
						.addGap(36)));

		// JButton btnLang = new JButton("New button");
		btnLang = new KButton();
		btnLang.setText(" Language");
		btnLang.setFocusPainted(true);
		btnLang.setkAllowGradient(false);
		btnLang.setkAllowTab(false);
		btnLang.setkFillButton(true);
		btnLang.setBorderPainted(false);
		btnLang.setkBorderRadius(0);
		btnLang.setkPressedColor(new Color(0xebcff2));
		// btnNew.setBackground(new Color(0x68217A));
		btnLang.setkBackGroundColor(new Color(0x9730b0));
		btnLang.setkForeGround(new Color(0xffffff));
		btnLang.setForeground(new Color(0xffffff));
		btnLang.setkHoverColor(new Color(0xffffff));
		btnLang.setkHoverForeGround(new Color(0x68217A));
		btnLang.setBorder(selectedBorder);
		btnLang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectLang) {
					selectLang = false;
					toDoList.remove(trLang);
				} else {
					selectLang = true;
					toDoList.add(trLang);
				}
				btnClicked(btnLang, selectLang);

			}
		});

		// JButton btnInvalidate = new JButton("New button");
		btnInvalidate = new KButton();
		btnInvalidate.setText("  Invalidate every encrypted files");
		btnInvalidate.setFocusPainted(true);
		btnInvalidate.setkAllowGradient(false);
		btnInvalidate.setkAllowTab(false);
		btnInvalidate.setkFillButton(true);
		btnInvalidate.setBorderPainted(false);
		btnInvalidate.setkBorderRadius(0);
		btnInvalidate.setkPressedColor(new Color(0xebcff2));
		// btnNew.setBackground(new Color(0x68217A));
		btnInvalidate.setkBackGroundColor(new Color(0x9730b0));
		btnInvalidate.setkForeGround(new Color(0xffffff));
		btnInvalidate.setForeground(new Color(0xffffff));
		btnInvalidate.setkHoverColor(new Color(0xffffff));
		btnInvalidate.setkHoverForeGround(new Color(0x68217A));
		btnInvalidate.setBorder(selectedBorder);
		btnInvalidate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectInvalidate) {
					System.out.println("btn - re-clicked");
					selectInvalidate = false;
					toDoList.remove(trInval);
				} else {

					System.out.println("btn - clicked");
					selectInvalidate = true;
					toDoList.add(trInval);
				}
				btnClicked(btnInvalidate, selectInvalidate);

			}
		});

		// JButton btnInit = new JButton("New button");
		btnInit = new KButton();
		btnInit.setText(" Initialize settings");
		btnInit.setFocusPainted(true);
		btnInit.setkAllowGradient(false);
		btnInit.setkAllowTab(false);
		btnInit.setkFillButton(true);
		btnInit.setBorderPainted(false);
		btnInit.setkBorderRadius(0);
		btnInit.setkPressedColor(new Color(0xebcff2));
		// btnNew.setBackground(new Color(0x68217A));
		btnInit.setkBackGroundColor(new Color(0x9730b0));
		btnInit.setkForeGround(new Color(0xffffff));
		btnInit.setForeground(new Color(0xffffff));
		btnInit.setkHoverColor(new Color(0xffffff));
		btnInit.setkHoverForeGround(new Color(0x68217A));
		btnInit.setBorder(selectedBorder);
		btnInit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectInit) {
					selectInit = false;
					toDoList.remove(trInit);
				} else {
					selectInit = true;
					toDoList.add(trInit);
				}
				btnClicked(btnInit, selectInit);

			}
		});

		GroupLayout gl_panelForBtn = new GroupLayout(panelForBtn);
		gl_panelForBtn.setHorizontalGroup(gl_panelForBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelForBtn.createSequentialGroup()
						.addComponent(btnInit, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE).addContainerGap())
				.addGroup(Alignment.TRAILING,
						gl_panelForBtn.createSequentialGroup()
								.addGroup(gl_panelForBtn.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnLang, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 232,
												Short.MAX_VALUE)
										.addComponent(btnInvalidate, GroupLayout.PREFERRED_SIZE, 232, Short.MAX_VALUE))
								.addContainerGap()));
		gl_panelForBtn.setVerticalGroup(gl_panelForBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelForBtn.createSequentialGroup()
						.addComponent(btnLang, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnInvalidate, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnInit, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panelForBtn.setLayout(gl_panelForBtn);

		JLabel lblNewLabel = new JLabel("Crypto Notepad");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
		lblNewLabel.setForeground(new Color(0xffffff));

		JLabel lblSdf = new JLabel("sdf");
		lblSdf.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon originIcon = new ImageIcon("resource\\\\encrypted_white_origin.png");
		Image originImg = originIcon.getImage();
		Image changedImg = originImg.getScaledInstance(71, 90, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);
		lblSdf.setIcon(Icon);

		JLabel lblSetting = new JLabel("Settings");
		lblSetting.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetting.setFont(new Font("Consolas", Font.BOLD, 18));
		lblSetting.setBackground(new Color(0x9730b0));
		lblSetting.setForeground(new Color(0xffffff));
		lblSetting.setOpaque(true);
		lblSetting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSetting.setBackground(new Color(0xffffff));
				lblSetting.setForeground(new Color(0x9730b0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblSetting.setBackground(new Color(0x9730b0));
				lblSetting.setForeground(new Color(0xffffff));
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap(101, Short.MAX_VALUE)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
								.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
								.addComponent(lblSetting, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addGap(217)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(56)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
				.addComponent(lblSetting, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
	}

	public boolean showDialog() {
		isConfirmed = false;
		trLang = new TrKsetLanguage();
		trInval = new TrKsetInvalidateFile();
		trInit = new TrKsetInitialize();
		
		selectLang = false;
		selectInvalidate = false;
		selectInit = false;
		btnClicked(btnLang, selectLang);
		btnClicked(btnInvalidate, selectInvalidate);
		btnClicked(btnInit, selectInit);
		
		toDoList = new ArrayList<Thread>();
		selectedTimer.start();
		setVisible(true);

		return isConfirmed;
	}

	public void applySettings() {
		if(isConfirmed) {
			for (Thread t : toDoList) {
				if(t.getState() == Thread.State.NEW) {
					System.out.println("task - KSetting");
					t.start();
					ThreadManager.getInstance().addThread(t);
				}
			}
		}
	}

	private void btnClicked(KButton btn, boolean isSelected) {

		System.out.println("btn listener" + isSelected);
		if (isSelected) {
			btn.setBorderPainted(true);
			btn.setkBackGroundColor(new Color(0xf0f0f0));
			btn.setkHoverColor(new Color(0xf0f0f0));
			btnSync();
		} else {
			btn.setBorderPainted(false);
			btn.setkBackGroundColor(new Color(0x9730b0));
			btn.setkForeGround(new Color(0xffffff));
			btn.setkHoverColor(new Color(0xffffff));
		}
	}

	private void btnSync() {
		langColored = false;
		invalColored = false;
		initColored = false;
	}

}