package presenter;

import view.MyView;
/**
 * Exit class
 * this class is implements Command interface and override doCommand function
 * the purpose of the class is to finish the program and exit
 * @author bar brownshtein and avihai sabiher
 */
public class exit implements Command{
	private MyView v;
	public exit(MyView view) {
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		((MyView)v).Print("Shutdown the program. thank you \n");	
	}
}
