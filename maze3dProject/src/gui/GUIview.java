package gui;

import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;
import view.view;

/*
TODO:

	properties


*/
public class GUIview extends Observable implements view, Observer {

	protected Display display;
	protected Shell shell;
	private mazeDisplay mazeDisplay;
	BufferedReader in;
	PrintWriter out;
	Properties p;
	ArrayList<String> names = new ArrayList<>();
	public Maze3d maze;
	public String mazeName;
	public String Photoselected = null;
	public Solution<Position> solution = new Solution<Position>();

	/**
	 * GUI view class all the animation is here, all this things the user will
	 * see- butttons and windows.
	 * 
	 * @param reader
	 * @param writer
	 * @param pro
	 */
	public GUIview(BufferedReader reader, PrintWriter writer, Properties pro) {
		this.in = reader;
		this.out = writer;
		this.p = pro;
		loadMazesNames();

	}
	
	protected void initWidgets() {

		/**
		 * main Display properties
		 */
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);

		Composite buttons = new Composite(shell, SWT.NONE);
	
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);

		shell.setText("PIZZA MAZE GAME");
		shell.setImage(new Image(null, "pictures/img1.jpg"));		
		
		mazeDisplay = new mazeDisplay(shell, SWT.NONE , Photoselected);
		
		mazeDisplay.setBackgroundImage(new Image(null, "pictures/backGround.jpg"));
		
		mazeDisplay.setLayoutData(new GridData(SWT.FILL,SWT.FILL, true, true));

		mazeDisplay.setFocus();

		/**
		 * buttons:
		 */

		/**
		 * new MAZE window
		 */
		ShellNewMaze win = new ShellNewMaze();
		win.addObserver(this);
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("New maze");

		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				win.start(display);
				saveMazesNames();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		/**
		 * load Maze
		 */
		
		ShellDisplayMaze dis = new ShellDisplayMaze(names);
		dis.addObserver(this);
		Button btnDisplayMaze = new Button(buttons, SWT.PUSH);
		btnDisplayMaze.setText("Load maze");

		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				dis.start(display);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		/**
		 * Start Maze
		 */

		Button btnStartGame = new Button(buttons, SWT.PUSH);
		btnStartGame.setText("Start Game");

		btnStartGame.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeDisplay.setBackgroundImage(new Image(null, "pictures/white.jpg"));
				loadCurrentMaze();
				mazeDisplay.setMazeData(mazeName, maze);
				mazeDisplay.redraw();
				mazeDisplay.setFocus();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		/**
		 * Get Advice
		 */
		Button btnGetAdvice = new Button(buttons, SWT.PUSH);
		btnGetAdvice.setText("Get Advice");
		btnGetAdvice.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (maze != null) {
					setChanged();
					loadCurrentMaze();

					if (!(mazeDisplay.character.getPos().x == maze.getStartPosition().x
							&& mazeDisplay.character.getPos().y == maze.getStartPosition().y
							&& mazeDisplay.character.getPos().z == maze.getStartPosition().z)) {

						if (p.getSolveAlgorithm() == 1)
							notifyObservers("solve" + " " + mazeName + " " + "bfs" + " "
									+ mazeDisplay.character.getPos().x + "_" + mazeDisplay.character.getPos().y + "_"
									+ mazeDisplay.character.getPos().z);
						else
							notifyObservers("solve" + " " + mazeName + " " + "dfs" + " "
									+ mazeDisplay.character.getPos().x + "_" + mazeDisplay.character.getPos().y + "_"
									+ mazeDisplay.character.getPos().z);
					}

					else {
						if (p.getSolveAlgorithm() == 1)
							notifyObservers("solve" + " " + mazeName + " " + "bfs");
						else
							notifyObservers("solve" + " " + mazeName + " " + "dfs");

					}

					loadCurrentSolution();
					mazeDisplay.setSolution(solution);
					mazeDisplay.goToTheTarget(0);

				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

		});

		/**
		 * Solve Maze
		 */
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (maze != null) {
					setChanged();
					loadCurrentMaze();

					if (!(mazeDisplay.character.getPos().x == maze.getStartPosition().x
							&& mazeDisplay.character.getPos().y == maze.getStartPosition().y
							&& mazeDisplay.character.getPos().z == maze.getStartPosition().z)) {

						if (p.getSolveAlgorithm() == 1)
							notifyObservers("solve" + " " + mazeName + " " + "bfs" + " "
									+ mazeDisplay.character.getPos().x + "_" + mazeDisplay.character.getPos().y + "_"
									+ mazeDisplay.character.getPos().z);
						else
							notifyObservers("solve" + " " + mazeName + " " + "dfs" + " "
									+ mazeDisplay.character.getPos().x + "_" + mazeDisplay.character.getPos().y + "_"
									+ mazeDisplay.character.getPos().z);
					}

					else {
						if (p.getSolveAlgorithm() == 1)
							notifyObservers("solve" + " " + mazeName + " " + "bfs");
						else
							notifyObservers("solve" + " " + mazeName + " " + "dfs");

					}

					loadCurrentSolution();
					mazeDisplay.setSolution(solution);
					mazeDisplay.goToTheTarget(1);

				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		/**
		 * Properties
		 */
		ShellProporties pro = new ShellProporties();
		pro.addObserver(this);
		Button btnProporties = new Button(buttons, SWT.PUSH);
		btnProporties.setText("Properties");
		btnProporties.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				pro.start(display);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		/**
		 * Your photo
		 */
		Button btnYourPhoto = new Button(buttons, SWT.PUSH);
		btnYourPhoto.setText("Your Photo");
		btnYourPhoto.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox msg=new MessageBox(shell);
				msg.setText("Your photo");
				msg.setMessage("In the future");
				msg.open();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		/**
		 * exit
		 */
		Button btnExit = new Button(buttons, SWT.PUSH);
		btnExit.setText("Exit");
		btnExit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveMazesNames();
				setChanged();
				notifyObservers("exit");
				System.exit(0);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	public void Print(String str) {
		// TODO Auto-generated method stub
	}

	public void displayMessage(String msg) {
		MessageBox msg2 = new MessageBox(shell, SWT.OK);
		msg2.setMessage(msg);
		msg2.open();
	}

	@Override
	public void getInformation(String name) {
		this.names.add(name);
		saveMazesNames();
	}

	@Override
	public void getMaze(String maze) {
	}

	public void setProperties(Properties p) {
		this.p = p;
	}

	/**
	 * update function as MVP structure
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 == "erase_all") {
			names.removeAll(names);
			saveMazesNames();
			loadMazesNames();
			maze = null;
		}
		setChanged();
		notifyObservers(arg1);
	}

	public void start() {
		display = new Display();
		shell = new Shell(display);

		initWidgets();
		shell.open();

		/**
		 * main event loop
		 */
		while (!shell.isDisposed()) { // window isn't closed
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * save mazes names
	 */
	public void saveMazesNames() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("files/AllMazesNamesCatch")));
			oos.writeObject(this.names);
			oos.close();
		} catch (IOException e1) {
		}

	}

	/**
	 * load mazes names
	 */
	public void loadMazesNames() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("files/AllMazesNamesCatch")));
			this.names = (ArrayList<String>) ois.readObject();
			ois.close();
		} catch (IOException e1) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load the current maze
	 */
	public void loadCurrentMaze() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("files/cuurentMaze")));
			this.mazeName = (String) ois.readObject();
			this.maze = (Maze3d) ois.readObject();
			ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load the current solution
	 */
	public void loadCurrentSolution() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("files/cuurentSolution")));
			this.solution = (Solution<Position>) ois.readObject();
			ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}