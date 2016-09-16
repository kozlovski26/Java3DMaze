package view;

import mazeGenerators.Maze3d;

/**
 * the View interface
 * 
 * @author Bar and Noa
 *
 */
public interface View {

	void displayMessage(String message);

	void start();

	public void PrintMaze2d(int[][] maze2d);

	public void Display(Maze3d mymaze);

}
