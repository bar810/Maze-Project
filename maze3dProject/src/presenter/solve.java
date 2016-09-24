package presenter;
import view.view;
import model.model;

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
	private model m;
	private view v;
	public solve(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if (args.length == 3){
			m.Solve(args[1] , args[2],"same");
		}
		if (args.length == 4){
			m.Solve(args[1] , args[2],args[3]);
		}
		else if(args.length==2){
			m.Solve(args[1],"properties","same");
		}
		else{
			 v.Print("Solve [(name] [(dfs/bfs) algorithm]\n");
		}
	}
}