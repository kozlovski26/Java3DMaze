package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;
	
	public Character() {
		img = new Image(null, "images/burger.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void moveRight() {
		pos.y++;
	}
	
	public void moveLeft() {
		pos.y--;
	}
	public void moveUP() {
		pos.x--;
	}
	public void moveDown() {
		pos.x++;
	}
	public void moveFloorUp() {
		pos.z++;
	}
	public void moveFloorDown() {
		pos.z--;
	}

}