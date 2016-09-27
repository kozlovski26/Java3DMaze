package view;



import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;

import algorithms.search.Solution;


public interface View {
	void displayMessage(String message);
	void start();
	public void PrintMaze2d(int[][] maze2d);
	public void Display(Maze3d mymaze);
	void displaySolution(Solution sol);
	void Exit();
	;
}
