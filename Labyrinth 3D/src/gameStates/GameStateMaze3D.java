package gameStates;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import engine.gameBase.GamePanel;
import engine.gameBase.GameState;
import engine.gameBase.GameStateHandler;
import game.Maze;
import game.elements.hud.MiniMap;
import game.entity.Entity;
import game.entity.Portal.Portal;
import game.entity.collectables.EntityCollectible;
import game.player.Card;
import game.player.PlayerData;
import gameStates.content.Images;
import gameStates.maze.Camera;
import gameStates.maze.Screen;

public class GameStateMaze3D extends GameState {

	public int mapWidth = 3;
	public int mapHeight = 24;


	public Camera camera;
	public Screen screen;

	private MiniMap minimap;

	public static int[][] map;

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	Maze maze;

	Random rand = new Random();

	int totalCollectables = 0;

	public GameStateMaze3D(GameStateHandler gsh, int mazeSize, int collectables) {
		this.gsh = gsh;

		maze = new Maze(mazeSize);
		maze.load();

		map = maze.getNumberGrid3D();

		totalCollectables = collectables;

		addCollectables(collectables);
		generatePortal();

		minimap = new MiniMap();

		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, 1024, 576);

	}

	private int cardPickup = 0; 

	@Override
	public void update() {
		super.update();

		camera.update(map);

		screen.updateWalls(camera, GamePanel.getScreenPixels(),entities);

		if(Camera.isPressed(Camera.T)){
			gsh.changeGameState(GameStateHandler.ISLAND);
		}
		for(Entity e : entities) {
			if(camera.xPos > e.worldPositionX-0.5 && camera.xPos < e.worldPositionX+0.5 ){
				if(camera.yPos > e.worldPositionY-0.5 && camera.yPos < e.worldPositionY+0.5 ){
					if(e instanceof EntityCollectible) {
						System.out.println("pick up ! ");
						PlayerData.currentlyCollectedCards.add(new Card(e.getID()).setPickedOrder(cardPickup));
						cardPickup++;
						entities.remove(e);
						break;
					}
				}
			}
			if(e instanceof Portal) {
				Portal portal = (Portal)e;
				portal.update();

				if(PlayerData.currentlyCollectedCards.size() >= totalCollectables)
					portal.setActive(true);

				if(portal.isActive()) {
					if(camera.xPos > e.worldPositionX-0.5 && camera.xPos < e.worldPositionX+0.5 ){
						if(camera.yPos > e.worldPositionY-0.5 && camera.yPos < e.worldPositionY+0.5 ){

							for(Card c : PlayerData.currentlyCollectedCards)
								PlayerData.cards.add(c);
							PlayerData.currentlyCollectedCards.clear();

							gsh.changeGameState(GameStateHandler.ISLAND);
						}
					}
				}
			}
		}

		minimap.update(camera);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

		if(Camera.isPressed(Camera.Map))
			minimap.draw(g);

		g.drawImage(Images.hud, 0, 0, GamePanel.W, GamePanel.H, null);

		if(!PlayerData.currentlyCollectedCards.isEmpty())
			for(Card card : PlayerData.currentlyCollectedCards) {
				card.draw(g);
			}
	}

	public void addCollectables(int loops) {
		Entity e = null;

		int turns = loops;
		int collectibleID = 0;

		while (turns > 0) {

			System.out.println(turns);

			int x = rand.nextInt(maze.getMazeSizeFixed()*3);
			int y = rand.nextInt(maze.getMazeSizeFixed()*3);

			if(map[x][y] == 0) {
				if(entities.isEmpty()) {

					e = new EntityCollectible(32, collectibleID).setFirstPosition(x+.5, y+.5);
					entities.add(e);
					turns--;
					collectibleID++;
					System.out.println("added entity " + e.getID() + " at " + e.worldPositionX+" "+e.worldPositionY  );

					continue;

				}else {
					boolean flag = false;
					for(Entity ent : entities) {
						if(ent.getWorldPositionX() != x+.5 && ent.getWorldPositionY() != y+.5) {
							flag = true;
						}else
							flag = false;
					}

					if(flag) {
						e = new EntityCollectible(32, collectibleID).setFirstPosition(x+.5, y+.5);
						entities.add(e);
						turns--;
						collectibleID++;
						System.out.println("added entity " + e.getID() + " at " + e.worldPositionX+" "+e.worldPositionY  );
						continue;
					}
				}
			}
		}
	}


	public void generatePortal() {

		Entity e = null;


		while (e == null) {

			int x = rand.nextInt(maze.getMazeSizeFixed()*3);
			int y = rand.nextInt(maze.getMazeSizeFixed()*3);

			if(map[x][y] == 0) {

				boolean flag = false;

				for(Entity ent : entities) {
					if(ent.getWorldPositionX() != x+.5 && ent.getWorldPositionY() != y+.5) {
						flag = true;
					}else
						flag = false;
				}

				if(flag) {
					e = new Portal(0, 0).setFirstPosition(x+.5, y+.5);
					entities.add(e);
					System.out.println("added portal" + " at " + e.worldPositionX+" "+e.worldPositionY  );
					break;
				}
			}
		}
	}









	public int[][] testMap(){
		return new int[][] {
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
				{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		};
	}
}
