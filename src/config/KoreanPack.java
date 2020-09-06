package config;

class KoreanPack extends Language {

	KoreanPack() {
		// MainUI
		btnNew = "\uC0C8\uB85C \uB9CC\uB4E4\uAE30";
		btnOpen = "\uC5F4\uAE30";
		rcntFiles = "\uC5F4\uC5B4\uBCF8 \uBAA9\uB85D";
		tblName = "\uC774\uB984";
		tblDate = "\uB0A0\uC9DC";
		tblSize = "\uD06C\uAE30";
		tblPath = "\uACBD\uB85C";

		// NotepadUI
		frmUntitled = "\uC81C\uBAA9 \uC5C6\uC74C";
		mbFile = "\uD30C\uC77C\u0028F\u0029 ";
		mbEdit = "\uD3B8\uC9D1\u0028E\u0029 ";
		mbFormat = "\uC11C\uC2DD\u0028O\u0029 ";
		mbView = "\uBCF4\uAE30\u0028V\u0029 ";
		mbHelp = "\uB3C4\uC6C0\uB9D0 ";

		miNew = "\uC0C8\uB85C \uB9CC\uB4E4\uAE30\u0028N\u0029        ";
		miOpen = "\uC5F4\uAE30\u0028O\u0029        ";
		miSave = "\uC800\uC7A5\u0028S\u0029        ";
		miSaveAs = "\uB2E4\uB978 \uC774\uB984\uC73C\uB85C \uC800\uC7A5\u0028A\u0029...        ";
		
		miImporter = "\uD504\uB85C\uADF8\uB7A8\uC5D0 \uD0A4 \uCD94\uAC00...  ";
		miExporter = "\uC678\uBD80\uD30C\uC77C\uC5D0 \uD0A4 \uC800\uC7A5...  ";
		//miImporter = "\uD0A4 \uCD94\uAC00\uD558\uAE30...	";
		//miExporter = "\uD0A4 \uCD94\uCD9C\uD558\uAE30...	";
		
		miPageSet = "\uD398\uC774\uC9C0 \uC124\uC815\u0028U\u0029...        ";
		miPrint = "\uC778\uC1C4\u0028P\u0029...        ";
		miExit = "\uB05D\uB0B4\uAE30\u0028X\u0029        ";

		miUndo = "\uC2E4\uD589 \uCDE8\uC18C\u0028U\u0029            ";
		miCut = "\uC798\uB77C\uB0B4\uAE30\u0028T\u0029            ";
		miCopy = "\uBCF5\uC0AC\u0028C\u0029            ";
		miPaste = "\uBD99\uC5EC\uB123\uAE30\u0028P\u0029            ";
		miDelete = "\uC0AD\uC81C\u0028L\u0029            ";
		miSearch = "Google\uC73C\uB85C \uAC80\uC0C9\u0028S\u0029...          ";
		miFind = "\uCC3E\uAE30\u0028F\u0029...            ";
		miFindNxt = "\uB2E4\uC74C \uCC3E\uAE30\u0028N\u0029            ";
		miReplce = "\uBC14\uAFB8\uAE30\u0028R\u0029...            ";
		miGoto = "\uC774\uB3D9\u0028G\u0029...            ";
		miSlctAll = "\uBAA8\uB450 \uC120\uD0DD\u0028A\u0029            ";
		miTimeDate = "\uC2DC\uAC04/\uB0A0\uC9DC\u0028D\u0029            ";

		miWordWrap = "\uC790\uB3D9 \uC904 \uBC14\uAFC8\u0028W\u0029        ";
		miFont = "\uAE00\uAF34\u0028F\u0029...        ";
		miCrypto = "\uC554\uD638\uD654        ";

		miStsBar = "\uC0C1\uD0DC \uD45C\uC2DC\uC904\u0028S\u0029        ";

		miViewHelp = "\uB3C4\uC6C0\uB9D0 \uBCF4\uAE30\u0028H\u0029        ";
		miCNWeb = "Cypher Notepad \uD648\uD398\uC774\uC9C0";
		miAbtCN = "Cypher Notepad \uC815\uBCF4\u0028A\u0029        ";
		miSetting = "\uC124\uC815\u0028S\u0029        ";

		// KFinder
		kfiTitle = "\uCC3E\uAE30";
		kfiFind = "\uCC3E\uC744 \uB0B4\uC6A9: ";
		kfiFindNxt = "\uB2E4\uC74C \uCC3E\uAE30";
		kfiDir = "\uBC29\uD5A5";
		kfiDirUp = "\uC704\uB85C";
		kfiDirDown = "\uC544\uB798\uB85C";
		kfiUppLow = "\uB300/\uC18C\uBB38\uC790 \uAD6C\uBD84";
		kfNoMoreFD_pre = "\"";
		kfNoMoreFD_post = "\"\uC744\u0028\uB97C\u0029 \uCC3E\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.";
		
		// KReplacer
		kreReplce = "\uBC14\uAFB8\uAE30";
		krelblReplce = "\uBC14\uAFC0 \uB0B4\uC6A9: ";
		kreReplceAll = "\uBAA8\uB450 \uBC14\uAFB8\uAE30";
		kreNoMoreRP = "\uB354 \uC774\uC0C1 \uBC14\uAFC0 \uD14D\uC2A4\uD2B8\uAC00 \uC5C6\uC2B5\uB2C8\uB2E4.";

		// KFontChooser
		kfcTitle = "\uAE00\uAF34 \uBCC0\uACBD";
		kfcFamily = "\uAE00\uAF34:";
		kfcStyle = "\uAE00\uAF34 \uC2A4\uD0C0\uC77C:";
		kfcSize = "\uD06C\uAE30:";
		kfcView = "\uBCF4\uAE30";
		kfcScript = "\uC2A4\uD06C\uB9BD\uD2B8";
		kfcColor = "\uC0C9 \uC0C1";
		kfKoreanFont = "\uD55C\uAE00 \uC9C0\uC6D0 \uD3F0\uD2B8";
		kfcPlain = "\uBCF4\uD1B5";
		kfcBold = "\uAD75\uAC8C";
		kfcItalic = "\uAE30\uC6B8\uC784\uAF34";

		// KSettings
		ksLang = " Language";
		ksLangHover = " Korean -> English";
		ksInval = " \uBAA8\uB4E0 \uD30C\uC77C \uBB34\uD6A8\uD654";
		ksInit = " \uC124\uC815 \uCD08\uAE30\uD654";
		ksNoti = "\u203B \uC77C\uBD80 \uBCC0\uACBD\uC0AC\uD56D\uC740 \uD504\uB85C\uADF8\uB7A8 \uC7AC\uC2DC\uC791 \uD6C4 \uC801\uC6A9\uB429\uB2C8\uB2E4.";

		//KeyExporter
		keTitle = "\uC678\uBD80\uD30C\uC77C\uC5D0 \uC554\uD638\uD0A4 \uC800\uC7A5";
		keKey = "\uC554\uD638\uD0A4";
		keDeleteKey = "\uD504\uB85C\uADF8\uB7A8\uC5D0\uC11C \uD0A4 \uC81C\uAC70\uD558\uAE30";
		keSave = "\uC800\uC7A5";
		keCopy = "\uBCF5\uC0AC";
		keCopied = "\uBCF5\uC0AC\uB428";
		keWarn = "1. \uD0A4\uD30C\uC77C\uC740 PEM\uD30C\uC77C \uD615\uC2DD\uC774\uC5B4\uC57C \uD569\uB2C8\uB2E4.\n" + 
				"2. \uC554\uD638\uD0A4\uB97C \uCD94\uCD9C\uD558\uC5EC \uD0A4\uD30C\uC77C\uC5D0 \uC800\uC7A5\uD558\uBA74 \uD30C\uC77C\uBB34\uD6A8\uD654\uC5D0 \uC601\uD5A5\uBC1B\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4.\n" + 
				"3. \uD0A4\uD30C\uC77C\uC744 \uAC16\uACE0 \uC788\uB2E4\uBA74 \uC554\uD638\uD654\uB41C \uD30C\uC77C\uC744 \uB2E4\uB978 \uAE30\uAE30\uC5D0\uC11C\uB3C4 \uBCF5\uD638\uD654 \uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\n";
		
		//KeyImporter
		kiTitle = "\uD504\uB85C\uADF8\uB7A8\uC5D0 \uC554\uD638\uD0A4 \uCD94\uAC00";
		kiMainContent = "\uD504\uB85C\uADF8\uB7A8\uC5D0 \uC554\uD638\uD0A4\uB97C \uCD94\uAC00\uD558\uC2DC\uACA0\uC2B5\uB2C8\uAE4C\u003F";
		kiSubContent = "\uC774 \uC791\uC5C5\uC740 \uD30C\uC77C\uC758 \uC554\uD638\uD0A4\uB97C \uD504\uB85C\uADF8\uB7A8\uC5D0\uC11C \uBCF4\uAD00\uD558\uB3C4\uB85D \uD569\uB2C8\uB2E4.\n\n" +
				"\uB2E4\uC74C\uC5D0 \uC774 \uD30C\uC77C\uC744 \uC5F4 \uACBD\uC6B0\u003A\n" +
				"  \u2022  \uC678\uBD80 \uD0A4\uD30C\uC77C\uC740 \uB354 \uC774\uC0C1 \uD544\uC694\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4.\n" +
				"  \u2022  \uD30C\uC77C\uC740 \uC790\uB3D9\uC73C\uB85C \uBCF5\uD638\uD654 \uB420 \uAC83\uC785\uB2C8\uB2E4.\n\n" +
				"\uC124\uC815\uCC3D\uC5D0\uC11C \uD30C\uC77C \uBB34\uD6A8\uD654\uB97C \uD560 \uACBD\uC6B0\u003A\n" +
				"  \u2022  \uD544\uC694\uD55C \uD30C\uC77C\uC758 \uC554\uD638\uD0A4\uB97C \uBBF8\uB9AC \uC800\uC7A5\uD574 \uB193\uC544\uC57C \uD569\uB2C8\uB2E4.\n" +
				"  \u2022  \uADF8\uB807\uC9C0 \uC54A\uC73C\uBA74 \uD574\uB2F9 \uD30C\uC77C\uC758 \uBCF5\uD638\uD654\uAC00 \uBD88\uAC00\uB2A5\uD574\uC9D1\uB2C8\uB2E4.";
		kiImport = "\uD504\uB85C\uADF8\uB7A8\uC5D0 \uCD94\uAC00...";
		
		//public String txtBoxDrag = "Drag or Open the keyfile.";
		//KeyOpener
		koTitle = "\uD0A4 \uC785\uB825";
		koMainContent = "\uC554\uD638\uD0A4\uB97C \uC785\uB825\uD574\uC8FC\uC138\uC694 \u003A ";
		koOpen = "\uD0A4\uD30C\uC77C \uBD88\uB7EC\uC624\uAE30...";
		koDecrypt = "\uBCF5\uD638\uD654";
		
		//keyVerifier
		kvTitle = "\uD0A4 \uAC80\uC99D";
		kvMainContent = "\uC720\uD6A8\uD55C \uD0A4\uC778\uC9C0 \uD655\uC778\uD558\uC2ED\uC2DC\uC624 \u003A ";
		kvVerify = "\uAC80\uC99D ";
		
		//Date
		am = "\uC624\uC804";
		pm = "\uC624\uD6C4";
		
		//CheckSave
		checkSave_pre = "\uC791\uC5C5\uC774 \uC800\uC7A5\uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4. \uBCC0\uACBD \uB0B4\uC6A9\uC744 [";
		checkSave_post = "]\uC5D0 \uC800\uC7A5\uD558\uC2DC\uACA0\uC2B5\uB2C8\uAE4C?   \n";
		save = "\uC800\uC7A5";
		noSave = "  \uC800\uC7A5 \uC548 \uD568  ";

		//OOME
		OOMEWarning = "\uBA54\uBAA8\uB9AC\uAC00 \uBD80\uC871\uD569\uB2C8\uB2E4. \uC77C\uBD80 \uAE30\uB2A5\uC774 \uC815\uC0C1\uC801\uC73C\uB85C \uB3D9\uC791\uD558\uC9C0 \uC54A\uC744 \uC218 \uC788\uC2B5\uB2C8\uB2E4. ";
	
		//Warning message
		keyNotValid = "\uC62C\uBC14\uB978 \uD0A4\uAC00 \uC544\uB2D9\uB2C8\uB2E4. \uC774\uC804 \uD654\uBA74\uC5D0\uC11C \uC720\uD6A8\uD55C \uD0A4\uB97C \uC800\uC7A5\uD558\uC2ED\uC2DC\uC624.";
		
		fileFormat_PEM = "\uC62C\uBC14\uB978 \uD30C\uC77C \uD615\uC2DD\uC774 \uC544\uB2D9\uB2C8\uB2E4. \uD0A4\uD30C\uC77C\uC740  PEM \uD30C\uC77C \uD615\uC2DD\uC774\uC5B4\uC57C \uD569\uB2C8\uB2E4.";
		fileNotExist = "\uD30C\uC77C\uC774 \uC874\uC7AC\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4. \uD30C\uC77C \uC774\uB984\uC744 \uD655\uC778\uD574 \uC8FC\uC2ED\uC2DC\uC624.";
		//public String notAvailable = "N/A";
		warningSaveKey = "\uC554\uD638\uD0A4\uAC00 \uC800\uC7A5\uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4. \uB2E4\uC74C\uC5D0 \uD30C\uC77C\uC744 \uC5F4\uAE30 \uC704\uD574\uC11C\u002C \uC544\uB798 \uC635\uC158\uC744 \uD1B5\uD574 \uD0A4\uB97C \uC800\uC7A5\uD558\uC2ED\uC2DC\uC624.";
		warningTurnOffEncryption = "\uC815\uB9D0 \uC554\uD638\uD654 \uBAA8\uB4DC\uB97C \uB044\uC2DC\uACA0\uC2B5\uB2C8\uAE4C\u003F     \n\uC774 \uC791\uC5C5\uC740 \uC989\uC2DC \uD30C\uC77C\uC5D0 \uC801\uC6A9\uB429\uB2C8\uB2E4. \uCD94\uCD9C\uB41C \uC678\uBD80 \uD0A4\uD30C\uC77C\u0028.pem\u0029\uC774 \uC788\uB2E4\uBA74 \uBB34\uD6A8\uD654\uB429\uB2C8\uB2E4.    ";
		
		//status logger
		status_save = " \uB97C \uC800\uC7A5\uD588\uC2B5\uB2C8\uB2E4.";
		status_open = " \uB97C \uC5F4\uC5C8\uC2B5\uB2C8\uB2E4.";
		status_import = "\uC554\uD638\uD0A4\uAC00 \uD504\uB85C\uADF8\uB7A8\uC5D0 \uCD94\uAC00\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
		status_export = "\uC554\uD638\uD0A4\uAC00 \uD0A4\uD30C\uC77C\u0028.pem\u0029\uB85C \uC800\uC7A5\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
		
		//MainUI title
		title_openWithExportedKey = "  (\uC678\uBD80 \uD0A4 \uD30C\uC77C \uC0AC\uC6A9)";
		title_needToImportOrExport = "  (\uC554\uD638\uD0A4 \uC800\uC7A5 \uD544\uC694)";
		title_notEncrypted = "  (\uC554\uD638\uD654\uB418\uC9C0 \uC54A\uC74C)";
		
		//dialog
		btnOK = "\uD655\uC778";
		btnCancel = "\uCDE8\uC18C";
		//btnNo = "No";
		IHaveExportedKeyFile = "\uC544\uB2C8\uC624\u002C \uC774\uBBF8 \uC554\uD638\uD0A4\uB97C \uC54C\uACE0 \uC788\uC2B5\uB2C8\uB2E4.";
		btnYes = "\uC608";
		
		//etc
		defaultFileName = "\uC81C\uBAA9 \uC5C6\uC74C";
		fileFilter_txt = "\uD14D\uC2A4\uD2B8 \uBB38\uC11C (*.txt)";
				
		
	}
}
