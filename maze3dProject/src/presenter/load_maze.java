package presenter;

import model.model;
import view.view;

/**
 * load maze class
 * 
 * @author bar brownshtein
 *
 */
public class load_maze implements Command {
	private model m;
	private view v;

	public load_maze(view v, model m) {
		this.m = m;
		this.v = v;
	}

	@Override
	public void doCommand(String[] args) {
		if (args.length == 2) {
			// right input
			m.loadFromFile(args[1]);
		} else {
			v.Print("Load_maze  [name] \n");
		}
	}
}
