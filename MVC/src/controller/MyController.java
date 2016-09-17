package controller;

import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;

import model.Model;
import view.View;

/**
 * the MyController class
 * 
 * @author Bar and Noa
 *
 */
public class MyController implements Controller {
	/** model,view,commandsManager variables **/
	private Model model;
	private View view;
	private CommandsManager commandsManager;

	/**ctor of MyController**/
	public MyController() {

		commandsManager = new CommandsManager();
	}

	/**
	 * Sets the model and the view and Set it in the CommandsManager
	 * 
	 * @param model
	 *            and view
	 */
	public void setModelAndView(Model model, View view) {
		this.model = model;
		this.view = view;

		commandsManager.SetCommandsManager(model, view);
	}

	@Override
	public void executeCommand(String commandLine) throws IOException {
		commandsManager.executeCommand(commandLine.split(" "));
	}

	/** sand to the view the display massage **/
	@Override
	public void displayMessage(String msg) {
		view.displayMessage(msg);

	}

	/** sand the 2dmaze to the view PrintMaze2d method **/
	@Override
	public void PrintMaze2d(int[][] maze) {
		view.PrintMaze2d(maze);

	}

	/** sand the maze to the view display method **/
	@Override
	public void Display(Maze3d mymaze) {
		view.Display(mymaze);

	}

	/**
	 * Gets the CommandsManager
	 *
	 * @return the CommandsManager
	 */
	@Override
	public CommandsManager GetCommandsManager() {
		return this.commandsManager;
	}

}
