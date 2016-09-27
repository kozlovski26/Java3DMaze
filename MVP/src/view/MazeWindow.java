package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseWheelListener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
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

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import test.Maze2DDisplay;
import test.MazeDisplay;

public class MazeWindow extends BasicWindow implements View {

	// private MazeDisplayer mazeDisplayer;
	private MazeDisplayer mazeDisplayer;
	private String Mazename;
	private Maze3d maze;
	// private MazeDisplay mazeDisplay;
	MouseWheelListener mouseZoomlListener;
	Clip music;
	Clip sound;
	Image img = new Image(null, "Images/bar.jpg");
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

		// shell.setImage(img);
		shell.setBackgroundImage(img);
		Color red = display.getSystemColor(SWT.COLOR_DARK_RED);

		Composite btnGroup = new Composite(shell, SWT.FILL);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		btnGroup.setBackground(red);

		// Generate maze Button
		Button generateButton = new Button(btnGroup, SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setBackground(red);

		// Display maze solution Button
		Button displayMazeSolutionButton = new Button(btnGroup, SWT.PUSH);
		displayMazeSolutionButton.setText("Display Maze solution");
		displayMazeSolutionButton.setBackground(red);

		// Solve maze Button
		Button solveButton = new Button(btnGroup, SWT.PUSH);
		solveButton.setText("Solve Maze");
		solveButton.setBackground(red);

		// Display Button
		Button DisplayButton = new Button(btnGroup, SWT.PUSH);
		DisplayButton.setText("Display Maze");
		DisplayButton.setBackground(red);

		// Save maze Button
		Button SaveButton = new Button(btnGroup, SWT.PUSH);
		SaveButton.setText("Save Maze");
		SaveButton.setBackground(red);
		SaveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Shell SaveWindow = new Shell(display);
				SaveWindow.setLayout(new GridLayout(2, true));
				SaveWindow.setSize(450, 220);
				SaveWindow.setText("Save");
				Label lblName = new Label(SaveWindow, SWT.NONE);
				lblName.setText("Maze Name: ");
				Text txtName = new Text(SaveWindow, SWT.BORDER);
				lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				Button SaveMaze = new Button(SaveWindow, SWT.PUSH);
				
				SaveMaze.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				SaveMaze.setText("Save Maze");
				// cancel button
				// cancel button
				Button Cancel = new Button(SaveWindow, SWT.PUSH);
				Cancel.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				Cancel.setText("Cancel");
				Cancel.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						SaveWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

				SaveMaze.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						FileDialog dialog = new FileDialog(shell, SWT.SAVE);
					    dialog
					        .setFilterNames(new String[] { "Batch Files", "All Files (*.*)" });
					    dialog.setFilterExtensions(new String[] { "*.maz", "*.*" }); // Windows
					                                    // wild
					                                    // cards
					    dialog.setFilterPath("c:\\"); // Windows path
					    dialog.setFileName("maze.bat");
					    System.out.println("Save to: " + dialog.open());
					

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				
				});	
				SaveWindow.open();
			}
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
				
			});	
			
		
		// Load maze Button
		Button LoadButton = new Button(btnGroup, SWT.PUSH);
		LoadButton.setText("Load Maze");
		LoadButton.setBackground(red);

		// Maze size Button
		Button MazeSizeButton = new Button(btnGroup, SWT.PUSH);
		MazeSizeButton.setText("Maze Size");
		MazeSizeButton.setBackground(red);

		// File size Button
		Button FileSizeButton = new Button(btnGroup, SWT.PUSH);
		FileSizeButton.setText("File Size");
		FileSizeButton.setBackground(red);

		// Exit Button
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
			
		/////////////////////////////// up
		/////////////////////////////// menu/////////////////////////////////////////////////
		Menu menuLine = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuLine);

		MenuItem File = new MenuItem(menuLine, SWT.CASCADE);
		File.setText("File");

		MenuItem Music = new MenuItem(menuLine, SWT.CASCADE);
		Music.setText("Music");

		/////////////////////////////////////////// Drop down functions for file
		/////////////////////////////////////////// button/////////////////////////////////////
		Menu subFileMenu = new Menu(shell, SWT.DROP_DOWN);
		File.setMenu(subFileMenu);
		Menu subMusicMenu = new Menu(shell, SWT.DROP_DOWN);
		Music.setMenu(subMusicMenu);

		MenuItem properties = new MenuItem(subFileMenu, SWT.PUSH);
		properties.setText("Load Properties");

		MenuItem exitButtonmenu = new MenuItem(subFileMenu, SWT.PUSH);
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

		MenuItem OnOffButtons = new MenuItem(subMusicMenu, SWT.PUSH);
		OnOffButtons.setText("Exit");
		OnOffButtons.setText("On/Off \tCtrl+M");
		// OnOffButtons.setSelection(true);
		OnOffButtons.setAccelerator(SWT.CTRL + 'M');
		playMusic(new File("music/sound.wav"));
		music.start();
		OnOffButtons.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (OnOffButtons.getSelection()) {
					music.start();
				} else
					music.stop();
			}

		});

		/////////////////////////////////////// Generate
		/////////////////////////////////////// Button////////////////////////////////////////////////
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

				//////////// just
				//////////// for////////////////////////////////////////////////////////
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
				Cancel.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						GenerateWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
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

				///////////////////////////// display Maze Solution
				///////////////////////////// Button/////////////////////////////////////////////////////////////////////////////////////

				displayMazeSolutionButton.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						setChanged();
					
						notifyObservers("displaysolution" + " " + Mazename);
						// mazeDisplay.setFocus();
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

		////////////////////////////////////// Display
		////////////////////////////////////// Button///////////////////////////////////////////////////

		DisplayButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Shell DisplayWindow = new Shell(display);
				DisplayWindow.setLayout(new GridLayout(2, true));
				DisplayWindow.setSize(450, 220);
				DisplayWindow.setText("Display");
				Label lblName = new Label(DisplayWindow, SWT.NONE);
				lblName.setText("Maze Name: ");
				Text txtName = new Text(DisplayWindow, SWT.BORDER);
				lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				Button DisplayMaze = new Button(DisplayWindow, SWT.PUSH);
				// SolveWindow.setDefaultButton(SolveMaze);
				DisplayMaze.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				DisplayMaze.setText("Display Maze");
				// cancel button
				// cancel button
				Button Cancel = new Button(DisplayWindow, SWT.PUSH);
				Cancel.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				Cancel.setText("Cancel");
				Cancel.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						DisplayWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

				DisplayMaze.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {

						setChanged();
						notifyObservers("display" + " " + Mazename);
						DisplayWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
				DisplayWindow.open();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		////////////////////////////////// Solve
		////////////////////////////////// Button/////////////////////////////////////////////////////////

		solveButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Shell SolveWindow = new Shell(display);
				SolveWindow.setLayout(new GridLayout(2, true));
				SolveWindow.setSize(450, 290);
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
				// cancel button
				Button Cancel = new Button(SolveWindow, SWT.PUSH);
				Cancel.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1));
				Cancel.setText("Cancel");
				Cancel.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						SolveWindow.close();

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
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
		// msg=message;
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
		mazeDisplayer.setCurrentMaze(mymaze);

		// maze2DDisplay.setMazeName(tempMazeName);
		mazeDisplayer.redraw();

		/*
		 * this.maze=mymaze; Position pos = mymaze.getStartPosition(); int[][]
		 * mazeData = mymaze.getCrossSectionByZ(2);
		 * mazeDisplay.setCharacterPosition(pos);
		 * mazeDisplay.setMazeData(mazeData,mymaze); mazeDisplay.redraw();
		 */
	}

	@Override
	public void displaySolution(Solution sol) {
		// TODO Auto-generated method stub

	}

	
	
	private void playMusic(File file) {
		try {
			music = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			music.open(inputStream);
			// loop infinitely
			music.setLoopPoints(0, -1);
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	

		}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}
}