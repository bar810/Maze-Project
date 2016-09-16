package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;


public interface view {

	public void start()throws Exception;
	public void Print(String string);
	public void setMaze3dData(Maze3d maze);
	void setSolution(Solution arg);
	public void displayMessage(String msg);
}