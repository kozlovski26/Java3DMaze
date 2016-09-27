package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class GameExit {
	
	/** The Constant icon. */
	private static final String icon = "images/kaki.png";
	
	/** The pos. */
	private Position pos;
	
	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Position getPos() {
		return pos;
	}
	
	/**
	 * Sets the pos.
	 *
	 * @param pos the new pos
	 */
	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	/**
	 * Draw.
	 *
	 * @param e the e
	 * @param cellWidth the cell width
	 * @param cellHeight the cell height
	 * @param i the i
	 * @param j the j
	 */
	public void draw(PaintEvent e, int cellWidth, int cellHeight, int i, int j) {
		//e.gc.setBackground(new Color(null, 255, 255, 255));
		
		Image img = new Image(null, icon);
		e.gc.drawImage(img, 0, 0, 128, 128, pos.getX() * cellWidth, pos.getY()* cellHeight, cellWidth, cellHeight);
	}
	
	
}

