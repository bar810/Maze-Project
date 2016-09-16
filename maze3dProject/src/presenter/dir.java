package presenter;
import view.MyView;
/**
 * Dir class
 * the class is implements Command interface and override doCommand function
 * the class return the the place where the saved files include.
 * @author bar brownshtein and avihai sabiher
 *
 */
public class dir implements Command{
	private MyView v;
	public dir(MyView view) {
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		ClassLoader clo = dir.class.getClassLoader();
		((MyView)v).Print(clo.getResource("").toString());
	}
}
