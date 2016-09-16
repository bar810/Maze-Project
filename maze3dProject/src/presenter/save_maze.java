package presenter;


import model.Model;
import model.MyModel;
import view.MyView;
import view.View;

public class save_maze implements Command {

	private MyModel m;
	private MyView v;
	public save_maze(MyView v , MyModel m) {
		this.m = m;
		this.v = v;
	}
	@Override
	public void doCommand(String[] args) {
			if (args.length == 3){
				//right input
			m.saveToFile(args[1], args[2]);
			}
			else{
				((MyView)v).Print("save_maze [name] [fileName.maz]");
			}
		}

}
