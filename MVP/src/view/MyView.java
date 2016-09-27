package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class MyView extends Observable implements View, Observer {

	/** Variables **/
	private BufferedReader in;
	private Writer out;
	private CLI cli;

	/**
	 * Ctor of MyView
	 * 
	 * create cli with all the parameters
	 * 
	 * @param controller
	 * @param in
	 * @param out
	 */
	public MyView(BufferedReader in, Writer out) {
		this.in = in;
		this.out = out;
		this.cli = new CLI(in, out);
		cli.addObserver(this);
	}

	/**
	 * displayMessage
	 * 
	 * get a message and print it on the screen
	 * 
	 * @param massage
	 */
	@Override
	public void displayMessage(String message) {
		try {
			out.write(message + "\n");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Start method
	 * 
	 * this method start the cli run in a new thread
	 * 
	 */
	@Override
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				cli.start();
			}

		});
		thread.start();
	}

	/**
	 * PrintMaze2d method
	 * 
	 * print a maze2d
	 * 
	 * @param maze2d
	 */
	@Override
	public void PrintMaze2d(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			System.out.print("");
			for (int j = 0; j < maze2d[i].length; j++) {
				System.out.print(maze2d[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println();

	}

	/**
	 * Display
	 * 
	 * print the maze3d
	 * 
	 */
	@Override
	public void Display(Maze3d mymaze) {
		mymaze.printMaze3d();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli)
			setChanged();
		notifyObservers(arg);
	}



	@Override
	public void displaySolution(Solution sol) {
		try {
			out.write(sol+ "\n");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
