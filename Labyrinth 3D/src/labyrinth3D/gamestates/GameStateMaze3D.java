package labyrinth3D.gamestates;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.engine.KeyHandler;
import labyrinth3D.game.entity.Entity;
import labyrinth3D.game.entity.collectables.Card;
import labyrinth3D.game.entity.collectables.EntityCollectible;
import labyrinth3D.game.entity.collectables.EntityKey;
import labyrinth3D.game.entity.collectables.EntityWeapon;
import labyrinth3D.game.entity.portal.Portal;
import labyrinth3D.game.gen.GenMaze;
import labyrinth3D.game.hud.HUD;
import labyrinth3D.game.hud.MiniMap;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.gamestates.maze3D.Camera;
import labyrinth3D.gamestates.maze3D.Screen;


public class GameStateMaze3D extends GameState {

	public int mapWidth = 3;
	public int mapHeight = 24;


	public Camera camera;
	public Screen screen;

	private MiniMap minimap;

	public static int[][] map;

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> enemies = new ArrayList<Entity>();

	GenMaze maze;

	Random rand = new Random();

	int totalCollectables = 0;

	HUD hud;
	
	public boolean attack;
	public int attackTime = 0;
	
	public GameStateMaze3D(GameStateHandler gsh, int mazeSize, int collectables) {
		this.gsh = gsh;

		maze = new GenMaze(mazeSize);
		maze.load();

		map = maze.getNumberGrid3D();

		totalCollectables = collectables;

		addCollectables(collectables);
		generatePortal();

		hud = new HUD(this);
		
		minimap = hud.getMinimap();

		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, 1024, 576);

	}

	private int cardPickup = 0; 

	@Override
	public void update() {
		super.update();

		camera.update(map);

		screen.updateWalls(camera, GamePanel.getScreenPixels(),entities);

//		System.out.println(camera.xDir + " " + camera.yDir);
		
		if(Camera.isPressed(Camera.T)){
			gsh.changeGameState(GameStateHandler.ISLAND);
		}
		
		if(KeyHandler.isPressed(KeyHandler.SPACE) && attackTime <= 0) {
			attack = true;
			attackTime = 120;
			//detect near enemies
		}
		
		if(attackTime > 0)
			attackTime-= PlayerData.attackSpeed;

		if(attackTime <= 0 && attack)
			attack = false;
		
		updateEntities();

		minimap.update(camera);
	}

	public void attackenemy(){
		for(Entity e : enemies) {
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

		hud.draw(g);
		
		if(!PlayerData.currentlyCollectedCards.isEmpty())
			for(Card card : PlayerData.currentlyCollectedCards) {
				card.draw(g);
			}
	}

	public void addCollectables(int loops) {

		generateKeys(loops);

		generateAid();

	}

	private void generateAid() {

		Entity e = null;

		int turns = 1;

		while (turns > 0) {
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
					e = new EntityWeapon(32, 3).setFirstPosition(x+.5, y+.5);
					entities.add(e);
					turns--;
					System.out.println("added weapon " + e.getID() + " at " + e.worldPositionX+" "+e.worldPositionY  );
					continue;
				}
			}
		}
	}

	private void generateKeys(int loops) {
		Entity e = null;

		int turns = loops;
		int collectibleID = 0;

		while (turns > 0) {
			int x = rand.nextInt(maze.getMazeSizeFixed()*3);
			int y = rand.nextInt(maze.getMazeSizeFixed()*3);

			if(map[x][y] == 0) {
				if(entities.isEmpty()) {

					e = new EntityKey(32, collectibleID).setFirstPosition(x+.5, y+.5);
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
						e = new EntityKey(32, collectibleID).setFirstPosition(x+.5, y+.5);
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

	private void updateEntities() {
		for(Entity e : entities) {
			if(camera.xPos > e.worldPositionX-0.5 && camera.xPos < e.worldPositionX+0.5 ){
				if(camera.yPos > e.worldPositionY-0.5 && camera.yPos < e.worldPositionY+0.5 ){
					if(e instanceof EntityCollectible) {
						System.out.println("pick up ! ");
						PlayerData.currentlyCollectedCards.add(new Card(e.getID()).setPickedOrder(cardPickup));
						cardPickup++;
						((EntityCollectible) e).onPickUp();
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
