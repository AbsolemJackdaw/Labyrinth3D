package game;

import static gameStates.content.Images.walls;

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

public class Maze {

	//returns the image at pos x, y
	public static BufferedImage[][] labyrinthGrid; 

	//returns the type at pos x, y
	public static int[][] numberGrid; 

	/**contains bools where you can go to
	 * up, down, left, right, n-s-w-e*/
	private static boolean[][][] coords;

	/**grid 3x bigger then 2d map to index all walls*/
	public static int[][] numberGrid3D;

	boolean printscreen = true;

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

	/**side value of the maze*/
	private static int mazeSize;
	/**size value of graphic tiles*/
	private static int tileSize;
	/**total map size aka all grids*/
	private static int mapSize;
	/**scale size of the map with graphics*/
	private static int worldSize;

	public Maze(int size, int tSize){
		mazeSize = size;
		tileSize= tSize;
		mapSize = size*size;
		worldSize = mapSize*tSize;
		
		labyrinthGrid = new BufferedImage[size][size];
		numberGrid = new int[size][size];
		coords = new boolean[size][size][];
		numberGrid3D = new int[size*3][size*3];
		
	}

	public Maze(int size) {
		this(size,64);
	}

	public static int getTileSize() {
		return tileSize;
	}
	public static int getWorldSize() {
		return worldSize;
	}
	public static int getMapSize() {
		return mapSize;
	}
	
	public void load(){

		new SwingWorker<Integer, Void>() {

			@Override
			protected Integer doInBackground() {

				//start loading icon

				System.out.println("loading maze. set size is " + mazeSize);
				try {

					for(int y = 0; y < mazeSize; y++)
						for(int x = 0; x < mazeSize; x++)
							numberGrid[x][y] = -1;

					createMazeMap();
					eventualFix();
					gridNumbers();
					gridLabyrinth();
					indexFor3D();

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

	/**grids down the labyrinth to corresponding images*/
	private void gridLabyrinth(){
		for(int y = 0; y < mazeSize; y ++){
			for(int x =0; x < mazeSize;x++){
				//walls array contains one sample of all wall images
				//map array contains the randomized numbers that refer to the images

				labyrinthGrid[x][y] = walls[numberGrid[x][y]];
			}
		}
	}

	/**grids down the labyrinth to index the type of tile*/
	private void gridNumbers(){
		for(int y = 0; y < mazeSize; y ++)
			for(int x =0; x < mazeSize;x++)
				if(getIndex(coords[x][y]) == -1)
					numberGrid[x][y] = 11;
				else
					numberGrid[x][y] = getIndex(coords[x][y]);
	}

	/**returns an index depending on how many passes came trough a tile*/
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

	/**creates a random maze*/
	private void createMazeMap(){
		int[][] cells = new int[mazeSize][mazeSize];
		ArrayList<Integer> dirsTaken = new ArrayList<Integer>();
		ArrayList<Integer> dirs = new ArrayList<Integer>();

		//init cells
		for(int y = 0; y < mazeSize ; y++ )
			for(int x = 0; x < mazeSize; x++){
				cells[x][y] = -1;
				boolean [] bool = new boolean[]{false,false,false,false};
				coords[x][y] = bool;
			}

		//position to start labyrinth generation
		int x=rand.nextInt(mazeSize);
		int y=rand.nextInt(mazeSize);

		int loop = 0;
		coords[x][y] = new boolean[]{true,true,true,true};

		while(loop < mapSize){
			System.out.println("loops done = " +loop);
			//find out possible neighbours
			E = (x+1 < mazeSize && cells[x+1][y] == -1);
			W = (x-1 >= 0 && cells[x-1][y] == -1);
			N = (y-1 >= 0 && cells[x][y-1] == -1);
			S = (y+1 < mazeSize && cells[x][y+1] == -1);

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
				if(loop == mapSize-1){
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

			if(x < 0 || x > mazeSize || y < 0 || y > mazeSize)
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
		for(int y = 0; y < mazeSize; y ++){
			for(int x =0; x <mazeSize;x++){
				int a = getIndex(coords[x][y]);
				if(a == -1){
					System.out.println("coords at  "+ x + " " + y + "were empty");
				}
			}
		}
	}

	/**turns the 2d grid map into an array 3 times bigger to index all walls of 1 tile into a 3x3 tile */
	private void indexFor3D() {

		int indeX = 0;
		int indeY = 0;

		for(int x = 0; x < mazeSize*3; x++){

			for(int y =0; y < mazeSize*3; y++){

				numberGrid3D[x][y] = getGridFromTile(numberGrid[x/3][y/3], indeY, indeX);
		
				indeY++;
				if(indeY>2)
					indeY=0;
			}
			indeX++;
			if(indeX>2)
				indeX=0;
		}

	}

	/**returns a 3x3 grid to transform tiles from*/
	private int getGridFromTile(int id, int x, int y){
		
		int[][] grid0 = {{2,2,2},{0,0,0},{2,2,2}};
		int[][] grid1 = {{2,0,2},{2,0,2},{2,0,2}};
		int[][] grid2 = {{2,2,2},{0,0,0},{2,0,2}};
		int[][] grid3 = {{2,0,2},{0,0,0},{2,2,2}};
		int[][] grid4 = {{2,0,2},{0,0,2},{2,0,2}};
		int[][] grid5 = {{2,0,2},{2,0,0},{2,0,2}};
		int[][] grid6 = {{2,2,2},{2,0,0},{2,0,2}};
		int[][] grid7 = {{2,2,2},{0,0,2},{2,0,2}};
		int[][] grid8 = {{2,0,2},{0,0,2},{2,2,2}};
		int[][] grid9 = {{2,0,2},{2,0,0},{2,2,2}};
		int[][] grid10 = {{2,0,2},{0,0,0},{2,0,2}};
		int[][] grid11 = {{2,2,2},{2,2,2},{2,2,2}};

		if(id == 0)
			return grid0[x][y];
		else if (id == 1)
			return grid1[x][y];
		else if (id == 2)
			return grid2[x][y];
		else if (id == 3)
			return grid3[x][y];
		else if (id == 4)
			return grid4[x][y];
		else if (id == 5)
			return grid5[x][y];
		else if (id == 6)
			return grid6[x][y];
		else if (id == 7)
			return grid7[x][y];
		else if (id == 8)
			return grid8[x][y];
		else if (id == 9)
			return grid9[x][y];
		else if (id == 10)
			return grid10[x][y];
		else if (id == 11)
			return grid11[x][y];

		return 1;
	}

	public int[][] getNumberGrid3D() {
		return numberGrid3D;
	}

	/////////////////////////////BONUS, take pic of maze////////////////////////

	private void draw(Graphics2D g){
		for(int x = 0; x < mazeSize;x++){
			for(int y = 0; y < mazeSize;y++){

				BufferedImage wall= labyrinthGrid[x][y];

				g.drawImage(wall, 
						x*tileSize , 
						y*tileSize , null);

			}
		}
	}

	private void printScreen(){
		Dimension size = new Dimension(mazeSize*tileSize, mazeSize*tileSize);
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
