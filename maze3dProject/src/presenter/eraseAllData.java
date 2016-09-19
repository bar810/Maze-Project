package presenter;


import model.MyModel;
import view.MyView;

public class eraseAllData implements Command {
	private MyModel m;
	private MyView v;
	public eraseAllData(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		m.eraseAllData();

	}

}
