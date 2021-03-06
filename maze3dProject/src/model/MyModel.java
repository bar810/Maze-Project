package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyDecompressorInputStream;
import io.myCompressorOutputStream;
import presenter.Properties;
import presenter.PropertiesHandler;
import java.net.Socket;

/**
 * my model class this class is all the algorithms build as MVP structure
 * 
 * @author bar brownshtein
 *
 */
public class MyModel extends Observable implements model {

	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution> solutions = new HashMap<String, Solution>();
	public ArrayList<String> mazesNames = new ArrayList<>();
	Maze3d maze;
	String message;
	int[][] maze2d = null;
	ExecutorService exs;
	Maze3dGenerator mg;
	Properties properties;
	PropertiesHandler ph = new PropertiesHandler();
	private final String file = "newFile.zip";

	/**
	 * Constructor
	 * 
	 * @param numThreads
	 * @param p
	 */
	public MyModel(int numThreads, Properties p) {
		exs = Executors.newFixedThreadPool(numThreads);
		this.properties = p;
		loadSolutions();
		loadMazes();

	}

	/**
	 * generate maze
	 */
	@Override
	public void generateMaze(String name, int flos, int rows, int cols) {
		maze = mazes.get(name);
		if (maze == null) {
			exs.submit(new Callable<Maze3d>() {

				@Override
				public Maze3d call() throws Exception {

					Maze3d maze = (Maze3d) queryServer("127.0.0.1", 8090, "generate maze",
							name + "," + flos + "," + rows + "," + cols, "GrowingTree");

					mazes.put(name, maze);
					mazesNames.add(name);
					saveMazes();
					saveSolutions();
					sendMazesNames(name);
					saveCurrentMaze(name);
					message = "Maze: " + name + " Generated succesfully!\n";
					setChanged();
					notifyObservers("maze_ready " + name);

					return maze;

				}
			});

		} else {
			message = "This name already exists!\n";
			setChanged();
			notifyObservers("display_msg maze_is_exist!");
		}
	}

	/**
	 * solve maze
	 */
	@Override
	public void Solve(String name, String algo, String newPos) {
		
		if (newPos == "same") {
			@SuppressWarnings("unchecked")
			Solution<Position> sol = (Solution<Position>) queryServer("127.0.0.1", 8090, "solve maze", name, algo);
			solutions.put(name, sol);
		}
		else{
			Maze3d newMaze = new Maze3d(mazes.get(name));
			Maze3d temp2 = new Maze3d(mazes.get(name));
			String[] p = newPos.split("_");
			temp2.setStartPosition(
					new Position(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2])));
			newMaze = temp2;
			MazeSearchableAdapter mazeAdapter = new MazeSearchableAdapter(newMaze);
			switch (algo) {
			case "dfs":
				solutions.put(name, new DFS().search(mazeAdapter));
				break;
			case "bfs":
				solutions.put(name, new BFS().search(mazeAdapter));
				break;
			case "properties":
				int temp = properties.getSolveAlgorithm();
				if (temp == 1)
					solutions.put(name, new BFS().search(mazeAdapter));
				if (temp == 2)
					solutions.put(name, new DFS().search(mazeAdapter));
				break;
			}
		}
		message = "Solution created!\n";
		saveCurrentSolution(name);	
	}

	/**
	 * display solutionnew
	 */
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

	/**
	 * get cross by section
	 */
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
		notifyObservers("getMaze message");
	}

	/**
	 * get maze3D
	 */
	@Override
	public void getMaze3d(String name) {
		if (mazes.get(name) != null) {
			message = mazes.get(name).toString() + "\nStart Position: " + mazes.get(name).getStartPosition()
					+ "\nGoal Position: " + mazes.get(name).getGoalPosition() + "\n";

			saveCurrentMaze(name);
			setChanged();
			notifyObservers("display_msg display_" + name + "_maze");
			if (properties.getRuntimeEnv() == 1)
				System.out.println(mazes.get(name));
		} else {
			message = "Couldn't find maze by name!\n";
			setChanged();
			notifyObservers("display_msg cant_find_maze!");
		}
	}

	/**
	 * save to file
	 */
	@Override
	public void saveToFile(String name) {
		if (mazes.get(name) != null) {
			try {
				OutputStream out = new myCompressorOutputStream(new FileOutputStream("maze_" + name + ".maz"));
				try {
					out.write(mazes.get(name).toByteArray());
					out.flush();
					out.close();
					message = "Maze saved to maze_" + name;
					setChanged();
					notifyObservers("save_ready " + name);
				} catch (IOException e) {
					message = "Loading Failed : Couldn't read the file 0x01";
				}
			} catch (FileNotFoundException e) {
				message = "Loading Failed : File Not Found 0x01";
			}
		} else {
			message = "Couldn't find maze by name!";
		}
	}

	/**
	 * load from file
	 */
	@Override
	public void loadFromFile(String name) {
		byte[] b = new byte[3];
		InputStream in = null;
		Maze3d maze = null;
		try {
			in = new FileInputStream("maze_" + name + ".maz");
		} catch (FileNotFoundException e2) {
			message = "Loading Failed : File Not Found 0x01";
		}
		try {
			in.read(b, 0, 3);
			int size = (b[0] * b[1] * b[2]) + 9;
			b = null;
			b = new byte[size];
			in.close();
		} catch (IOException e2) {
			message = "Loading Failed : Couldn't read the file 0x01";
		}
		try {
			in = new MyDecompressorInputStream(new FileInputStream("maze_" + name + ".maz"));
		} catch (FileNotFoundException e1) {
			message = "Loading Failed : File Not Found 0x02";
		}
		try {
			in.read(b);
			maze = new Maze3d(b);
			mazes.put(name, maze);
			in.close();
			message = "Maze Loaded Succefully!";
			setChanged();
			notifyObservers("load_ready " + name);
		} catch (IOException e) {
			message = "Loading Failed : Couldn't read the file 0x02";
		}
	}

	/**
	 * save solutions
	 */
	public void saveSolutions() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("files/AllSolutionsCatch")));
			oos.writeObject(this.solutions);
			oos.close();
		} catch (IOException e1) {
			message = "save faild";
		}
	}

	/**
	 * load solutions
	 */
	public void loadSolutions() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("files/AllSolutionsCatch")));
			this.solutions = (HashMap<String, Solution>) ois.readObject();
			ois.close();
		} catch (IOException e1) {
			message = "load faild";
		} catch (ClassNotFoundException e) {
			message = "load faild- class not found";
			e.printStackTrace();
		}
		message = "Mazes loaded!\n";

	}

	/**
	 * save mazes
	 */
	@Override
	public void saveMazes() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("files/AllMazesCatch")));
			oos.writeObject(this.mazes);
			oos.close();
		} catch (IOException e1) {
			message = "save faild";
		}
		message = "";

	}

	/**
	 * load mazes GZIP is make it small. object can save any object. the object
	 * must be seriazible
	 */
	@Override
	public void loadMazes() {
		ObjectInputStream ois = null;
		try {

			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("files/AllMazesCatch")));
			this.mazes = (HashMap<String, Maze3d>) ois.readObject();
			ois.close();
		} catch (IOException e1) {
			message = "load faild";
		} catch (ClassNotFoundException e) {
			message = "load faild- class not found";
			e.printStackTrace();
		}
		message = "Mazes loaded!\n";
	}

	@Override
	public String getPendingMessage() {
		return message;
	}

	/**
	 * exit
	 */
	@Override
	public void Exit() {
		saveSolutions();
		saveMazes();
		try {
			PropertiesHandler.write(properties, "properties.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("display_msg GOOD_BYE_!");
	}

	@Override
	public void setProperties(Properties p) {
		this.properties = p;
	}

	/**
	 * reset properties
	 */
	@Override
	public void resetProperties() {
		properties.setMaxNumOfThreads(50);
		properties.setMazeGenerator(1);
		properties.setRuntimeEnv(0);
		properties.setSolveAlgorithm(1);
		try {
			PropertiesHandler.write(properties, "properties.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("display_msg Return_to_deafult_properties!");

	}

	/**
	 * erase all data
	 */
	public void eraseAllData() {
		this.mazes.clear();
		this.solutions.clear();
		setChanged();
		notifyObservers("display_msg Data_erased!");
	}

	/**
	 * get mazes names
	 */
	public String getMazesNames() {
		String temp = "";
		for (String s : mazesNames) {
			temp += s + " ";
		}

		return temp;
	}

	/**
	 * set properties
	 */
	public void setPropertiesEX(String[] str) {
		if (str[1] == "GrowingTree")
			properties.setMazeGenerator(1);
		properties.setMazeGenerator(0);

		if (str[2] == "BFS")
			properties.setSolveAlgorithm(1);
		else
			properties.setSolveAlgorithm(0);
		if (str[3] == "GUI")
			properties.setRuntimeEnv(0);
		else
			properties.setRuntimeEnv(1);
		try {
			PropertiesHandler.write(properties, "properties.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("display_msg Properties_saved!");

	}

	void sendMazesNames(String name) {
		setChanged();
		notifyObservers("getInformation" + " " + name);
	}

	/**
	 * save current maze
	 * 
	 * @param name
	 */
	public void saveCurrentMaze(String name) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("files/cuurentMaze")));
			oos.writeObject(name);
			oos.writeObject(this.mazes.get(name));
			oos.close();
		} catch (IOException e1) {
		}
	}

	/**
	 * save current solution
	 * 
	 * @param name
	 */
	public void saveCurrentSolution(String name) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("files/cuurentSolution")));
			oos.writeObject(this.solutions.get(name));
			oos.close();
		} catch (IOException e1) {
		}
	}

	private Object queryServer(String serverIP, int serverPort, String command, String data, String property) {
		Object result = null;
		Socket server = null;

		try {
			System.out.println("Trying to connect server, IP: " + serverIP + " " + serverPort);
			server = new Socket("127.0.0.1", 8090);// (serverIP,serverPort);
			PrintWriter writerToServer = new PrintWriter((new OutputStreamWriter(server.getOutputStream())));
			writerToServer.println(command);
			writerToServer.flush();
			writerToServer.println(property);
			writerToServer.flush();
			writerToServer.println(data);
			writerToServer.flush();
			ObjectInputStream inputDecompressed = null;
			inputDecompressed = new ObjectInputStream(server.getInputStream());
			result = inputDecompressed.readObject();
			writerToServer.close();
			inputDecompressed.close();
			server.close();

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();

		}

		return result;

	}


}
