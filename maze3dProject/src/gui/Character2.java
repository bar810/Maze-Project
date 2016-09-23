package gui;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character2 {
	
	private Position pos;
	private Image img;
	
	public Character2() {
		img = new Image(null, "Character2.jpg");
	}
		
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.y, cellHeight * pos.z, cellWidth, cellHeight);
	}
	public void moveRight() {//change
		pos.y++;
	}
	public void moveForward() {
		pos.z--;
	}
	public void moveBack() {
		pos.z++;
	}
	public void moveLeft() {
		pos.y--;
	}
	public void moveDown() {
		pos.x++;
	}
	public void moveUp() {
		pos.x--;
	}
}