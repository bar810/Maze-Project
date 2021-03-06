package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import controller.exit;
import presenter.getMaze;

/**
 * maze display class extend Canvas and make the maze view
 * 
 * @author bar brownshtein
 *
 */
public class mazeDisplay extends Canvas {

	int flag = 0;
	public Character character;
	private Character character2;
	private Character tar;
	private Character fin;
	private int[][] mazeCurFloor;
	Solution<Position> sol;

	// try:
	private int[][] nextFloor;
	private int[][] previosFloor;

	Maze3d maze;
	String mazeName;
	int[][][] tempMaze;
	int curFloor;

	/**
	 * print all the maze and the characters
	 * 
	 * @param parent
	 * @param style
	 */
	public mazeDisplay(Composite parent, int style, String Photoselected) {
		super(parent, style);

		if (maze != null) {
			tempMaze = maze.getMaze();
			curFloor = maze.getStartPosition().x;
			mazeCurFloor = maze.getCrossSectionByZ(curFloor);
		}

		if (Photoselected == null) {
			character = new Character("Character.jpg");
			character2 = new Character("Character2.jpg");
		} else {
			character = new Character(Photoselected);
			character2 = new Character(Photoselected);
		}
		tar = new Character("target.jpg");
		fin = new Character("pizzaTime.jpg");

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = character.getPos();
				tempMaze = maze.getMaze();
				int temp;

				switch (e.keyCode) {

				case SWT.ARROW_DOWN:
					if (mazeCurFloor[character.getPos().y + 1][character.getPos().z] != 1)
						character.moveBack();
					redraw();
					break;
				case SWT.ARROW_UP:
					if (mazeCurFloor[character.getPos().y - 1][character.getPos().z] != 1)
						character.moveForward();
					redraw();
					break;
				case SWT.ARROW_RIGHT:
					if (mazeCurFloor[character.getPos().y][character.getPos().z + 1] != 1) {
						flag = 0;
						character2.setPos(character2.getPos());
						character.moveRight();
					}
					redraw();
					break;
				case SWT.ARROW_LEFT:
					if (mazeCurFloor[character.getPos().y][character.getPos().z - 1] != 1) {
						flag = 1;
						character.moveLeft();
						character2.setPos(character.getPos());
					}
					redraw();
					break;
				case SWT.PAGE_UP:
					if (maze.getx() - curFloor > 1) {
						temp = curFloor;
						if (tempMaze[temp + 1][character.getPos().y][character.getPos().z] != 1) {
							character.moveUp();
							character.moveUp();
							curFloor += 2;
							mazeCurFloor = maze.getCrossSectionByZ(curFloor);
						}
					}
					redraw();
					break;
				case SWT.PAGE_DOWN:
					if (curFloor > 0) {
						temp = curFloor;
						if (tempMaze[temp - 1][character.getPos().y][character.getPos().z] != 1) {
							character.moveDown();
							character.moveDown();
							curFloor -= 2;
							mazeCurFloor = maze.getCrossSectionByZ(curFloor);
						}
					}
					redraw();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}
		});

		
		addMouseWheelListener(new MouseWheelListener() {
			
		
			@Override
			public void mouseScrolled(MouseEvent e) {
				tempMaze = maze.getMaze();
				int noches=e.count;
			
				if(noches>0){
					if(maze.getx()-curFloor>=1){
				
					if (tempMaze[curFloor + 1][character.getPos().y][character.getPos().z] != 1) {
					character.moveUp();
					character.moveUp();
					curFloor += 2;
					mazeCurFloor = maze.getCrossSectionByZ(curFloor);
					redraw();
				}
				}
					
				}
					if(noches<0){
						if(curFloor>0){
						if (tempMaze[curFloor - 1][character.getPos().y][character.getPos().z] != 1) {
							character.moveDown();
							character.moveDown();
							curFloor -= 2;
							mazeCurFloor = maze.getCrossSectionByZ(curFloor);
							redraw();
						}
						}
				}
				
				
			}

			public void mouseScrolled1(MouseEvent arg0) {
			}
		
			});
	
	
	

		

	
		
		this.addPaintListener(new PaintListener() {

			int moves = 0;

			/**
			 * paint control
			 */
			@Override
			public void paintControl(PaintEvent e) {

				if (maze != null) {
					curFloor = maze.getStartPosition().x;

					boolean next = false;
					boolean prev = false;

					if (maze.getz() - curFloor > 1) {
						nextFloor = maze.getCrossSectionByZ(curFloor + 1);
						next = true;
					}
					if (curFloor > 2) {
						previosFloor = maze.getCrossSectionByZ(curFloor - 1);
						prev = true;
					}

					e.gc.setBackground(new Color(null, 0, 0, 0));

					e.gc.setForeground(new Color(null, 255, 255, 255));

					int width = getSize().x;
					int height = getSize().y;

					if (mazeCurFloor != null) {

						character.setPos(maze.getStartPosition());
						tar.setPos(maze.getGoalPosition());
						fin.setPos(maze.getGoalPosition());

						int w = width / mazeCurFloor[0].length;
						int h = height / mazeCurFloor.length;

						for (int i = 0; i < mazeCurFloor.length; i++)
							for (int j = 0; j < mazeCurFloor[i].length; j++) {
								int x = j * w;
								int y = i * h;

								if (mazeCurFloor[i][j] != 0 && mazeCurFloor[i][j] != 2 && mazeCurFloor[i][j] != 3)
									e.gc.fillRectangle(x, y, w, h);// ->>>here
																	// he paint
																	// the maze

								if (prev == true) {
									if (previosFloor[i][j] == 0) {

										e.gc.setBackground(new Color(null, 230, 230, 230));
										e.gc.fillRectangle(x, y, w, h);
										e.gc.setBackground(new Color(null, 0, 0, 0));

									}
								}
								if (next == true) {
									if (nextFloor[i][j] == 0) {

										e.gc.setBackground(new Color(null, 0, 255, 255));
										e.gc.fillRectangle(x, y, w, h);
										e.gc.setBackground(new Color(null, 0, 0, 0));

									}
								}

							}

						if (flag == 1)
							character2.draw(w, h, e.gc);
						if (flag == 0)
							character.draw(w, h, e.gc);

						if (tar.getPos().x == curFloor)
							tar.draw(w, h, e.gc);

						if (character.getPos().x == tar.getPos().x && character.getPos().y == tar.getPos().y
								&& character.getPos().z == tar.getPos().z) {
							fin.draw(w, h, e.gc);
							MessageBox msg = new MessageBox(getShell(), SWT.OK);
							msg.setText("PIZZA MAZE GAME");
							msg.setMessage("YOU GOT THE PIZZA!!!!!");
							msg.open();

						}
					}
					if (maze != null) {
						e.gc.drawString("Maze name: " + mazeName + "  (" + maze.getx() + "/" + maze.gety() + "/"
								+ maze.getz() + ")" + "  Your position: (" + character.getPos().x + " , "
								+ character.getPos().y + " , " + character.getPos().z + ")  Goal position: ("
								+ tar.getPos().x + " , " + tar.getPos().getY() + " , " + tar.getPos().getZ()
								+ ")Total moves:  " + moves, 5, 5, false);
						moves++;
					}

				}
			}

		});
	}

	/**
	 * set maze data
	 * 
	 * @param name
	 * @param md
	 */
	public void setMazeData(String name, Maze3d md) {

		this.mazeName = name;
		this.maze = md;
		setMazeCurFloor(maze.getCrossSectionByZ(maze.getStartPosition().x));
	}

	public void mazeDisplay(Composite parent, int style) {
	}

	void setMazeCurFloor(int[][] t) {
		mazeCurFloor = t;
	}

	/**
	 * load current maze
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

	Position getCurentPosition() {
		return character.getPos();
	}

	void setSolution(Solution<Position> t) {
		this.sol = t;
	}

	/**
	 * go to the target- when the user want to solve if num==1 solve if num==0
	 * is just advice
	 * 
	 * @param num
	 */
	void goToTheTarget(int num) {

		int j = 0;
		TimerTask task = new TimerTask() {

			String where;

			int i = 0;
			int loops = sol.getSize() - 1;

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {

						if (num == 0)
							loops = 1;

						if (i < loops) {
							where = whereToMove(sol.getStates().get(i).getPosition(),
									sol.getStates().get(i + 1).getPosition());

							int temp;
							switch (where) {
							case "down":

								temp = curFloor;
								character.moveDown();
								curFloor--;
								mazeCurFloor = maze.getCrossSectionByZ(curFloor);
								break;
							case "up":
								temp = curFloor;
								character.moveUp();
								curFloor++;
								mazeCurFloor = maze.getCrossSectionByZ(curFloor);
								break;
							case "forward":
								character.moveForward();
								break;
							case "left":
								character.moveLeft();
								break;
							case "back":
								character.moveBack();
								break;
							case "right":
								character.moveRight();
								break;
							default:
								break;
							}
							redraw();
							i++;
						}

						if (character.getPos() == maze.getGoalPosition())
							cancel();

					}
				});
			}
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 200);
	}

	/**
	 * this function tell you where to move
	 * 
	 * @param now
	 * @param to
	 * @return
	 */
	String whereToMove(Position now, Position to) {

		int x = now.x - to.x;
		int y = now.y - to.y;
		int z = now.z - to.z;

		if (x != 0) {
			if (x == 1)
				return "down";
			else
				return "up";
		}
		if (y != 0) {
			if (y == 1)
				return "forward";
			else
				return "back";
		}
		if (z != 0) {
			if (z == 1)
				return "left";
			else
				return "right";
		}
		return null;
	}

}
