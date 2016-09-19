package presenter;
import view.view;
/**
 * Dir class
 * the class is implements Command interface and override doCommand function
 * the class return the the place where the saved files include.
 * @author bar brownshtein and avihai sabiher
 *
 */
public class dir implements Command{
	private view v;
	public dir(view view) {
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		ClassLoader clo = dir.class.getClassLoader();
		v.Print(clo.getResource("").toString());
	}
}
