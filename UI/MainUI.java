package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import File.FileManager;
import UI.Custom.KButton;
import VO.MemoVO;

public class MainUI extends JFrame implements UI {

	private JPanel contentPane;
	private JTable table;
	int mpX, mpY;

	public MainUI() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mpX = e.getX();
				mpY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(getLocation().x + e.getX() - mpX, getLocation().y + e.getY() - mpY);
			}
		});
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 719);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		this.setUndecorated(true);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x68217A));

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(FileManager.getInstance().loadRecentFiles(),
				new String[] { "Name", "Date", "Size", "Path" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.setSelectionBackground(new Color(0x451651));
		table.setSelectionForeground(new Color(0xffffff));
		table.setRowHeight(50);

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 1; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		// list.
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE).addComponent(panel,
						Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)));

		JLabel lblNewLabel = new JLabel("Crypto Notepad");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
		lblNewLabel.setForeground(new Color(0xffffff));

		// JButton btnNew = new JButton("New");

		KButton btnNew = new KButton();
		btnNew.setText("New");
		btnNew.setFocusPainted(true);
		btnNew.setkAllowGradient(false);
		btnNew.setkAllowTab(false);
		btnNew.setkFillButton(true);
		btnNew.setBorderPainted(false);
		btnNew.setkBorderRadius(50);
		btnNew.setkPressedColor(new Color(0xebcff2));
		// btnNew.setBackground(new Color(0x68217A));
		btnNew.setkBackGroundColor(new Color(0x9730b0));
		btnNew.setkForeGround(new Color(0xffffff));
		btnNew.setForeground(new Color(0xffffff));
		btnNew.setkHoverColor(new Color(0xffffff));
		btnNew.setkHoverForeGround(new Color(0x68217A));
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIManager.getInstance().setUI(new NotepadUI());

			}

		});

		// JButton btnOpen = new JButton("Open");

		KButton btnOpen = new KButton();
		btnOpen.setText("Open");
		btnOpen.setFocusPainted(true);
		btnOpen.setkAllowGradient(false);
		btnOpen.setkAllowTab(false);
		btnOpen.setkFillButton(true);
		btnOpen.setBorderPainted(false);
		btnOpen.setkBorderRadius(50);
		btnOpen.setkPressedColor(new Color(0xebcff2));
		// btnOpen.setBackground(new Color(0x68217A));
		btnOpen.setkBackGroundColor(new Color(0x9730b0));
		btnOpen.setkForeGround(new Color(0xffffff));
		btnOpen.setForeground(new Color(0xffffff));
		btnOpen.setkHoverColor(new Color(0xffffff));
		btnOpen.setkHoverForeGround(new Color(0x68217A));
		btnOpen.addActionListener(e->{
			JFileChooser fc = new JFileChooser();
			
			int response = fc.showOpenDialog(this);
			if(response == fc.APPROVE_OPTION) {
				System.out.println(fc.getSelectedFile());
				UIManager.getInstance().setUI(new NotepadUI(fc.getSelectedFile()));
			}
		});

		JLabel lblSdf = new JLabel("sdf");
		lblSdf.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon originIcon = new ImageIcon("C:\\Users\\matth\\Desktop\\Untitled_tp.png");
		Image originImg = originIcon.getImage();
		Image changedImg = originImg.getScaledInstance(71, 90, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);
		lblSdf.setIcon(Icon);

		// JButton btnX = new JButton("X");
		KButton btnX = new KButton();
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
		// btnOpen.setBackground(new Color(0x68217A));
		btnX.setkBackGroundColor(new Color(0x9730b0));
		btnX.setkForeGround(new Color(0xffffff));
		btnX.setForeground(new Color(0xffffff));
		btnX.setkHoverColor(new Color(0xffffff));
		btnX.setkHoverForeGround(new Color(0x68217A));
		btnX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				erase();
				// System.exit(0);
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap(525, Short.MAX_VALUE).addComponent(btnX,
						GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addGap(98)
						.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnOpen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNew, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 226,
												Short.MAX_VALUE)))
						.addContainerGap(17, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel
								.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup().addGap(73).addComponent(lblSdf,
										GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
						.addGap(17).addComponent(btnNew, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnX, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(18).addComponent(btnOpen, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(29, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}

	@Override
	public void erase() {
		this.dispose();

	}

}
