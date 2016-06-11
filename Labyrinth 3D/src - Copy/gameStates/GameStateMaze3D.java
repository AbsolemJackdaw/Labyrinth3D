package gameStates;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.gameBase.GamePanel;
import engine.gameBase.GameState;
import engine.gameBase.GameStateHandler;
import game.Maze;
import game.elements.hud.MiniMap;
import gameStates.content.Images;
import gameStates.maze.Camera;
import gameStates.maze.Screen;
import gameStates.maze.Texture;

public class GameStateMaze3D extends GameState {

	public int mapWidth = 21;
	public int mapHeight = 21;

	public ArrayList<Texture> textures;
	
	public Camera camera;
	public Screen screen;
	
	private MiniMap minimap;
	
	public static int[][] map;
//	= 
//		{
//		{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
//		{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
//		{1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
//		{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
//		{1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
//		{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
//		{1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
//		{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
//		{1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
//		{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
//		{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
//		{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
//		{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
//		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
//		{1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
//		};
	
	public GameStateMaze3D(GameStateHandler gsh) {
		this.gsh = gsh;
		
		Maze maze = new Maze(10);
		maze.load();
		map = maze.getNumberGrid3D();
		
		minimap = new MiniMap();
		
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		camera = new Camera(1.5, 1.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, 1024, 576);
		
	}
	
	@Override
	public void update() {
		super.update();
		
		screen.update(camera, GamePanel.getScreenPixels());
		camera.update(map);
		
		if(Camera.isPressed(Camera.Enter)){
			gsh.changeGameState(GameStateHandler.MENU);
		}
		
		minimap.update(camera);
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		minimap.draw(g);
		
		g.drawImage(Images.hud, 0, 0, GamePanel.W, GamePanel.H, null);
	}
}
