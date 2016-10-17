package presenter;

import model.model;
import view.view;

/**
 * maze ready class
 * 
 * @author bar brownshtein
 *
 */
public class maze_ready implements Command {

	private view v;

	public maze_ready(view view, model model) {
		this.v = view;
	}

	@Override
	public void doCommand(String[] args) {
		String msg = "maze " + args[1] + " is ready !";
		v.displayMessage(msg);
	
	}

}
