package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class KFontChooser extends JDialog {

	public static final int FONT_SIZE_CORRECTION = 7;
	private static final String TEST_STRING = "AaBbYyZz";
	private final JPanel contentPanel = new JPanel();
	private String[] allfonts;
	private String[] fonts;
	private String[] styles;
	private String[] sizes;
	private Font selectedFont;
	private Color selectedColor;
	private boolean isConfirmed;

	private JLabel txtrScriptView;
	private JList listFont, listStyle, listSize;

	private ArrayList<String> fontValidation() {
		//Font test = new Font("Arial", Font.PLAIN, 10);
		Font test;
		ArrayList<String> filtered = new ArrayList<String>();
		
		for(String fam : allfonts) {
			test = new Font(fam,Font.PLAIN, 10);
			if(test.canDisplay('a') && test.canDisplay('가')) {

				filtered.add(fam);
			}
		}
		return filtered;
	}
	
	public KFontChooser(JFrame jframe) {
		allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fonts = new String[] {};
		fonts = fontValidation().toArray(fonts);
		styles = new String[] { "Plain", "Italics", "Bold" };
		sizes = new String[] { "2", "4", "6", "8", "10", "11", "12", "13", "14", "16", "18", "20", "22", "24", "30",
				"36", "48", "72" };
		selectedFont = new Font("Courier", Font.PLAIN, 12);

		this.setTitle("Select Font");

		setBounds(100, 100, 556, 621);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblFont = new JLabel("Font:");

		JLabel lblFontStyle = new JLabel("Style:");

		JLabel lblFontSize = new JLabel("Size:");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\uBCF4\uAE30", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		txtrScriptView = new JLabel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(txtrScriptView, BorderLayout.CENTER);
		txtrScriptView.setText(TEST_STRING);
		int txtViewWidth = txtrScriptView.getParent().getWidth();
		int txtViewHeight = txtrScriptView.getParent().getHeight();
		txtrScriptView.setMinimumSize(new Dimension(txtViewWidth, txtViewHeight));
		txtrScriptView.setPreferredSize(new Dimension(txtViewWidth, txtViewHeight));
		txtrScriptView.setMaximumSize(new Dimension(txtViewWidth, txtViewHeight));

		//listFont = new JList(fonts);
		listFont = new JList(fonts);
		
		JScrollPane listFontScroll = new JScrollPane(listFont);
		listFontScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFont.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				//derive font로 코드량 줄일수 있을듯
				selectedFont = new Font(fonts[listFont.getSelectedIndex()], selectedFont.getStyle(),
						selectedFont.getSize());
				txtrScriptView.setFont(selectedFont);
			}
		});

		listStyle = new JList(styles);
		JScrollPane listStyleScroll = new JScrollPane(listStyle);
		listStyleScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStyle.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				selectedFont = new Font(selectedFont.getFamily(), getSelectedStyle(listStyle.getSelectedIndex()),
						selectedFont.getSize());
				txtrScriptView.setFont(selectedFont);
			}
		});

		listSize = new JList(sizes);
		JScrollPane listSizeScroll = new JScrollPane(listSize);
		listSizeScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSize.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				selectedFont = new Font(selectedFont.getFamily(), selectedFont.getStyle(),
						Integer.parseInt(sizes[listSize.getSelectedIndex()]) + FONT_SIZE_CORRECTION);
				txtrScriptView.setFont(selectedFont);
			}
		});

		JLabel lblScript = new JLabel("\uC2A4\uD06C\uB9BD\uD2B8");
		JTextField txtScript = new JTextField();
		txtScript.setText(TEST_STRING);
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

		JButton btnColor = new JButton("Color");
		JColorChooser cc = null;
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(cc, "Select Color", selectedColor);
				if (color != null) {
					selectedColor = color;
					txtrScriptView.setForeground(selectedColor);
				}
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
	}

	public boolean showDialog(Font curFont, Color curColor) {
		isConfirmed = false;

		selectedFont = curFont;
		selectedColor = curColor;

		txtrScriptView.setFont(curFont);
		txtrScriptView.setForeground(curColor);

		listFont.setSelectedValue(curFont.getFamily(), true);
		listStyle.setSelectedIndex(getStyleIdx(curFont.getStyle()));
		listSize.setSelectedValue(String.valueOf(curFont.getSize() - FONT_SIZE_CORRECTION), true);

		setVisible(true);
		return isConfirmed;
	}

	private int getSelectedStyle(int selectedIdx) {
		int style = Font.PLAIN;
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

	private int getStyleIdx(int style) {
		int idx = 0;
		if (style == Font.PLAIN) {
			idx = 0;
		}
		if (style == Font.ITALIC) {
			idx = 1;
		}
		if (style == Font.BOLD) {
			idx = 2;
		}
		return idx;
	}

	public Font getSelctedFont() {
		return selectedFont;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

}
