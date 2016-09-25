package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import presenter.Properties;

/**
 * My View class this class is implements view interface
 * 
 * @author bar brownshtein
 *
 */
public class MyView extends Observable implements view, Observer {

	CLI cli;
	private BufferedReader reader;
	private PrintWriter writer;
	Properties properties;

	/**
	 * constructor
	 * 
	 * @param reader
	 * @param writer
	 * @throws Exception
	 */
	public MyView(BufferedReader reader, PrintWriter writer) throws Exception {
		this.reader = reader;
		this.writer = writer;
		cli = new CLI(reader, writer);
		cli.addObserver(this);
	}

	/**
	 * update function MVP structure
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 == cli) {
			this.setChanged();
			this.notifyObservers(arg1);
		}
	}

	/**
	 * START class run the program
	 */
	@Override
	public void start() throws Exception {
		Thread cliThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					cli.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		cliThread.run();
	}

	@Override
	public void Print(String str) {
		writer.write(str);
		writer.flush();
	}

	public void setMaze3dData(Maze3d maze) {
	}

	public void setSolution(Solution arg) {
	}

	@Override
	public void displayMessage(String msg) {
		writer.println(msg);
		writer.flush();
	}

	@Override
	public void setProperties(Properties p) {
		this.properties = p;

	}

	@Override
	public void getInformation(String info) {
	}

	@Override
	public void getMaze(String name) {
	}
}