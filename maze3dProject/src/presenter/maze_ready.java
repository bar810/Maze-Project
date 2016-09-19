package presenter;


import model.model;
import view.view;

public class maze_ready implements Command {

	private model m;
	private view v;
	public maze_ready(view view , model model) {
		this.m = model;
		this.v = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String name = args[0];
		String msg = "maze is ready";
		v.displayMessage(msg);
	}

}
