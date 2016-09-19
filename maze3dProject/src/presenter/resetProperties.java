package presenter;
import model.model;
import view.view;


public class resetProperties implements Command {
	private model m;
	private view v;
	public resetProperties(view v , model m) {
		this.m = m;
		this.v = v;
	}
	@Override
	public void doCommand(String[] args) {
		m.resetProperties();

	}

}
