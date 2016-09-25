package presenter;

import model.model;
import view.view;

/**
 * get mazes names class return all the mazes names
 * 
 * @author bar brownshtein
 *
 */
public class getMazesNames implements Command {
	public model m;
	public view v;

	public getMazesNames(view view, model model) {
		this.m = model;
		this.v = view;
	}

	@Override
	public void doCommand(String[] args) {
		m.getMazesNames();
	}
}
