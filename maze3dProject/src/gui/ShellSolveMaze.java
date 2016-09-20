package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class ShellSolveMaze extends dialogWindow {

	@Override
	protected void initWidgets() {
		shell.setText("Solve Maze");
		shell.setSize(200, 200);		
				
		shell.setLayout(new GridLayout(1, false));	
				
		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("algorithem: ");
	
		
		Button BFS=new Button(shell,SWT.RADIO);
		Button DFS=new Button(shell,SWT.RADIO);
		BFS.setText("BFS");
		DFS.setText("DFS");
		
		
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(BFS);//proporties put here
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("solve");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Algorithem");
				msg.setMessage("Button was clicked");
				String algo = msg.getText();
				
				msg.setMessage("Solving maze: " +algo);
				
				msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	}

}
