package presenter;

import view.MyView;
import model.MyModel;
/**
 * Solve class
 * the class is implements Command interface and override doCommands function
 * the purpose of the class is to solve a maze by 2 search algorithms- BFS and DFS
 * the user can choose each one he want to use
 * @param algorithm
 * @param name of a maze
 * @author bar brownshtein and avihai sabiher
 */
public class solve implements Command {
	private MyModel m;
	private MyView v;
	public solve(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if (args.length == 3){
			m.Solve(args[1] , args[2]);
		}
		else if(args.length==2){
			m.Solve(args[1],"properties" );
		}
		else{
			((MyView) v).Print("Solve [(name] [(dfs/bfs) algorithm]\n");
		}
	}
}