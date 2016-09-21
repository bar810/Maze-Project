package presenter;

import model.model;
import view.view;

public class getInformation implements Command {
	public model m;
	public view v;
	public  getInformation(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		v.getInformation(args[1]);
		
		
	}
}
