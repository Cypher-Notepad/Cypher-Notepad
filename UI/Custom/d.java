package UI.Custom;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class d extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			d dialog = new d();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public d() {
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String[] styles={"Plain","Italics","Bold"};
		String[] sizes = new String[] { "2","4","6","8","10","11","12","13","14","16","18","20","22","24","30","36","48","72" };
        Font font = new Font("Courier",Font.PLAIN,12);
		
		
		setBounds(100, 100, 556, 621);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblFont = new JLabel("Font:");
		
		JLabel lblFontStyle = new JLabel("Style:");
		
		JLabel lblFontSize = new JLabel("Size:");
		
		JList listFont = new JList(fonts);
		
		JList listStyle = new JList(styles);
		
		JList listSize = new JList(sizes);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\uBCF4\uAE30", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblScript = new JLabel("\uC2A4\uD06C\uB9BD\uD2B8");
		
		txtScript = new JTextField();
		txtScript.setText("AaBbYyZz");
		txtScript.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFont)
						.addComponent(listFont, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblScript)
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFontStyle)
								.addComponent(listStyle, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFontSize)
								.addComponent(listSize, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
						.addComponent(txtScript, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFont)
						.addComponent(lblFontStyle)
						.addComponent(lblFontSize))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(listFont, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
						.addComponent(listStyle, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addComponent(listSize, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
					.addGap(50)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(lblScript)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtScript, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(73, Short.MAX_VALUE))
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrScriptView = new JTextArea();
		txtrScriptView.setText("asdfsadf");
		panel.add(txtrScriptView, BorderLayout.CENTER);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	
	public static int OK_OPTION = 0;
    public static int CANCEL_OPTION = 1;

    private JList fontList, sizeList;
    private JCheckBox cbBold, cbItalic;
    private JTextArea txtSample;

    private int OPTION;

    private final String[] sizes = new String[]
            { "2","4","6","8","10","11","12","13","14","16","18","20","22","24","30","36","48","72" };
    private JTextField txtScript;

    public int showDialog(Font font)
    {
        setFont(font);
        return showDialog();
    }

    public int showDialog()
    {
        setVisible(true);

        return OPTION;
    }
/*
    public JFontChooser(Frame parent)
    {
        
    }
*/
    @Override
    public void setFont(Font font)
    {
        if (font == null) font = txtSample.getFont();

        fontList.setSelectedValue(font.getName(), true);
        fontList.ensureIndexIsVisible(fontList.getSelectedIndex());
        sizeList.setSelectedValue("" + font.getSize(), true);
        sizeList.ensureIndexIsVisible(sizeList.getSelectedIndex());

        cbBold.setSelected(font.isBold());
        cbItalic.setSelected(font.isItalic());
    }

    @Override
    public Font getFont()
    {
        if (OPTION == OK_OPTION)
        {
            return getCurrentFont();
        }
        else return null;
    }

    private Font getCurrentFont()
    {
        try {
            String fontFamily = (String)fontList.getSelectedValue();
            int fontSize = Integer.parseInt((String)sizeList.getSelectedValue());

            int fontType = Font.PLAIN;

            if (cbBold.isSelected()) fontType += Font.BOLD;
            if (cbItalic.isSelected()) fontType += Font.ITALIC;
            return new Font(fontFamily, fontType, fontSize);
        } catch (Exception ex) {
            // if error return current sample font.
            return txtSample.getFont();
        }
    }
}
