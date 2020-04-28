package thread;

import ui.MainUI;
import ui.NotepadUI;

public class TrPrepareNotepadUI extends Thread{

	@Override
	public void run() {
		//System.out.println("[MainUI]Preload NotepadUI");
		MainUI.notepadUI = new NotepadUI();
		MainUI.notepadUI.initializeUI();
	}
}
