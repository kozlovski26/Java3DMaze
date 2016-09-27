package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.HashMap;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

import properties.Properties;

public class MyModel extends Observable implements Model {
	/**
	 * Variables
	 */
	private String message = null;
	private HashMap<String, Maze3d> mazes = new HashMap<String, Maze3d>();
	private HashMap<String, Solution> solved = new HashMap<String, Solution>();
	private HashMap<String, CommonSearcher> algorithms = new HashMap<String, CommonSearcher>();
	private ExecutorService executor;
	private Properties properties;

	/**
	 * MyModel Ctor
	 * 
	 * @param executor
	 */
	public MyModel(Properties properties) {
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		this.properties = properties;
	}

	@Override
	public void GenerateMaze(String name, int rows, int cols, int floors) {
		if (mazes.containsKey(name)) {
			this.message = "Maze " + name + "Already exist";
			setChanged();
			notifyObservers("display_message");
			return;
		}
		Callable<Maze3d> mazeC = new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Random rand = new Random();
				int choose = rand.nextInt(2);
				Maze3dGenerator mg;
				if (choose == 0) {
					mg = new GrowingTreeGenerator(new LastCellChoose());
				} else {
					mg = new GrowingTreeGenerator(new RandomCellChoose());
				}
				Maze3d maze = mg.generate(rows, cols, floors);
				mazes.put(name, maze);
				message = "Maze " + name + " is ready";
				setChanged();
				notifyObservers("display_message");
				return maze;
			}
		};
		Future<Maze3d> futureMaze = executor.submit(mazeC);
	}

	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	@Override
	public void Dir(String path) {
		File myfile = new File(path);
		if (!myfile.isDirectory()) {
			this.message = "Bad Path";
			setChanged();
			notifyObservers("display_message");
			return;
		}
		String[] myfolders = myfile.list();
		for (int i = 0; i < myfolders.length; i++) {
			this.message = myfolders[i];
			setChanged();
			notifyObservers("display_message");
		}

	}

	@Override
	public void SaveMaze(String name, String filename) {
		if (!mazes.containsKey(name)) {

			this.message = "Maze " + name + " Already exist";
			;
			setChanged();
			notifyObservers("display_message");
			return;
		}
		Maze3d maze = mazes.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(filename));
			byte[] bytes = maze.toByteArray();
			out.write(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			this.message = "File Not Found";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IOException e) {
			this.message = "IOEXception";
			setChanged();
			notifyObservers("display_message");
			return;
		}

	}

	@Override
	public void Loadmaze(String name, String filename) {
		if (!mazes.containsKey(name)) {

			this.message = "Maze " + name + " Already exist";
		}
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream(new File(filename)));
			byte b[] = new byte[in.read()];
			in.read(b);
			mazes.put(name, new Maze3d(b));
			in.close();
		} catch (FileNotFoundException e) {
			this.message = "Maze " + name + " does not exist";
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (IOException e) {
			this.message = "IOEXception";
			setChanged();
			notifyObservers("display_message");
			return;
		}

	}

	@Override
	public void FileSize(String filename) {
		try {
			File myfile = new File(filename);
			if (!myfile.isFile()) {
				this.message = "File Not Found";
				setChanged();
				notifyObservers("display_message");
				return;
			}
			this.message = ((int) myfile.length() + " " + "bytes");
			setChanged();
			notifyObservers("display_message");
			return;
		} catch (NullPointerException n) {
			this.message = "File Not Found";
			setChanged();
			notifyObservers("display_message");
			return;
		}

	}

	@Override
	public void Solve(String name, String algorithm) {
		/*if (!mazes.containsKey(name)) {
			this.message = "Maze " + name + " does not exist";
			setChanged();
			notifyObservers("display_message");
			return;
		}
		if (!algorithms.containsKey(algorithm)) {
			this.message = "Algorithm " + algorithm + " does not exist";
			setChanged();
			notifyObservers("display_message");
			return;
		}
*/
		Callable<Solution> solC = new Callable<Solution>() {

			@Override
			public Solution call() throws Exception {
				properties.setSolveMazeAlgorithm(algorithm);
				Maze3d maze;
				maze = mazes.get(name);
				Solution sol = null;
				if (solved.containsKey(algorithm)) {
					solved.remove(name);
				}
				if (properties.getSolveMazeAlgorithm().equals("dfs")) {

					sol = new DFS().search(new SearchableMaze3d(maze));
					message = "DFS for maze " + name + " is ready\n";
					setChanged();
					notifyObservers("display_message");
					solved.put(name, sol);
				} else if (properties.getSolveMazeAlgorithm().equals("bfs")) {

					sol = new BFS().search(new SearchableMaze3d(maze));
					message = "BFS for maze " + name + " is ready\n";
					setChanged();
					notifyObservers("display_message");
					solved.put(name, sol);
				}

				return sol;
			}

		};
		Future<Solution> MazeSolution = executor.submit(solC);
	}

	@Override
	public Solution getSolution(String name) {
		return solved.get(name);
	}

	@Override
	public int[][] getCrossSection(String type, int index, String name) {
		Maze3d maze = mazes.get(name);
		switch (type) {
		case "x":
		case "X":
			return maze.getCrossSectionByX(index);
		case "y":
		case "Y":
			return maze.getCrossSectionByY(index);
		case "z":
		case "Z":
			return maze.getCrossSectionByZ(index);
		default:
			return null;
		}
	}

	@Override
	public void MazeSize(String name) {
		if (!mazes.containsKey(name)) {
			this.message = "Maze " + name + " does not exist";
			setChanged();
			notifyObservers("display_message");
			return;

		}
		Maze3d mymaze = mazes.get(name);
		byte[] bytemaze = mymaze.toByteArray();
		this.message = bytemaze.length + "";
		setChanged();
		notifyObservers("display_message");

	}

	public String getMessage() {
		return message;
	}

}
