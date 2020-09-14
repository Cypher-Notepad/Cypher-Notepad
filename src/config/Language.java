package config;

public abstract class Language {
	//MainUI
	public String btnNew = "New";
	public String btnOpen = "Open";
	public String rcntFiles = "Recent Files";
	public String tblName = "Name";
	public String tblDate = "Date";
	public String tblSize = "Size";
	public String tblPath = "Path";
	
	
	//NotepadUI
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
	public String miCrypto = "Encryption        ";
	
	public String miStsBar = "Status Bar        ";
	
	public String miViewHelp = "View Help        ";
	public String miSendFeedback = "Send Feedback";
	public String miCNWeb = "Cypher Notepad Website    ";
	public String miAbtCN = "About Cypher Notepad        ";
	public String miUpdate = "Check for update";
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
	
	//KeyExporter
	public String keTitle = "Export Key";
	public String keKey = "Key";
	public String keDeleteKey = "delete from program (highly recommended)";
	public String keSave = "Save";
	public String keCopy = "Copy";
	public String keCopied = "Copied";
	public String keWarn = "1. The keyfile must be PEM format.\n" + 
			"2. If you export the key of file, the file will not be affected by file-invalidation.\n" + 
			"3. If you have exported keyfile, you can decrypt the encrypted file on other devices.";
	public String keWarn2 = "4. This will re-encrypt the file with the new encrpytion key.\n" + 
			"5. The PEM file will be created with the new encryption key.";
	
	//KeyImporter
	public String kiTitle = "Import Key";
	public String kiMainContent = "Do you really want to import the key?";
	public String kiSubContent = "This process will store your key internally.\n\n" +
			"When you open this file next time :\n" +
			"  \u2022  Exported keyfile isn't needed anymore.\n" +
			"  \u2022  The file will be decrypted automatically.\n\n" +
			"When you invalidate keyfile on settings :\n" +
			"  \u2022  The key must be saved before key invalidation.\n" +
			"  \u2022  Or the file won't be able to be decrypted.";
	public String kiImport = "Import the key...";
	
	public String txtBoxDrag = "Drag or Open the keyfile.";
	//KeyOpener
	public String koTitle = "Enter Key";
	public String koMainContent = "Enter the key for decryption : ";
	public String koOpen = "Open keyfile...";
	public String koDecrypt = "Decrypt";
	
	//keyVerifier
	public String kvTitle = "Verification";
	public String kvMainContent = "Verify your key : ";
	public String kvVerify = "Verify";
	
	//KUpdater
	public String kuCheck = "Check for update";
	public String kuDefaultResult = "Press the button below to check the latest version.";
	public String kuCheckingResult = "Checking...";
	public String kuNewVersionResult_pre = "";
	public String kuNewVersionResult_post = " has been released!!";
	public String kuUpToDate = "You're up to date.";
	public String kuGetTheLatestVersion = "Get the latest version!";
	public String kuCurVersion = "Current version: ";
	public String kuLatVersion = "Latest version : ";
	public String kuFailedToConnect = "Cannot Connect to the server.\nPlease check your network connection.";
			
	//Date
	public String am = "AM";
	public String pm = "PM";
	
	//CheckSave
	public String checkSave_pre = "Your work has not been saved. Do you want to save changes to [";
	//public String checkSaveToExport_pre = "Your work has to be saved to export key. Do you want to save changes to [";
	public String checkSave_post = "]?   \n";
	public String save = "Save";
	public String noSave = "  Don't Save  ";
	
	//OOME
	public String OOMEWarning = "Out of Memory. Some functions may not work correctly.";
	
	//Warning message
	public String keyNotValid = "The key you entered is not valid for this file. Please go back to import or export the valid key";
	public String fileFormat_PEM = "The keyfile format must be PEM format. Please check your file format.";
	public String fileNotExist = "The file does not exist." + " Please check your file name.";
	public String notAvailable = "N/A";
	public String warningSaveKey = "The key used for this file is not saved. To open this file next time, keep the key via options below    ";
	public String warningTurnOffEncryption = "Do you really want to turn off encryption mode?     \n" + 
			"It will be applied instantly. If you already have exported keyfile, it will be invalidated.    ";
	public String warnOverwriteFile_pre = " [";
	public String warnOverwriteFile_post = "] already exists. \n Do you want to replace it?        ";
	
	
	//status logger
	public String status_save = " is saved.";
	public String status_open = " is opened.";
	public String status_import = "The key of current file is imported.";
	public String status_export = "The key of current file is exported.";
	public String status_turnOnEncryption = "Encryption mode ON.";
	public String status_turnOffEncryption = "Encryption mode OFF.";
	
	//MainUI title
	public String title_openWithExportedKey = "  (opened with exported keyfile)";
	public String title_needToImportOrExport = "  (required to import or export the key)";
	public String title_notEncrypted = "  (Not encrypted)";
	
	//dialog
	public String btnOK = "OK";
	public String btnCancel = "Cancel";
	public String btnNo = "No";
	public String IHaveExportedKeyFile = "No, I have exported keyfile.";
	public String btnYes = "Yes";
	
	//Language Support
	public char testChar_EN = 'a';
	public char testChar_KO = '\uAC00';
	
	//etc
	public String defaultFileName = "Untitled";
	public String fileFilter_txt = "Text Documents (*.txt)";
		
}
