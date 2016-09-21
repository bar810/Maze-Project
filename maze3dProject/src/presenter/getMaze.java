package presenter;

import model.model;
import view.view;

public class getMaze implements Command{
	public model m;
	public view v;
	public  getMaze(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		v.getMaze(args[1]);
		
	}
	
	
	
}
