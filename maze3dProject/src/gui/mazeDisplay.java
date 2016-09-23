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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import controller.exit;

public class mazeDisplay extends Canvas {
	
	int flag=0;
	private Character character;
	private Character2 character2;
	private target tar;
	private finish fin;
	private int[][] mazeCurFloor;
	Solution<Position> sol;
	
	Maze3d maze;
	int[][][] tempMaze;
	int curFloor;	
	
	public mazeDisplay(Composite parent, int style) {
		super(parent, style);
		 
		loadCurrentMaze();
		tempMaze=maze.getMaze();
		
		System.out.println(maze);//------------------->for check
		
		curFloor=maze.getStartPosition().x;
		mazeCurFloor=maze.getCrossSectionByZ(curFloor);
		
		
		//do it after he got the information 
		character= new Character();
		character.setPos(new Position(1, 1, 1));
		
//		character2= new Character2();
//		character2.setPos(new Position(1, 1, 1));--------------------->character 2 or two moves look
		
		tar=new target();
		tar.setPos(new Position(1, 2, 1));

		fin=new finish();
		fin.setPos(new Position(1,1,2));
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
			Position pos = character.getPos();
			int temp;
			switch (e.keyCode) {
			
			case SWT.ARROW_DOWN:	
				if(mazeCurFloor[character.getPos().z+1][character.getPos().y]!=1)
					character.moveBack();
				redraw();
				break;
			case SWT.ARROW_UP:
				if(mazeCurFloor[character.getPos().z-1][character.getPos().y]!=1)
					character.moveForward();
				redraw();
				break;
			case SWT.ARROW_RIGHT:	
				if(mazeCurFloor[character.getPos().z][character.getPos().y+1]!=1){
//					flag=0;
//					character2.setPos(character2.getPos());
					character.moveRight();
					}
				redraw();
				break;
			case SWT.ARROW_LEFT:	
				if(mazeCurFloor[character.getPos().z][character.getPos().y-1]!=1){
//					flag=1;
					character.moveLeft();
//					character2.setPos(character.getPos());
					}
				redraw();
				break;
			case SWT.PAGE_UP:
				if(maze.getx()-curFloor>1){
					temp=curFloor;
					if(tempMaze[temp+1][character.getPos().y][character.getPos().z]!=1){
					character.moveUp();
					curFloor++;
					mazeCurFloor=maze.getCrossSectionByZ(curFloor);
					}
				}
				redraw();
				break;
			case SWT.PAGE_DOWN:
				if(curFloor>0){
				temp=curFloor;
				if(tempMaze[temp-1][character.getPos().y][character.getPos().z]!=1){
					character.moveDown();
					curFloor--;
					mazeCurFloor=maze.getCrossSectionByZ(curFloor);
				}
				}
				redraw();
				break;
			}
			}
			
		@Override
		public void keyReleased(KeyEvent arg0) {}
	});
		
		this.addPaintListener(new PaintListener() {
			
			int moves=0;
			@Override
			public void paintControl(PaintEvent e) {
			
			
				e.gc.setBackground(new Color(null,0,0,0));
				e.gc.setForeground(new Color(null,255,255,255));

				   int width=getSize().x;
				   int height=getSize().y;
				   
				   
				   if(mazeCurFloor!=null){
					
					   
					   character.setPos(maze.getStartPosition());
					   tar.setPos(maze.getGoalPosition());
					   fin.setPos(maze.getGoalPosition());
					   
					   System.out.println("start:"+maze.getStartPosition()+"  finish:"+ maze.getGoalPosition());//for check only
					   
				   int w=width/mazeCurFloor[0].length;
				   int h=height/mazeCurFloor.length;

				   for(int i=0;i<mazeCurFloor.length;i++)
				      for(int j=0;j<mazeCurFloor[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(mazeCurFloor[i][j]!=0&&mazeCurFloor[i][j]!=2&&mazeCurFloor[i][j]!=3)
				              e.gc.fillRectangle(x,y,w,h);
				          
				      		}
				  
//				  if(flag==1)
//					  character2.draw(w, h, e.gc);
//				  if(flag==0)
					  character.draw(w, h, e.gc);
				  
				  if(tar.getPos().x==curFloor)
					  tar.draw(w, h,e.gc);
				   
				   
				  
				  
				 
				  if(character.getPos().x==tar.getPos().x&&character.getPos().y==tar.getPos().y&&character.getPos().z==tar.getPos().z){//finish
					   fin.draw(w, h, e.gc);
				  			}
				   }	   
				   
			e.gc.drawString("Your position:  floor: "+curFloor+"  row: "+character.getPos().y+"  col: "+character.getPos().z+
					"         The goal position in:  ("+tar.getPos().x+" , "+tar.getPos().getY()+" , "+tar.getPos().getZ()+")     Total moves:  "+moves, 5, 5,false);
			moves++;
		}
			
			
		});
	}
	public void  setMazeData(Maze3d md){
	this.maze=md;
	}
		
		
	public void mazeDisplay(Composite parent, int style) {}
	
	void setMazeCurFloor(int [][] t){
		mazeCurFloor=t;
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
	
	Position getCurentPosition(){
		return character.getPos();
	}
	
	void setSolution(Solution<Position> t){
		this.sol=t;
	}
	


	
	void goToTheTarget(){
		
		
			TimerTask task = new TimerTask() {
				
				String where;
				
				int i=0;
			@Override
			public void run() {	
				getDisplay().syncExec(new Runnable() {					
					@Override
					public void run() {
						
						
						if(i<sol.getSize()){
						where=whereToMove(sol.getStates().get(i).getPosition(), sol.getStates().get(i+1).getPosition());
						
						
						int temp;
						switch(where){
							case"down":
									temp=curFloor;									
										character.moveDown();
										curFloor--;
										mazeCurFloor=maze.getCrossSectionByZ(curFloor);
									
								break;
							case"up":
									temp=curFloor;
									character.moveUp();
									curFloor++;
									mazeCurFloor=maze.getCrossSectionByZ(curFloor);
								break;
							case"forward":
									character.moveForward();
								redraw();
								break;
							case"left":
//									flag=1;
									character.moveLeft();
//									character2.setPos(character.getPos());								
									break;
							case"back":
									character.moveBack();
									break;
							case"right":					
//									flag=0;
//									character2.setPos(character2.getPos());
									character.moveRight();
									break;
							default:
									break;
						}
						redraw();
						}
						i++;
					}
				});
			}};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);	
	}
	
	String whereToMove(Position now,Position to){
		
		int x=now.x-to.x;
		int y=now.y-to.y;
		int z=now.z-to.z;
		
		if(x!=0){
			if(x==1)
				return "down";
			else
				return "up";
		}
		if(y!=0){
			if(y==1)
				return "forward";
			else
				return"back";
		}
		if(z!=0){
			if(z==1)
				return "left";
			else
				return "right";
		}
		return null;
	}

	
	
	
}
