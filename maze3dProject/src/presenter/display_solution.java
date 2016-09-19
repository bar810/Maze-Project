package presenter;
import model.model;
import view.view;
/**
 * Display solution class
 * this class is implements Command interface and override doCommand function
 * the purpose of this class is to print the solution on the maze.
 * @author bar brownshtein and avihai sabiher
 *
 */
public class display_solution implements Command {
	private model m;
	private view v;
	public display_solution(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if (args.length == 2){
			m.Display_Sol(args[1]);
		}
		else{
			 v.Print("display_solution [name]\n");
		}		
	}
}