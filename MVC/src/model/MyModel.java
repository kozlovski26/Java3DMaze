package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import algorithms.demo.SearchableMaze3d;
import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastCellChoose;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.RandomCellChoose;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.Solution;
import algorithms.search.State;
import controller.Controller;

/**
 * the My Model class
 * 
 * @author Bar and Noa
 *
 */
public class MyModel implements Model {

	/**
	 * Variables of mymodel: controller and threads,mazes,solved,algorithms
	 * HashMaps
	 **/

	private Controller controller;
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution> solved = new HashMap<String, Solution>();
	private HashMap<String, CommonSearcher> algorithms = new HashMap<String, CommonSearcher>();

	/**
	 * ctor of MyModel intilizing with new controller and creates new BFS and
	 * DFS and put into algorithms(HashMap<String, CommonSearcher>)
	 * 
	 * @param controller
	 */
	public MyModel(Controller controller) {
		this.controller = controller;
		BFS mybfs = new BFS();
		DFS mydfs = new DFS();
		algorithms.put("bfs", mybfs);
		algorithms.put("dfs", mydfs);
	}

	/** sand a massage from the controller **/
	@Override

	public void displayMessage(String message) {
		controller.displayMessage(message);
	}

	/**
	 * GenerateMaze
	 * 
	 * generate randomaly maze by two methods of Growing Tree Generator
	 * run in a thread
	 * 
	 * @param name,rows,column,floor
	 */
	@Override
	public void GenerateMaze(String name, int rows, int cols, int floor) {
		if (mazes.containsKey(name)) {
			controller.displayMessage("Maze" + name + "Already exist");
			return;

		}
		Thread generateMazeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
			/*	Random rand = new Random();
				int choose = rand.nextInt(2);*/
				Maze3dGenerator mg;
			/*	if (choose == 0) {*/
					//mg = new GrowingTreeGenerator(new LastCellChoose());
					mg = new GrowingTreeGenerator(new RandomCellChoose());
			/*	} else {
					mg = new GrowingTreeGenerator(new RandomCellChoose());
				}*/
				Maze3d maze = mg.generate(rows, cols, floor);
				
				BFS bfs = new BFS();
				SearchableMaze3d searchbleMaze = new SearchableMaze3d(maze);
				bfs.search(searchbleMaze);
				Solution sol=bfs.getSol();
				int i=0;
				System.out.println(i);
				System.out.println(sol);
				while(sol==null)
				{
				maze = mg.generate(rows, cols, floor);
				searchbleMaze = new SearchableMaze3d(maze);
				bfs.search(searchbleMaze);
				sol=bfs.getSol();
				System.out.println(i);
				System.out.println(sol);
				
				
				}
				mazes.put(name, maze);
				controller.displayMessage("Maze " +name+ " is ready");
			}
		});
		generateMazeThread.start();

		threads.add(generateMazeThread);
	}

	/**
	 * Dir
	 *
	 *show the path of the file
	 *
	 * @param path
	 *            
	 **/
	@Override

	public void Dir(String path) {
		File myfile = new File(path);
		if (!myfile.isDirectory()) {
			controller.displayMessage("Bad Path");
			return;
		}
		String[] myfolders = myfile.list();
		for (int i = 0; i < myfolders.length; i++) {
			controller.displayMessage(myfolders[i]);
		}

	}

	/**
	 * SaveMaze
	 * 
	 * save a maze
	 * @param name,filename
	 */
	@Override
	public void SaveMaze(String name, String fileName) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist\n");
			return;
		}
		Maze3d maze = mazes.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			byte[] bytes = maze.toByteArray();
			out.write(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Loadmaze
	 * 
	 * load a maze
	 * @param name,filename
	 */
	@Override
	public void Loadmaze(String name, String fileName) {

		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream(new File(fileName)));
			byte b[] = new byte[in.read()];
			in.read(b);
			mazes.put(name, new Maze3d(b));
			in.close();
		} catch (FileNotFoundException e) {
			controller.displayMessage("Maze " + name + " does not exist\n");
			return;
		} catch (IOException e) {
			// e.printStackTrace());
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}

	}

	/**
	 * FileSize 
	 * 
	 * show the file size in bytes
	 * 
	 * @param filename
	 */
	@Override
	public void FileSize(String filename) {
		try {
			File myfile = new File(filename);
			if (!myfile.isFile()) {
				controller.displayMessage("File Not Found");
				return;
			}
			controller.displayMessage((int) myfile.length() + " " + "bytes");
		} catch (NullPointerException n) {
			controller.displayMessage("File Not Found");
			return;
		}

	}

	/**
	 * Solve
	 * 
	 * solve the maze with BFS or DFS run in a new a thread
	 * 
	 * @param name,algorithm
	 */
	@Override
	public void Solve(String name, String algorithm) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		if (!algorithms.containsKey(algorithm)) {
			controller.displayMessage("Algorithm " + name + " does not exist");
			return;
		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				SearchableMaze3d mymaze = new SearchableMaze3d(mazes.get(name));
				CommonSearcher solv = algorithms.get(algorithm);
				solved.put(name, solv.search(mymaze));
				controller.displayMessage("Maze " + name + " Is Solved");
			
			}
		});
		thread.start();
		threads.add(thread);
	}

	/**
	 * DisplaySolution
	 * 
	 * display the solution of the maze
	 * 
	 * @param name
	 */
	@Override
	public void DisplaySolution(String name) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		if (solved.containsKey(name)) {
			ArrayList<State> mysolvedmaze = solved.get(name).getStates();

			for (int i = 0; i < mysolvedmaze.size(); i++) {
				controller.displayMessage(mysolvedmaze.get(i).getState().toString());
			}
		} else {
			controller.displayMessage("Dosen't have a Solution..");
		}

	}

	/**
	 * DisplayCrossSectionByX
	 * 
	 * display cross section by x
	 * 
	 * @param name,x
	 */
	@Override
	public void DisplayCrossSectionByX(String name, int x) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		Maze3d mymaze = mazes.get(name);
		if (mymaze.getRows() <= x || x < 0) {
			controller.displayMessage("Bad Input,X is out off Bound");
			return;
		}
		int[][] maze2d = null;
		maze2d = mymaze.getCrossSectionByX(x);
		controller.PrintMaze2d(maze2d);

	}

	/**
	 * DisplayCrossSectionByY
	 * 
	 * display cross section by y
	 * 
	 * @param name,y
	 */
	@Override
	public void DisplayCrossSectionByY(String name, int y) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		Maze3d mymaze = mazes.get(name);
		if (mymaze.getColumns() <= y || y < 0) {
			controller.displayMessage("Bad Input,Y is out off Bound");
			return;
		}
		int[][] maze2d = null;
		maze2d = mymaze.getCrossSectionByX(y);
		controller.PrintMaze2d(maze2d);

	}

	/**
	 * DisplayCrossSectionByZ
	 * 
	 * display cross section by z
	 * 
	 * @param name,z
	 */
	@Override
	public void DisplayCrossSectionByZ(String name, int z) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		Maze3d mymaze = mazes.get(name);
		if (mymaze.getFloors() <= z || z < 0) {
			controller.displayMessage("Bad Input,Z  is out off Bound");
			return;
		}
		int[][] maze2d = null;
		maze2d = mymaze.getCrossSectionByX(z);
		controller.PrintMaze2d(maze2d);

	}

	/**
	 * Display
	 * 
	 * display the maze3d
	 * 
	 * @param name
	 */
	@Override
	public void Display(String name) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		controller.Display(mazes.get(name));
	
	}
	
	

	/**
	 * MazeSize
	 * 
	 * show the maze length
	 * 
	 * @param name
	 */
	@Override
	public void MazeSize(String name) {
		if (!mazes.containsKey(name)) {
			controller.displayMessage("Maze " + name + " does not exist");
			return;
		}
		Maze3d mymaze = mazes.get(name);
		byte[] bytemaze = mymaze.toByteArray();
		String my = bytemaze.length + "";

		controller.displayMessage(my);

	}


}