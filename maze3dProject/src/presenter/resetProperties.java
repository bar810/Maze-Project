package presenter;
import model.model;
import view.view;


public class resetProperties implements Command {
	private model m;
	public resetProperties(view v , model m) {
		this.m = m;
	}
	@Override
	public void doCommand(String[] args) {
		m.resetProperties();

	}

}
