package presenter;

import model.MyModel;
import view.MyView;

public class display_message implements Command{
	private MyModel m;
	private MyView v;
	
	public display_message(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		v.Print(m.getPendingMessage());	
		}
}