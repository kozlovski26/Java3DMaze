package view;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class Maze3D extends MazeDisplayer {
	public Maze3d currentMaze;
	/** The character. */
	public Position character = new Position(1, 1, 1);

	/** The goal. */
	public Position goal = new Position(1, 1, 1);
	public Character car;
	public int exitX=0;
	public int exitY=2;
	/** The my image. */
	Image myImage = new Image(getShell().getDisplay(), "images\\Wall.png");

	/** The my image2. */
	Image myImage2 = new Image(getShell().getDisplay(), "images\\golden.PNG");

	/** The paint goal. */
	private GameExit paintGoal = new GameExit();

	/** The background image. */
	Image backgroundImage = new Image(getShell().getDisplay(), "images\\golden2.png");

	/** The timer. */
	private Timer timer;

	/** The task. */
	private TimerTask task;
	
	//Image img = new Image (null, "Images/100.png");
	private void paintCube(double[] p,double h,PaintEvent e){
        int[] f=new int[p.length];
        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
        
        e.gc.drawPolygon(f);
        
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        

        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
        e.gc.drawPolygon(b);
        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
        e.gc.drawPolygon(fr);
        
        e.gc.fillPolygon(r);
		
	}

	public Maze3D(Composite parent, int style) {
		super(parent, style);
		final Color white=new Color(null, 255, 255, 255);
		final Color black=new Color(null, 150,150,150);
		setBackground(white);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null)
					return;
					e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));

				   int width=getSize().x;
				   int height=getSize().y;
				   
				   int mx=width/2;

				   double w=(double)width/mazeData[0].length;
				   double h=(double)height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++){
					   double w0=0.7*w +0.3*w*i/mazeData.length;
					   double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
					   double start=mx-w0*mazeData[i].length/2;
					   double start1=mx-w1*mazeData[i].length/2;
				      for(int j=0;j<mazeData[i].length;j++){
				          double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
				          double cheight=h/2;
				          if(mazeData[i][j]!=0)
				        	  paintCube(dpoints, cheight,e);
							// draw the character image when he exit

						if (i == goal.getX() && j == goal.getY()
									&& character.getX() == goal.getX()) {
								e.gc.drawImage(myImage2, 0, 0, 360, 360, (int) Math.round(dpoints[0] + 2),
										(int) Math.round(dpoints[1] - cheight / 2 + 2),
										(int) Math.round((w0 + w1) / 2 / 1.5), (int) Math.round(h / 1.5));
							}
							// draw the character image when he moving
							if (i == character.getX() && j == character.getY()) {
								e.gc.drawImage(myImage, 0, 0, 360, 360, (int) Math.round(dpoints[0] + 2),
										(int) Math.round(dpoints[1] - cheight / 2 + 2),
										(int) Math.round((w0 + w1) / 2 / 1.5), (int) Math.round(h / 1.5));
							}

							
							// draw the target image in the goal position
							if (i == currentMaze.getRows() && j == currentMaze.getColumns()
									&& character.getZ() == currentMaze.getFloors()) {
								paintGoal.draw(e, (int) Math.round(dpoints[0] + 2) + 10,
										(int) Math.round(dpoints[1] - cheight / 2 + 2),
										(int) Math.round((w0 + w1) / 3.5), (int) Math.round(cheight / 2));

							}
							else {
								e.gc.drawImage(backgroundImage, 50, 50);
						}

					
				}
			} 
			
		}
	});
}
	
	private void moveCharacter(int x,int y){
		if(x>=0 && x<mazeData[0].length && y>=0 && y<mazeData.length && mazeData[y][x]==0){
	
			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveUp()
	 */
	@Override
	public void moveUp() {
		Position pos = getCharacter();
		Position tempPos = new Position(pos.getX(), pos.getY(), pos.getZ() + 1);
		if (currentMaze.isCellInBound(tempPos)
				&& currentMaze.getMaze_matrix()[pos.getX()][pos.getY()][pos.getZ() + 1] == currentMaze.getPass()) {
			if (currentMaze.getCrossSectionByZ(pos.getZ() + 1)[pos.getX()][pos.getY()] == 1) {
				return;
			}
			// moveCharacter(pos.getX(), pos.getY(), pos.getFloors()+1);
			mazeData = currentMaze.getCrossSectionByZ(pos.getZ() + 1);
			setCharacterPosition(tempPos);
			/*
			 * redraw(); update(); getShell().update(); getDisplay().update();
			 * layout();
			 */
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					redraw();
					getShell().update();
					getDisplay().update();

				}
			});
		}
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveDown()
	 */
	@Override
	public void moveDown() {
		Position pos = getCharacter();
		Position tempPos = new Position(pos.getX(), pos.getY(), pos.getZ() - 1);
		if (currentMaze.isCellInBound(tempPos)
				&& currentMaze.getMaze_matrix()[pos.getX()][pos.getY()][pos.getZ() - 1] == currentMaze.getPass()) {
			if (currentMaze.getCrossSectionByZ(pos.getZ() - 1)[pos.getX()][pos.getY()] == 1) {
				return;
			}
			try {
				// moveCharacter(pos.getX(), pos.getY(),
				// pos.getFloors()-1);
				mazeData = currentMaze.getCrossSectionByZ(pos.getZ() - 1);
				setCharacterPosition(tempPos);

				/*
				 * redraw(); update(); getShell().update();
				 * getDisplay().update(); layout();
				 */
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						redraw();
						getShell().update();
						getDisplay().update();

					}
				});

			} catch (Exception e) {
				System.out.println("not good");
			}

		}
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveLeft()
	 */
	@Override
	public void moveLeft() {
		Position pos = getCharacter();
		Position tempPos = new Position(pos.getX(), pos.getY() - 1, pos.getZ());
		if (currentMaze.isCellInBound(tempPos)
				&& currentMaze.getMaze_matrix()[pos.getX()][pos.getY() - 1][pos.getZ()] == currentMaze.getPass()) {
			moveCharacter(pos.getX(), pos.getY() - 1, pos.getZ());
			redraw();
			update();
			layout();
		}
	}
	/* (non-Javadoc)
	 * @see view.MazeDisplayer#moveRight()
	 */
	@Override
	public void moveRight() {
		Position pos = getCharacter();
		Position tempPos = new Position(pos.getX(), pos.getY() + 1, pos.getZ());
		if (currentMaze.isCellInBound(tempPos)
				&& currentMaze.getMaze_matrix()[pos.getX()][pos.getY() + 1][pos.getZ()] == currentMaze.getPass()) {
			moveCharacter(pos.getX(), pos.getY() + 1, pos.getZ());
			redraw();
			update();
			layout();
		}
	}
	
	
	private boolean moveCharacter(int x, int y, int z) {
		// Position pos = new Position(x, y, z);
		if ((x >= 0 && x < currentMaze.getRows()) && (y >= 0 && y < currentMaze.getColumns())
				&& (z >= 0 && z < currentMaze.getFloors())) {
			// if (pos == 0){
			character.setX(x);
			;
			character.setY(y);
			;
			character.setZ(z);
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					redraw();
					getShell().update();
					getDisplay().update();

				}
			});
			return true;
			// }
		}
		return false;
	}
	
	@Override
	public Position getCharacter() {
		return character;
	}

	@Override
	public void setCurrentMaze(Maze3d m) {
		currentMaze = m;
		mazeData = currentMaze.getCrossSectionByZ(currentMaze.getStartPosition().getZ());
		setCharacterPosition(currentMaze.getStartPosition());
		goal = currentMaze.getGoalPosition();
		paintGoal.setPos(currentMaze.getGoalPosition());
		
	}

	@Override
	public Maze3d getCurrentMaze() {
		return currentMaze;
	}

	public void setCharacter(Position character) {
		this.character = character;
	}

	@Override
	public void moveForward() {
		Position pos = getCharacter();
		Position tempPos = new Position(pos.getX()+1, pos.getY(), pos.getZ());
		if (currentMaze.isCellInBound(tempPos)
				&& currentMaze.getMaze_matrix()[pos.getX()+1][pos.getY()][pos.getZ()] == currentMaze.getPass()) {
			moveCharacter(pos.getX()+1, pos.getY(), pos.getZ());
			redraw();
			update();
			layout();
	}
		
	}

	@Override
	public void moveBackward() {
		Position pos = getCharacter();
		Position tempPos = new Position(pos.getX()-1, pos.getY(), pos.getZ());
		if (currentMaze.isCellInBound(tempPos)
				&& currentMaze.getMaze_matrix()[pos.getX()-1][pos.getY() ][pos.getZ()] == currentMaze.getPass()) {
			moveCharacter(pos.getX()-1, pos.getY(), pos.getZ());
			redraw();
			update();
			layout();
	}
	}

	@Override
	public void walkToExit(Solution solution) {
		Boolean solq = false;
		Collections.reverse(solution.getStates());
		timer = new Timer();
		task = new TimerTask() {
			int i = solution.getStates().size() - 1;
			Position podd = new Position(1, 1, 1);
			String s = solution.getStates().get(i).getState();
			String[] parts = s.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int z = Integer.parseInt(parts[2]);
			Position position = new Position(x, y, z);

			// setCharacterPosition(position);
			@Override
			public void run() {

				// i = solution.getStates().size() -1;
				s = solution.getStates().get(i).getState();
				parts = s.split(",");
				x = Integer.parseInt(parts[0]);
				y = Integer.parseInt(parts[1]);
				z = Integer.parseInt(parts[2]);
				position = new Position(x, y, z);
				// setCharacterPosition(position);
				if (i >= 0) {
					podd = new Position(x, y, z);
					mazeData = currentMaze.getCrossSectionByZ(z);
					setCharacterPosition(podd);

					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							redraw();
							update();
							layout();
							if (podd.getX() == goal.getX() && podd.getY() == goal.getY()
									&& podd.getZ() == goal.getZ()) {

								MessageBox messageBox = new MessageBox(getShell());
								messageBox.setMessage("you won");
								messageBox.open();
							}
						}
					});

					// new Thread(new Runnable() {
					// public void run() {
					// while (true) {
					// try { Thread.sleep(1000); } catch (Exception e) { }
					//
					// update();
					// layout();
					//
					// }
					// });
					// }
					// }
					// }).start();

					// setCharacterPosition(position);
					i--;
				} else {
					timer.cancel();
					timer.purge();

				}
			}
		};
		// timer.scheduleAtFixedRate(task, 0, 100);
		timer.scheduleAtFixedRate(task, 0, 500);
		// timer.purge();
		// redraw();
		// update();
		// layout();
/*
		MessageBox messageBox = new MessageBox(getShell());
		messageBox.setMessage("you won");
		messageBox.open();
*/
	}
		
	
	@Override
	public void walkByHint(Solution solution) {
		Collections.reverse(solution.getStates());
		int i = solution.getStates().size() - 1;
		String s = solution.getStates().get(i).getState();
		String[] parts = s.split(",");

		int x = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		int z = Integer.parseInt(parts[3]);
		Position position = new Position(x, y, z);
		setCharacterPosition(position);
		
	}

	@Override
	public void setCharacterPosition(Position pos) {
		mazeData = currentMaze.getCrossSectionByZ(pos.getZ());
		moveCharacter(pos.getX(), pos.getY(), pos.getZ());
		
	}

}
