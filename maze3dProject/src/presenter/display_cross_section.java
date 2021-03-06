package presenter;

import model.model;
import view.view;

/**
 * Display cross section class. implements Command class and override doCommand
 * interface. the purpose of this class is to print the maze by choosing
 * coordinate.
 * 
 * @author bar brownshtein and avihai sabiher
 */
public class display_cross_section implements Command {
	private model m;
	private view v;

	public display_cross_section(view view, model model) {
		this.m = model;
		this.v = view;
	}

	@Override
	public void doCommand(String[] args) {
		if (args.length == 4) {
			m.getCrossSection(args[1], Integer.parseInt(args[2]), args[3]);
		} else {
			v.Print("Syntax should be: display_cross_section [X/Y/Z] [Index] [name]\n");
		}
	}
}
