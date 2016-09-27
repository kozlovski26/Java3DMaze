package view;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.RandomCellChoose;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow implements View {
	
	

	private MazeDisplayer mazeDisplayer;
	private String Mazename;
	private Maze3d maze;
	private MazeDisplay mazeDisplay;
	MouseWheelListener mouseZoomlListener;
	Image img = new Image (null, "Images/wall.png");
	String msg;
	
	
	
	
	public MazeWindow(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	void initWidgets() {

		// number of columns-2,different size
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);	
		shell.setText("Maze3d Game");
		
		
		
		//shell.setImage(img);
		shell.setBackgroundImage(img);
		Color red = display.getSystemColor(SWT.COLOR_DARK_RED);

		Composite btnGroup = new Composite(shell, SWT.FILL);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		btnGroup.setBackgroundImage(img);
		
		//Generate maze Button
		Button generateButton = new Button(btnGroup, SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setBackground(red);
		
		//Display maze solution Button
		Button displayMazeSolutionButton = new Button(btnGroup, SWT.PUSH);
		displayMazeSolutionButton.setText("Display Maze solution");
		displayMazeSolutionButton.setBackground(red);
		
		//Solve maze Button
		Button solveButton = new Button(btnGroup, SWT.PUSH);
		solveButton.setText("Solve Maze");
		solveButton.setBackground(red);
		
		//Display Button
		Button DisplayButton = new Button(btnGroup, SWT.PUSH);
		DisplayButton.setText("Display Maze");
		DisplayButton.setBackground(red);
		
		//Save maze Button
		Button SaveButton = new Button(btnGroup, SWT.PUSH);
		SaveButton.setText("Load Maze");
		SaveButton.setBackground(red);
		
		//Load maze Button
		Button LoadButton = new Button(btnGroup, SWT.PUSH);
		LoadButton.setText("Save Maze");
		LoadButton.setBackground(red);
		
		//Maze size Button
		Button MazeSizeButton = new Button(btnGroup, SWT.PUSH);
		MazeSizeButton.setText("Maze Size");
		MazeSizeButton.setBackground(red);
		
		//File size Button
		Button FileSizeButton = new Button(btnGroup, SWT.PUSH);
		FileSizeButton.setText("File Size");
		FileSizeButton.setBackground(red);
		
		
		
		//Exit Button
		Button ExitButton = new Button(btnGroup, SWT.PUSH);
		ExitButton.setText("Exit");
		ExitButton.setBackground(red);
		ExitButton.addSelectionListener(new SelectionListener() {

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
		/////////////////////////////// up menu/////////////////////////////////////////////////
		Menu menuButton = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuButton);
		MenuItem File = new MenuItem(menuButton, SWT.CASCADE);
		File.setText("File");

		/////////////////////////////////////////// Drop down functions for file
		/////////////////////////////////////////// button/////////////////////////////////////
		Menu subMenu = new Menu(shell, SWT.DROP_DOWN);
		File.setMenu(subMenu);

	/*	MenuItem SaveMaze = new MenuItem(subMenu, SWT.PUSH);
		SaveMaze.setText("SaveMaze\tCtrl+S");
		MenuItem LoadMaze = new MenuItem(subMenu, SWT.PUSH);
		LoadMaze.setText("LoadMaze\tCtrl+L");*/
		
		MenuItem properties = new MenuItem(subMenu, SWT.PUSH);
		properties.setText("Load Properties");
		
		
		
		MenuItem exitButtonmenu = new MenuItem(subMenu, SWT.PUSH);
		exitButtonmenu.setText("Exit");
		exitButtonmenu.setAccelerator(SWT.CTRL + 'x');
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

		
	
		/*
		 * MazeDisplayer maze=new Maze3D(shell, SWT.BORDER);
		 * maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		 */
	
	
		
///////////////////////////////////////Generate  Button////////////////////////////////////////////////
		generateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				// Generate Maze Window
				Shell GenerateWindow = new Shell(display);
				GenerateWindow.setLayout(new GridLayout(2, true));
				GenerateWindow.setSize(460, 350);
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

				//////////// just for////////////////////////////////////////////////////////
				//////////// chacking///////////////////////////////////////////////////////
				txtName.setText("bar");
				txtRows.setText("20");
				txtColumns.setText("20");
				txtFloors.setText("10");
				////////////////////////////////////////////////////////////////////////////////////

				Composite btnGroup2 = new Composite(GenerateWindow, SWT.FILL);
				RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
				btnGroup2.setLayout(rowLayout);
				// Generate Maze button in the GenerateWindow
				Button GenerateMaze = new Button(GenerateWindow, SWT.PUSH);
				GenerateMaze.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				GenerateMaze.setText("Generate Maze");

				// cancel button
				Button Cancel = new Button(GenerateWindow, SWT.PUSH);
				Cancel.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				Cancel.setText("Cancel");

				GenerateWindow.open();
				GenerateMaze.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {

						MessageBox msg = new MessageBox(GenerateWindow, SWT.OK);
						Mazename = txtName.getText();
						msg.setText("Creating " + Mazename + " Maze");

						int rows = Integer.parseInt(txtRows.getText());
						int cols = Integer.parseInt(txtColumns.getText());
						int floors = Integer.parseInt(txtFloors.getText());

						setChanged();
						notifyObservers("generate3dmaze " + Mazename + " " + rows + " " + cols + " " + floors);

						msg.setMessage("maze: " + Mazename);
						msg.open();
						GenerateWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}

				});

				
				
				
				
	/////////////////////////////display Maze Solution Button/////////////////////////////////////////////////////////////////////////////////////
		
				displayMazeSolutionButton.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						setChanged();
						// notifyObservers("display_solution"+" "+ "amiran");
						notifyObservers("displaysolution" + " " + Mazename);
						//mazeDisplay.setFocus();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

//////////////////////////////////////Display Button///////////////////////////////////////////////////
	
		DisplayButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("display" + " " + Mazename);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
//////////////////////////////////Solve Button/////////////////////////////////////////////////////////
	
		solveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Shell SolveWindow = new Shell(display);
				SolveWindow.setLayout(new GridLayout(2, true));
				SolveWindow.setSize(450,290);
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
						String algo = null;
						String algomsg = null;
						if (btnBFS.getSelection() == true) {
							algomsg = "BFS";
							algo = "bfs";
						}

						else if (btnDFS.getSelection() == true) {
							algomsg = "DFS";
							algo = "dfs";
						}

						MessageBox msg = new MessageBox(SolveWindow, SWT.OK);
						
						
						setChanged();
						notifyObservers("solve" + " " + Mazename + " " + algo);
						Mazename = txtName.getText();
						msg.setMessage("solve");
						msg.setText("solving " + Mazename + " Maze");
						msg.setMessage(algomsg + " for maze " + Mazename + " is ready!");
						msg.open();
						SolveWindow.close();

					
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

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void displayMessage(String message) {
		//msg=message;
System.out.println(message);
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
		/*
		 * MazeDisplay maze=new MazeDisplay(shell, SWT.BORDER);
		 * maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		 * maze.setMazeData(mymaze);
		 */
		mazeDisplayer = new Maze3D(shell, SWT.BORDER);
		mazeDisplayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		mazeDisplayer.setMazeData(mymaze.getCrossSectionByZ(1));

	}

	@Override
	public void displaySolution(Solution sol) {
		// TODO Auto-generated method stub

	}



	@Override
	public void Exit() {
		
	}

	/*
	 * Button saveButton = new Button(shell, SWT.PUSH);
	 * saveButton.setText("Save Maze"); saveButton.setLayoutData(new
	 * GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
	 * //saveButton.setBackground(red);
	 * 
	 * Button loadButton = new Button(shell, SWT.PUSH);
	 * loadButton.setText("load Maze"); loadButton.setLayoutData(new
	 * GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
	 * //loadButton.setBackground(red);
	 * 
	 * Button exitButton = new Button(shell, SWT.PUSH);
	 * exitButton.setText("EXIT"); exitButton.setLayoutData(new
	 * GridData(SWT.FILL,SWT.NONE,false,false, 1, 1));
	 * //exitButton.setBackground(red);
	 * 
	 * exitButton.addSelectionListener(new SelectionListener() {
	 * 
	 * @Override public void widgetSelected(SelectionEvent e) { setChanged();
	 * notifyObservers("exit"); shell.close(); }
	 * 
	 * @Override public void widgetDefaultSelected(SelectionEvent arg0) { } });
	 * 
	 */

}
