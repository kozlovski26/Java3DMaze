package controller;

import view.View;

import java.io.IOException;

import mazeGenerators.Maze3d;
import model.Model;

/**
 * The Interface Controller.
 * 
 * @author Bar and Noa
 */
public interface Controller {

	public void setModelAndView(Model model, View view);

	void executeCommand(String commandLine) throws IOException;

	void displayMessage(String message);

	public void PrintMaze2d(int[][] maze);

	public void Display(Maze3d mymaze);

	public CommandsManager GetCommandsManager();
}