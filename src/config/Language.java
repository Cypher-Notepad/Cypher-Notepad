package config;

public abstract class Language {
	
	//KoreanSupport
	public char testChar_EN = 'a';
	public char testChar_KO = '\uAC00';
	
	//MainUI
	public String btnNew = "New";
	public String btnOpen = "Open";
	public String rcntFiles = "Recent Files";
	public String tblName = "Name";
	public String tblDate = "Date";
	public String tblSize = "Size";
	public String tblPath = "Path";
	
	
	//NotepadUI
	public String btnOK = "OK";
	public String btnCancel = "Cancel";
	
	public String frmUntitled = "Untitled";
	public String mbFile = " File ";
	public String mbEdit = " Edit ";
	public String mbFormat = " Format ";
	public String mbView = " View ";
	public String mbHelp = " Help ";
	
	public String miNew = "New        ";
	public String miOpen = "Open        ";
	public String miSave = "Save        ";
	public String miSaveAs = "Save as...        ";
	public String miImporter = "Import Key...		";
	public String miExporter = "Export Key...		";
	public String miPageSet = "Page Setup...        ";
	public String miPrint = "Print...        ";
	public String miExit = "Exit        ";
	
	public String miUndo = "Undo        ";
	public String miCut = "Cut        ";
	public String miCopy = "Copy        ";
	public String miPaste = "Paste        ";
	public String miDelete = "Delete        ";
	public String miSearch = "Search with Google...          ";
	public String miFind = "Find...        ";
	public String miFindNxt = "Find Next        ";
	public String miReplce = "Replace...        ";
	public String miGoto = "Go To...        ";
	public String miSlctAll = "Select All        ";
	public String miTimeDate = "Time/Date        ";
	
	public String miWordWrap = "Word Wrap        ";
	public String miFont = "Font...        ";
	public String miCrypto = "Encrpytion        ";
	
	public String miStsBar = "Status Bar        ";
	
	public String miViewHelp = "View Help        ";
	public String miCNWeb = "Crypto Notepad Website    ";
	public String miAbtCN = "About Crypto Notepad        ";
	public String miSetting = "Settings        ";
	
	//KFinder
	public String kfiTitle = "Find";
	public String kfiFind = "Find:";
	public String kfiFindNxt = "Find Next";
	public String kfiDir = "Direction";
	public String kfiDirUp = "Up";
	public String kfiDirDown = "Down";
	public String kfiUppLow = "Match case";
	public String kfNoMoreFD_pre = "Cannot find \"";
	public String kfNoMoreFD_post = "\"";
	
	//KReplacer
	public String kreReplce = "Replace";
	public String krelblReplce = "Replace:";
	public String kreReplceAll = "Replace All";
	public String kreNoMoreRP = "No more to replace.";
	
	//KFontChooser
	public String kfcTitle = "Select Font";
	public String kfcFamily = "Font Family:";
	public String kfcStyle = "Style:";
	public String kfcSize = "Size:";
	public String kfcView = "View";
	public String kfcScript = "Script";
	public String kfcColor = "Color";
	public String kfKoreanFont = "Only Korean fonts";
	public String kfcPlain = "Plain";
	public String kfcBold = "Bold";
	public String kfcItalic = "Italics";
	

	//KSettings
	public String ksLang = " \uC5B8\uC5B4";
	public String ksLangHover = " \uC601\uC5B4 -> \uD55C\uAD6D\uC5B4";
	public String ksInval = "  Invalidate All Encrypted Files";
	public String ksInit = " Initialize Settings";
	public String ksNoti = "\u203B Some changes will take effect after restart.";
	public String ksLangWarn = " Language(Not Recommended)";
	public String ksLangWarnHover = "English -> Korean(Not Recommended)";
	
	//Date
	public String am = "AM";
	public String pm = "PM";
	
	//CheckSave
	public String checkSave_pre = "Your work has not been saved. Do you want to save changes to [";
	public String checkSaveToExport_pre = "Your work has to be saved to export key. Do you want to save changes to [";
	public String checkSave_post = "]?   \n";
	public String save = "Save";
	public String noSave = "  Don't Save  ";
	
	//OOME
	public String OOMEWarning = "Out of Memory. Some functions may not work correctly.";
	
	//Warning message
	public String fileNotExist = "The file does not exist." + " Please check your file name.";
	public String notAvailable = "N/A";
	public String warningSaveKey = "The key used for this file is not saved. To open this file next time, keep the key via options below    ";
	public String warningTurnOffEncryption = "Do you want to turn off encryption mode?     \nIt will be applied instantly. If you have exported keyfile, it will be invalidated.    ";
	
	//status logger
	public String status_save = " is saved.";
	public String status_open = " is opened.";
	public String status_import = "The key of current file is imported";
	public String status_export = "The key of current file is exported";
	
	//title
	public String title_openWithExportedKey = "  (The file is opened with exported keyfile.)";
	public String title_needToImportOrExport = "  (It is required to import or export the key.)";
	public String title_notEncrypted = "  (The file is not encrypted.)";
	
	
	//minor things
	public String defaultFileName = "Untitled";
	public String fileFilter_txt = "Text Documents (*.txt)";
	
	public String IHaveExportedKeyFile = "No, I have exported keyfile.";
	public String yes = "Yes";
}
