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

import config.Language;
import config.Property;
import thread.ThreadManager;
import thread.TrKsetInitialize;
import thread.TrKsetInvalidateFile;
import thread.TrKsetLanguage;

public class KSettings extends JDialog {

	private static final long serialVersionUID = -2123099396815339083L;
	
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
	private Language lang;
	
	private ArrayList<Thread> toDoList;
	private Thread trLang;
	private Thread trInval;
	private Thread trInit;
	
	private String btnLangTxt;
	private String btnLangHoverTxt;
	private boolean supportKorean = true;
	
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
		lang = Property.getLanguagePack();
		
		selectedBorder = new TitledBorder(lang.ksReserved);
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

		JButton btnOk = new JButton(lang.btnOK);
		btnOk.addActionListener(e -> {
			isConfirmed = true;
			selectedTimer.stop();
			setVisible(false);
		});
		
		JButton btnCancel = new JButton(lang.btnCancel);
		btnCancel.addActionListener(e -> {
			isConfirmed = false;
			selectedTimer.stop();
			setVisible(false);
		});

		panelForBtn = new JPanel();
		
		JLabel lblSomeFeaturesMay = new JLabel(lang.ksNoti);
		lblSomeFeaturesMay.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(159)
					.addComponent(panelForBtn, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(159, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(129)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(122)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(127, Short.MAX_VALUE))
				.addComponent(lblSomeFeaturesMay, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(panelForBtn, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
					.addComponent(lblSomeFeaturesMay)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnCancel))
					.addGap(36))
		);

		btnLang = new KButton();
		btnLang.setText(lang.ksLang);
		btnLang.setFocusPainted(true);
		btnLang.setkAllowGradient(false);
		btnLang.setkAllowTab(false);
		btnLang.setkFillButton(true);
		btnLang.setBorderPainted(false);
		btnLang.setkBorderRadius(0);
		btnLang.setkPressedColor(new Color(0xebcff2));
		btnLang.setkBackGroundColor(new Color(0x9730b0));
		btnLang.setkForeGround(new Color(0xffffff));
		btnLang.setForeground(new Color(0xffffff));
		btnLang.setkHoverColor(new Color(0xffffff));
		btnLang.setkHoverForeGround(new Color(0x68217A));
		btnLang.setBorder(selectedBorder);

		supportKorean = true;
		btnLangTxt = lang.ksLang;
		btnLangHoverTxt = lang.ksLangHover;
		if (Property.getProperties().get(Property.language).equals("ENGLISH")) {
			if(!btnLang.getFont().canDisplay(lang.testChar_KO)) {
				supportKorean = false;
				btnLangTxt = lang.ksLangWarn;
				btnLangHoverTxt = lang.ksLangWarnHover;
				btnLang.setFont(btnLang.getFont().deriveFont(10f));
				btnLang.setText(btnLangTxt);
			}
		}
		
		btnLang.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectLang) {
					selectLang = false;
					btnLang.setText(btnLangTxt);
					toDoList.remove(trLang);
				} else {
					selectLang = true;
					btnLang.setText(btnLangHoverTxt);
					toDoList.add(trLang);
				}
				if(supportKorean) {
					btnClicked(btnLang, selectLang);
				} else {
					NRBtnClicked(btnLang, selectLang);
				}

			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!selectLang) {
					btnLang.setText(btnLangHoverTxt);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(!selectLang) {
					btnLang.setText(btnLangTxt);
				}
				
			}
		});
		
		btnInvalidate = new KButton();
		btnInvalidate.setText(lang.ksInval);
		btnInvalidate.setFocusPainted(true);
		btnInvalidate.setkAllowGradient(false);
		btnInvalidate.setkAllowTab(false);
		btnInvalidate.setkFillButton(true);
		btnInvalidate.setBorderPainted(false);
		btnInvalidate.setkBorderRadius(0);
		btnInvalidate.setkPressedColor(new Color(0xebcff2));
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
					selectInvalidate = false;
					toDoList.remove(trInval);
				} else {
					selectInvalidate = true;
					toDoList.add(trInval);
				}
				btnClicked(btnInvalidate, selectInvalidate);
			}
		});

		btnInit = new KButton();
		btnInit.setText(lang.ksInit);
		btnInit.setFocusPainted(true);
		btnInit.setkAllowGradient(false);
		btnInit.setkAllowTab(false);
		btnInit.setkFillButton(true);
		btnInit.setBorderPainted(false);
		btnInit.setkBorderRadius(0);
		btnInit.setkPressedColor(new Color(0xebcff2));
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

		JLabel lblNewLabel = new JLabel(lang.cypherNotepad);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
		lblNewLabel.setForeground(new Color(0xffffff));

		JLabel lblSdf = new JLabel("sdf");
		lblSdf.setHorizontalAlignment(SwingConstants.CENTER);

		Image originImg = new ImageIcon(getClass().getClassLoader().getResource("encrypted_white_origin.png")).getImage();
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
		
		if(supportKorean) {
			btnClicked(btnLang, selectLang);
		} else {
			NRBtnClicked(btnLang, selectLang);
		}
		btnClicked(btnInvalidate, selectInvalidate);
		btnClicked(btnInit, selectInit);
		
		toDoList = new ArrayList<Thread>();
		selectedTimer.start();
		setVisible(true);

		return isConfirmed;
	}

	public void applySettings() {
		System.out.println("[KSettings] Apply settings");
		if(isConfirmed) {
			for (Thread t : toDoList) {
				if(t.getState() == Thread.State.NEW) {
					t.start();
					ThreadManager.getInstance().addThread(t);
				}
			}
		}
	}

	private void btnClicked(KButton btn, boolean isSelected) {
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
	
	private void NRBtnClicked(KButton btn, boolean isSelected) {
		if (isSelected) {
			btn.setBorderPainted(true);
			btn.setkBackGroundColor(new Color(0xf0f0f0));
			btn.setkHoverColor(new Color(0xf0f0f0));
			btnSync();
		} else {
			btn.setBorderPainted(false);
			btn.setkBackGroundColor(new Color(0xaaaaaa));
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
