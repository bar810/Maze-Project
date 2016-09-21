package gui;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

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

import presenter.Presenter;
import presenter.Properties;
import view.view;

public class GUIview extends Observable implements view, Observer{

	protected Display display;
	protected Shell shell;	
	private mazeDisplay mazeDisplay;
	BufferedReader in;
	PrintWriter out;
	
	
	public GUIview(BufferedReader reader ,PrintWriter writer) {
		this.in = reader;
		this.out = writer;
	}
	
	protected void initWidgets() {
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		
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
				
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	//Display Maze
		Button btnDisplayMaze = new Button(buttons, SWT.PUSH);
		btnDisplayMaze.setText("Display maze");
		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ShellDisplayMaze dis = new ShellDisplayMaze();	
				dis.start(display);	
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
		btnProporties.setText("Proporties");
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
				setChanged();
				notifyObservers("exit");
				display.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		shell.setText("PIZZA MAZE GAME");
		shell.setImage(new Image(null,"img1.jpg"));
		mazeDisplay = new mazeDisplay(shell, SWT.BORDER);
		mazeDisplay.setBackground(new Color(null,255,255,255));
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}
	
	public void Print(String str) {
		// TODO Auto-generated method stub
	}
	public void displayMessage(String msg) {
		MessageBox msg2 = new MessageBox(shell, SWT.OK);
		msg2.setMessage(msg);
		msg2.open();
	}
	public void setProperties(Properties p) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void update(Observable arg0, Object arg1) {
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
}