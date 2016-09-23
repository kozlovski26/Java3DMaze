package controller;

import java.util.HashMap;

import model.Model;

import view.View;

/**
 * the CommandsManager class
 * 
 * contains all classes of the command and the 
 * HashMap<String,Command> commands of all the commands
 * 
 * @author Bar and Noa
 *
 */
public class CommandsManager {
	/**
	 * model and view that use in all commands classes,and the
	 * HashMap<String,Command> commands of all the commands
	 */
	private Model model;
	private View view;
	private HashMap<String, Command> commands = new HashMap<String, Command>();

	/**
	 * Ctor of the CommandsManger puts all commands in the
	 * HashMap<String,Command>.
	 */
	public CommandsManager() {
		commands.put("generate3dmaze", new GenerateMaze());
		commands.put("dir", new Dir());
		commands.put("savemaze", new SaveMaze());
		commands.put("loadmaze", new LoadMaze());
		commands.put("filesize", new FileSize());
		commands.put("solve", new Solve());
		commands.put("displaysolution", new DisplaySolution());
		commands.put("display_cross_section_by_X", new DisplayCrossSectionByX());
		commands.put("display_cross_section_by_Y", new DisplayCrossSectionByY());
		commands.put("display_cross_section_by_Z", new DisplayCrossSectionByZ());
		commands.put("display", new Display());
		commands.put("mazesize", new MazeSize());
		
	}

	/**
	 * set the commands manager with new model and view
	 * 
	 * @param model
	 * @param view
	 */
	public void SetCommandsManager(Model m, View v) {
		this.model = m;
		this.view = v;

	}

	/**
	 * get all the string[] that the user enters and start the doCommand in the
	 * relevant command classes
	 * 
	 * @param args
	 */
	public void executeCommand(String[] args) {

		commands.get(args[0]).doCommand(args);

	}

	/**
	 * chack if the command String is valid
	 * 
	 * @param cmd
	 * @return boolean (true or false)
	 */
	public boolean ValidateCommand(String cmd) {
		if (commands.containsKey(cmd)) {
			return true;
		}
		return false;

	}

	/**
	 * for all commands we create new class that implements command interface
	 * with is method doCommand. first we check if the number of the arguments
	 * is correct,in some commands we check if the arguments that we get is the
	 * correctly arguments and then we start the matching command in the model
	 * 
	 *
	 */

	/** class Dir **/
	class Dir implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String path;
			path = args[1];
			model.Dir(path);
		}
	}

	/** class GenerateMaze **/

	class GenerateMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 5) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			
			try {
				String name_of_maze = args[1];
				int rows = Integer.parseInt(args[2]);
				int columns = Integer.parseInt(args[3]);
				int floors = Integer.parseInt(args[4]);
				model.GenerateMaze(name_of_maze, rows, columns, floors);
			} catch (Exception e) {
				model.displayMessage("the maze arguments you sand are invalid!,please fix the command:");
			}

		}

	}

	/** class SaveMaze **/
	class SaveMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			String fileName = args[2];

			model.SaveMaze(name, fileName);

		}

	}

	/** class LoadMaze **/
	class LoadMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			String fileName = args[2];

			model.Loadmaze(name, fileName);

		}

	}

	/** class FileSize **/
	class FileSize implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String filename = args[1];
			model.FileSize(filename);

		}

	}

	/** class Solve **/
	class Solve implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			String algo = args[2];

			model.Solve(name, algo);

		}

	}

	// ** class DisplaySolution **//*
	class DisplaySolution implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			model.DisplaySolution(name);
		}

	}

	/** class DisplayCrossSectionByX **/
	class DisplayCrossSectionByX implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			try {
				int x = Integer.parseInt(args[2]);
				model.DisplayCrossSectionByX(name, x);
			} catch (Exception e) {
				model.displayMessage("please enter a valid int!");
			}

		}

	}

	/** class DisplayCrossSectionByY **/
	class DisplayCrossSectionByY implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			try {
				int y = Integer.parseInt(args[2]);
				model.DisplayCrossSectionByX(name, y);
			} catch (Exception e) {
				model.displayMessage("please enter a valid int!");
			}

		}

	}

	/** class DisplayCrossSectionByZ **/
	class DisplayCrossSectionByZ implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			try {
				int z = Integer.parseInt(args[2]);
				model.DisplayCrossSectionByX(name, z);
			} catch (Exception e) {
				model.displayMessage("please enter a valid int!");
			}
		}

	}

	/** class Display **/
	class Display implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			model.Display(name);

		}

	}
	

	/** class MazeSize **/
	class MazeSize implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				model.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[1];
			model.MazeSize(name);
		}

	}
}
