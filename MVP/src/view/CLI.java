package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Observable;

import presenter.Command;
import presenter.CommandsManager;



public class CLI extends Observable {
	private BufferedReader in;
	private Writer out;	
	
	public CLI(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;		
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {				 
				try {					
					String line = null;
					do {
						out.write("Enter command: ");
						out.flush();
						line = in.readLine();
						setChanged();
						notifyObservers(line);						
					} while (!(line.equals("exit")));				
											
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}						
			}
			
		});
		thread.start();
	}
	
	
}
