package presenter;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import model.Model;
import view.MyView;
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

	/**
	 * Ctor of the CommandsManger puts all commands in the
	 * HashMap<String,Command>.
	 */
	
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;	
	}
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands=new HashMap<String,Command>();
		commands.put("generate3dmaze", new GenerateMaze());
		commands.put("dir", new Dir());
		commands.put("savemaze", new SaveMaze());
		commands.put("loadmaze", new LoadMaze());
		commands.put("filesize", new FileSize());
		commands.put("solve", new Solve());
		commands.put("displaysolution", new DisplaySolution());
		commands.put("display_cross_section", new DisplayCrossSection());
		commands.put("display", new Display());
		commands.put("mazesize", new MazeSize());
		commands.put("display_message",new displaymessage());
		commands.put("exit", new Exit());
		return commands;
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
	 * for all commands we create new class that implements command interface
	 * with is method doCommand. first we check if the number of the arguments
	 * is correct,in some commands we check if the arguments that we get is the
	 * correctly arguments and then we start the matching command in the model
	 * 
	 *
	 */

	class displaymessage implements Command
	{

	@Override
	public void doCommand(String[] args) {
		String message=model.getMessage();
		view.displayMessage(message);
		
	}
		
		
		
		
		
	}
	/** class Dir **/
	class Dir implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String path;
			path = args[0];
			model.Dir(path);
		}
	}

	/** class GenerateMaze **/

	class GenerateMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 4) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			
			try {
				String name_of_maze = args[0];
				int rows = Integer.parseUnsignedInt(args[1]);
				int columns = Integer.parseUnsignedInt(args[2]);
				int floors = Integer.parseUnsignedInt(args[3]);
				model.GenerateMaze(name_of_maze, rows, columns, floors);
			} catch (Exception e) {
				view.displayMessage("please enter a valid maze arguments!");
			}

		}

	}

	/** class SaveMaze **/
	class SaveMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[0];
			String fileName = args[1];

			model.SaveMaze(name, fileName);

		}

	}

	/** class LoadMaze **/
	class LoadMaze implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[0];
			String fileName = args[1];

			model.Loadmaze(name, fileName);

		}

	}

	/** class FileSize **/
	class FileSize implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String filename = args[0];
			model.FileSize(filename);

		}

	}

	/** class Solve **/
	class Solve implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[0];
			String algo = args[1];

			model.Solve(name, algo);

		}

	}

	// ** class DisplaySolution **//*
	class DisplaySolution implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			Solution s= model.getSolution(args[0]);
			view.displaySolution(s);
		}

	}

	/** class DisplayCrossSection **/
	class DisplayCrossSection implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String type = args[0];
			try {
				int index = Integer.parseInt(args[1]);
				String name=args[2];
				int[][] maze2d;
				maze2d=model.getCrossSection(type,index,name);
				view.PrintMaze2d(maze2d);
			} catch (Exception e) {
				view.displayMessage("please enter a valid int!");
			}

		}

	}

	/** class Display **/
	class Display implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.displayMessage("display!!Incorrect number of arguments!!");
				return;
			}
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			if (maze == null)
				view.displayMessage("Maze " + name + "not found");
			else
				view.Display(maze);
		}

	}
	

	/** class MazeSize **/
	class MazeSize implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.displayMessage("Incorrect number of arguments!!");
				return;
			}
			String name = args[0];
			model.MazeSize(name);
		}

	}
	class Exit implements Command {

		@Override
		public void doCommand(String[] args) {
			
			
			model.exit();
		}
	}
}
