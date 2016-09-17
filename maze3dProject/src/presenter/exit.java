package presenter;

import model.MyModel;
import view.MyView;
/**
 * Exit class
 * this class is implements Command interface and override doCommand function
 * the purpose of the class is to finish the program and exit
 * @author bar brownshtein and avihai sabiher
 */
public class exit implements Command{
	private MyView v;
	private MyModel m;
	
	public exit(MyView view,MyModel model) {
		this.v = view;
		this.m=model;
	}
	@Override
	public void doCommand(String[] args) {
		m.Exit();
		((MyView)v).Print("\nShutdown the program. thank you \n");	
	}
}
