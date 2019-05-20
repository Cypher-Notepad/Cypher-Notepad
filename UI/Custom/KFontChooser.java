package UI.Custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import UI.UI;

public class KFontChooser extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String[] fonts;
	private String[] styles;
	private String[] sizes;
	private Font selectedFont;
	private Color fontColor;
	private boolean isConfirmed;

	public KFontChooser() {
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		styles = new String[] { "Plain", "Italics", "Bold" };
		sizes = new String[] { "2", "4", "6", "8", "10", "11", "12", "13", "14", "16", "18", "20", "22", "24", "30",
				"36", "48", "72" };
		selectedFont = new Font("Courier", Font.PLAIN, 12);

	}

	public boolean showDialog(JFrame jframe, Font curFont, Color curColor) {
		/*
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String[] styles = { "plain", "italics", "bold" };
		String[] sizes = new String[] { "2", "4", "6", "8", "10", "11", "12", "13", "14", "16", "18", "20", "22", "24",
				"30", "36", "48", "72" };
		*/
		this.setTitle("Select Font");
		selectedFont = curFont;

		setBounds(100, 100, 556, 621);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblFont = new JLabel("Font:");

		JLabel lblFontStyle = new JLabel("Style:");

		JLabel lblFontSize = new JLabel("Size:");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\uBCF4\uAE30", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel txtrScriptView = new JLabel();
		txtrScriptView.setText("AaBbYyZz");

		JList listFont = new JList(fonts);
		JScrollPane listFontScroll = new JScrollPane(listFont);
		listFontScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFont.setSelectedValue(curFont.getFamily(), true);
		listFont.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				selectedFont = new Font(fonts[listFont.getSelectedIndex()], selectedFont.getStyle(), selectedFont.getSize());
				txtrScriptView.setFont(selectedFont);
			}
		});

		JList listStyle = new JList(styles);
		JScrollPane listStyleScroll = new JScrollPane(listStyle);
		listStyleScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String fontName = curFont.getFontName();
		String curStyle = fontName.substring(fontName.indexOf(".")+1, fontName.length());
		curStyle = curStyle.replace(curStyle.charAt(0), curStyle.substring(0, 1).toUpperCase().charAt(0));
		System.out.println(curStyle);
		listStyle.setSelectedValue(curStyle, true);
		listStyle.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				selectedFont = new Font(selectedFont.getFamily(), getSelectedStyle(listStyle.getSelectedIndex()), selectedFont.getSize());
				txtrScriptView.setFont(selectedFont);
			}
		});

		JList listSize = new JList(sizes);
		JScrollPane listSizeScroll = new JScrollPane(listSize);
		listSizeScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSize.setSelectedValue(String.valueOf(curFont.getSize()), true);
		listSize.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				selectedFont = new Font(selectedFont.getFamily(), selectedFont.getStyle(), Integer.parseInt(sizes[listSize.getSelectedIndex()]));
				txtrScriptView.setFont(selectedFont);
			}
		});
		
		JLabel lblScript = new JLabel("\uC2A4\uD06C\uB9BD\uD2B8");
		JTextField txtScript = new JTextField();
		txtScript.setText("AaBbYyZz");
		txtScript.setColumns(10);
		txtScript.getDocument().addDocumentListener(new DocumentListener() {
			private void update() {
				txtrScriptView.setText(txtScript.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				update();
			}
		});

		JButton btnColor = new JButton("color");
		JColorChooser cc = null;
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontColor = JColorChooser.showDialog(cc, "Select Color", fontColor);
				txtrScriptView.setForeground(fontColor);
			}
		});

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addGap(24)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblFont)
								.addComponent(
										listFontScroll, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnColor))
						.addGap(27)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblScript)
								.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblFontStyle).addComponent(listStyleScroll,
														GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblFontSize).addComponent(listSizeScroll,
														GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
								.addComponent(txtScript, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 269,
										Short.MAX_VALUE)
								.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 269,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup().addGap(22)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblFont)
						.addComponent(lblFontStyle).addComponent(lblFontSize))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(listFontScroll, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
						.addComponent(listStyleScroll, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addComponent(listSizeScroll, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
				.addGap(50)
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
								.addGap(39).addComponent(lblScript).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtScript, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(btnColor))
				.addContainerGap(73, Short.MAX_VALUE)));

		panel.setLayout(new BorderLayout(0, 0));
		panel.add(txtrScriptView, BorderLayout.CENTER);

		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(e -> {
					isConfirmed = true;
					setVisible(false);
				});

			}
			{
				JButton cancelButton = new JButton("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(e -> {
					isConfirmed = false;
					setVisible(false);
				});
			}
		}

		setLocationRelativeTo(jframe);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setVisible(true);

		return isConfirmed;
	}

	private int getSelectedStyle(int selectedIdx) {
		int style = Font.BOLD;
		if (styles[selectedIdx].equals("Bold")) {
			style = Font.BOLD;
		}
		if (styles[selectedIdx].equals("Plain")) {
			style = Font.PLAIN;

		}
		if (styles[selectedIdx].equals("Italics")) {
			style = Font.ITALIC;
		}
		return style;
	}
	
	public Font getSelctedFont() {
		return selectedFont;
	}

}
