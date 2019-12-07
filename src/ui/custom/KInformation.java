package ui.custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class KInformation extends JDialog {

	private JPanel contentPane;
	JButton btnOk;
	
	public KInformation() {
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

		btnOk = new JButton("OK");
		btnOk.addActionListener(e -> this.dispose());

		JTextPane txtpnCryptonotepadVersion = new JTextPane();
		txtpnCryptonotepadVersion.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnCryptonotepadVersion.setEditable(false);
		// txtpnCryptonotepadVersion.setForeground(new Color(0x000000));
		txtpnCryptonotepadVersion.setBackground(new Color(240, 240, 240));
		txtpnCryptonotepadVersion.setText(
				"\r\nThe Crypto-Notepad was built for protecting user's data. Its interface is from the Microsoft Windows10 Notepad. \r\n\r\n\r\nThe source code of this program may be found on Github, \"https://github.com/LeeDongGeon1996/Crypto-Notepad\". It is always welcomed you to press \"Follow\" and \"Star\" button.");
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

		Image originImg1 = new ImageIcon(getClass().getClassLoader().getResource("encrypted_black_origin.png")).getImage();
		Image changedImg1 = originImg1.getScaledInstance(70, 68, Image.SCALE_SMOOTH);
		ImageIcon Icon1 = new ImageIcon(changedImg1);
		lblIcon.setIcon(Icon1);
		
		JTextPane txtpnG = new JTextPane();
		txtpnG.setText("Crypto-Notepad\r\nVersion 2.0\r\nCreated by \"LEEDONGGEON1996\" on Github");
		txtpnG.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnG.setEditable(false);
		txtpnG.setBackground(new Color(240, 240, 240));
		
		// list.
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(232)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(232, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(72)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnCryptonotepadVersion, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblIcon, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtpnG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addGap(65)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtpnG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIcon, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtpnCryptonotepadVersion, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnOk)
					.addContainerGap(40, Short.MAX_VALUE))
		);

		JLabel lblNewLabel = new JLabel("Crypto Notepad");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
		lblNewLabel.setForeground(new Color(0xffffff));

		JLabel lblSdf = new JLabel("sdf");
		lblSdf.setHorizontalAlignment(SwingConstants.CENTER);

		Image originImg = new ImageIcon(getClass().getClassLoader().getResource("encrypted_white_origin.png")).getImage();
		Image changedImg = originImg.getScaledInstance(71, 90, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);
		lblSdf.setIcon(Icon);

		JLabel lblAbout = new JLabel("About");
		lblAbout.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbout.setFont(new Font("Consolas", Font.BOLD, 18));
		lblAbout.setBackground(new Color(0x9730b0));
		lblAbout.setForeground(new Color(0xffffff));
		lblAbout.setOpaque(true);
		lblAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAbout.setBackground(new Color(0xffffff));
				lblAbout.setForeground(new Color(0x9730b0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAbout.setBackground(new Color(0x9730b0));
				lblAbout.setForeground(new Color(0xffffff));
			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(101, Short.MAX_VALUE)
					.addComponent(lblSdf, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(237)
					.addComponent(lblAbout, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(237, Short.MAX_VALUE))
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
					.addComponent(lblAbout, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

	}

	public void showDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setVisible(true);
		
		System.out.println(btnOk.getSize());
	}
}
