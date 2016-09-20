package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class ShellDisplayMaze extends dialogWindow {

	String update;
	
	@Override
	protected void initWidgets() {
		shell.setText("Display Maze");
		shell.setSize(400, 300);		
		shell.setLocation(400,400);
		shell.setLayout(new GridLayout(2, false));	
		
		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("choose maze: ");
		
		String[] items= "maze1 maze2 maze3 maze4 maze5 maze6".split(" ");
		Combo combo =new Combo(shell,SWT.SINGLE|SWT.DROP_DOWN);
		combo.setItems(items);
		
		Button BTNdisplayMaze = new Button(shell, SWT.PUSH);
		BTNdisplayMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		BTNdisplayMaze.setText("Display maze");
		
		BTNdisplayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Display maze");
				msg.setMessage("Button was clicked");
				
				msg.setMessage("Display maze: " +combo.getText());
				
				msg.open();
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		
		});
	}
	@Override
	public String GetUpdate(){
		return update;
	}
}





