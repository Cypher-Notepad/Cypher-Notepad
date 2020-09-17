package ui.custom;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import config.Language;
import config.Property;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class KUpdater extends JDialog {

	private static final long serialVersionUID = -4760411000350458806L;
	
	private static final String latestVersionURL = "https://Cypher-Notepad.github.io/version.html";
	private static final String latestReleaseNoteURL = "https://Cypher-Notepad.github.io/wiki/#/release_note?id=latest-release";
	
	private JPanel contentPane;
	private JTextArea txtrConsole; 
	private JButton btnCheck;
	private JLabel lblResult;
	public boolean isConfirmed;

	private Language lang;
	
	/**
	 * Create the dialog.
	 */
	public KUpdater() {
		lang = Property.getLanguagePack();

		setBounds(100, 100, 550, 644);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		this.setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x68217A));

		btnCheck = new JButton(lang.kuCheck);
		btnCheck.addActionListener(e -> {
			btnCheck.setEnabled(false);

			if(btnCheck.getText().equals(lang.kuCheck)) {
				TrVersionChecker versionChecker = new TrVersionChecker();
				versionChecker.start();

			} else {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					try {
						Desktop.getDesktop()
						.browse(new URI(latestReleaseNoteURL));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				btnCheck.setEnabled(true);
			}
		});

		JButton btnCancel = new JButton(lang.btnCancel);
		btnCancel.addActionListener(e -> {
			isConfirmed = false;
			setVisible(false);
		});

		lblResult = new JLabel(lang.kuDefaultResult);
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(95)
					.addComponent(btnCheck, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(102))
				.addComponent(lblResult, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(94)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(97, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblResult)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCheck)
						.addComponent(btnCancel))
					.addGap(36))
		);

		txtrConsole = new JTextArea();
		txtrConsole.setText("");
		txtrConsole.setEditable(false);
		scrollPane.setViewportView(txtrConsole);

		JLabel lblNewLabel = new JLabel(lang.cypherNotepad);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 39));
		lblNewLabel.setForeground(new Color(0xffffff));

		JLabel lblSdf = new JLabel("sdf");
		lblSdf.setHorizontalAlignment(SwingConstants.CENTER);

		Image originImg = new ImageIcon(getClass().getClassLoader().getResource("encrypted_white_origin.png"))
				.getImage();
		Image changedImg = originImg.getScaledInstance(71, 90, Image.SCALE_SMOOTH);
		ImageIcon Icon = new ImageIcon(changedImg);
		lblSdf.setIcon(Icon);

		JLabel lblSetting = new JLabel("Update");
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
	
	public void showDialog() {
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class TrVersionChecker extends Thread {
		//public static final int CHECKING = -1;
		public static final int PREPARED = 0;
		public static final int LATEST = 1; 
		public static final int OUTDATED = 2;  

		public int result = PREPARED;
		
		private BufferedReader in = null;
		private String latestVersion = "";
		
		public void run() {	
			try {
				lblResult.setText(lang.kuCheckingResult);
				txtrConsole.setText("Start to check version of Cypher Notepad....\n");
				txtrConsole.append("connecting to server...");
				
				URL oracle = new URL(latestVersionURL);
				in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			
				txtrConsole.append("OK\n");
				txtrConsole.append("getting the latest version...");
				
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					latestVersion += inputLine;
				String curVersion = (String) Property.getProperties().get(Property.version);
				
				txtrConsole.append("OK\n");
				txtrConsole.append("\n===============\n");
				txtrConsole.append(lang.kuCurVersion + curVersion + "\n");
				txtrConsole.append(lang.kuLatVersion + latestVersion);
				
				if(Integer.parseInt(latestVersion.replace(".", "")) 
						> Integer.parseInt(curVersion.replace(".", ""))){
					result = OUTDATED;
					lblResult.setText(lang.kuNewVersionResult_pre + lang.cypherNotepad + " " + latestVersion + lang.kuNewVersionResult_post);
				} else {
					result = LATEST;
					lblResult.setText(lang.kuUpToDate);
				}
				
				if(result == TrVersionChecker.OUTDATED) {
					if(btnCheck.isShowing()) {
						btnCheck.setText(lang.kuGetTheLatestVersion);
					}
				} 
				btnCheck.setEnabled(true);
				
				
			} catch (IOException e) {
				lblResult.setText("");
				txtrConsole.append("\n===============\n");
				txtrConsole.append(lang.kuFailedToConnect);
			}
		}
	}
}
