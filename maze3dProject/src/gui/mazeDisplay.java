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

import algorithms.mazeGenerators.Position;

public class mazeDisplay extends Canvas {
	
	private CharacterR characterR;
	private CharacterL characterL;
	private target tar;
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
		
		characterL= new CharacterL();
		
		characterR= new CharacterR();
		characterR.setPos(new Position(1,1, 1));
		//tar=new target();
		//tar.setPos(new Position(1,1,1));

		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = characterR.getPos();
				switch (e.keyCode) {
				case SWT.ARROW_DOWN:	
					characterR.moveDown();
					//characterL.setPos(characterR.getPos());
					redraw();
					break;
				case SWT.ARROW_UP:
					characterR.moveUp();
					redraw();
					break;
				case SWT.ARROW_RIGHT:	
					characterR.moveRight();
					redraw();
					break;
				case SWT.LEFT:	
					//if(mazeData[characterR.getPos().y+1][characterR.getPos().z]!=1)
					characterR.moveLeft();
					redraw();
					break;
				}
			}
		});
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));
				   

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
				   
				 
				   characterR.draw(w, h, e.gc);
				
			}
		});
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {	
				getDisplay().syncExec(new Runnable() {					

					@Override
					public void run() {
						
						//characterR.moveRight();
						//redraw();
					}
				});
				
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);
	
	}



	public void mazeDisplay(Composite parent, int style) {}


}
