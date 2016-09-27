package test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public abstract class MazeDisplay extends Canvas {
	protected GameCharacter character = new GameCharacter();
	Maze3d mymaze;

	protected int[][] mazeData;
	
	protected abstract void drawMaze(PaintEvent e);

	protected abstract void goLeft();

	protected abstract void goRight();

	protected abstract void goForward();

	protected abstract void goBackward();

	protected abstract void goUp();
	
	protected abstract void goDown();


		

	public void setMazeData(int[][] mazeData,Maze3d mymaze) {
		this.mazeData = mazeData;
		this.mymaze=mymaze;
	}
	
	public void setCharacterPosition(Position pos) {
		character.setPos(pos);
	}

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				drawMaze(e);
			}
		});

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_LEFT:
					goLeft();
					break;
				case SWT.ARROW_RIGHT:
					goRight();
					break;
				case SWT.ARROW_UP:
					goForward();
					break;
				case SWT.ARROW_DOWN:
					goBackward();
					break;
				case SWT.PAGE_UP:
					goUp();
					break;
				case SWT.PAGE_DOWN:
					goDown();
					break;


				// TODO: Finish this list
				}
			}
		});
		

	}
	
}