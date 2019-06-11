package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import Config.Property;
import File.FileManager;
import UI.Custom.JFontChooser;
import UI.Custom.KFinder;
import UI.Custom.KFontChooser;
import UI.Custom.KFontChooser_T;
import UI.Custom.KPrinter;
import VO.MemoVO;

public class NotepadUI extends JFrame implements UI {
	// Frame
	public JFrame frame;
	// Menu bar
	public JMenuBar menuBar;
	// Menus
	public JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	// Menu items
	public JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, pageSetupMenuItem, printMenuItem,
			exitMenuItem, undoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, findMenuItem,
			findNextMenuItem, replaceMenuItem, goToMenuItem, selectAllMenuItem, timeDateMenuItem, fontMenuItem,
			statusBarMenuItem, viewHelpMenuItem, aboutNotepadMenuItem;

	public JCheckBoxMenuItem wordWrapMenuItem;
	// Text area
	public JTextArea textArea;

	public String fileName;
	public File directory;
	public String savedContext;

	private KFontChooser fc;
	private KPrinter pt;
	private KFinder fd;
	
	public NotepadUI() {
		fileName = "Untitled";
		frame = new JFrame(fileName + " - Notepad");
		textArea = new JTextArea();
		savedContext = "";

	}
	public NotepadUI(File file) {
		String path;
		try {
			path = file.getCanonicalPath();
			directory = new File(path.substring(0, path.lastIndexOf("\\")));
			fileName = path.substring(path.lastIndexOf("\\") + 1);
			MemoVO loadedContent = FileManager.getInstance().loadMemo(path);
			savedContext = loadedContent.getContent();
			frame = new JFrame(fileName + " - Notepad");
			textArea = new JTextArea();
			textArea.setText(savedContext);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/*
		fileName = "Untitled";
		frame = new JFrame(fileName + " - Notepad");
		*/
		
		// Bar
		menuBar = new JMenuBar();

		// menu
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		formatMenu = new JMenu("Format");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Help");

		// menu mnemonic keys
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		formatMenu.setMnemonic(KeyEvent.VK_O);
		viewMenu.setMnemonic(KeyEvent.VK_V);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		// add menu to bar, but not set yet.
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(formatMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		// menu items
		newMenuItem = new JMenuItem("New");
		openMenuItem = new JMenuItem("Open...");
		saveMenuItem = new JMenuItem("Save");
		saveAsMenuItem = new JMenuItem("Save As...");
		pageSetupMenuItem = new JMenuItem("Page Setup...");
		printMenuItem = new JMenuItem("Print...");
		exitMenuItem = new JMenuItem("Exit");

		undoMenuItem = new JMenuItem("Undo");
		cutMenuItem = new JMenuItem("Cut");
		copyMenuItem = new JMenuItem("Copy");
		pasteMenuItem = new JMenuItem("Paste");
		deleteMenuItem = new JMenuItem("Delete");
		findMenuItem = new JMenuItem("Find...");
		findNextMenuItem = new JMenuItem("Find Next");
		replaceMenuItem = new JMenuItem("Replace...");
		goToMenuItem = new JMenuItem("Go To...");
		selectAllMenuItem = new JMenuItem("Select All");
		timeDateMenuItem = new JMenuItem("Time/Date");

		wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
		fontMenuItem = new JMenuItem("Font...");

		statusBarMenuItem = new JMenuItem("Status Bar");

		viewHelpMenuItem = new JMenuItem("View Help");
		aboutNotepadMenuItem = new JMenuItem("About Notepad");

		//textArea = new JTextArea();
		//savedContext = "";

		fc = new KFontChooser(this);
		pt = new KPrinter(textArea);
		fd = new KFinder(textArea);

		// sub menu accelerators keys
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));

		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		findMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
		findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
		goToMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		timeDateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

		// sub menu mnemonic keys
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		openMenuItem.setMnemonic(KeyEvent.VK_O);
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveAsMenuItem.setMnemonic(KeyEvent.VK_A);
		saveAsMenuItem.setDisplayedMnemonicIndex(5);
		pageSetupMenuItem.setMnemonic(KeyEvent.VK_U);
		printMenuItem.setMnemonic(KeyEvent.VK_P);
		exitMenuItem.setMnemonic(KeyEvent.VK_X);

		undoMenuItem.setMnemonic(KeyEvent.VK_U);
		cutMenuItem.setMnemonic(KeyEvent.VK_T);
		copyMenuItem.setMnemonic(KeyEvent.VK_C);
		pasteMenuItem.setMnemonic(KeyEvent.VK_P);
		deleteMenuItem.setMnemonic(KeyEvent.VK_L);
		findMenuItem.setMnemonic(KeyEvent.VK_F);
		findNextMenuItem.setMnemonic(KeyEvent.VK_N);
		findNextMenuItem.setDisplayedMnemonicIndex(5);
		replaceMenuItem.setMnemonic(KeyEvent.VK_R);
		goToMenuItem.setMnemonic(KeyEvent.VK_G);
		selectAllMenuItem.setMnemonic(KeyEvent.VK_A);
		timeDateMenuItem.setMnemonic(KeyEvent.VK_D);

		wordWrapMenuItem.setMnemonic(KeyEvent.VK_W);
		fontMenuItem.setMnemonic(KeyEvent.VK_F);

		statusBarMenuItem.setMnemonic(KeyEvent.VK_S);

		viewHelpMenuItem.setMnemonic(KeyEvent.VK_H);
		aboutNotepadMenuItem.setMnemonic(KeyEvent.VK_A);

		// add items to menus
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(pageSetupMenuItem);
		fileMenu.add(printMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		editMenu.add(undoMenuItem);
		editMenu.addSeparator();
		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.add(deleteMenuItem);
		editMenu.addSeparator();
		editMenu.add(findMenuItem);
		editMenu.add(findNextMenuItem);
		editMenu.add(replaceMenuItem);
		editMenu.add(goToMenuItem);
		editMenu.addSeparator();
		editMenu.add(selectAllMenuItem);
		editMenu.add(timeDateMenuItem);

		formatMenu.add(wordWrapMenuItem);
		formatMenu.add(fontMenuItem);

		viewMenu.add(statusBarMenuItem);

		helpMenu.add(viewHelpMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutNotepadMenuItem);

		// sets it
		frame.setJMenuBar(menuBar);
		frame.add(textArea);

		// actions
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (textArea.getText().trim().length() != 0) {
					System.out.println("Contains Text");
					if (checkSave()) {
						textArea.setText(null);
						directory = null;
						fileName = "Untitled";
						frame.setTitle(fileName + " - Notepad");
					}
				}
			}
		});

		openMenuItem.addActionListener(e -> {
			if (checkSave()) {
				JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(frame);
				if(response == fc.APPROVE_OPTION) {
					String selectedPath;
					try {
						selectedPath = fc.getSelectedFile().getCanonicalPath();
						MemoVO memo = FileManager.getInstance().loadMemo(selectedPath);
						textArea.setText(memo.getContent());
						directory = new File(selectedPath.substring(0, selectedPath.lastIndexOf("\\")));
						fileName = selectedPath.substring(selectedPath.lastIndexOf("\\") + 1);
						frame.setTitle(fileName + " - Notepad");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (directory != null) {
					saveMemo();
				} else {
					saveAsAction();
				}
			}
		});

		saveAsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				saveAsAction();
			}
		});

		// pagesetup
		pageSetupMenuItem.setActionCommand("PageSetup");
		pageSetupMenuItem.addActionListener(pt);
		// print
		printMenuItem.setActionCommand("Print");
		printMenuItem.addActionListener(pt);

		// exit
		exitMenuItem.addActionListener(e -> erase());
		//
		// undo
		// cut
		cutMenuItem.addActionListener(e -> textArea.cut());
		// copy
		copyMenuItem.addActionListener(e -> textArea.copy());
		// paste
		pasteMenuItem.addActionListener(e -> textArea.paste());
		// delete
		deleteMenuItem.addActionListener(e -> textArea.replaceSelection(""));

		// find
		findMenuItem.addActionListener(e -> fd.showDialog());

		// findnext
		findNextMenuItem.addActionListener(fd);
		// replace

		// goto
		goToMenuItem.setEnabled(false);

		// selectall
		selectAllMenuItem.addActionListener(e -> textArea.selectAll());
		// timedate
		timeDateMenuItem.addActionListener(e -> {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR);
			int minute = cal.get(Calendar.MINUTE);
			int amPm = cal.get(Calendar.AM_PM);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int year = cal.get(Calendar.YEAR);
			String ampm = "";
			if (amPm == 0) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}

			int pos = textArea.getCaretPosition();
			textArea.insert(String.format("%2d:%2d " + ampm + "%2d/%2d/%4d", hour, minute, month, day, year), pos);
		});

		// wordwrap
		wordWrapMenuItem.addActionListener(e -> {
			if (wordWrapMenuItem.isSelected()) {
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
			} else {
				textArea.setLineWrap(false);
				textArea.setWrapStyleWord(false);
			}
		});

		// font
		fontMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (showFontChooser()) {
					textArea.setFont(fc.getSelctedFont());
					textArea.setForeground(fc.getSelectedColor());
				}
			}
		});
		// statusbar
		// view
		// about

		/*
		 * // menu items newMenuItem = new JMenuItem("New"); openMenuItem = new
		 * JMenuItem("Open..."); saveMenuItem = new JMenuItem("Save"); saveAsMenuItem =
		 * new JMenuItem("Save As..."); pageSetupMenuItem = new
		 * JMenuItem("Page Setup..."); printMenuItem = new JMenuItem("Print...");
		 * exitMenuItem = new JMenuItem("Exit");
		 * 
		 * undoMenuItem = new JMenuItem("Undo"); cutMenuItem = new JMenuItem("Cut");
		 * copyMenuItem = new JMenuItem("Copy"); pasteMenuItem = new JMenuItem("Paste");
		 * deleteMenuItem = new JMenuItem("Delete"); findMenuItem = new
		 * JMenuItem("Find..."); findNextMenuItem = new JMenuItem("Find Next");
		 * replaceMenuItem = new JMenuItem("Replace..."); goToMenuItem = new
		 * JMenuItem("Go To..."); selectAllMenuItem = new JMenuItem("Select All");
		 * timeDateMenuItem = new JMenuItem("Time/Date");
		 * 
		 * wordWrapMenuItem = new JMenuItem("Word Wrap"); fontMenuItem = new
		 * JMenuItem("Font...");
		 * 
		 * statusBarMenuItem = new JMenuItem("Status Bar");
		 * 
		 * viewHelpMenuItem = new JMenuItem("View Help"); aboutNotepadMenuItem = new
		 * JMenuItem("About Notepad");
		 * 
		 */

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1450, 750);
		frame.setResizable(true);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// call the function, erase().
				UIManager.getInstance().closeWindow();
			}
		});
	}

	@Override
	public void erase() {
		// TODO Auto-generated method stub
		if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this window?", "Close Window?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			this.dispose();
			FileManager.getInstance().saveProperties();
			System.exit(0);
		}
	}

	public void saveAsAction() {
		// finalChooser
		final JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("*.txt"));

		int userSelection = fc.showSaveDialog(frame);

		if (userSelection == fc.APPROVE_OPTION) {
			fileName = fc.getSelectedFile().getName();
			directory = fc.getCurrentDirectory();
			saveMemo();

			Property.addRecentFiles(directory + "\\" + fileName);

			frame.setTitle(fileName + " - Notepad");
		} else if (userSelection == fc.CANCEL_OPTION) {
			System.out.println("Cancel selected!");
		} else if (userSelection == fc.ERROR_OPTION) {
			System.out.println("Error detected!");
		}
	}

	public void saveMemo() {
		String filePath = directory + "\\" + fileName;
		MemoVO memo = new MemoVO();
		savedContext = textArea.getText();
		memo.setContent(savedContext);
		FileManager.getInstance().saveMemo(filePath, memo);
	}

	private boolean showFontChooser() {
		return fc.showDialog(textArea.getFont(), textArea.getForeground());
	}

	public boolean checkSave() {
		if (savedContext != textArea.getText()) {
			Object[] options = { "Save", "Don't Save", "Cancel" };

			int response = JOptionPane.showOptionDialog(frame,
					"Your work is not saved. Do you want to save changes to Untitled?", "Notepad",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			if (response == JOptionPane.YES_OPTION) {
				if (directory != null) {
					saveMemo();
				} else {
					saveAsAction();
				}
				return true;
			} else if (response == JOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
