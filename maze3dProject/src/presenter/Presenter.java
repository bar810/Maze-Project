package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import model.model;
import view.view;

public class Presenter extends Observable implements Observer{

	private model model;
	private view view;
	private HashMap<String, Command> commands;
	private ExecutorService exs;
	public Properties properties;
	
	public Presenter(model model, view view,int threads,Properties p) {
		this.model = model;
		this.view = view;
		this.set();
		exs = Executors.newFixedThreadPool(threads);
		this.properties=p;
		
		model.setProperties(properties);
		view.setProperties(properties);
	}
	public HashMap<String, Command> get (){
		return commands;
	}
	public void set (){
		commands = new HashMap<String, Command>();
	
		commands.put("dir", new dir(view));
		commands.put("generate_3d_maze", new generate_3d_maze(view,model));
		commands.put("display", new display(view,model));
		commands.put("solve", new solve(view,model));
		commands.put("display_solution", new display_solution(view,model));
		commands.put("display_cross_section", new display_cross_section(view,model));
		commands.put("save_maze", new save_maze(view,model));
		commands.put("load_maze", new load_maze(view,model));
		commands.put("exit", new exit(view,model));;
		commands.put("display_msg", new display_message(view,model));
		commands.put("load_ready",  new load_ready(view,model));
		commands.put("maze_ready", new maze_ready(view, model));
		commands.put("solve_ready", new solve_ready(view, model));
		commands.put("save_ready",  new save_ready(view,model));
		commands.put("erase_all",  new eraseAllData(view,model));
		commands.put("reset_properties",  new resetProperties(view,model));
		commands.put("setPropertiesEX",  new setProperties(view,model));
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