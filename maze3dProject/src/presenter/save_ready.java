package presenter;


import model.model;
import view.view;

public class save_ready implements Command {
	
	private model m;
	private view v;
	
	public save_ready(view view , model model) {
		this.m = model;
		this.v = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String name = args[0];
	}

}
