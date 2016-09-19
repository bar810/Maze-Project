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
		v.Print(m.getPendingMessage());	
		}
}