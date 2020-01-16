package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.MenuSelectionManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.Language;
import config.Property;
import file.FileDrop;
import file.FileManager;
import thread.ThreadManager;
import ui.custom.KFinder;
import ui.custom.KFontChooser;
import ui.custom.KInformation;
import ui.custom.KPrinter;
import ui.custom.KReplacer;
import ui.custom.KSettings;
import vo.MemoVO;

public class NotepadUI extends JFrame implements UI {
	private static final long serialVersionUID = -6173408665024649253L;

	// Frame
	public JFrame frame;
	// Menu bar
	public JMenuBar menuBar;
	// Menus
	public JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	// Menu items
	public JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, pageSetupMenuItem, printMenuItem,
			exitMenuItem, undoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, findMenuItem,
			findNextMenuItem, replaceMenuItem, searchMenuItem, goToMenuItem, selectAllMenuItem, timeDateMenuItem,
			fontMenuItem, statusBarMenuItem, viewHelpMenuItem , HomepageMenuItem, aboutNotepadMenuItem, settingsMenuItem;

	public JCheckBoxMenuItem wordWrapMenuItem, cryptoMenuItem;

	// Text area
	public static JTextArea textArea = new JTextArea();
	public JScrollPane scrollPane;

	public String fileName;
	public File directory;
	public String savedContext;
	public String undoText;

	private JFileChooser fc;
	private KFontChooser fontChooser;
	private KPrinter pt;
	private KFinder fd;
	private KReplacer rp;
	private KInformation info;
	private KSettings st;

	private Language lang;
	private boolean isEncrypted = true;
	private static boolean invalidationFlag = false;

	private MouseAdapter menuBarCloser = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (!menuBar.hasFocus()) {
				MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		}
	};

	public NotepadUI() {
		lang = Property.getLanguagePack();

		fileName = lang.frmUntitled;
		frame = new JFrame(fileName + " - Crypto Notepad");
		savedContext = "";
		undoText = savedContext;
	}

	public NotepadUI(File file) {
		this();
		String path;
		try {
			path = file.getCanonicalPath();
			directory = new File(path.substring(0, path.lastIndexOf("\\")));
			fileName = path.substring(path.lastIndexOf("\\") + 1);
			frame = new JFrame(fileName + " - Crypto Notepad");
			MemoVO loadedContent = FileManager.getInstance().loadMemo(frame, path);
			savedContext = loadedContent.getContent();
			undoText = savedContext;
			textArea = new JTextArea();
			textArea.setText(savedContext);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initializeUI() {
		// reduce the time for loading.
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
		menuBar.setBorder(BorderFactory.createLineBorder(Color.white));

		// menu
		fileMenu = new JMenu(lang.mbFile);
		editMenu = new JMenu(lang.mbEdit);
		formatMenu = new JMenu(lang.mbFormat);
		viewMenu = new JMenu(lang.mbView);
		helpMenu = new JMenu(lang.mbHelp);

		// add menu to bar, but not set yet.
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(formatMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		// menu items
		newMenuItem = new JMenuItem(lang.miNew);
		openMenuItem = new JMenuItem(lang.miOpen);
		saveMenuItem = new JMenuItem(lang.miSave);
		saveAsMenuItem = new JMenuItem(lang.miSaveAs);
		pageSetupMenuItem = new JMenuItem(lang.miPageSet);
		printMenuItem = new JMenuItem(lang.miPrint);
		exitMenuItem = new JMenuItem(lang.miExit);

		undoMenuItem = new JMenuItem(lang.miUndo);
		cutMenuItem = new JMenuItem(lang.miCut);
		copyMenuItem = new JMenuItem(lang.miCopy);
		pasteMenuItem = new JMenuItem(lang.miPaste);
		deleteMenuItem = new JMenuItem(lang.miDelete);
		searchMenuItem = new JMenuItem(lang.miSearch);
		findMenuItem = new JMenuItem(lang.miFind);
		findNextMenuItem = new JMenuItem(lang.miFindNxt);
		replaceMenuItem = new JMenuItem(lang.miReplce);
		goToMenuItem = new JMenuItem(lang.miGoto);
		selectAllMenuItem = new JMenuItem(lang.miSlctAll);
		timeDateMenuItem = new JMenuItem(lang.miTimeDate);

		wordWrapMenuItem = new JCheckBoxMenuItem(lang.miWordWrap);
		fontMenuItem = new JMenuItem(lang.miFont);
		cryptoMenuItem= new JCheckBoxMenuItem(lang.miCrypto);
		statusBarMenuItem = new JMenuItem(lang.miStsBar);

		viewHelpMenuItem = new JMenuItem(lang.miViewHelp);
		HomepageMenuItem = new JMenuItem(lang.miCNWeb);
		aboutNotepadMenuItem = new JMenuItem(lang.miAbtCN);
		settingsMenuItem = new JMenuItem(lang.miSetting);

		// In the most of case UI shows up before its listener is added.
		// IDKDIKDIDK......
		new Thread() {
			public void run() {
				settings();
				System.out.println("[NotepainUI]Finish settings() in thread.");
			}
		}.start();

		System.out.println("[NotepainUI]Finish innitializeUI() in thread. But the thread for settings() may be alive.");
	}

	@Override
	public void draw() {

		Image originImg = new ImageIcon(getClass().getClassLoader().getResource("encrypted_black_crop_bg.png"))
				.getImage();
		Image changedImg = originImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		frame.setIconImage(changedImg);

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
		editMenu.add(searchMenuItem);
		editMenu.add(findMenuItem);
		editMenu.add(findNextMenuItem);
		editMenu.add(replaceMenuItem);
		editMenu.add(goToMenuItem);
		editMenu.addSeparator();
		editMenu.add(selectAllMenuItem);
		editMenu.add(timeDateMenuItem);

		formatMenu.add(wordWrapMenuItem);
		formatMenu.add(fontMenuItem);
		formatMenu.addSeparator();
		formatMenu.add(cryptoMenuItem);

		viewMenu.add(statusBarMenuItem);

		//helpMenu.add(viewHelpMenuItem);
		//helpMenu.addSeparator();
		helpMenu.add(HomepageMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutNotepadMenuItem);
		helpMenu.add(settingsMenuItem);

		// sets it
		frame.setJMenuBar(menuBar);
		frame.add(textArea);

		scrollPane = new JScrollPane(textArea);
		scrollPane.addMouseListener(menuBarCloser);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(950, 500);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//curText must be set after loading content.
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			String curText = undoText;
			
			private void update() {
				undoText = curText;
				curText = textArea.getText();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();	
			}	
		});
		
	}

	@Override
	public void erase() {
		if (checkSave()) {
			System.out.println("[NotepadUI] Close Window on NotepadUI");
			ThreadManager.getInstance().joinThreads();
			FileManager.getInstance().saveProperties();
			this.dispose();
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

		new FileDrop(textArea, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				if (checkSave()) {
					loadMemo(files[0]);
				}
			}
		});

		frame.addMouseListener(menuBarCloser);
		textArea.addMouseListener(menuBarCloser);

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
						savedContext = "";
						undoText = savedContext;
						setInvalidationFlag(false);
						frame.setTitle(fileName + " - Crypto Notepad");
					}
				}
			}
		});

		openMenuItem.addActionListener(e -> {
			if (checkSave()) {
				int response = fc.showOpenDialog(frame);
				if (response == JFileChooser.APPROVE_OPTION) {
					loadMemo(fc.getSelectedFile());
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
		undoMenuItem.addActionListener(e -> {
			String origin = textArea.getText();
			textArea.setText(undoText);
			undoText = origin;
		});
		
		// cut
		cutMenuItem.addActionListener(e -> textArea.cut());
		// copy
		copyMenuItem.addActionListener(e -> textArea.copy());
		// paste
		pasteMenuItem.addActionListener(e -> textArea.paste());
		// delete
		deleteMenuItem.addActionListener(e -> textArea.replaceSelection(""));

		// search
		searchMenuItem.addActionListener(e -> {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					String selected = textArea.getSelectedText();
					if (selected == null)
						Desktop.getDesktop().browse(new URI("http://www.google.com"));
					else
						Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=" + selected));

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});

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
				ampm = lang.am;
			} else {
				ampm = lang.pm;
			}

			String date;
			if (Property.getProperties().get(Property.language).equals("KOREAN")) {
				date = String.format("%4d/%02d/%02d " + ampm + "%02d:%02d ", year, month, day, hour, minute);
			} else {
				date = String.format("%02d/%02d/%4d " + "%02d:%02d " + ampm + " ", day, month, year, hour, minute);
			}
			textArea.insert(date, textArea.getCaretPosition());
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
				if (showFontChooser()) {
					textArea.setFont(fontChooser.getSelctedFont());
					textArea.setForeground(fontChooser.getSelectedColor());
					Property.setFont(fontChooser.getSelctedFont(), fontChooser.getSelectedColor());

					FileManager.getInstance().saveProperties();
				}
			}
		});
			
		cryptoMenuItem.setSelected(true);
		cryptoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cryptoMenuItem.isSelected()) {
					isEncrypted = true;
				} else {
					isEncrypted = false;
				}
			}
		});
		
		// statusbar
		statusBarMenuItem.setEnabled(false);
		
		// view
		viewHelpMenuItem.addActionListener(e -> {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					if (Property.getProperties().get(Property.language).equals("ENGLISH")) {
						Desktop.getDesktop()
								.browse(new URI("https://www.bing.com/search?q=windows+10+notepad+help&FORM=AWRE"));
					} else {
						Desktop.getDesktop().browse(new URI("https://www.bing.com/search?q=windows+"
								+ "10%ec%9d%98+%eb%a9%94%eb%aa%a8%ec%9e%a5%ec%97%90+%eb%8c%80%ed%95%9c+"
								+ "%eb%8f%84%ec%9b%80%eb%a7%90+%eb%b3%b4%ea%b8%b0&filters=guid%3a%224466414-ko-dia%22+"
								+ "lang%3a%22ko%22&ocid=HelpPane-BingIA&setmkt=en-us&setlang=en-us"));
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		HomepageMenuItem.addActionListener(e->{
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop()
					.browse(new URI("https://leedonggeon1996.github.io/Crypto-Notepad-Web/"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});

		// about
		aboutNotepadMenuItem.addActionListener(e -> info.showDialog());

		// settings
		settingsMenuItem.addActionListener(e -> {
			if (st.showDialog()) {
				st.applySettings();
			}
		});

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

		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		searchMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
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

		pageSetupMenuItem.setMnemonic(KeyEvent.VK_U);
		printMenuItem.setMnemonic(KeyEvent.VK_P);
		exitMenuItem.setMnemonic(KeyEvent.VK_X);

		undoMenuItem.setMnemonic(KeyEvent.VK_U);
		cutMenuItem.setMnemonic(KeyEvent.VK_T);
		copyMenuItem.setMnemonic(KeyEvent.VK_C);
		pasteMenuItem.setMnemonic(KeyEvent.VK_P);
		deleteMenuItem.setMnemonic(KeyEvent.VK_L);
		searchMenuItem.setMnemonic(KeyEvent.VK_S);
		findMenuItem.setMnemonic(KeyEvent.VK_F);
		findNextMenuItem.setMnemonic(KeyEvent.VK_N);
		replaceMenuItem.setMnemonic(KeyEvent.VK_R);
		goToMenuItem.setMnemonic(KeyEvent.VK_G);
		selectAllMenuItem.setMnemonic(KeyEvent.VK_A);
		timeDateMenuItem.setMnemonic(KeyEvent.VK_D);

		wordWrapMenuItem.setMnemonic(KeyEvent.VK_W);
		fontMenuItem.setMnemonic(KeyEvent.VK_F);

		statusBarMenuItem.setMnemonic(KeyEvent.VK_S);

		viewHelpMenuItem.setMnemonic(KeyEvent.VK_H);
		aboutNotepadMenuItem.setMnemonic(KeyEvent.VK_A);

		settingsMenuItem.setMnemonic(KeyEvent.VK_S);

		if (Property.getProperties().get(Property.language).equals("ENGLISH")) {
			findNextMenuItem.setDisplayedMnemonicIndex(5);
			saveAsMenuItem.setDisplayedMnemonicIndex(5);
		} else {
			aboutNotepadMenuItem.setDisplayedMnemonicIndex(18);
		}

	}

	public boolean saveAsAction() {
		boolean rtn = false;
		int userSelection = fc.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileName = fc.getSelectedFile().getName();
			if (!fileName.endsWith(".txt")) {
				fileName += ".txt";
			}
			directory = fc.getCurrentDirectory();
			saveMemo();

			Property.addRecentFiles(directory + "\\" + fileName);

			frame.setTitle(fileName + " - Crypto Notepad");
			rtn = true;
		} else if (userSelection == JFileChooser.CANCEL_OPTION) {
			//do nothing.
		} else if (userSelection == JFileChooser.ERROR_OPTION) {
			//do nothing.
		}
		return rtn;
	}

	public void saveMemo() {
		String filePath = directory + "\\" + fileName;
		MemoVO memo = new MemoVO();
		savedContext = textArea.getText();
		memo.setContent(savedContext);
		FileManager.getInstance().saveMemo(filePath, memo, isEncrypted);
		setInvalidationFlag(false);
	}

	private boolean showFontChooser() {
		return fontChooser.showDialog(textArea.getFont(), textArea.getForeground());
	}

	public boolean checkSave() {
		boolean rtn = false;
		if (((!textArea.getText().equals("")) && (invalidationFlag)) || (!savedContext.equals(textArea.getText()))) {
			Object[] options = { lang.save, lang.noSave, lang.btnCancel };

			int response = JOptionPane.showOptionDialog(frame, lang.checkSave_pre + fileName + lang.checkSave_post,
					"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[0]);

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

	public boolean loadMemo(File file) {
		String selectedPath;
		try {
			selectedPath = file.getCanonicalPath();
			Property.addRecentFiles(selectedPath);
			MemoVO memo = FileManager.getInstance().loadMemo(frame, selectedPath);
			if (memo != null) {
				savedContext = memo.getContent();
				undoText = savedContext;
				setInvalidationFlag(false);
				textArea.setText(memo.getContent());
				directory = new File(selectedPath.substring(0, selectedPath.lastIndexOf("\\")));
				fileName = selectedPath.substring(selectedPath.lastIndexOf("\\") + 1);
				frame.setTitle(fileName + " - Crypto Notepad");
				return true;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static void setInvalidationFlag(boolean b) {
		invalidationFlag = b;
	}

	public boolean isEncrypted() {
		return isEncrypted;
	}
	
}
