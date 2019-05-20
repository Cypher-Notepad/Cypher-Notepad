package UI.Custom;

import java.awt.*;
import java.awt.event.*;

//
// Name: David Escobedo
// Project: 2
// Due: 3/7/18
// Course: CS-245-01-w18
// Description: JFontChooser class used by FontChooserDemo
//

import javax.swing.*;
import javax.swing.event.*;
import java.awt.Font;
public class KFontChooser_T {
    private Font font;
    private Color c;
    private String[] fonts;
    private String[] sizes;
    private String[] styles={"Plain","Italics","Bold"};
    JList jlstF;
    JList jlstS;
    JList jlstSt;
    JScrollPane js;
    JScrollPane js2;
    JScrollPane js3;
    JLabel lbl;
    JLabel lbl2;
    JLabel lbl3;
    JLabel txt;
    JButton bttn;
    JButton bttnColor;
    JColorChooser jcolor;
    boolean test=false;
    /**
     * @param args the command line arguments
     */
        public KFontChooser_T() {
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        sizes = new String[30];
        for(int i = 0;i < sizes.length; i++) {
                sizes[i] = (String.valueOf(i+12));
        }
        font = new Font("Courier",Font.PLAIN,12);

    }
    public boolean showDialog(JFrame jfrm) {
        JDialog jdg = new JDialog(jfrm,"JFontChooser", true);
        jdg.setLayout(new FlowLayout());
        jdg.setSize(350,700);
        jdg.setLocationRelativeTo(jfrm);

        JPanel j = new JPanel();
        j.setLayout(new FlowLayout());
        j.setPreferredSize(new Dimension(100, 600));
        j.setOpaque(true);
        jlstF = new JList(fonts);
        jlstF.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lbl = new JLabel("Font Name");
        js = new JScrollPane(jlstF);
        js.setPreferredSize(new Dimension(100,600));
        lbl.setLabelFor(js);
        j.add(lbl);
        j.add(js);

        JPanel j1 = new JPanel();
        j1.setLayout(new FlowLayout());
        j1.setPreferredSize(new Dimension(100, 600));
        j1.setOpaque(true);
        lbl2 = new JLabel("Font Size");
        jlstS = new JList(sizes);
        jlstS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        js2 = new JScrollPane(jlstS);
        js2.setPreferredSize(new Dimension(100,600));
        lbl2.setLabelFor(js2);
        j1.add(lbl2);
        j1.add(js2);

        JPanel j2 = new JPanel();
        j2.setLayout(new FlowLayout());
        j2.setPreferredSize(new Dimension(100, 600));
        j2.setOpaque(true);
        lbl3 = new JLabel("Font Style");
        jlstSt = new JList(styles);
        jlstSt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        js3 = new JScrollPane(jlstSt);
        js3.setPreferredSize(new Dimension(100,600));
        lbl3.setLabelFor(js3);
        j2.add(lbl3);
        j2.add(js3);

        JPanel mainJ = new JPanel();
        mainJ.setPreferredSize(new Dimension(350, 700));
        mainJ.setLayout(new FlowLayout());
        mainJ.add(j);
        mainJ.add(j1);
        mainJ.add(j2);
        txt = new JLabel("Sample Text");
        txt.setForeground(c);
        txt.setFont(font);
        jdg.add(txt);
        bttn = new JButton("Okay");
        mainJ.add(bttn);
        bttnColor = new JButton("Color");
        mainJ.add(bttnColor);
        jdg.add(mainJ);

        bttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    System.out.println(font);
                    jdg.dispose();
        }});
        bttnColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    c = JColorChooser.showDialog(jcolor, "Select Color", c);
                    txt.setForeground(c);
        }});
                //Listener for the list that holds fonts
         jlstF.addListSelectionListener((ListSelectionEvent le) -> {
            int val = jlstF.getSelectedIndex();
            font = new Font(fonts[val],font.getStyle(),font.getSize());
            txt.setFont(font);
        });
                 //Listener for the list that holds sizes
        jlstS.addListSelectionListener((ListSelectionEvent le) -> {
            int val = jlstS.getSelectedIndex();
            font = new Font(font.getFontName(),font.getStyle(),Integer.parseInt(sizes[val]));
            txt.setFont(font);
        });
        //Listener for the list that holds styles
        jlstSt.addListSelectionListener((ListSelectionEvent le) -> {
            int val = jlstSt.getSelectedIndex();
            int sty = 0;
            if(styles[val].equals("Bold")) {
                sty = Font.BOLD;
            }
            if(styles[val].equals("Plain")) {
                sty = Font.PLAIN;

            }
            if(styles[val].equals("Italics")) {
                sty = Font.ITALIC;
            }
            font = new Font(font.getFontName(),sty,font.getSize());
            txt.setFont(font);
        });
        jdg.setVisible(true);
        return true;
    }

    void setDefault(Font font) {
        font = new Font(font.getFontName(),font.getStyle(),font.getSize());
    }

    void setDefault(Color color) {
        c = color;
    }
   public Font getFont() {
        return new Font(font.getFontName(),font.getStyle(),font.getSize());
    }
   public Color getColor() {
       return c;
   }


}


