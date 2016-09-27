package test;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class GameCharacter {
	private static final String icon = "images/100.png";
	private Position pos;
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(PaintEvent e, int cellWidth, int cellHeight) {
		e.gc.setBackground(new Color(null, 255, 255, 255));
		
		Image img = new Image(null, icon);
		e.gc.drawImage(img, 0, 0, 120, 160,pos.getX() * cellHeight, pos.getY() * cellWidth,cellHeight, cellWidth);
	}
	
	
}
