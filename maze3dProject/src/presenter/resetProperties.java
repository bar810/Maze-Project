package presenter;
import model.model;
import view.view;

/**
 * reser properties
 * @author bar brownshtein
 *
 */
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
