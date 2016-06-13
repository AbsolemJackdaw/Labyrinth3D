package labyrinth3D.game.gen;

import static labyrinth3D.rscMngr.ImageLoader.walls;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import labyrinth3D.utility.Maze2DScreenShot;

public class GenMaze {

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

	/**size that was originally set with border fix*/
	private int mazeSizeFixed;
	/**size that was originally set with border fix*/
	private int mapSizeFixed;

	/**size that was originally set with border fix*/
	private int worldSizeFixed;

	/**side value of the maze*/
	private int mazeSize;
	/**total map size aka all grids*/
	private int mapSize;
	/**scale size of the map with graphics*/
	private int worldSize;

	/**size value of graphic tiles*/
	private static int tileSize;

	public GenMaze(int size, int tSize){

		int sizeFix = size + 2;

		tileSize = tSize;

		mazeSizeFixed = sizeFix;
		mapSizeFixed = sizeFix*sizeFix;
		worldSizeFixed = sizeFix*tSize;

		mazeSize = size;
		mapSize = size*size;
		worldSize = mapSize*tSize;

		labyrinthGrid = new BufferedImage[sizeFix][sizeFix];
		numberGrid = new int[sizeFix][sizeFix];
		coords = new boolean[sizeFix][sizeFix][];
		numberGrid3D = new int[sizeFix*3][sizeFix*3];

	}

	public GenMaze(int size) {
		this(size,64);
	}

	public int getTileSize() {
		return tileSize;
	}
	public int getWorldSize() {
		return worldSize;
	}
	public int getMapSize() {
		return mapSize;
	}

	public int getMapSizeFixed() {
		return mapSizeFixed;
	}

	public int getMazeSize() {
		return mazeSize;
	}

	public int getMazeSizeFixed() {
		return mazeSizeFixed;
	}

	public int getWorldSizeFixed() {
		return worldSizeFixed;
	}

	public void load(){

		System.out.println("loading maze. set size is " + mazeSize);

		for(int y = 0; y < mazeSizeFixed; y++)
			for(int x = 0; x < mazeSizeFixed; x++)
				numberGrid[x][y] = -1;

		createMazeMap();
		eventualFix();
		gridNumbers();
		gridLabyrinth();
		indexFor3D();

		if(printscreen)
			printScreen();
	}

	/**grids down the labyrinth to corresponding images*/
	private void gridLabyrinth(){
		for(int y = 0; y < mazeSizeFixed; y ++){
			for(int x =0; x < mazeSizeFixed;x++){
				//walls array contains one sample of all wall images
				//map array contains the randomized numbers that refer to the images

				labyrinthGrid[x][y] = walls[numberGrid[x][y]];
			}
		}
	}

	/**grids down the labyrinth to index the type of tile*/
	private void gridNumbers(){
		for(int y = 0; y < mazeSizeFixed; y ++)
			for(int x =0; x < mazeSizeFixed;x++)
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

		//cells with value -1 or 0 to check wether or not we passed here
		int[][] cells = new int[mazeSizeFixed][mazeSizeFixed];

		//arrays keeping track of where we went
		ArrayList<Integer> dirsTaken = new ArrayList<Integer>();
		ArrayList<Integer> dirs = new ArrayList<Integer>();

		//init cells : fills all cells with -1 flag and booleans for full blocks.
		for(int y = 0; y < mazeSizeFixed ; y++ )
			for(int x = 0; x < mazeSizeFixed; x++){
				cells[x][y] = -1;
				boolean [] bool = new boolean[]{false,false,false,false};
				coords[x][y] = bool;
			}

		//		for(int loop = 0; loop < mazeSizeFixed ; loop++ ){
		//			cells[0][loop] = 0;
		//			cells[loop][0] = 0;
		//			cells[mazeSizeFixed-1][loop] = 0;
		//			cells[loop][mazeSizeFixed-1] = 0;
		//
		//		}

		//position to start labyrinth generation
		int x=rand.nextInt(mazeSize)+1;
		int y=rand.nextInt(mazeSize)+1;

		int loop = 0;
		coords[x][y] = new boolean[]{true,true,true,true};

		while(loop < mapSize){
			System.out.println("loops done = " +loop);
			//find out possible neighbours
			E = (x+1 <= mazeSize && cells[x+1][y] == -1);
			W = (x-1 >= 1 && cells[x-1][y] == -1);
			N = (y-1 >= 1 && cells[x][y-1] == -1);
			S = (y+1 <= mazeSize && cells[x][y+1] == -1);

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
		for(int y = 0; y < mazeSizeFixed; y ++){
			for(int x =0; x <mazeSizeFixed;x++){
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

		for(int x = 0; x < mazeSizeFixed*3; x++){

			for(int y =0; y < mazeSizeFixed*3; y++){

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

	private int i() {
		return rand.nextInt(5)+1;
	}

	/**returns a 3x3 grid to transform tiles from*/
	private int getGridFromTile(int id, int x, int y){

		int[][] grid0 = {{i(),i(),i()},{0,0,0},{i(),i(),i()}};
		int[][] grid1 = {{i(),0,i()},{i(),0,i()},{i(),0,i()}};
		int[][] grid2 = {{i(),i(),i()},{0,0,0},{i(),0,i()}};
		int[][] grid3 = {{i(),0,i()},{0,0,0},{i(),i(),i()}};
		int[][] grid4 = {{i(),0,i()},{0,0,i()},{i(),0,i()}};
		int[][] grid5 = {{i(),0,i()},{i(),0,0},{i(),0,i()}};
		int[][] grid6 = {{i(),i(),i()},{i(),0,0},{i(),0,i()}};
		int[][] grid7 = {{i(),i(),i()},{0,0,i()},{i(),0,i()}};
		int[][] grid8 = {{i(),0,i()},{0,0,i()},{i(),i(),i()}};
		int[][] grid9 = {{i(),0,i()},{i(),0,0},{i(),i(),i()}};
		int[][] grid10 = {{i(),0,i()},{0,0,0},{i(),0,i()}};
		int[][] grid11 = {{i(),i(),i()},{i(),i(),i()},{i(),i(),i()}};

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
	private void printScreen() {
		new Maze2DScreenShot(mazeSizeFixed, labyrinthGrid, tileSize);
	}
}
