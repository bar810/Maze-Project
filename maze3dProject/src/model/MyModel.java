package model;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution> solutions = new HashMap<String, Solution>();
	Maze3d maze;
	String message;
	int[][] maze2d = null;
	ExecutorService exs;
	Maze3dGenerator mg;
	
	public MyModel(int numThreads) {
		exs = Executors.newFixedThreadPool(numThreads);
	loadAllMazesFromFile(mazes);
	
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
			message= "The solution for this maze is already exist!";
			notifyObservers("display_msg " );
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
		} 
		else {
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
		notifyObservers("display_msg");	
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
	public void saveToFile (String name){
		if (mazes.get(name) != null){
			try{
				OutputStream out = new myCompressorOutputStream(new FileOutputStream("maze_"+name+".maz"));
				try{
					out.write(mazes.get(name).toByteArray());
					out.flush();
					out.close();
					message= "Maze saved to maze_" + name;
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
	public void loadFromFile (String name){
		byte [] b = new byte [3];
		InputStream in = null;
		Maze3d maze = null;
		try {
			in = new FileInputStream("maze_"+name+".maz");
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
			in = new MyDecompressorInputStream(new FileInputStream("maze_"+name+".maz"));
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
	public void saveSolution(String name) {
		try{
		ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solution_" + name + ".maz")));
		//oos.writeObject(name);
		oos.writeObject(solutions.get(name));
		oos.close();
		} catch (IOException e1) {
			message = "save faild";
		}
		message="solution saved!\n";
		setChanged();
		notifyObservers("display_msg");
	}


	@Override
	public void loadSolution(String name)  {
		try{
		ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solution_" + name + ".maz")));
		//String tmpName = (String) ois.readObject();
		Solution tmpSolution =new Solution<>();
				tmpSolution=(Solution) ois.readObject();
		solutions.put(name, tmpSolution);
		ois.close();
		} catch (IOException e1) {
			message = "load faild";
		} catch (ClassNotFoundException e) {
			message = "load faild- class not found";
			e.printStackTrace();
		}
		message="solution loaded!\n";
		setChanged();
		notifyObservers("display_msg");
	}
	
	public void saveAllMazesToFile(HashMap<String, Maze3d> mazes){
		PrintStream out = null;
		try {
			 out = new PrintStream(new FileOutputStream("mazes_names.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry<String, Maze3d> entry : mazes.entrySet()) {
		    String name = entry.getKey();
		    out.println(name);
		    saveToFile(name);
		}
		  out.println("end");
	}
	public void loadAllMazesFromFile(HashMap<String, Maze3d> mazes){
	
		ArrayList<String> names=new ArrayList<String>();
		String temp=new String();
		BufferedReader br = null;
		int i=0;
		try {
			br = new BufferedReader(new FileReader("mazes_names.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(!temp.equals("end")){
			try {
				names.add(br.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			temp=names.get(i);
			i++;
		}
		names.remove(i-1);
	for(int k=0;k<names.size();k++){
		loadFromFile(names.get(k));
	}
			    
}   
	
	@Override
	public String getPendingMessage() {
		return message;
	}

	@Override
	public void Exit() {
		saveAllMazesToFile(mazes);
		message= "\nall the data was saved!";
		setChanged();
		notifyObservers("display_msg");
	}	
}
