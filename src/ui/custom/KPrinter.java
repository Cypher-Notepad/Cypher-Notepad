package ui.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicTextUI;
import javax.swing.text.BoxView;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.StyleContext;
import javax.swing.text.View;


public class KPrinter implements ActionListener, Printable {

	private JTextArea textAreaToPrint;
	private JTextArea textArea;
	protected PrintView m_printView;
	protected DefaultStyledDocument m_doc;
    StyleContext m_context;

	public KPrinter(JTextArea textArea) {
		this.textAreaToPrint = textArea;
	}
	
	private void createDummyTextArea() {
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		m_context = new StyleContext();
		m_doc = new DefaultStyledDocument(m_context);
		textArea.setDocument(m_doc);
	}
	
	public int print(Graphics pg, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
		createDummyTextArea();
        textArea.setText(textAreaToPrint.getText());
		
		pg.translate((int)pageFormat.getImageableX(),
		        (int)pageFormat.getImageableY());
		        int wPage = (int)pageFormat.getImageableWidth();
		        int hPage = (int)pageFormat.getImageableHeight();
		        pg.setClip(0, 0, wPage, hPage);
		         
		        // Only do this once per print
		        if (m_printView == null) {
		            BasicTextUI btui = (BasicTextUI)textArea.getUI();
		            View root = btui.getRootView( textArea );
		            m_printView = new PrintView(
		            m_doc.getDefaultRootElement(),
		            root, wPage, hPage);
		        }
		         
		        boolean bContinue = m_printView.paintPage(pg,
		        hPage, pageIndex);
		        System.gc();
		         
		        if (bContinue)
		            return PAGE_EXISTS;
		        else {
		            m_printView = null;
		            return NO_SUCH_PAGE;
		        }
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("Print")) {
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(this);
			if (job.printDialog() == true) {
				try {
					job.print();
				} catch (PrinterException ex) {
					System.out.println("[KPrinter] Printing failed");
				}
			}
		}
		else if (action.equals("PageSetup")) {
			PrinterJob pj = PrinterJob.getPrinterJob();
			PageFormat pf = pj.pageDialog(pj.defaultPage());
			pj.setPrintable(this, pf);
		}
		
	}
	
    private class PrintView extends BoxView {
        protected int m_firstOnPage = 0;
        protected int m_lastOnPage = 0;
        protected int m_pageIndex = 0;
         
        public PrintView(Element elem, View root, int w, int h) {
            super(elem, Y_AXIS);
            setParent(root);
            setSize(w, h);
            layout(w, h);
        }
         
        public boolean paintPage(Graphics g, int hPage,
        int pageIndex) {
            if (pageIndex > m_pageIndex) {
                m_firstOnPage = m_lastOnPage + 1;
                if (m_firstOnPage >= getViewCount())
                    return false;
                m_pageIndex = pageIndex;
            }
            int yMin = getOffset(Y_AXIS, m_firstOnPage);
            int yMax = yMin + hPage;
            Rectangle rc = new Rectangle();
             
            for (int k = m_firstOnPage; k < getViewCount(); k++) {
                rc.x = getOffset(X_AXIS, k);
                rc.y = getOffset(Y_AXIS, k);
                rc.width = getSpan(X_AXIS, k);
                rc.height = getSpan(Y_AXIS, k);
                if (rc.y+rc.height > yMax)
                    break;
                m_lastOnPage = k;
                rc.y -= yMin;
                paintChild(g, rc, k);
            }
            return true;
        }
    }

}
