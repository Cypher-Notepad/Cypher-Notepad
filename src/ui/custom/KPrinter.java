package ui.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextArea;


public class KPrinter implements ActionListener, Printable {

	private JTextArea textArea;

	public KPrinter(JTextArea textArea) {
		this.textArea = textArea;
		
	}

	public int print(Graphics gx, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;
		}
		Graphics2D g = (Graphics2D) gx;
		g.translate(pf.getImageableX(), pf.getImageableY());
		g.drawString(textArea.getText(), 100, 100);
		System.out.println("text is: " + textArea.getText());
		return PAGE_EXISTS;
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
					System.out.println("Printing failed");
				}
			}
		}
		else if (action.equals("PageSetup")) {
			PrinterJob pj = PrinterJob.getPrinterJob();
			PageFormat pf = pj.pageDialog(pj.defaultPage());
			pj.setPrintable(this, pf);
		}
		
	}

}
