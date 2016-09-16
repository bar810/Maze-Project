package presenter;


import model.MyModel;
import view.MyView;

public class save_ready implements Command {
	
	private MyModel m;
	private MyView v;
	
	public save_ready(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	
	@Override
	public void doCommand(String[] args) {
		String name = args[0];
		String msg = "the maze are saved !";
		v.displayMessage(msg);
	}

}
