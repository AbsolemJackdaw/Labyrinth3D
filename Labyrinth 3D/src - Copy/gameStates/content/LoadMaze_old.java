package gameStates.content;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import static gameStates.content.Images.*;

public class LoadMaze_old {
	//returns the image at pos x, y
	public static BufferedImage labyrinthGrid[][] = new BufferedImage[Map_old.mapSizeX][Map_old.mapSizeY]; 

	//returns the type at pos x, y
	public static int numberGrid[][] = new int[Map_old.mapSizeX][Map_old.mapSizeY]; 

	/**contains bools where you can go to
	 * up, down, left, right, n-s-w-e*/
	private static boolean[][][] coords = new boolean[Map_old.mapSizeX][Map_old.mapSizeY][];

	boolean printscreen = false;

	//N W S E
	final int UP = 0;
	final int DOWN = 1;
	final int LEFT = 2;
	final int RIGHT = 3;

	boolean N;
	boolean S;
	boolean W;
	boolean E;

	static Random rand = new Random();
	
	public void load(){

		new SwingWorker<Integer, Void>() {

			@Override
			protected Integer doInBackground() {

				//start loading icon

				try {

					for(int y = 0; y < Map_old.mapSizeY; y++)
						for(int x = 0; x < Map_old.mapSizeX; x++)
							numberGrid[x][y] = -1;

					createRecursiveMap();
					eventualFix();
					gridNumbers();
					gridLabyrinth();

					if(printscreen)
						printScreen();

				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void done() {
				super.done();
				//stop loading icon

				//switch gamestate
			}
		}.execute();

	}

	private void gridLabyrinth(){
		for(int y = 0; y < Map_old.mapSizeY; y ++){
			for(int x =0; x < Map_old.mapSizeX;x++){
				//walls array contains one sample of all wall images
				//map array contains the randomized numbers that refer to the images

				labyrinthGrid[x][y] = walls[numberGrid[x][y]];
			}
		}
	}

	private void gridNumbers(){
		for(int y = 0; y < Map_old.mapSizeY; y ++)
			for(int x =0; x < Map_old.mapSizeX;x++)
				if(getIndex(coords[x][y]) == -1)
					numberGrid[x][y] = 11;
				else
					numberGrid[x][y] = getIndex(coords[x][y]);
	}

	private int getIndex(boolean[] bs) {
		if(bs == null){
			System.out.println("boolean array was null ! exiting ...");
			System.exit(0);
		}

		if(bs[UP] && bs[LEFT] && bs[DOWN] && bs[RIGHT])
			return 10;
		else if(!bs[UP] && !bs[DOWN] && bs[LEFT] && bs[RIGHT])
			return 0;
		else if(bs[UP] && bs[DOWN] && !bs[LEFT] && !bs[RIGHT])
			return 1;
		else if(!bs[UP] && bs[DOWN] && bs[LEFT] && bs[RIGHT])
			return 2;
		else if(bs[UP] && !bs[DOWN] && bs[LEFT] && bs[RIGHT])
			return 3;
		else if(bs[UP] && bs[DOWN] && bs[LEFT] && !bs[RIGHT])
			return 4;
		else if(bs[UP] && bs[DOWN] && !bs[LEFT] && bs[RIGHT])
			return 5;
		else if(!bs[UP] && bs[DOWN] && !bs[LEFT] && bs[RIGHT])
			return 6;
		else if(!bs[UP] && bs[DOWN] && bs[LEFT] && !bs[RIGHT])
			return 7;
		else if(bs[UP] && !bs[DOWN] && bs[LEFT] && !bs[RIGHT])
			return 8;
		else if(bs[UP] && !bs[DOWN] && !bs[LEFT] && bs[RIGHT])
			return 9;

		//exceptions
		else if(bs[UP] && !bs[LEFT] && !bs[DOWN] && !bs[RIGHT])
			return 1;
		else if(!bs[UP] && !bs[LEFT] && bs[DOWN] && !bs[RIGHT])
			return 1;
		else if(!bs[UP] && bs[LEFT] && !bs[DOWN] && !bs[RIGHT])
			return 0;
		else if(!bs[UP] && !bs[LEFT] && !bs[DOWN] && bs[RIGHT])
			return 0;

		return -1;
	}

	// called in game after a tile has been changed
	//unused so far
	public  void redrawMap(){
		gridLabyrinth();
	}

	private void createRecursiveMap(){
		int[][] cells = new int[Map_old.mapSizeX][Map_old.mapSizeY];
		ArrayList<Integer> dirsTaken = new ArrayList<Integer>();
		ArrayList<Integer> dirs = new ArrayList<Integer>();

		//init cells
		for(int y = 0; y < Map_old.mapSizeY ; y++ )
			for(int x = 0; x < Map_old.mapSizeX; x++){
				cells[x][y] = -1;
				boolean [] bool = new boolean[]{false,false,false,false};
				coords[x][y] = bool;
			}

		//position to start labyrinth generation
		int x=rand.nextInt(Map_old.mapSizeX);
		int y=rand.nextInt(Map_old.mapSizeY);

		int loop = 0;
		coords[x][y] = new boolean[]{true,true,true,true};

		while(loop < Map_old.mapSize){
			System.out.println("loops done = " +loop);
			//find out possible neighbours
			E = (x+1 < Map_old.mapSizeX && cells[x+1][y] == -1);
			W = (x-1 >= 0 && cells[x-1][y] == -1);
			N = (y-1 >= 0 && cells[x][y-1] == -1);
			S = (y+1 < Map_old.mapSizeY && cells[x][y+1] == -1);

			//if no possible neighbor is found
			if(!N && !W && !S && !E){
				System.out.println("no possible neighbour ! "+ x +" "+y);

				if(dirsTaken.size() -1 < 0){
					System.out.println("broke loop, no more steps possible back");
					break;
				}
				//one step back
				int prevStep = dirsTaken.get(dirsTaken.size() -1);

				//previous step taken must be inverted to go back
				switch (prevStep) {
				case UP:
					y++;
					break;
				case DOWN:
					y--;
					break;
				case LEFT:
					x++;
					break;
				case RIGHT:
					x--;
					break;
				default:
					throw new IllegalArgumentException("wrong direction " + prevStep) ;
				}
				//this means all cases are full !
				if(loop == Map_old.mapSize-1){
					System.out.println("broke loop, end of mapcreation");
					break;
				}

				//remove last step from list
				dirsTaken.remove(dirsTaken.size() -1);
				//skip this iteration, and rerun it with the old coordinates(newly set):
				continue;
			}

			//register possible neighbors
			if(E)dirs.add(RIGHT);
			if(W)dirs.add(LEFT);
			if(N)dirs.add(UP);
			if(S)dirs.add(DOWN);

			//pick random neighbor
			int rand = new Random().nextInt(dirs.size());

			int id = dirs.get(rand);

			int prevX = x;
			int prevY = y;

			switch (id) {
			case UP:
				dirsTaken.add(UP);
				y--;
				break;
			case DOWN:
				dirsTaken.add(DOWN);
				y++;
				break;
			case LEFT:
				dirsTaken.add(LEFT);
				x--;
				break;
			case RIGHT:
				dirsTaken.add(RIGHT);
				x++;
				break;
			default:
				throw new IllegalArgumentException("wrong direction " + id) ;
			}

			if(x < 0 || x > Map_old.mapSizeX || y < 0 || y > Map_old.mapSizeY)
				throw new IllegalArgumentException("out of bounds ! x=" + x + " y=" + y) ;

			//add boolean to connect previous piece
			//i was created from previous coord
			boolean[] bool = new boolean[]{false,false,false,false};
			bool[inverse(id)] = true;
			coords[x][y] = bool;
			cells[x][y] = 0;

			//add boolean to connect the new created piece
			//i created a new piece to ? coord
			boolean[] bool2 = coords[prevX][prevY];
			bool2[id] = true;
			coords[prevX][prevY] = bool2;

			dirs.clear();
			loop++;
		}

	}

	private int inverse(int i){
		return (i == UP ? DOWN : i == LEFT ? RIGHT : i == DOWN ? UP : i == RIGHT ? LEFT : -1);
	}

	/**Doesnt actually run a fix, this happens in gridNumbers()
	 * just checks if there was an empty spot*/
	private void eventualFix(){
		System.out.println("running eventuel fix");
		//last checkup
		for(int y = 0; y < Map_old.mapSizeY; y ++){
			for(int x =0; x < Map_old.mapSizeX;x++){
				int a = getIndex(coords[x][y]);
				if(a == -1){
					System.out.println("coords at  "+ x + " " + y + "were empty");
				}
			}
		}
	}


	/////////////////////////////BONUS, take pic of maze////////////////////////

	private void draw(Graphics2D g){
		for(int x = 0; x < Map_old.mapSizeX;x++){
			for(int y = 0; y < Map_old.mapSizeY;y++){

				BufferedImage wall= labyrinthGrid[x][y];

				g.drawImage(wall, 
						x*Map_old.tileSize , 
						y*Map_old.tileSize , null);

			}
		}
	}

	private void printScreen(){
		Dimension size = new Dimension(Map_old.worldSizeX, Map_old.worldSizeY);
		BufferedImage img = new BufferedImage (size.width, size.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = img.getGraphics ();
		draw((Graphics2D) g);
		g.dispose ();
		try
		{
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minutes = cal.get(Calendar.MINUTE);
			int seconds = cal.get(Calendar.SECOND);

			String s = String.valueOf(year)+ "y" +
					String.valueOf(month+1) + "m" +
					String.valueOf(day) + "d" +
					String.valueOf(hour) + "h" + 
					String.valueOf(minutes) + "m" + 
					String.valueOf(seconds)+ "s.png";

			String dir = "End Results";
			File f = new File(dir);
			if(!f.exists())
				f.mkdir();

			ImageIO.write (img, "png", new File(dir + File.separator + s));

			System.out.println("printed screen");
		}
		catch (IOException ex)
		{
			ex.printStackTrace ();
			System.out.println("printing screen failed");
		}
	}
}
