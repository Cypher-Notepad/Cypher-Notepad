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
	public String miImportKey = "Import key...	";
	public String miExportKey = "Export key...	";
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
	
	public String miStsBar = "Status Bar        ";
	
	public String miViewHelp = "View Help        ";
	public String miCNWeb = "Visit Crypto Notepad Web    ";
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
	public String checkSave_post = "]?   \n";
	public String save = "Save";
	public String noSave = "  Don't Save  ";
	
}
