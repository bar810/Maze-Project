package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import model.MyModel;
import view.MyView;

public class Presenter extends Observable implements Observer{

	private MyModel model;
	private MyView view;
	private HashMap<String, Command> commands;
	private HashMap<String, Command> ModelCmd;
	private ExecutorService exs;
	
	public Presenter(MyModel model2, MyView view2,int threads) {
		this.model = model2;
		this.view = view2;
		this.set();
		exs = Executors.newFixedThreadPool(threads);
	}
	public HashMap<String, Command> get (){
		return commands;
	}
	public void set (){
		commands = new HashMap<String, Command>();
		ModelCmd = new HashMap<String, Command>();
	
		commands.put("dir", new dir(view));
		commands.put("generate_3d_maze", new generate_3d_maze(view,model));
		commands.put("display", new display(view,model));
		commands.put("solve", new solve(view,model));
		commands.put("display_solution", new display_solution(view,model));
		commands.put("display_cross_section", new display_cross_section(view,model));
		commands.put("save_maze", new save_maze(view,model));
		commands.put("load_maze", new load_maze(view,model));
		commands.put("exit", new exit(view));;
		commands.put("display_msg", new display_message(view,model));
		commands.put("load_ready",  new load_ready(view,model));
		commands.put("maze_ready", new maze_ready(view, model));
		commands.put("solve_ready", new solve_ready(view, model));
		commands.put("save_ready",  new save_ready(view,model));
	}
	@Override
	public void update(Observable o, Object arg) {
		String Cmdstr = (String)arg;
		String [] spliter = Cmdstr.split(" ");
		Command tempCmd = commands.get(spliter[0]);
		if (tempCmd == null){
			view.Print("Command Not Found!");
		}
		else{
			tempCmd.doCommand(spliter);
		}
	}
}	