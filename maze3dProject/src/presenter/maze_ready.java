package presenter;


import model.model;
import view.view;

public class maze_ready implements Command {

	private view v;
	public maze_ready(view view , model model) {
		this.v = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String msg = "maze is ready";
		v.displayMessage(msg);
	}

}
