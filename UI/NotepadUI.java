package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.ImageIcon;
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
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


import Config.Property;
import File.FileManager;
import UI.Custom.JFontChooser;
import UI.Custom.KFinder;
import UI.Custom.KFontChooser;
import UI.Custom.KFontChooser_T;
import UI.Custom.KInformation;
import UI.Custom.KPrinter;
import UI.Custom.KReplacer;
import UI.Custom.KSettings;
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
			statusBarMenuItem, viewHelpMenuItem, aboutNotepadMenuItem, settingsMenuItem;

	public JCheckBoxMenuItem wordWrapMenuItem;
	// Text area
	public JTextArea textArea;

	public String fileName;
	public File directory;
	public String savedContext;

	private JFileChooser fc;
	private KFontChooser fontChooser;
	private KPrinter pt;
	private KFinder fd;
	private KReplacer rp;
	private KInformation info;
	private KSettings st;

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

	public void initializeUI() {
		//reduce the time for loading.
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			String[][] icons = { { "OptionPane.errorIcon", "65581" }, { "OptionPane.warningIcon", "65577" },
					{ "OptionPane.questionIcon", "65579" }, { "OptionPane.informationIcon", "65583" } };
			// obtain a method for creating proper icons
			Method getIconBits = Class.forName("sun.awt.shell.Win32ShellFolder2").getDeclaredMethod("getIconBits",
					new Class[] { long.class, int.class });
			getIconBits.setAccessible(true);
			// calculate scaling factor
			double dpiScalingFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
			int icon32Size = (dpiScalingFactor == 1) ? (32)
					: ((dpiScalingFactor == 1.25) ? (40)
							: ((dpiScalingFactor == 1.5) ? (45) : ((int) (32 * dpiScalingFactor))));
			Object[] arguments = { null, icon32Size };
			for (String[] s : icons) {
				if (javax.swing.UIManager.get(s[0]) instanceof ImageIcon) {
					arguments[0] = Long.valueOf(s[1]);
					// this method is static, so the first argument can be null
					int[] iconBits = (int[]) getIconBits.invoke(null, arguments);
					if (iconBits != null) {
						// create an image from the obtained array
						BufferedImage img = new BufferedImage(icon32Size, icon32Size, BufferedImage.TYPE_INT_ARGB);
						img.setRGB(0, 0, icon32Size, icon32Size, iconBits, 0, icon32Size);
						ImageIcon newIcon = new ImageIcon(img);
						// override previous icon with the new one
						javax.swing.UIManager.put(s[0], newIcon);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Properties p = Property.getProperties();
		Font textFont = new Font(p.getProperty(Property.fontFamily),
				Integer.parseInt(p.getProperty(Property.fontStyle)),
				Integer.parseInt(p.getProperty(Property.fontSize)) + KFontChooser.FONT_SIZE_CORRECTION);
		textArea.setFont(textFont);
		textArea.setForeground(new Color(Integer.parseInt(p.getProperty(Property.fontColor))));

		// Bar
		menuBar = new JMenuBar();

		// menu
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		formatMenu = new JMenu("Format");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Help");

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
		settingsMenuItem = new JMenuItem("Settings");

		
		new Thread() {
			public void run() {
				System.out.println("[Frame] settings()");
				settings();
			}
		}.start();
		
		System.out.println("notepad init finish");
		
		/*
		System.out.println("[Frame] settings()");
		settings();
		*/
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		

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
		helpMenu.add(settingsMenuItem);

		// sets it
		frame.setJMenuBar(menuBar);
		frame.add(textArea);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// frame.setSize(1450, 750);
		frame.setSize(950, 500);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void erase() {
		// TODO Auto-generated method stub
		if (checkSave()) {
			System.out.println("[Frame] Close Window");
			this.dispose();
			FileManager.getInstance().saveProperties();
			System.exit(0);
		}
	}

	public void settings() {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// call the function, erase().
				UIManager.getInstance().closeWindow();
			}
		});

		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
		fontChooser = new KFontChooser(this);
		pt = new KPrinter(textArea);
		fd = new KFinder(textArea);
		rp = new KReplacer(textArea);
		info = new KInformation();
		st = new KSettings();

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
				int response = fc.showOpenDialog(frame);
				if (response == fc.APPROVE_OPTION) {
					loadMemo(fc.getSelectedFile());
					/*
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
					*/
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
		exitMenuItem.addActionListener(e -> UIManager.getInstance().closeWindow());
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
		replaceMenuItem.addActionListener(e -> rp.showDialog());

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
					textArea.setFont(fontChooser.getSelctedFont());
					textArea.setForeground(fontChooser.getSelectedColor());
					Property.setFont(fontChooser.getSelctedFont(), fontChooser.getSelectedColor());
				}
			}
		});
		// statusbar
		// view
		// about
		aboutNotepadMenuItem.addActionListener(e -> info.showDialog());
		settingsMenuItem.addActionListener(e -> {
			if (st.showDialog()) {
				System.out.println("confirmed");
				st.applySettings();
			}
		});

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

		// menu mnemonic keys
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		formatMenu.setMnemonic(KeyEvent.VK_O);
		viewMenu.setMnemonic(KeyEvent.VK_V);
		helpMenu.setMnemonic(KeyEvent.VK_H);

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

	}

	public boolean saveAsAction() {
		boolean rtn = false;
		int userSelection = fc.showSaveDialog(frame);
		if (userSelection == fc.APPROVE_OPTION) {
			fileName = fc.getSelectedFile().getName();
			if (!fileName.endsWith(".txt")) {
				fileName += ".txt";
			}
			directory = fc.getCurrentDirectory();
			saveMemo();

			Property.addRecentFiles(directory + "\\" + fileName);

			frame.setTitle(fileName + " - Notepad");
			rtn = true;
		} else if (userSelection == fc.CANCEL_OPTION) {
			System.out.println("Cancel selected!");
		} else if (userSelection == fc.ERROR_OPTION) {
			System.out.println("Error detected!");
		}
		return rtn;
	}

	public void saveMemo() {
		String filePath = directory + "\\" + fileName;
		MemoVO memo = new MemoVO();
		savedContext = textArea.getText();
		memo.setContent(savedContext);
		FileManager.getInstance().saveMemo(filePath, memo);
	}

	private boolean showFontChooser() {
		return fontChooser.showDialog(textArea.getFont(), textArea.getForeground());
	}

	public boolean checkSave() {
		boolean rtn = false;
		if (!savedContext.equals(textArea.getText())) {
			Object[] options = { "Save", "Don't Save", "Cancel" };

			int response = JOptionPane.showOptionDialog(frame,
					"Your work has not been saved. Do you want to save changes to Untitled?", "Notepad",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			if (response == JOptionPane.YES_OPTION) {
				if (directory != null) {
					saveMemo();
					rtn = true;
				} else {
					rtn = saveAsAction();
				}
			} else if (response == JOptionPane.NO_OPTION) {
				rtn = true;
			} else {
				rtn = false;
			}
		} else {
			rtn = true;
		}
		return rtn;
	}
	
	public void loadMemo(File file) {
		String selectedPath;
		try {
			selectedPath = file.getCanonicalPath();
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
