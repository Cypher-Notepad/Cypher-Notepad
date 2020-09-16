package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import config.Language;
import config.Property;
import file.FileManager;
import thread.ThreadManager;
import thread.TrKeyLoad;
import thread.TrPrepareNotepadUI;
import ui.custom.KButton;

public class MainUI extends JFrame implements UI {

	private static final long serialVersionUID = -8901510103598913554L;
	
	private Container contentPane;
	private JTable table;
	private KButton btnNew, btnOpen, btnX;
	public static NotepadUI notepadUI;
	private Language lang;
	int mpX, mpY;

	public MainUI() {
		lang = Property.getLanguagePack();
	}

	@Override
	public void draw() {

		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Image originFrameImg = new ImageIcon(getClass().getClassLoader().getResource("encrypted_black_crop_bg.png")).getImage();
		Image changedFrameImg = originFrameImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		setIconImage(changedFrameImg);
		
		setBounds(100, 100, 550, 719);
		this.setLocationRelativeTo(null);

		contentPane = this.getContentPane();
		this.setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x68217A));

		table = new JTable();
		table.setModel(new DefaultTableModel(FileManager.getInstance().loadRecentFiles(),
				new String[] { lang.tblName, lang.tblDate, lang.tblSize, lang.tblPath }) {

			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setSelectionBackground(new Color(0x451651));
		table.setSelectionForeground(new Color(0xffffff));
		table.setRowHeight(50);
		
		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		
		table.getColumnModel().getColumn(1).setMinWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(160);
		table.getColumnModel().getColumn(1).setMaxWidth(160);
		
		table.getColumnModel().getColumn(2).setMinWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setMaxWidth(80);
		
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 1; i < tcmSchedule.getColumnCount()-1; i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		JLabel lblRecentFiles = new JLabel(lang.rcntFiles);
		lblRecentFiles.setFont(new Font("Condolas", Font.BOLD, 15));

		// list.
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(lblRecentFiles)
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblRecentFiles, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGap(4)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)));

		JLabel lblNewLabel = new JLabel(lang.cypherNotepad);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
		lblNewLabel.setForeground(new Color(0xffffff));

		btnNew = new KButton();
		btnNew.setText(lang.btnNew);
		btnNew.setFocusPainted(true);
		btnNew.setkAllowGradient(false);
		btnNew.setkAllowTab(false);
		btnNew.setkFillButton(true);
		btnNew.setBorderPainted(false);
		btnNew.setkBorderRadius(50);
		btnNew.setkPressedColor(new Color(0xebcff2));
		btnNew.setkBackGroundColor(new Color(0x9730b0));
		btnNew.setkForeGround(new Color(0xffffff));
		btnNew.setForeground(new Color(0xffffff));
		btnNew.setkHoverColor(new Color(0xffffff));
		btnNew.setkHoverForeGround(new Color(0x68217A));

		btnOpen = new KButton();
		btnOpen.setText(lang.btnOpen);
		btnOpen.setFocusPainted(true);
		btnOpen.setkAllowGradient(false);
		btnOpen.setkAllowTab(false);
		btnOpen.setkFillButton(true);
		btnOpen.setBorderPainted(false);
		btnOpen.setkBorderRadius(50);
		btnOpen.setkPressedColor(new Color(0xebcff2));
		btnOpen.setkBackGroundColor(new Color(0x9730b0));
		btnOpen.setkForeGround(new Color(0xffffff));
		btnOpen.setForeground(new Color(0xffffff));
		btnOpen.setkHoverColor(new Color(0xffffff));
		btnOpen.setkHoverForeGround(new Color(0x68217A));

		JLabel lblSdf = new JLabel("sdf");
		lblSdf.setHorizontalAlignment(SwingConstants.CENTER);
		Image originImg = new ImageIcon(getClass().getClassLoader().getResource("encrypted_white_origin.png")).getImage();
		Image changedImg = originImg.getScaledInstance(71, 90, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);
		lblSdf.setIcon(Icon);

		btnX = new KButton();
		btnX.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
		btnX.setMargin(new Insets(1, 1, 1, 1));
		btnX.setText("X");
		btnX.setFocusPainted(true);
		btnX.setkAllowGradient(false);
		btnX.setkAllowTab(false);
		btnX.setkFillButton(true);
		btnX.setBorderPainted(false);
		btnX.setkBorderRadius(5);
		btnX.setkPressedColor(new Color(0xebcff2));
		btnX.setkBackGroundColor(new Color(0x9730b0));
		btnX.setkForeGround(new Color(0xffffff));
		btnX.setForeground(new Color(0xffffff));
		btnX.setkHoverColor(new Color(0xffffff));
		btnX.setkHoverForeGround(new Color(0x68217A));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap(525, Short.MAX_VALUE).addComponent(btnX,
						GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup().addGap(98)
						.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup().addGap(154)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnOpen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNew, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 235,
										Short.MAX_VALUE))
						.addGap(161)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel
								.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup().addGap(73).addComponent(lblSdf,
										GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnX, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)).addGap(23)
						.addComponent(btnNew, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(22, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

		setVisible(true);

		Thread prepareNotepadUIThread = new TrPrepareNotepadUI();
		prepareNotepadUIThread.start();
		ThreadManager.getInstance().addThread(prepareNotepadUIThread);
		
		Thread keyLoadThread = new TrKeyLoad();
		keyLoadThread.start();
		ThreadManager.getInstance().setKeyLoadingThead(keyLoadThread);
		
		addListeners();
	}

	@Override
	public void erase() {
		this.dispose();
	}

	public void addListeners() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mpX = e.getX();
				mpY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			private static final int STICKY_THRESHOLD = 17;
			Insets taskBar;
			Point curFramePos;
			int screenXSize = Toolkit.getDefaultToolkit().getScreenSize().width;
			int screenYSize = Toolkit.getDefaultToolkit().getScreenSize().height;
			int frameWidth, frameHeight;
			int posX, posY;
			int left, right, top, bottom;
			int distanceX, distanceY;

			@Override
			public void mouseDragged(MouseEvent e) {
				taskBar = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
				curFramePos = getLocation();
				frameWidth = getWidth();
				frameHeight = getHeight();
				distanceX = e.getX() - mpX;
				distanceY = e.getY() - mpY;
				posX = curFramePos.x + distanceX;
				posY = curFramePos.y + distanceY;

				// Calculate the distance from the boundaries of the screen.
				left = curFramePos.x - taskBar.left;
				right = screenXSize - left - frameWidth - taskBar.right - taskBar.left;
				top = curFramePos.y - taskBar.top;
				bottom = screenYSize - top - frameHeight - taskBar.bottom - taskBar.top;

				// Horizontal magnet frame.
				if (Math.abs(left) < STICKY_THRESHOLD) {
					posX = 0 + taskBar.left;
					if (distanceX > STICKY_THRESHOLD) posX = STICKY_THRESHOLD + taskBar.left;
					if (distanceX < -STICKY_THRESHOLD) posX = -STICKY_THRESHOLD + taskBar.left;
				} else if (Math.abs(right) < STICKY_THRESHOLD) {
					posX = screenXSize - frameWidth - taskBar.right;
					if (distanceX > STICKY_THRESHOLD) posX = screenXSize - frameWidth +  STICKY_THRESHOLD - taskBar.right; 
					if (distanceX < -STICKY_THRESHOLD) posX = screenXSize - frameWidth - STICKY_THRESHOLD - taskBar.right;
				}

				// Vertical magnet frame.
				if (Math.abs(top) < STICKY_THRESHOLD) {
					posY = 0 + taskBar.top;
					if (distanceY > STICKY_THRESHOLD) posY = STICKY_THRESHOLD + taskBar.top;
					if (distanceY < -STICKY_THRESHOLD) posY = -STICKY_THRESHOLD + taskBar.top;
				} else if (Math.abs(bottom) < STICKY_THRESHOLD) {
					posY = screenYSize - frameHeight - taskBar.bottom;
					if (distanceY > STICKY_THRESHOLD) posY = screenYSize - frameHeight + STICKY_THRESHOLD - taskBar.bottom;
					if (distanceY < -STICKY_THRESHOLD) posY = screenYSize - frameHeight - STICKY_THRESHOLD - taskBar.bottom;
				}
				
				setLocation(posX, posY);
			}
		});
		

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String path = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
					if(notepadUI == null) {
						ThreadManager.getInstance().joinThreads();
					}
					
					boolean isLoaded = notepadUI.loadMemo(new File(path));
					if (isLoaded) {
						Property.addRecentFiles(path);
						UIManager.getInstance().setUI(notepadUI);
					}
				}
			}
		});

		btnNew.addActionListener(e -> {
			ThreadManager.getInstance().joinThreads();
			UIManager.getInstance().setUI(notepadUI);
		});

		btnOpen.addActionListener(e -> {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter(lang.fileFilter_txt, "txt"));
			boolean toBeSelected = true;
			while(toBeSelected) {
				int response = fc.showOpenDialog(this);
				if (response == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile().exists()) {
						toBeSelected = false;
						System.out.println(fc.getSelectedFile());
						if(notepadUI == null) {
							ThreadManager.getInstance().joinThreads();
						}
						
						boolean isLoaded = notepadUI.loadMemo(fc.getSelectedFile());
						if (isLoaded) {
							try {
								Property.addRecentFiles(fc.getSelectedFile().getCanonicalPath());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							UIManager.getInstance().setUI(notepadUI);
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

		btnX.addActionListener(e -> {
			System.out.println("[MainUI] Close Window on Main UI");
			UIManager.getInstance().closeWindow();
			ThreadManager.getInstance().joinKeyLoadingThread();
			ThreadManager.getInstance().joinThreads();
			FileManager.getInstance().saveProperties();
			System.exit(0);
		});
	}

}
