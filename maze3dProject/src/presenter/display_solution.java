package presenter;
import model.MyModel;
import view.MyView;
/**
 * Display solution class
 * this class is implements Command interface and override doCommand function
 * the purpose of this class is to print the solution on the maze.
 * @author bar brownshtein and avihai sabiher
 *
 */
public class display_solution implements Command {
	private MyModel m;
	private MyView v;
	public display_solution(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if (args.length == 2){
			m.Display_Sol(args[1]);
		}
		else{
			((MyView) v).Print("display_solution [name]\n");
		}		
	}
}