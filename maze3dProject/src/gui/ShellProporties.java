package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

public class ShellProporties extends dialogWindow {
	String update;

	String Generator;
	String Algorithem;
	String enviroment;
	
	@Override
	protected void initWidgets() {
		shell.setText(" Properties");
		shell.setSize(500, 400);		
		shell.setLocation(400,400);
		shell.setLayout(new GridLayout(1, false));	
				
		Group group1=new Group(shell,SWT.SHADOW_OUT);
		
		group1.setText("Maze Generator: ");
		group1.setLayout(new GridLayout(2,true));
		
		Button GR=new Button(group1,SWT.RADIO);
		Button SI=new Button(group1,SWT.RADIO);
		GR.setText("GrowingTree");
		SI.setText("Simple");
		GR.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Generator="GrowingTree";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		SI.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Generator="Simple";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
	
		Group group2=new Group(shell,SWT.SHADOW_OUT);
		
		group2.setText("Solve Algorithem: ");
		group2.setLayout(new GridLayout(2,true));
		
		Button b=new Button(group2,SWT.RADIO);
		Button d=new Button(group2,SWT.RADIO);
		b.setText("BFS");
		d.setText("DFS");
		
		b.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Algorithem="BFS";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		d.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Algorithem="DFS";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Group group3=new Group(shell,SWT.SHADOW_OUT);
		group3.setText("Run time enviroment: ");
		group3.setLayout(new GridLayout(2,true));
		
		Button GUI=new Button(group3,SWT.RADIO);
		Button CLI=new Button(group3,SWT.RADIO);
		GUI.setText("GUI");
		CLI.setText("CLI");
		
		GUI.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				enviroment="GUI";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		CLI.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				enviroment="CLI";
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button save=new Button(shell,SWT.PUSH);
		save.setText("SAVE PROPERTIES");
		
		
		Button reset=new Button(shell,SWT.PUSH);
		reset.setText("DEAFULT PROPERTIES");
		
	
	
		
		save.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("SAVE");
				msg.setMessage("proporties save!");
				
				String algo = msg.getText();
				update="setProperties"+" "+Generator+" "+Algorithem+" "+enviroment;
				msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	reset.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("RESET");
				msg.setMessage("proporties RESET!");
				update="resetProperties";
				String algo = msg.getText();
				
				msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	}
	@Override
	public String GetUpdate(){
		return update;
	}

}
