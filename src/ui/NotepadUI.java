package ui;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
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
import ui.custom.KeyExporter;
import ui.custom.KeyImporter;
import ui.custom.KeyOpener;
import vo.MemoVO;

public class NotepadUI extends JFrame implements UI {
	private static final long serialVersionUID = -6173408665024649253L;
	private static final int YES_OPTION = 1;
	private static final int NO_OPTION = 2;
	private static final int CANCEL_OPTION = 0;
	
	// Frame
	public JFrame frame;
	// Menu bar
	public JMenuBar menuBar;
	// Menus
	public JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	// Menu items
	public JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, keyImportMenuItem, keyExportMenuItem, 
			pageSetupMenuItem, printMenuItem, exitMenuItem, undoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, 
			deleteMenuItem, findMenuItem, findNextMenuItem, replaceMenuItem, searchMenuItem, goToMenuItem, 
			selectAllMenuItem, timeDateMenuItem, fontMenuItem, viewHelpMenuItem , 
			HomepageMenuItem, aboutNotepadMenuItem, settingsMenuItem;

	public JCheckBoxMenuItem wordWrapMenuItem, statusBarMenuItem, cryptoMenuItem;

	// Text area
	public static JTextArea textArea = new JTextArea();
	public JScrollPane scrollPane;
	private Font curFont = null;

	//status bar
	private JPanel statusBar;
	private JTextPane txtPaneStatus, txtPanRowCol, txtPanMagnification;
	private Thread stsUpdateThread = null;
	private int fontMagnification = 100;
	
	public String fileName;
	public File directory;
	public String savedContext;
	public String undoText;

	private Thread dialogCreationThread = null;
	
	private JFileChooser fc = null;
	private KFontChooser fontChooser = null;
	private KPrinter pt = null;
	private KFinder fd = null;
	private KReplacer rp = null;
	private KInformation info = null;
	private KSettings st = null;

	private Language lang;
	private boolean isEncrypted = true;
	private static boolean invalidationFlag = false;

	private JTextArea trickTxtArea;
	FileDrop filedrop = null;
	
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
		/*Never used yet*/
		this();
		String path;
		try {
			path = file.getCanonicalPath();
			directory = new File(path.substring(0, path.lastIndexOf(FileManager.SEPARATOR)));
			fileName = path.substring(path.lastIndexOf(FileManager.SEPARATOR) + 1);
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
		curFont = new Font(p.getProperty(Property.fontFamily),
				Integer.parseInt(p.getProperty(Property.fontStyle)),
				Integer.parseInt(p.getProperty(Property.fontSize)) + KFontChooser.FONT_SIZE_CORRECTION);
		textArea.setFont(curFont);
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
		keyImportMenuItem = new JMenuItem(lang.miImporter);
		keyImportMenuItem.setEnabled(false);
		keyExportMenuItem = new JMenuItem(lang.miExporter);
		keyExportMenuItem.setEnabled(false);
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
		statusBarMenuItem = new JCheckBoxMenuItem(lang.miStsBar);

		viewHelpMenuItem = new JMenuItem(lang.miViewHelp);
		HomepageMenuItem = new JMenuItem(lang.miCNWeb);
		aboutNotepadMenuItem = new JMenuItem(lang.miAbtCN);
		settingsMenuItem = new JMenuItem(lang.miSetting);
		
		statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
		txtPaneStatus = new JTextPane();
		txtPanRowCol = new JTextPane();
		txtPanMagnification = new JTextPane();
		updateRowCol();
		txtPanMagnification.setText("100%");

		// In the most of case UI shows up before its listener is added.
		// IDKDIKDIDK......
		new Thread() {
			public void run() {
				settings();
				System.out.println("[NotepainUI]Finish settings() in thread.");
			}
		}.start();

		System.out.println("[NotepainUI]Finish initializeUI() in thread. But the thread for settings() may be alive.");
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
		fileMenu.add(keyImportMenuItem);
		fileMenu.add(keyExportMenuItem);
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
		helpMenu.add(aboutNotepadMenuItem);
		helpMenu.add(HomepageMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(settingsMenuItem);

		// sets it
		frame.setJMenuBar(menuBar);
		frame.add(textArea);

		scrollPane = new JScrollPane(textArea);
		scrollPane.addMouseListener(menuBarCloser);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);
		
		/*Start statusBar*/
		/**
		 * put maximum string length of status instead of 450
		 */
		txtPaneStatus.setMinimumSize(new Dimension(450, txtPaneStatus.getHeight()));
		txtPanRowCol.setMinimumSize(new Dimension(0, txtPanRowCol.getHeight()));
		txtPanMagnification.setMinimumSize(new Dimension(0, txtPanMagnification.getHeight()));
		
		txtPanRowCol.setMaximumSize(new Dimension(160, 50));
		txtPanMagnification.setMaximumSize(new Dimension(60, 50));
		
		txtPanRowCol.setPreferredSize(new Dimension(160, 24));
		txtPanMagnification.setPreferredSize(new Dimension(60, 24));
		
		txtPaneStatus.setBackground(new Color(0xF0F0F0));
		txtPanRowCol.setBackground(new Color(0xF0F0F0));
		txtPanMagnification.setBackground(new Color(0xF0F0F0));
		
		statusBar.add(txtPaneStatus);
		statusBar.add(Box.createHorizontalStrut(5));
		statusBar.add(new JSeparator(SwingConstants.VERTICAL));
		statusBar.add(Box.createHorizontalStrut(5));
		statusBar.add(txtPanRowCol);
		statusBar.add(Box.createHorizontalStrut(5));
		statusBar.add(new JSeparator(SwingConstants.VERTICAL));
		statusBar.add(Box.createHorizontalStrut(5));
		statusBar.add(txtPanMagnification);
		
		frame.add(statusBar, BorderLayout.SOUTH);
		/*End statusBar*/
		
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
		
		textArea.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				updateRowCol();
			}
		});
		
		textArea.addMouseWheelListener(new MouseWheelListener() {
			
			int rotation = 0;

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.isControlDown()) {
					rotation = e.getWheelRotation();
					updataMagVal(-rotation);
					updateFontMag();
					txtPanMagnification.setText(fontMagnification + "%");
				} else if (e.isShiftDown()) {
	                // Horizontal scrolling
	                Adjustable adj = scrollPane.getHorizontalScrollBar();
	                int scroll = e.getUnitsToScroll() * adj.getBlockIncrement();
	                adj.setValue(adj.getValue() + scroll);
	            } else {
	                // Vertical scrolling
	                Adjustable adj = scrollPane.getVerticalScrollBar();
	                int scroll = e.getUnitsToScroll() * adj.getBlockIncrement();
	                adj.setValue(adj.getValue() + scroll);
	            }
			}

			private void updataMagVal(int val) {
				fontMagnification += (val*10);
				
				if(fontMagnification < 10) {
					fontMagnification = 10;
				} else if(fontMagnification > 500) {
					fontMagnification = 500;
				}
			}
			
		});
	}
	
	public void updateRowCol() {
		if(txtPanRowCol != null) {
			String[] t = textArea.getText().substring(0, textArea.getCaretPosition()).split("\n",-1);
			txtPanRowCol.setText("Ln " + t.length + ", Col " + (t[t.length-1].length()+1));
		}
	}
	
	public void updateFontMag() {
		if(fontMagnification == 100) {
			textArea.setFont(curFont);
		} else {
			textArea.setFont(curFont.deriveFont(curFont.getSize()*(fontMagnification*0.01f)));
		}
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
		
		long start = System.currentTimeMillis();
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// call the function, erase().
				UIManager.getInstance().closeWindow();
			}
		});

		
		
		
		/*
		try {
			filedrop.joinListenerThread();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		*/

		frame.addMouseListener(menuBarCloser);
		textArea.addMouseListener(menuBarCloser);

		long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
		System.out.println( "0.5구간 실행 시간 : " + ( end - start )/1000.0 +"초");
		 start = System.currentTimeMillis();
		
		

		 /*
		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
		fontChooser = new KFontChooser(this);
		pt = new KPrinter(textArea);
		fd = new KFinder(textArea);
		rp = new KReplacer(textArea);
		info = new KInformation();
		st = new KSettings();
*/
		dialogCreationThread = new Thread() {
			public void run() {
				fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
				info = new KInformation();
				st = new KSettings();
				fontChooser = new KFontChooser(frame);
				pt = new KPrinter(textArea);
				fd = new KFinder(textArea);
				rp = new KReplacer(textArea);
				
				// findnext
				findNextMenuItem.addActionListener(fd);
				
				// pagesetup
				pageSetupMenuItem.setActionCommand("PageSetup");
				pageSetupMenuItem.addActionListener(pt);
				// print
				printMenuItem.setActionCommand("Print");
				printMenuItem.addActionListener(pt);

			}
		};
		dialogCreationThread.start();

		end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
		System.out.println( "1구간 실행 시간 : " + ( end - start )/1000.0 +"초");
		 start = System.currentTimeMillis();
		
		// actions
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkSave()) {
					textArea.setText("");
					directory = null;
					fileName = "Untitled";
					savedContext = "";
					undoText = savedContext;

					//keyExportMenuItem.setEnabled(false);
					setInvalidationFlag(false);
					FileManager.getInstance().newBtnProcedure();
					
					setTempMode(FileManager.getInstance().isTemporary());
					
					//correct setting.
					setEncryptMode(true);
					
					frame.setTitle(fileName + " - Crypto Notepad");
				}
			}
		});

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
		findMenuItem.addActionListener(e -> {
			checkDialogCreated(fd);
			fd.showDialog();
		});

		// replace
		replaceMenuItem.addActionListener(e -> {
			checkDialogCreated(rp);
			rp.showDialog();
		});

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
					curFont = fontChooser.getSelctedFont();
					fontMagnification = 100;
					txtPanMagnification.setText("100%");
					
					textArea.setFont(curFont);
					textArea.setForeground(fontChooser.getSelectedColor());
					Property.setFont(fontChooser.getSelctedFont(), fontChooser.getSelectedColor());

					FileManager.getInstance().saveProperties();
				}
			}
		});
		
		// statusbar
		statusBarMenuItem.setSelected(true);
		statusBarMenuItem.addActionListener(e -> {
			if (statusBarMenuItem.isSelected()) {
				statusBar.setVisible(true);
			} else {
				statusBar.setVisible(false);
			}
		});
		
		
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
		aboutNotepadMenuItem.addActionListener(e -> {
			checkDialogCreated(info);
			info.showDialog();
		});

		// settings
		settingsMenuItem.addActionListener(e -> {
			checkDialogCreated(st);
			if (st.showDialog()) {
				st.applySettings();
			}
		});
		
		
		// These listeners must be added after joining init-thread.
		//[Block A - start]*****************************************************************************
		cryptoMenuItem.setSelected(true);
		cryptoMenuItem.addActionListener(new ActionListener() {
			
			public void applyInstantly(boolean isEncrypted) {
				String filePath = directory + FileManager.SEPARATOR + fileName;
				MemoVO savedContextMemo = new MemoVO();
				savedContextMemo.setContent(savedContext);
				ThreadManager.getInstance().joinKeyLoadingThread();
				FileManager.getInstance().saveMemo(filePath, savedContextMemo, isEncrypted);
				setTempMode(FileManager.getInstance().isTemporary());
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cryptoMenuItem.isSelected()) {
					isEncrypted = true;
					if(directory != null) {
						applyInstantly(true);
					}
					System.out.println("Selected");
				} else {
					if(directory != null) {
						int response = showEncryptModeDialog();
						if(response == YES_OPTION) {
							applyInstantly(false);
							isEncrypted = false;
						} else {
							cryptoMenuItem.setSelected(true);
							isEncrypted = true;
						}
						
					} else {
						isEncrypted = false;
					}
				}
			}
		});
		
		openMenuItem.addActionListener(e -> {
			if (checkSave()) {
				checkJFileChooserCreated(fc);
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
		
		keyImportMenuItem.addActionListener(e->{
			importKey();
		});
		
		keyExportMenuItem.addActionListener(e->{
			exportKey();
		});
		//[Block A - end]*****************************************************************************

		end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
		System.out.println( "2 구간 실행 시간 : " + ( end - start )/1000.0 +"초");
		 start = System.currentTimeMillis();
		
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
		
		HomepageMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		aboutNotepadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		settingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		
		

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

		//viewHelpMenuItem.setMnemonic(KeyEvent.VK_H);
		aboutNotepadMenuItem.setMnemonic(KeyEvent.VK_A);
		settingsMenuItem.setMnemonic(KeyEvent.VK_S);

		if (Property.getProperties().get(Property.language).equals("ENGLISH")) {
			findNextMenuItem.setDisplayedMnemonicIndex(5);
			saveAsMenuItem.setDisplayedMnemonicIndex(5);
		} else {
			aboutNotepadMenuItem.setDisplayedMnemonicIndex(18);
		}

		ThreadManager.getInstance().joinKeyLoadingThread();
		filedrop = new FileDrop(textArea, true, false, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				if (checkSave()) {
					loadMemo(files[0]);
				}
			}
		});
		
		
		end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
		System.out.println( "3구간 실행 시간 : " + ( end - start )/1000.0 + "초");

	}

	public boolean saveAsAction() {
		boolean rtn = false;
		checkJFileChooserCreated(fc);
		int userSelection = fc.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileName = fc.getSelectedFile().getName();
			if (!fileName.endsWith(".txt")) {
				fileName += ".txt";
			}
			directory = fc.getCurrentDirectory();
			saveMemo();

			Property.addRecentFiles(directory + FileManager.SEPARATOR + fileName);

			rtn = true;
		} else if (userSelection == JFileChooser.CANCEL_OPTION) {
			//do nothing.
		} else if (userSelection == JFileChooser.ERROR_OPTION) {
			//do nothing.
		}
		return rtn;
	}

	public void saveMemo() {
		String filePath = directory + FileManager.SEPARATOR + fileName;
		MemoVO memo = new MemoVO();
		savedContext = textArea.getText();
		memo.setContent(savedContext);
		FileManager.getInstance().saveMemo(filePath, memo, isEncrypted);
		/*
		if(isEncrypted) {
			keyExportMenuItem.setEnabled(true);
		} else {
			keyExportMenuItem.setEnabled(false);
		}
		*/
		frame.setTitle(fileName + " - Crypto Notepad");
		setTempMode(FileManager.getInstance().isTemporary());
		
	}

	private boolean showFontChooser() {
		checkDialogCreated(fontChooser);
		return fontChooser.showDialog(curFont, textArea.getForeground());
	}

	public boolean checkSave() {
		boolean rtn = false;
		int response = CANCEL_OPTION;
		/*
		if (((directory != null) && (invalidationFlag)) || (!savedContext.equals(textArea.getText()))) {
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
		*/
		
		
		
		boolean isChanged = !savedContext.equals(textArea.getText());
		boolean isUnsafeKey = (!FileManager.getInstance().isOpenedWithExportedKey()) &&
				(FileManager.getInstance().isCurrentFileEncrypted()) && 
				(directory != null) && 
				(invalidationFlag);
		
		if (isEncrypted) {
			if (isChanged) {
				if (isUnsafeKey) {
					response = showSaveOrNotDialog();
					if ((response == YES_OPTION) || (response == NO_OPTION)) {
						response = showImportOrExportDialog();
						if(response == YES_OPTION) {
							rtn = true;
						}
					}
				} else {
					response = showSaveOrNotDialog();
					if((response == YES_OPTION) || (response == NO_OPTION)) {
						rtn = true;
					}
				}
			} else if (isUnsafeKey) {
				response = showImportOrExportDialog();
				if(response == YES_OPTION) {
					rtn = true;
				}
			} else {
				// the case of nothing changed
				rtn = true;
			}
		} else {
			if (isChanged) {
				response = showSaveOrNotDialog();
				if(response == YES_OPTION) {
					rtn = true;
				} else if(response == NO_OPTION) {
					if(isUnsafeKey) {
						response = showImportOrExportDialog();
						if(response == YES_OPTION) {
							rtn = true;
						}
					} else {
						rtn = true;
					}
				}
			} else {
				if(isUnsafeKey) {
					response = showImportOrExportDialog();
					if(response == YES_OPTION) {
						rtn = true;
					}
				} else {
					rtn = true;
				}
			}
		}

		/*
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
		*/
		
		return rtn;
	}

	public int showSaveOrNotDialog() {
		int rtn = CANCEL_OPTION;
		Object[] options = { lang.save, lang.noSave, lang.btnCancel };

		int response = JOptionPane.showOptionDialog(frame, lang.checkSave_pre + fileName + lang.checkSave_post,
				"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
				options[0]);

		if (response == JOptionPane.YES_OPTION) {
			if (directory != null) {
				saveMemo();
				rtn = YES_OPTION;
			} else {
				if(saveAsAction()) {
					rtn = YES_OPTION;
				}
			}
		} else if (response == JOptionPane.NO_OPTION) {
			rtn = NO_OPTION;
		} else {
			rtn = CANCEL_OPTION;
		}

		return rtn;
	}
	
	public int showImportOrExportDialog() {
		int rtn = CANCEL_OPTION;
		Object[] options = { "Import key", "Export Key", "No, I have exported keyfile." };

		boolean toBeContinue = true;
		while (toBeContinue) {
			int response = JOptionPane.showOptionDialog(frame,
					"The key used for this file is not saved. To open this file next time, keep the key via options below    ",
					"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[0]);

			if (response == 0) {
				response = importKey();
				if (response == KeyImporter.IMPORT_OPTION) {
					rtn = YES_OPTION;
					toBeContinue = false;
				}
			} else if (response == 1) {
				response = exportKey();
				if (response == KeyExporter.EXPORT_OPTION) {
					rtn = YES_OPTION;
					toBeContinue = false;
				}
			} else if (response == 2) {
				// do nothing
				rtn = YES_OPTION;
				toBeContinue = false;
			} else {
				System.out.println("Closed option in showDialogImportOrExport()");
				rtn = CANCEL_OPTION;
				toBeContinue = false;
			}

		}
		
		
		if(rtn == YES_OPTION) {
			setInvalidationFlag(false);
		}
		return rtn;
	}
	/*
	public int showImportDialog() {
		int rtn = CANCEL_OPTION;
		
		Object[] options = { "      " + "Import key..." + "      ", lang.btnCancel };
		

		int response = JOptionPane.showOptionDialog(frame, "Do you really want to import the key?",
				"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
				options[1]);

	}
	*/
	public boolean checkSaveToExport() {
		boolean rtn = false;
		/*
		if (((!textArea.getText().equals("")) && (invalidationFlag)) || (!savedContext.equals(textArea.getText()))) {
			//to make looks better, add space
			Object[] options = { "      " +lang.save + "      ", lang.btnCancel };

			int response = JOptionPane.showOptionDialog(frame, lang.checkSaveToExport_pre + fileName + lang.checkSave_post,
					"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[1]);

			if (response == JOptionPane.YES_OPTION) {
				if (directory != null) {
					saveMemo();
					rtn = true;
				} else {
					rtn = saveAsAction();
				}
			} else if (response == JOptionPane.NO_OPTION) {
				rtn = false;
			}
		} else {
			rtn = true;
		}
		*/

		/*
		boolean isCurrentFileEncrypted = FileManager.getInstance().isCurrentFileEncrypted();
		int response = CANCEL_OPTION;
		if (!savedContext.equals(textArea.getText())) {
			response = showSaveOrNotDialogToExport();
			if(response == )

		}
*/
	
		rtn = true;
		
		return rtn;
	}
	
	public int showSaveOrNotDialogToExport() {
		int rtn = CANCEL_OPTION;
		//to make looks better, add space
		Object[] options = { "      " +lang.save + "      ", lang.btnCancel };

		int response = JOptionPane.showOptionDialog(frame, lang.checkSaveToExport_pre + fileName + lang.checkSave_post,
				"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
				options[1]);

		if (response == JOptionPane.YES_OPTION) {
			if (directory != null) {
				saveMemo();
				rtn = YES_OPTION;
			} else {
				if(saveAsAction()) {
					rtn = YES_OPTION;
				}
			}
		} else if (response == JOptionPane.NO_OPTION) {
			rtn = NO_OPTION;
		} else {
			rtn = CANCEL_OPTION;
		}

		return rtn;
	}
	
	public int showEncryptModeDialog() {
		int rtn = CANCEL_OPTION;
		//to make looks better, add space
		Object[] options = { "      " + "Yes" + "      ", lang.btnCancel };

		int response = JOptionPane.showOptionDialog(frame, "Do you want to turn off encryption mode?     \nIt will be applied instantly. " + "If you have exported keyfile, it will be invalidated.    ",
				"Crypto Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
				options[1]);

		if(response == 0) {
			rtn = YES_OPTION;
		} else {
			rtn = CANCEL_OPTION;
		}
		
		return rtn;
	}

	public boolean loadMemo(File file) {
		String selectedPath;
		try {
			selectedPath = file.getCanonicalPath();
			Property.addRecentFiles(selectedPath);
			
			ThreadManager.getInstance().joinKeyLoadingThread();
			MemoVO memo = FileManager.getInstance().loadMemo(frame, selectedPath);
			if (memo != null) {
				savedContext = memo.getContent();
				undoText = savedContext;
				setInvalidationFlag(false);
				/*
				if(memo.getKey() == null) {
					keyExportMenuItem.setEnabled(false);
				} else {
					keyExportMenuItem.setEnabled(true);
				}
				*/
				textArea.setText(memo.getContent());
				directory = new File(selectedPath.substring(0, selectedPath.lastIndexOf(FileManager.SEPARATOR)));
				fileName = selectedPath.substring(selectedPath.lastIndexOf(FileManager.SEPARATOR) + 1);
				frame.setTitle(fileName + " - Crypto Notepad");
				setTempMode(FileManager.getInstance().isTemporary());
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
	
	public int importKey() {
		ThreadManager.getInstance().joinKeyLoadingThread();
		KeyImporter importer = new KeyImporter();
		int response = importer.showDialog(frame);
		if(response == KeyImporter.IMPORT_OPTION) {
			System.out.println("Do import");
			String filePath = directory + FileManager.SEPARATOR + fileName;
			MemoVO savedContextMemo = new MemoVO();
			savedContextMemo.setContent(savedContext);
			FileManager.getInstance().importKey(filePath, savedContextMemo);
			setTempMode(FileManager.getInstance().isTemporary());
		}
		
		return response;
	}
	
	public int exportKey() {
		ThreadManager.getInstance().joinKeyLoadingThread();
		int response = new KeyExporter().showDialog(this);
		if(response == KeyExporter.EXPORT_OPTION) {
			setInvalidationFlag(false);
			FileManager.getInstance().setOpenedWithExportedKey(true);
		}
		setTempMode(FileManager.getInstance().isTemporary());
		return response;
	}

	public void setTempMode(boolean isTemporary) {
		if (FileManager.getInstance().isCurrentFileEncrypted()) {
			if (isTemporary) {
				if (directory != null) {
					if(FileManager.getInstance().isOpenedWithExportedKey()) {
						frame.setTitle(
								fileName + " - Crypto Notepad" + "  (The file is opened with exported keyfile.)");
						keyImportMenuItem.setEnabled(true);
						keyExportMenuItem.setEnabled(false);
					} else {
						/*the case of invalidation*/
						frame.setTitle(
								fileName + " - Crypto Notepad" + "  (It is required to import or export the key.)");
						keyImportMenuItem.setEnabled(true);
						keyExportMenuItem.setEnabled(true);
					}
				}
			} else {

				System.out.println("no app teml");
				frame.setTitle(fileName + " - Crypto Notepad");

				keyImportMenuItem.setEnabled(false);
				keyExportMenuItem.setEnabled(true);

			}
			setEncryptMode(true);
			
		} else {
			if (directory != null) {
				frame.setTitle(fileName + " - Crypto Notepad" + "  (The file is not encrypted.)");
				keyImportMenuItem.setEnabled(false);
				keyExportMenuItem.setEnabled(false);
			}
			setEncryptMode(false);
			
		}
	}
	
	private void setEncryptMode(boolean b) {
		cryptoMenuItem.setSelected(b);
		isEncrypted = b;
	}
	
	/*
	fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
		fontChooser = new KFontChooser(this);
		pt = new KPrinter(textArea);
		fd = new KFinder(textArea);
		rp = new KReplacer(textArea);
		info = new KInformation();
		st = new KSettings(this);
	 * */
	
	
	/*
	 * These functions is for Adding listener faster for UX.
	 * */
	public void checkKeyLoading() {
		ThreadManager.getInstance().joinInitThread();
	}
	
	private void checkDialogCreated(JDialog dialog) {
		if(dialog == null) {
			try {
				dialogCreationThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void checkJFileChooserCreated(JFileChooser fc) {
		if(fc == null) {
			try {
				dialogCreationThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	private void checkKPrinterCreated(KPrinter kp) {
		if(kp == null) {
			try {
				dialogCreationThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	*/
	
		
/*
	public boolean isEncrypted() {
		return isEncrypted;
	}
	
	*/
}
