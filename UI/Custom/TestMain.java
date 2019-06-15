package UI.Custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import UI.NotepadUI;
import UI.UIManager;

public class TestMain extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private KButton btnNew, btnOpen, btnX;
	int mpX, mpY;
	private JLabel lblRecentFiles;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestMain frame = new TestMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestMain() {
		// TODO Auto-generated method stub
				try {
					javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				setBounds(100, 100, 550, 719);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
				setContentPane(contentPane);
				this.setUndecorated(true);

				JPanel panel = new JPanel();
				panel.setBackground(new Color(0x68217A));

				table = new JTable();
				table.setModel(new DefaultTableModel(FileManager.getInstance().loadRecentFiles(),
						new String[] { "Name", "Date", "Size", "Path" }) {
					
					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] { false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
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
				
				lblRecentFiles = new JLabel("Recent Files");
				lblRecentFiles.setFont(new Font("Condolas", Font.BOLD, 15));
				
				// list.
				GroupLayout gl_contentPane = new GroupLayout(contentPane);
				gl_contentPane.setHorizontalGroup(
					gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(lblRecentFiles)
							.addContainerGap())
				);
				gl_contentPane.setVerticalGroup(
					gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblRecentFiles, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(4)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
				);

				JLabel lblNewLabel = new JLabel("Crypto Notepad");
				lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
				lblNewLabel.setForeground(new Color(0xffffff));

				// JButton btnNew = new JButton("New");

				btnNew = new KButton();
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
				

				// JButton btnOpen = new JButton("Open");

				btnOpen = new KButton();
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
				

				JLabel lblSdf = new JLabel("sdf");
				lblSdf.setHorizontalAlignment(SwingConstants.CENTER);
				ImageIcon originIcon = new ImageIcon("C:\\Users\\matth\\Desktop\\Untitled_tp.png");
				Image originImg = originIcon.getImage();
				Image changedImg = originImg.getScaledInstance(71, 90, Image.SCALE_SMOOTH);
				ImageIcon Icon = new ImageIcon(changedImg);
				lblSdf.setIcon(Icon);

				// JButton btnX = new JButton("X");
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
				// btnOpen.setBackground(new Color(0x68217A));
				btnX.setkBackGroundColor(new Color(0x9730b0));
				btnX.setkForeGround(new Color(0xffffff));
				btnX.setForeground(new Color(0xffffff));
				btnX.setkHoverColor(new Color(0xffffff));
				btnX.setkHoverForeGround(new Color(0x68217A));

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

}
