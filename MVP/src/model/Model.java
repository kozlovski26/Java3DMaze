package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

/**
 * the Model interface
 * 
 * @author Bar and Noa
 *
 */

public interface Model {

	void GenerateMaze(String name, int rows, int cols, int floors);

	void Dir(String path);

	void SaveMaze(String name, String filename);

	void Loadmaze(String name, String filename);

	void FileSize(String filename);

	void Solve(String name, String algorithm);

	public Solution getSolution(String name);

	public int[][] getCrossSection(String axis, int index, String name);

	void MazeSize(String name);

	public String getMessage();

	Maze3d getMaze(String name);

}