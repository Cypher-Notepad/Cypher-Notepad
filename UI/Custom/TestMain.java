package UI.Custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;

public class TestMain extends JFrame {

	private JPanel contentPane;
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
				btnOk.addActionListener(e -> this.dispose());
				
				//JButton btnLang = new JButton("New button");
				KButton btnLang = new KButton();
				btnLang.setText("Language");
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
				
				//JButton btnInvalidate = new JButton("New button");
				KButton btnInvalidate = new KButton();
				btnInvalidate.setText("Invalidate every encrypted files");
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
				
				
				//JButton btnInit = new JButton("New button");
				KButton btnInit = new KButton();
				btnInit.setText("Initialize settings");
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
				
				JButton btnCancel = new JButton("OK");

				// list.
				GroupLayout gl_contentPane = new GroupLayout(contentPane);
				gl_contentPane.setHorizontalGroup(
					gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(129)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addGap(106)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(143, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(159)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnInvalidate, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnInit, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLang, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(160, Short.MAX_VALUE))
				);
				gl_contentPane.setVerticalGroup(
					gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(btnLang, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(btnInvalidate, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(btnInit, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnOk)
								.addComponent(btnCancel))
							.addGap(36))
				);

				JLabel lblNewLabel = new JLabel("Crypto Notepad");
				lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
				lblNewLabel.setForeground(new Color(0xffffff));

				JLabel lblSdf = new JLabel("sdf");
				lblSdf.setHorizontalAlignment(SwingConstants.CENTER);
				ImageIcon originIcon = new ImageIcon("C:\\Users\\matth\\Desktop\\Untitled_tp.png");
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
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap(101, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addComponent(lblSetting, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
									.addGap(217))))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(56)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
							.addComponent(lblSetting, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
				);
				panel.setLayout(gl_panel);
				contentPane.setLayout(gl_contentPane);

			}

			public void showDialog() {
				setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.setLocationRelativeTo(null);
				setVisible(true);
			}
}
