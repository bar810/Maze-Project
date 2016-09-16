package presenter;
import model.MyModel;
import view.MyView;
/**
 * Generate 3d maze class
 * the class is implements Command interface and override doCommand function
 * the purpose of the class is to generate a maze with the parameters that the user put inside
 * @param name
 * @param flors
 * @param rows
 * @param colums
 * @author bar brownshtein and avihai sabiher
 */
public class generate_3d_maze implements Command {
	private MyModel m;
	private MyView v;
	public generate_3d_maze(MyView view , MyModel model) {
		this.m = model;
		this.v = view;
	}
	@Override
	public void doCommand(String[] args) {
		if 	(args.length == 5){
			String name = args[1];
			if(isInteger(args[2])){
				int flos = Integer.parseInt(args[2]);
				if(isInteger((args[3]))) {
					int rows = Integer.parseInt(args[3]);
					if(isInteger(args[4])) {
						int cols = Integer.parseInt(args[4]);
						m.generateMaze(name, flos, rows, cols);					
					}
				}	
			}	
		}
		else{
			((MyView) v).Print("Syntax should be: generate_3d_maze [name] [flors] [rows] [colums]\n");
		}
	}
	/**
	 * chack if the number is int
	 * @param s
	 * @return true/false
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
}
