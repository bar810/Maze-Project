package presenter;
import view.view;
import model.model;
/**
 * Display class
 * this class is implements Command interface and override doCommand function
 * the purpose of this class is to print the maze
 * @author bar brownshtein and avihai sabiher
 */
public class display implements Command {
	private model m;
	private view v;
	public display(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if (args.length == 2){
			 m.getMaze3d(args[1]);
		}
		else{
			 v.Print("display [name]\n");
		}	
	}
}