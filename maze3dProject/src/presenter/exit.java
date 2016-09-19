package presenter;

import model.model;
import view.view;
/**
 * Exit class
 * this class is implements Command interface and override doCommand function
 * the purpose of the class is to finish the program and exit
 * @author bar brownshtein and avihai sabiher
 */
public class exit implements Command{
	private view v;
	private model m;
	
	public exit(view view,model model) {
		this.v = view;
		this.m=model;
	}
	@Override
	public void doCommand(String[] args) {
		m.Exit();
		v.Print("\nShutdown the program. thank you \n");	
	}
}
