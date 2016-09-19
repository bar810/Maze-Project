package presenter;


import model.MyModel;
import view.MyView;

public class resetProperties implements Command {
	private MyModel m;
	private MyView v;
	public resetProperties(MyView v , MyModel m) {
		this.m = m;
		this.v = v;
	}
	@Override
	public void doCommand(String[] args) {
		m.resetProperties();

	}

}
