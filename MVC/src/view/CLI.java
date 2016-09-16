package view;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.Writer;



import controller.CommandsManager;
/**
 * 
 * @author User
 *
 */

public class CLI {
	private BufferedReader in;
	private Writer out;
	private CommandsManager commandsManager;

	public CLI(BufferedReader in, Writer out, CommandsManager commandsManager) {
		this.in = in;
		this.out = out;
		this.commandsManager = commandsManager;
	}

	public void start() {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String line = "";
					while (!(line.equals("exit"))) {
						System.out.println("");
						out.write("Please enter a command from the list:\n");
						out.write("#dir <path>\n#generate3dmaze <mazename> <rows> <columns> <floors>\n#display <name>\n#display_cross_section_by_X or Y or Z <name> <index> \n#savemaze <name> <filename>\n#loadmaze <name> <filename>\n#mazesize <name>\n#filesize <name>\n#solve <name> <algorithem>\n#displaysolution <name>\n#exit\n\n");
						out.flush();
						System.out.println("");
						line = in.readLine();
						String[] args = line.split(" ");
						if (commandsManager.ValidateCommand(args[0]))
							commandsManager.executeCommand(args);
						else
							out.write("invaild command!!\n");
						System.out.println("");
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});

		thread.start();

	}
}