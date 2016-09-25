package presenter;

import model.model;
import view.view;

/**
 * load ready class
 * 
 * @author bar brownshtein
 *
 */
public class load_ready implements Command {
	private view v;

	public load_ready(view view, model model) {
		this.v = view;
	}

	@Override
	public void doCommand(String[] args) {
		String msg = "the maze are loaded !";
		v.displayMessage(msg);
	}
}
