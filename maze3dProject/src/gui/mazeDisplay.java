package gui;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class mazeDisplay extends Canvas {
	
	int flag=0;
	private Character character;
	private Character2 character2;
	private target tar;
	//try
	
	
	
	
	//
	private int[][] mazeData = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
			{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1}		
	};
	
	public mazeDisplay(Composite parent, int style) {
		super(parent, style);
		
		character= new Character();
		character.setPos(new Position(1,1,1));
		
		character2= new Character2();
		character2.setPos(new Position(1,1,1));
		
		tar=new target();
		tar.setPos(new Position(12,9,1));

		this.addKeyListener(new KeyListener() {
			
		
			
			@Override
			public void keyPressed(KeyEvent e) {
			Position pos = character2.getPos();
			switch (e.keyCode) {
			case SWT.ARROW_DOWN:	
				if(mazeData[character.getPos().z+1][character.getPos().y]!=1)						
					character.moveBack();
					redraw();
				break;
			case SWT.ARROW_UP:
				if(mazeData[character.getPos().z-1][character.getPos().y]!=1)
					character.moveForward();
				redraw();
				break;
			case SWT.ARROW_RIGHT:	
				if(mazeData[character.getPos().z][character.getPos().y+1]!=1){
					flag=0;
					character.setPos(character2.getPos());
					character.moveRight();
					}
				redraw();
				break;
			case SWT.ARROW_LEFT:	
				if(mazeData[character.getPos().z][character.getPos().y-1]!=1){
					flag=1;
					character.moveLeft();
					character2.setPos(character.getPos());
					}
				redraw();
				break;
				}
			}
		@Override
		public void keyReleased(KeyEvent arg0) {
			}
		});
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				
				e.gc.setBackground(new Color(null,0,0,0));
				e.gc.setForeground(new Color(null,255,255,255));
				
				   int width=getSize().x;
				   int height=getSize().y;

				   int w=width/mazeData[0].length;
				   int h=height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++)
				      for(int j=0;j<mazeData[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
				      }
				  
				  if(flag==1)
					  character2.draw(w, h, e.gc);
				  if(flag==0)
				  character.draw(w, h, e.gc);
				  
				  tar.draw(w, h, e.gc);
				 
				
			}
		});
	}
//		
//		TimerTask task = new TimerTask() {
//			
//			@Override
//			public void run() {	
//				getDisplay().syncExec(new Runnable() {					
//
//					@Override
//					public void run() {
//						
//						//characterR.moveRight();
//						//redraw();
//					}
//				});
//				
//			}
//		};
//		Timer timer = new Timer();
//		timer.scheduleAtFixedRate(task, 0, 500);
//	
//	}
	public void mazeDisplay(Composite parent, int style) {}


}
