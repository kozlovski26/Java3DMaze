package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;

public class Presenter implements Observer {
	/** model,view,commandsManager,HashMap<String,Command>**/
	private Model model;
	private View view;
	private CommandsManager commandsManager;
	private HashMap<String, Command> commands;

	/**
	 * Presenter
	 * 
	 * @param model
	 * @param view
	 */
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();
	}

	@Override
	public void update(Observable o, Object arg) {
		String commandLine = (String) arg;

		String arr[] = commandLine.split(" ");
		String command = arr[0];

		if (!commands.containsKey(command)) {
			view.displayMessage("Command doesn't exist");
		} else {
			String[] args = null;
			if (arr.length > 1) {
				String commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);
				args = commandArgs.split(" ");
			}
			Command cmd = commands.get(command);
			cmd.doCommand(args);
		}
	}
}
