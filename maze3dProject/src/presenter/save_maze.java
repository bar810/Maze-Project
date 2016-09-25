package presenter;

import model.model;
import view.view;

/**
 * save maze
 * 
 * @author bar brownshtein
 *
 */
public class save_maze implements Command {

	private model m;
	private view v;

	public save_maze(view v, model m) {
		this.m = m;
		this.v = v;
	}

	@Override
	public void doCommand(String[] args) {
		if (args.length == 2) {
			// right input
			m.saveToFile(args[1]);
		} else {
			v.Print("save_maze [name] ");
		}
	}

}
