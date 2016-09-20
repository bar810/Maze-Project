package gui;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import presenter.Presenter;
import presenter.Properties;

public class GUIview extends baseWindow implements  Observer{


	private mazeDisplay mazeDisplay;
	private Character character;
	BufferedReader in;
	PrintWriter out;
	
	public GUIview(BufferedReader reader ,PrintWriter writer) {
		this.in = reader;
		this.out = writer;
	}
	
	@Override
	protected void initWidgets() {
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		
	//buttons:
	//New Maze
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("New maze");
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ShellNewMaze win = new ShellNewMaze();				
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
				ShellDisplayMaze sol = new ShellDisplayMaze();				
				sol.start(display);
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
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	//Properties
		Button btnProporties = new Button(buttons, SWT.PUSH);
		btnProporties.setText("Proporties");
		btnProporties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ShellProporties pro = new ShellProporties();				
				pro.start(display);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		mazeDisplay = new mazeDisplay(shell, SWT.BORDER);	
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}

	@Override
	public void Print(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperties(Properties p) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addObserver(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}



}
