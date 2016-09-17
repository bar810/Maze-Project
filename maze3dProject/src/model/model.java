package model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface model {
	public void generateMaze(String name, int flos, int rows, int cols);
	public void Solve(String name, String algo);
	public void Display_Sol(String name);
	public void getCrossSection(String axis, int index, String name);
	public void getMaze3d(String name);	
	public String getPendingMessage ();

	public void Exit ();
	public void saveToFile (String name);
	public void loadFromFile (String name);


}
