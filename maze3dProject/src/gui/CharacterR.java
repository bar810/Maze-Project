package gui;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import algorithms.mazeGenerators.Position;



public class CharacterR {
	private Position pos;
	private Image img;
	
	public CharacterR() {
		img = new Image(null, "CharacterR.jpg");
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
	
	public void moveRight() {
		pos.z++;
	}
	public void moveDown() {
		pos.x--;
	}
	public void moveForward() {
		pos.y--;
	}
	public void moveBack() {
		pos.y++;
	}
	public void moveUp() {
		pos.x++;
	}
	public void moveLeft() {
		pos.z--;
	}
}
