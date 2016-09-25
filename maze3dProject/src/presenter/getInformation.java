package presenter;

import model.model;
import view.view;

/**
 * get information class trander information beetwen the view and the model
 * 
 * @author bar brownshtein
 *
 */
public class getInformation implements Command {
	public model m;
	public view v;

	public getInformation(view view, model model) {
		this.m = model;
		this.v = view;
	}

	@Override
	public void doCommand(String[] args) {
		v.getInformation(args[1]);

	}
}
