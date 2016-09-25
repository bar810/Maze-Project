package presenter;

import model.model;
import view.view;
/**
 * set properties
 * @author bar brownshtein
 *
 */
public class setProperties implements Command {

	private model m;
	private view v;
	public setProperties(view view , model model) {
		this.m = model;
		this.v = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		m.setPropertiesEX(args);
	}
}
