package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	int[][] mazeData = null;
	String mazeName = null;

	public String getMazeName() {
		return mazeName;
	}

	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	/**
	 * Ctor
	 * 
	 * @param parent
	 * @param style
	 */
	MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * sets the current maze
	 */
	public abstract void setCurrentMaze(Maze3d m);

	/**
	 * get the current maze
	 */
	public abstract Maze3d getCurrentMaze();

	/**
	 * sets the character position parameters
	 */
	public abstract void setCharacterPosition(Position pos);

	/**
	 * Return the position of the character.
	 * 
	 * @return
	 */
	public abstract Position getCharacter();

	/**
	 * moving the character to upper dimension
	 */
	public abstract void moveUp();

	/**
	 * moving the character to lower dimension
	 */
	public abstract void moveDown();

	/**
	 * moving the character up
	 */
	public abstract void moveForward();

	/**
	 * moving the character down
	 */
	public abstract void moveBackward();

	/**
	 * moving the character left
	 */
	public abstract void moveLeft();

	/**
	 * moving the character right
	 */
	public abstract void moveRight();

	/**
	 * taking the character step by step to the exit by timer task
	 */
	public abstract void walkToExit(Solution solution);

	/**
	 * moving the character one step to help him.
	 */
	public abstract void walkByHint(Solution solution);
}