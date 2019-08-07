package UI;

import Thread.ThreadManager;

public class UIManager {

	private static UIManager instance = null;
	UI UIStrategy = null;

	private UIManager() {
	}

	public static UIManager getInstance() {
		if (instance == null) {
			instance = new UIManager();
		}
		return instance;
	}

	public void setDefaultUI() {
		// Default UI - Viewer;
		//UIStrategy = new ViewerUI();
		UIStrategy = new MainUI();
		UIStrategy.draw();
	}

	public void setUI(UI ui) {
		UIStrategy.erase();
		UIStrategy = ui;
		ui.draw();
	}
	
	public void closeWindow() {
		ThreadManager.getInstance().joinThreads();
		UIStrategy.erase();
	}

}
