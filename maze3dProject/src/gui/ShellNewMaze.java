package gui;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class ShellNewMaze extends dialogWindow {
	
	String update;
	
	
	@Override
	protected void initWidgets() {
		shell.setText("New Maze");
		shell.setSize(400, 300);		
				
		shell.setLayout(new GridLayout(2, false));	
				
		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("name: ");
		
		Text txtname = new Text(shell, SWT.BORDER);
		txtname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblfloors = new Label(shell, SWT.NONE);
		lblfloors.setText("floors: ");
		
		Text txtfloors = new Text(shell, SWT.BORDER);
		txtfloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		
		Text txtCols = new Text(shell, SWT.BORDER);
		txtCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Title");
				msg.setMessage("Button was clicked");
				String name=txtname.getText();
				int floors = Integer.parseInt(txtfloors.getText());
				int rows = Integer.parseInt(txtRows.getText());
				int cols = Integer.parseInt(txtCols.getText());
				update="generate_3d_maze"+ " " + name +" "+ floors +" "+ rows + " "+ cols;
				msg.setMessage("Generating maze: " +name);
				
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
