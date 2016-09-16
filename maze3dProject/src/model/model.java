package model;

public interface model {
	public void generateMaze(String name, int flos, int rows, int cols);
	public void Solve(String name, String algo);
	public void Display_Sol(String name);
	public void getCrossSection(String axis, int index, String name);
	public void getMaze3d(String name);	
	public String getPendingMessage ();
	public void saveToFile(String name);
	public void loadFromFile(String Name);
	public String Exit ();
	public void saveToFile (String name, String fileName);
	public void loadFromFile (String fileName, String name);
}
