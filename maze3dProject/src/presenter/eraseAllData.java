package presenter;
import model.model;
import view.view;

public class eraseAllData implements Command {
	private model m;
	private view v;
	public eraseAllData(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		m.eraseAllData();

	}

}
