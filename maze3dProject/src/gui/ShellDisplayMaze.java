package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;

/**
 * shell display maze
 * 
 * @author bar brownshtein
 *
 */
public class ShellDisplayMaze extends Observable {

	protected Shell shell;
	ArrayList<String> names = new ArrayList<>();

	public ShellDisplayMaze(ArrayList<String> names) {
		this.names = names;
	}

	String[] getNames() {
		String[] temp = new String[names.size()];
		for (int i = 0; i < names.size(); i++)
			temp[i] = names.get(i);
		return temp;
	}

	public void start(Display display) {
		shell = new Shell(display);
		initWidgets();
		shell.open();
	}

	String update;

	protected void initWidgets() {
		shell.setText("Load Maze");
		shell.setSize(400, 300);
		shell.setLocation(400, 400);
		shell.setLayout(new GridLayout(2, false));

		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("choose maze: ");

		String[] items = getNames();
		Combo combo = new Combo(shell, SWT.SINGLE | SWT.DROP_DOWN);
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
				String name = combo.getText();

				setChanged();
				notifyObservers("display" + " " + name);
				shell.close();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

		});
	}

}
