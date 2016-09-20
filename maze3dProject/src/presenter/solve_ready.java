package presenter;

import model.model;
import view.view;

public class solve_ready implements Command {
	private view v;
	public solve_ready(view view , model model) {
		this.v = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String msg = "solve is ready";
		v.displayMessage(msg);
	}
}
