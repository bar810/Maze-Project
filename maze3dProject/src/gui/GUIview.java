package gui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import presenter.Presenter;
import presenter.Properties;
import view.view;

public class GUIview extends Observable implements view, Observer{

	protected Display display;
	protected Shell shell;	
	private mazeDisplay mazeDisplay;
	BufferedReader in;
	PrintWriter out;
	Properties p;
	ArrayList<String> names= new ArrayList<>();
	public Maze3d maze;

		
	public GUIview(BufferedReader reader ,PrintWriter writer) {
		this.in = reader;
		this.out = writer;
		loadMazes();
	}

	protected void initWidgets() {
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		
		shell.setText("PIZZA MAZE GAME");
		shell.setImage(new Image(null,"img1.jpg"));
		mazeDisplay = new mazeDisplay(shell, SWT.BORDER);
		mazeDisplay.setBackground(new Color(null,255,255,255));
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	
		//mazeDisplay.setFocus();
		
		
	//buttons:
	//New Maze
		ShellNewMaze win = new ShellNewMaze();	
		win.addObserver(this);
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("New maze");
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				win.start(display);
				setChanged();
				notifyObservers("loadMazes");
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	//Display Maze
		ShellDisplayMaze dis = new ShellDisplayMaze(names);	
		dis.addObserver(this);
		Button btnDisplayMaze = new Button(buttons, SWT.PUSH);
		btnDisplayMaze.setText("Display maze");
		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//need to print
				dis.start(display);
				
				loadCurrentMaze();
				
				mazeDisplay.setMazeData(maze);
				
				mazeDisplay.setMazeCurFloor(maze.getCrossSectionByZ(3));
				
				mazeDisplay.redraw();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub	
			}
			
		
		});
	//Get Advice
		Button btnGetAdvice = new Button(buttons, SWT.PUSH);
		btnGetAdvice.setText("Get Advice");
	//Solve Maze
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
			btnSolveMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ShellSolveMaze sol = new ShellSolveMaze();				
				sol.start(display);
				setChanged();
				notifyObservers(sol.GetUpdate());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub	
			}
		});
	//Properties
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
				// TODO Auto-generated method stub
				
			}
		});
	//exit
		Button btnExit = new Button(buttons, SWT.PUSH);
		btnExit.setText("Exit");
		btnExit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveMazes();
				setChanged();
				notifyObservers("exit");
				display.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
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
	public void getInformation(String name){
		this.names.add(name);
	}
	
	@Override
	public void getMaze(String maze){

	}
	
	
	public void setProperties(Properties p) {
	this.p=p;	
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1=="erase_all"){
			names.removeAll(names);
			saveMazes();
			loadMazes();
			maze=null;
		}
		setChanged();
		notifyObservers(arg1);
	}
	public void start() {
		display = new Display();
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
		
		// main event loop
		while(!shell.isDisposed()){ // window isn't closed
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
	
	public void saveMazes(){
		ObjectOutputStream oos=null;
		try{
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("AllMazesNamesCatch")));
			oos.writeObject(this.names);
			oos.close();
			} catch (IOException e1) {
			}
			
	}
	
	public void loadMazes(){
		ObjectInputStream ois=null;
		try{
			//GZIP is make it small. object can save any object. the object must be seriazible
		 ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("AllMazesNamesCatch")));
		this.names=(ArrayList<String>) ois.readObject();
		ois.close();
		} catch (IOException e1) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadCurrentMaze(){
		ObjectInputStream ois=null;
		try{
		 ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("cuurentMaze")));
		this.maze=(Maze3d) ois.readObject();
		ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}	
}