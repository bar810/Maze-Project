package presenter;
import model.model;
import view.view;

public class display_message implements Command{
	private model m;
	private view v;
	
	public display_message(view view , model model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
//		if(args.length==1)
//		v.Print(m.getPendingMessage());	
//		else
			v.displayMessage(args[1]);	
		}
}