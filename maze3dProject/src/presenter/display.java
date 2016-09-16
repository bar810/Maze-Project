package presenter;
import view.MyView;
import model.MyModel;
/**
 * Display class
 * this class is implements Command interface and override doCommand function
 * the purpose of this class is to print the maze
 * @author bar brownshtein and avihai sabiher
 */
public class display implements Command {
	private MyModel m;
	private MyView v;
	public display(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if (args.length == 2){
			((MyModel) m).getMaze3d(args[1]);
		}
		else{
			((MyView) v).Print("display [name]\n");
		}	
	}
}