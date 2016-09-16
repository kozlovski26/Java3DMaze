package model;

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

	void DisplaySolution(String name);

	void DisplayCrossSectionByX(String name, int x);

	void DisplayCrossSectionByY(String name, int y);

	void DisplayCrossSectionByZ(String name, int z);

	void Display(String name);

	void MazeSize(String name);
	
	
	public void displayMessage(String message);

}