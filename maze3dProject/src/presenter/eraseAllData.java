package presenter;

import model.model;
import view.view;

/**
 * erase all data class just erase all the data
 * 
 * @author bar brownshtein
 *
 */
public class eraseAllData implements Command {
	private model m;

	public eraseAllData(view view, model model) {
		this.m = model;
	}

	@Override
	public void doCommand(String[] args) {
		m.eraseAllData();

	}
}
