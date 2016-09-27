package test;


import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Position;

public class Maze2DDisplay extends MazeDisplay {
	int count =0;
	static TimerTask task;
	static Timer timer;
	public void checkFinish(Position pos)
	{
		
		
	}
	public Maze2DDisplay(Composite parent, int style)
	{
		super(parent, style);	
		
		
		this.setBackground(new Color(null, 0, 200, 0));
	}
	
	@Override
	protected void drawMaze(PaintEvent e) {
		e.gc.setBackground(new Color(null, 0, 0, 0));
		// width and height of the canvas
		int width = getSize().x;
		int height = getSize().y;
		if(mymaze!=null && mymaze.getGoalPosition().equals(character.getPos()))
		{
			String icon = "images/kaki.png";
			Image img = new Image(null, icon);
			e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 0,0,width,height);
			return;
		}

		
		// width and height of each cell
		int cellWidth = width / mazeData.length;
		int cellHeight = height / mazeData[0].length;
		
		for (int i = 0; i < mazeData.length; i++) {
			for (int j = 0;j < mazeData[0].length; j++) {
				if (mazeData[i][j] != 0) {
					//e.gc.fillRectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
					String icon = "images/wall.jpeg";
					Image img = new Image(null, icon);
					e.gc.drawImage(img, 0, 0, 99, 100, j * cellWidth, i * cellHeight, cellWidth, cellHeight);

				}
			}
		}		
	
		
		character.draw(e, cellHeight,cellWidth);
		if(mymaze!=null)
		{
		drawGoal(e, cellWidth, cellHeight, character);
		}
		this.redraw();

	}

	@Override
	protected void goLeft() {
		
		Position pos = character.getPos();		
		if(pos.getY()-1 < 0)
			return;
		if(this.mazeData[pos.getX()][pos.getY()-1]==1)
		return;
		character.setPos(new Position(pos.getX() , pos.getY()-1, pos.getZ()));
		this.redraw();
	}

	@Override
	protected void goRight() {
		Position pos = character.getPos();	
		if(pos.getY()+1 >= mymaze.getColumns())
			return;
		if(this.mazeData[pos.getX()][pos.getY()+1]==1)
		return;
		character.setPos(new Position(pos.getX() , pos.getY()+1, pos.getZ()));
		this.redraw();
	}

	@Override
	protected void goForward() {
		Position pos = character.getPos();	
		if(pos.getX()-1 < 0)
			return;
		if(this.mazeData[pos.getX()-1][pos.getY()]==1)
		return;
		character.setPos(new Position(pos.getX() -1 , pos.getY(), pos.getZ()));
		this.redraw();

	}

	@Override
	protected void goBackward() {
		Position pos = character.getPos();	
		if(pos.getX()+1 >= mymaze.getRows())
			return;
		if(this.mazeData[pos.getX()+1][pos.getY()]==1)
		return;
		character.setPos(new Position(pos.getX()+1 , pos.getY(), pos.getZ()));
		this.redraw();

	}

	@Override
	protected void goUp() {
		Position pos = character.getPos();	
		if(pos.getZ()+1 >= mymaze.getFloors())
			return;
		int[][]  upFloor = mymaze.getCrossSectionByZ(pos.getZ()+1);
		if(upFloor[pos.getX()][pos.getY()]==1)
		return;
		character.setPos(new Position(pos.getX() , pos.getY(), pos.getZ()+1));
		this.setMazeData(upFloor, mymaze);
		this.redraw();

	}

	@Override
	protected void goDown() {
		Position pos = character.getPos();	
		if(pos.getZ()-1 < 0)
			return;
		int[][]  downFloor = mymaze.getCrossSectionByZ(pos.getZ()-1);
		if(downFloor[pos.getX()][pos.getY()]==1)
		return;
		character.setPos(new Position(pos.getX() , pos.getY(), pos.getZ()-1));
		this.setMazeData(downFloor, mymaze);
		this.redraw();

	}

	void drawGoal(PaintEvent e,int cellWidth,int cellHeight,GameCharacter cher)
	{
		if(cher.getPos().getZ() == mymaze.getGoalPosition().getZ())
		{
		String icon = "images/100.png";
		Image img = new Image(null, icon);
		e.gc.drawImage(img, 0, 0, 150, 117, mymaze.getGoalPosition().getX() * cellWidth, mymaze.getGoalPosition().getY() * cellHeight, cellWidth, cellHeight);
		}
	}
	
}