package UI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import UI.Custom.JFontChooser;
import UI.Custom.KFontChooser;
import UI.Custom.KFontChooser_T;

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
			findNextMenuItem, replaceMenuItem, goToMenuItem, selectAllMenuItem, timeDateMenuItem, wordWrapMenuItem,
			fontMenuItem, statusBarMenuItem, viewHelpMenuItem, aboutNotepadMenuItem;
	// Text area
	public JTextArea textArea;

	public String fileName;
	public File directory;
	
	private KFontChooser fc;

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		fileName = "Untitled";
		frame = new JFrame(fileName + " - Notepad");

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

		wordWrapMenuItem = new JMenuItem("Word Wrap");
		fontMenuItem = new JMenuItem("Font...");

		statusBarMenuItem = new JMenuItem("Status Bar");

		viewHelpMenuItem = new JMenuItem("View Help");
		aboutNotepadMenuItem = new JMenuItem("About Notepad");

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

		textArea = new JTextArea();
		frame.add(textArea);

		// actions
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (textArea.getText().trim().length() != 0) {
					System.out.println("Contains Text");
					// JOptionPane.showConfirmDialog(frame, "Do you want to save changes to
					// Untitled?");

					Object[] options = { "Save", "Don't Save", "Cancel" };

					int x = JOptionPane.showOptionDialog(frame, "Do you want to save changes to Untitled?", "Notepad",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

					if (x != 2) {
						if (x == 0) {
							// Save action should be called here
							System.out.println("Save");
							saveAsAction();
						} else if (x == 1) {
							System.out.println("Don't Save");
						}
						textArea.setText(null);
						fileName = "Untitled";
						frame.setTitle(fileName + " - Notepad");
					} else {
						System.out.println("Cancel Selected");
					}

				} else {
					System.out.println("Is Empty");
				}
			}
		});

		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				final JFileChooser fc = new JFileChooser();
				int userSelection = fc.showOpenDialog(frame);

				if (userSelection == fc.APPROVE_OPTION) {
					try {
						File file = new File(fc.getCurrentDirectory() + "/" + fc.getSelectedFile().getName());
						String contents = new String(Files.readAllBytes(file.toPath()));
						textArea.setText(contents);

						fileName = fc.getSelectedFile().getName();
						directory = fc.getCurrentDirectory();
						frame.setTitle(fileName + " - Notepad");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("Directory is: " + directory);
				if (directory != null) {
					FileWriter fw;
					try {
						fw = new FileWriter(new File(directory + "/" + fileName));
						fw.write(textArea.getText());
						System.out.println("Saved");
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
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
		
		//pagesetup
		//print
		//exit
		//
		//undo
		//cut
		//copy
		//paste
		//delete
		//find
		//findnext
		//replace
		//goto
		//selectall
		//timedate
		//wordwrap
		//font
		fontMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(showFontChooser()) {
					textArea.setFont(fc.getSelctedFont());
					textArea.setForeground(fc.getSelectedColor());
				}
			}
		});
		//statusbar
		//view
		//about
		
		/*
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

				wordWrapMenuItem = new JMenuItem("Word Wrap");
				fontMenuItem = new JMenuItem("Font...");

				statusBarMenuItem = new JMenuItem("Status Bar");

				viewHelpMenuItem = new JMenuItem("View Help");
				aboutNotepadMenuItem = new JMenuItem("About Notepad");
		
		*/
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1450, 750);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	@Override
	public void erase() {
		// TODO Auto-generated method stub

	}
	
	public void saveAsAction() {
		// finalChooser
		final JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("*.txt"));
		int userSelection = fc.showSaveDialog(frame);

		if (userSelection == fc.APPROVE_OPTION) {
			FileWriter fw;
			try {
				fw = new FileWriter(new File(fc.getCurrentDirectory() + "/" + fc.getSelectedFile().getName()));
				fw.write(textArea.getText());
				System.out.print(textArea.getText());
				fw.close();

				fileName = fc.getSelectedFile().getName();
				directory = fc.getCurrentDirectory();
				frame.setTitle(fileName + " - Notepad");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (userSelection == fc.CANCEL_OPTION) {
			System.out.println("Cancel selected!");
		} else if (userSelection == fc.ERROR_OPTION) {
			System.out.println("Error detected!");
		}
	}
	
	private boolean showFontChooser() {
		fc = new KFontChooser();
		return fc.showDialog(this, textArea.getFont(), textArea.getForeground());
	}
}
