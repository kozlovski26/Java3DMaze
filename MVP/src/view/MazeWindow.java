package view;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.RandomCellChoose;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow implements View {

	private String nameMaze;

	public MazeWindow(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	Timer timer;
	TimerTask task;

	private void randomWalk(MazeDisplayer maze) {
		Random r = new Random();
		boolean b1, b2;
		b1 = r.nextBoolean();
		b2 = r.nextBoolean();
		if (b1 && b2)
			maze.moveUp();
		if (b1 && !b2)
			maze.moveDown();
		if (!b1 && b2)
			maze.moveRight();
		if (!b1 && !b2)
			maze.moveLeft();

		maze.redraw();
	}

	@Override
	void initWidgets() {

		// number of columns-2,different size
		shell.setLayout(new GridLayout(2, false));
		Menu menuButton = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuButton);

		//
		MenuItem File = new MenuItem(menuButton, SWT.CASCADE);
		File.setText("File");

		// Drop down functions for file button
		Menu subMenu = new Menu(shell, SWT.DROP_DOWN);
		File.setMenu(subMenu);

		MenuItem SaveMaze = new MenuItem(subMenu, SWT.PUSH);
		SaveMaze.setText("SaveMaze\tCtrl+S");
		MenuItem LoadMaze = new MenuItem(subMenu, SWT.PUSH);
		LoadMaze.setText("LoadMaze\tCtrl+L");
		MenuItem properties = new MenuItem(subMenu, SWT.PUSH);
		properties.setText("Properties");
		MenuItem exitButtonmenu = new MenuItem(subMenu, SWT.PUSH);
		exitButtonmenu.setText("Exit");

		exitButtonmenu.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				setChanged();
				notifyObservers("exit");
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		// generate maze Button
		Button generateButton = new Button(shell, SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.NONE, SWT.LEFT, false, false, 1, 1));

		// colors
		Color red = display.getSystemColor(SWT.COLOR_BLUE);
		generateButton.setBackground(red);
		generateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// Generate Maze Window
				Shell GenerateWindow = new Shell(display);
				GenerateWindow.setLayout(new GridLayout(2, true));
				GenerateWindow.setSize(350, 300);
				GenerateWindow.setText("Generate");

				// maze name
				Label lblName = new Label(GenerateWindow, SWT.NONE);
				lblName.setText("Maze Name: ");
				Text txtName = new Text(GenerateWindow, SWT.BORDER);
				lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				// rows
				Label lblRows = new Label(GenerateWindow, SWT.NONE);
				lblRows.setText("Rows: ");
				Text txtRows = new Text(GenerateWindow, SWT.BORDER);
				lblRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				// columns
				Label lblColumns = new Label(GenerateWindow, SWT.NONE);
				lblColumns.setText("Columns: ");
				Text txtColumns = new Text(GenerateWindow, SWT.BORDER);
				lblColumns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtColumns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				// floors
				Label lblFloors = new Label(GenerateWindow, SWT.NONE);
				lblFloors.setText("Floors: ");
				Text txtFloors = new Text(GenerateWindow, SWT.BORDER);
				lblFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				// Generate Maze button in the GenerateWindow
				Button GenerateMaze = new Button(GenerateWindow, SWT.PUSH);
				GenerateWindow.setDefaultButton(GenerateMaze);
				GenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
				GenerateMaze.setText("Generate Maze");

				// cancel button
				Button Cancel = new Button(GenerateWindow, SWT.PUSH);
				Cancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
				Cancel.setText("Cancel");

				GenerateWindow.open();
				GenerateMaze.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						MessageBox msg = new MessageBox(GenerateWindow, SWT.OK);
						nameMaze = txtName.getText();
						msg.setText("Create Maze -> " + nameMaze);

						int rows = Integer.parseInt(txtRows.getText());
						int cols = Integer.parseInt(txtColumns.getText());
						int floors = Integer.parseInt(txtFloors.getText());

						setChanged();
						notifyObservers("generate3dmaze " + nameMaze + " " + rows + " " + cols + " " + floors);

						msg.setMessage("maze: " + nameMaze);
						msg.open();

						GenerateWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		Button DisplayButton = new Button(shell, SWT.PUSH);
		DisplayButton.setText("Display Maze");
		DisplayButton.setLayoutData(new GridData(SWT.NONE, SWT.LEFT, false, false, 1, 1));
		DisplayButton.setBackground(red);

		DisplayButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("display" + " " + nameMaze);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button solveButton = new Button(shell, SWT.PUSH);
		solveButton.setText("Solve Maze");
		solveButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 3, 1));
		solveButton.setBackground(red);
		solveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Shell SolveWindow = new Shell(display);
				SolveWindow.setLayout(new GridLayout(2, true));
				SolveWindow.setSize(350, 300);
				SolveWindow.setText("Solve");

				Label lblName = new Label(SolveWindow, SWT.NONE);
				lblName.setText("Maze Name: ");
				Text txtName = new Text(SolveWindow, SWT.BORDER);
				lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				Button btnDFS = new Button(SolveWindow, SWT.RADIO);
				btnDFS.setText("DFS");
				SolveWindow.setDefaultButton(btnDFS);
				btnDFS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

				Button btnBFS = new Button(SolveWindow, SWT.RADIO);
				btnBFS.setText("BFS");
				SolveWindow.setDefaultButton(btnDFS);
				btnBFS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

				Button SolveMaze = new Button(SolveWindow, SWT.PUSH);
				// SolveWindow.setDefaultButton(SolveMaze);
				SolveMaze.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				SolveMaze.setText("Solve Maze");
				// cancel button
				Button Cancel = new Button(SolveWindow, SWT.PUSH);
				Cancel.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				Cancel.setText("Cancel");
				SolveWindow.open();
				SolveMaze.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {

						btnBFS.addListener(SWT.MouseDown, new Listener() {

							@Override

							public void handleEvent(Event arg0) {
								MessageBox msg = new MessageBox(SolveWindow, SWT.OK);
								msg.setMessage("solve");
								nameMaze = txtName.getText();
								setChanged();
								notifyObservers("solve" + " " + nameMaze + " " + "BFS");

							}

						});

						btnDFS.addListener(SWT.MouseDown, new Listener() {

							@Override
							public void handleEvent(Event arg0) {
								MessageBox msg = new MessageBox(SolveWindow, SWT.OK);
								msg.setMessage("solve");
								setChanged();
								nameMaze = txtName.getText();
								notifyObservers("solve" + " " + nameMaze + " " + "DFS");

							}
						});

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		MazeDisplayer maze = new Maze3D(shell, SWT.BORDER);
		Maze3dGenerator myMaze = new GrowingTreeGenerator(new RandomCellChoose());
		Maze3d maze3d = myMaze.generate(19, 20, 3);
		maze.setMazeData(maze3d.getCrossSectionByZ(2));
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
shell.setLayout(new GridLayout(2,false));
		
		Button startButton=new Button(shell, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
				
		
		Button stopButton=new Button(shell, SWT.PUSH);
		stopButton.setText("Stop");
		stopButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		stopButton.setEnabled(false);
		
		
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				timer=new Timer();
				task=new TimerTask() {
					@Override
					public void run() {
						display.syncExec(new Runnable() {
							@Override
							public void run() {
								randomWalk(maze);
							}
						});
					}
				};				
				timer.scheduleAtFixedRate(task, 0, 100);				
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				task.cancel();
				timer.cancel();
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	public static void main(String[] args) {
		MazeWindow win = new MazeWindow(800, 800);

		win.run();
	}

	@Override
	public void displayMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void PrintMaze2d(int[][] maze2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Display(Maze3d mymaze) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displaySolution(Solution sol) {
		// TODO Auto-generated method stub

	}

}
