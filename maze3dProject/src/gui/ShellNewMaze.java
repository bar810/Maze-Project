package gui;

import java.util.Observable;

import javax.xml.stream.Location;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ShellNewMaze extends Observable {

	protected Shell shell;

	public void start(Display display) {
		shell = new Shell(display);
		initWidgets();
		shell.open();
	}

	// @Override
	protected void initWidgets() {
		shell.setText("New Maze");
		shell.setSize(400, 300);
		shell.setLocation(400, 400);

		shell.setLayout(new GridLayout(2, false));

		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("name: ");

		Text txtname = new Text(shell, SWT.BORDER);
		txtname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label lblfloors = new Label(shell, SWT.NONE);
		lblfloors.setText("floors: ");

		String[] floors = "5,10,11,12,13,14,15,16,17,18,19,20".split(",");
		Combo comboF = new Combo(shell, SWT.SINGLE | SWT.DROP_DOWN);
		comboF.setItems(floors);

		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");

		String[] rows = "5,10,11,12,13,14,15,16,17,18,19,20".split(",");
		Combo comboR = new Combo(shell, SWT.SINGLE | SWT.DROP_DOWN);
		comboR.setItems(rows);

		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");

		String[] cols = "5,10,11,12,13,14,15,16,17,18,19,20".split(",");
		Combo comboS = new Combo(shell, SWT.SINGLE | SWT.DROP_DOWN);
		comboS.setItems(cols);

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
				String name = txtname.getText();
				int floors = Integer.parseInt(comboF.getText());
				int rows = Integer.parseInt(comboR.getText());
				int cols = Integer.parseInt(comboS.getText());
				setChanged();
				notifyObservers("generate_3d_maze" + " " + name + " " + floors + " " + rows + " " + cols);

				shell.close();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

	}

}
