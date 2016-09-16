package model;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import algorithms.demo.MazeSearchableAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyDecompressorInputStream;
import io.myCompressorOutputStream;
public class MyModel extends Observable implements model {
///
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution> solutions = new HashMap<String, Solution>();
	Maze3d maze;
	String message;
	int[][] maze2d = null;
	ExecutorService exs;
	Maze3dGenerator mg;
	public MyModel(int numThreads) {
		exs = Executors.newFixedThreadPool(numThreads);
	}
	@Override
	public void generateMaze(String name, int flos, int rows, int cols) {
		mg = new GrowingTreeGenerator();
		maze = mazes.get(name);
		if (maze == null) {
			FutureTask<Maze3d> f = new FutureTask<Maze3d>(new Callable<Maze3d>() {
				@Override
				public Maze3d call() throws Exception {
					return mg.generate(flos, rows, cols);
				}
			});
			exs.execute(f);
			try {
				maze = f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			mazes.put(name, maze);
			setChanged();
			message = "Maze: " + name + " Generated succesfully!\n";
			notifyObservers("maze_ready " + name);
		} else {
			message = "This name already exists!\n";
			setChanged();
			notifyObservers("display_msg");
		}		
	}
	@Override
	public void Solve(String name, String algo) {
		//solve is exist
		if(solutions.containsKey(name)){
			setChanged();
			System.out.println("chack chack chack");
			notifyObservers("solve_ready " + name);
		}
		else if (mazes.get(name) != null) {
			MazeSearchableAdapter mazeAdapter = new MazeSearchableAdapter(mazes.get(name));
			FutureTask<Solution> f = new FutureTask<Solution>(new Callable<Solution>() {
				@Override
				public Solution call() throws Exception {
					switch (algo) {
					case "dfs":
						solutions.put(name, new DFS().search(mazeAdapter));
						break;
					case "bfs":
						solutions.put(name, new BFS().search(mazeAdapter));
						break;
					}
					return solutions.get(name);
				}
			});
			exs.execute(f);
			message = "Solution created!\n";
			setChanged();
			notifyObservers("solve_ready " + name);
		} else {
			message = "Couldn't find maze!\n";
			setChanged();
			notifyObservers("display_msg");
		}
	}
	@Override
	public void Display_Sol(String name) {
		if (mazes.get(name) != null) {
			if (solutions.get(name) != null) {
				message = solutions.get(name).getStates().toString();
			} else {
				message = "There is no solution yet. Please run Solve [name] [dfs/bfs algorithm] command first! \n";
			}
		} else {
			message = "Couldn't find maze!\n";
		}
		setChanged();
		notifyObservers("display_msg");		
	}

	@Override
	public void getCrossSection(String axis, int index, String name) {

		StringBuilder sb = new StringBuilder();
		if (mazes.get(name) != null) {
			Maze3d maze = mazes.get(name);

			switch (axis) {
			case "x":
				maze2d = maze.getCrossSectionByX(index);
				break;
			case "y":
				maze2d = maze.getCrossSectionByY(index);
				break;
			case "z":
				maze2d = maze.getCrossSectionByZ(index);
				break;
			default:
				message = "Wrong Coordinate, only X/Y/Z accepted\n";
				setChanged();
				notifyObservers("display_msg");
				break;
			}

			for (int i = 0; i < maze2d.length; i++) {
				for (int j = 0; j < maze2d[0].length; j++) {
					sb.append((((Integer) maze2d[i][j]).toString() + " "));
				}
				sb.append("\n");
			}

		} else {
			message = "Couldn't find maze by name!\n";
			setChanged();
			notifyObservers("display_msg");
		}

		message = sb.toString();
		setChanged();
		notifyObservers("display_msg");	//getMazeData	
	}

	@Override
	public void getMaze3d(String name) {
		if (mazes.get(name) != null) {
			message = mazes.get(name).toString() + "\nStart Position: " + mazes.get(name).getStartPosition()
					+ "\nGoal Position: " + mazes.get(name).getGoalPosition() + "\n";
			setChanged();
			notifyObservers("display_msg");
		} else {
			message = "Couldn't find maze by name!\n";
			setChanged();
			notifyObservers("display_msg");
		}		
	}

	@Override
	public void saveToFile (String name, String fileName){
		if (mazes.get(name) != null){
			try{
				OutputStream out = new myCompressorOutputStream(new FileOutputStream(fileName));
				try{
					out.write(mazes.get(name).toByteArray());
					out.flush();
					out.close();
					message= "Maze saved to " + fileName;
					setChanged();
					notifyObservers("save_ready " + name);
				}
				catch(IOException e){
					message= "Loading Failed : Couldn't read the file 0x01";
				}
			}
			catch(FileNotFoundException e){
				message= "Loading Failed : File Not Found 0x01";
			}
		}
		else{
			message= "Couldn't find maze by name!";
		}
	}
	
	@Override
	public void loadFromFile (String fileName, String name){
		byte [] b = new byte [3];
		InputStream in = null;
		Maze3d maze = null;
		try {
			in = new FileInputStream(fileName);
		} 
		catch (FileNotFoundException e2) {
			message= "Loading Failed : File Not Found 0x01";
		}
		try {
			in.read(b, 0, 3);
			int size = (b[0] * b[1] * b[2]) + 9;
			b = null;
			b = new byte [size];
			in.close();
		} 
		catch (IOException e2) {
			message= "Loading Failed : Couldn't read the file 0x01";
		}
		try {
			in = new MyDecompressorInputStream(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			message= "Loading Failed : File Not Found 0x02";
		}
		try {
			in.read(b);
			maze = new Maze3d(b);
			mazes.put(name, maze);
			in.close();
			message= "Maze Loaded Succefully!";
			setChanged();
			notifyObservers("load_ready " + name);
		} 
		catch (IOException e) {
			message= "Loading Failed : Couldn't read the file 0x02";
		}
}
	
	@Override
	public String getPendingMessage() {
		return message;
	}

	@Override
	public void saveToFile(String name) {
		// TODO Auto-generated catch block
	}

	@Override
	public void loadFromFile(String Name) {
		// TODO Auto-generated catch block
	}

	@Override
	public String Exit() {
		return null;
	}

}
