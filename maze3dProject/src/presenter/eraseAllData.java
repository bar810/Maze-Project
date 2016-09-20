package presenter;
import model.model;
import view.view;

public class eraseAllData implements Command {
	private model m;
	public eraseAllData(view view , model model) {
		this.m = model;
	}
	@Override
	public void doCommand(String[] args) {
		m.eraseAllData();

	}
}
