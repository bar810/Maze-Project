package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import presenter.Properties;


public interface view {

	public void start()throws Exception;
	public void Print (String str);
	public void displayMessage(String msg);
	public void setProperties(Properties p);
	
}
