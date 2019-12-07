package config;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

import file.FileManager;
import ui.NotepadUI;
import ui.custom.KFontChooser;

public class Property {

	public static String version = "VERSION";
	public static String language = "LANGUAGE";
	public static String isEncrypted = "ENCRYPTED";
	public static String nOfRcntFiles = "NUMBER_OF_RECENT_FILES";
	public static String rcntFile = "Recent File";
	public static String fontFamily = "FONT_FAMILY";
	public static String fontStyle = "FONT_STYLE";
	public static String fontSize = "FONT_SIZE";
	public static String fontColor = "FONT_COLOR";

	private static Property property = null;
	private Properties prop;
	private PropertyUpdater updater;
	private Language languagePack;

	private class PropertyUpdater {

		private static final int DUPLICATION_INTERVAL = 50;

		private WatchService ws;
		private WatchKey watchKey;
		private Thread updater;

		// make catch-throw clear...
		public PropertyUpdater() {
			ws = null;
			try {
				ws = FileSystems.getDefault().newWatchService();
				Path path = Paths.get(FileManager.DIR_NAME);
				path.register(ws, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

				updater = new Thread(() -> {
					while (true) {
						try {
							watchKey = ws.take();
							Thread.sleep(DUPLICATION_INTERVAL);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						List<WatchEvent<?>> events = watchKey.pollEvents();

						for (WatchEvent<?> event : events) {
							Kind<?> kind = event.kind();
							Path paths = (Path) event.context();
							String eventFile = paths.getFileName().toString();

							switch (eventFile) {
							case FileManager.FILE_NAME_PROP:
								if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
									// Reload new property and apply.
									System.out.println("MODIFIED" + paths.getFileName());
									FileManager.getInstance().loadProperties(true);
									applyReloadedProp();

								} else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
									System.out.println("***Prop Deleted");
									FileManager.getInstance().saveProperties();
								}
								break;

							case FileManager.FILE_NAME_KEYS:
								if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
									System.out.println("***Key Modi");
									FileManager.getInstance().loadKeys(true);

								} else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
									System.out.println("***Key Deleted");
									FileManager.getInstance().saveKeys();
								}
								break;

							default:

							}
						}

						if (!watchKey.reset()) {
							try {
								ws.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				});
			} catch (IOException e) {
				try {
					ws.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}

		public void startUpdater() {
			updater.start();
			System.out.println("Updater Start!");
		}

	}

	private Property() {
		prop = new Properties();

		updater = new PropertyUpdater();
		updater.startUpdater();
	}

	private static class LazyHolder {
		private static final Property INSTANCE = new Property();
	}

	public static Properties getProperties() {
		return LazyHolder.INSTANCE.prop;
	}

	public static Language getLanguagePack() {
		return LazyHolder.INSTANCE.languagePack;
	}

	public static void load(InputStream inStream) {
		try {
			Property.getProperties().load(inStream);
			switch (Property.getProperties().getProperty(Property.language)) {
			case "KOREAN":
				System.out.println("###set lang");
				LazyHolder.INSTANCE.languagePack = new KoreanPack();
				break;
			default:
				System.out.println("###set lang");
				LazyHolder.INSTANCE.languagePack = new EnglishPack();
				break;
			}

		} catch (IOException e) {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

	public static void reload(InputStream inStream) {
		try {
			Property.getProperties().load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void store(OutputStream out, String comments) {
		System.out.println("###Proprty store");
		try {
			Property.getProperties().store(out, comments);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setDefaultProperties() {
		Properties prop = Property.getProperties();
		prop.setProperty(isEncrypted, "TRUE");
		prop.setProperty(nOfRcntFiles, "5");
		prop.setProperty(fontFamily, "Dialog");
		prop.setProperty(fontStyle, String.valueOf(Font.PLAIN));
		prop.setProperty(fontSize, String.valueOf(11));
		prop.setProperty(fontColor, "0");
	}

	public static void initialize() {
		Properties prop = Property.getProperties();
		prop.setProperty(version, "1.0");
		prop.setProperty(language, "ENGLISH");
		prop.setProperty(isEncrypted, "TRUE");
		prop.setProperty(nOfRcntFiles, "5");
		prop.setProperty(fontFamily, "Dialog");
		prop.setProperty(fontStyle, String.valueOf(Font.PLAIN));
		prop.setProperty(fontSize, String.valueOf(11));
		prop.setProperty(fontColor, "0");
	}

	public static void setFont(Font font, Color color) {
		Properties prop = Property.getProperties();
		prop.setProperty(fontFamily, font.getFamily());
		prop.setProperty(fontStyle, String.valueOf(font.getStyle()));
		prop.setProperty(fontSize, String.valueOf(font.getSize() - KFontChooser.FONT_SIZE_CORRECTION));
		prop.setProperty(fontColor, String.valueOf(color.getRGB()));
	}

	private void applyReloadedProp() {
		try {
			Properties p = Property.getProperties();
			Font textFont = new Font(p.getProperty(Property.fontFamily),
					Integer.parseInt(p.getProperty(Property.fontStyle)),
					Integer.parseInt(p.getProperty(Property.fontSize)) + KFontChooser.FONT_SIZE_CORRECTION);

			NotepadUI.textArea.setForeground(new Color(Integer.parseInt(p.getProperty(Property.fontColor))));
			NotepadUI.textArea.setFont(textFont);
		} catch (NullPointerException e) {
			// not critical.
		}
	}

	public static void addRecentFiles(String newFilePath) {
		Properties prop = Property.getProperties();
		int i;

		i = 0;
		while (i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			String path = prop.getProperty(rcntFile + i, null);
			if (newFilePath.equals(path))
				break;
			i++;
		}
		removeRecentFile(i);

		i = Integer.parseInt(prop.getProperty(nOfRcntFiles)) - 2;
		while (i >= 0) {
			String moveDown = prop.getProperty(rcntFile + i, null);
			if (moveDown != null) {
				prop.setProperty(rcntFile + (i + 1), moveDown);
			}
			i--;
		}
		prop.setProperty(rcntFile + "0", newFilePath);

		// debug
		i = 0;
		while (i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			String path = prop.getProperty(rcntFile + i, null);
			System.out.println("rcnt File  : " + path);
			i++;
		}

		FileManager.getInstance().saveProperties();
	}

	public static void removeAllRecentFiles() {
		Properties prop = Property.getProperties();

		int i = 0;
		while (i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			prop.remove(rcntFile + i);
			i++;
		}

		FileManager.getInstance().saveProperties();
	}

	public static void removeRecentFile(int idx) {
		Properties prop = Property.getProperties();

		int max = Integer.parseInt(prop.getProperty(nOfRcntFiles)) - 1;
		if (idx < 0 || idx > max)
			return;
		prop.remove(rcntFile + idx);
		while (idx < max) {
			String moveUp = prop.getProperty(rcntFile + (idx + 1), null);
			if (moveUp != null) {
				prop.setProperty(rcntFile + idx, moveUp);
			} else {
				prop.remove(rcntFile + idx);
				break;
			}
			idx++;
		}

		FileManager.getInstance().saveProperties();
	}

	public static ArrayList<String> getRecentFilePaths() {
		ArrayList<String> rcntFiles = new ArrayList<String>();
		Properties prop = Property.getProperties();
		System.out.println(prop.getProperty(nOfRcntFiles));

		int i = 0;
		while (i < Integer.parseInt(prop.getProperty(nOfRcntFiles))) {
			String path = prop.getProperty(rcntFile + i);
			if (path != null) {
				rcntFiles.add(prop.getProperty(rcntFile + i));
			}
			i++;
		}

		return rcntFiles;
	}

}
