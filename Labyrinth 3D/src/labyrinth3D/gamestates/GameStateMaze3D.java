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
import labyrinth3D.game.entity.collectables.EntityAid;
import labyrinth3D.game.entity.collectables.EntityCape;
import labyrinth3D.game.entity.collectables.EntityCollectible;
import labyrinth3D.game.entity.collectables.EntityFeather;
import labyrinth3D.game.entity.collectables.EntityGrail;
import labyrinth3D.game.entity.collectables.EntityKey;
import labyrinth3D.game.entity.collectables.EntityScythe;
import labyrinth3D.game.entity.enemy.EntityEnemy;
import labyrinth3D.game.entity.portal.Ladder;
import labyrinth3D.game.gen.GenMaze;
import labyrinth3D.game.hud.HUD;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.gamestates.maze3D.Camera;
import labyrinth3D.gamestates.maze3D.Screen;


public class GameStateMaze3D extends GameState {

	public int mapWidth = 3;
	public int mapHeight = 24;


	public Camera camera;
	public Screen screen;

	public int[][] map;

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> enemies = new ArrayList<Entity>();

	GenMaze maze;

	Random rand = new Random();

	int totalCollectables = 0;

	HUD hud;

	public	boolean debug = false;

	public boolean attack;
	public int attackTime = 0;

	public GameStateMaze3D(GameStateHandler gsh, int mazeSize, int collectables) {
		this.gsh = gsh;

		if(debug){
			map = testMap();
		}else {
			maze = new GenMaze(mazeSize);
			maze.load();

			map = maze.getNumberGrid3D();
		}

		totalCollectables = collectables;

		addCollectables(collectables);
		generatePortal();

		hud = new HUD(this);

		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, 1024, 576);

		enemies.add((EntityEnemy) new EntityEnemy(0, 0).setFirstPosition(10.5, 10.5));

	}

	private int cardPickup = 0; 

	@Override
	public void update() {
		super.update();

		camera.update(map);

		screen.updateWalls(camera, GamePanel.getScreenPixels(), entities, enemies);

		//		System.out.println(camera.xDir + " " + camera.yDir);

		if(Camera.isPressed(Camera.T)){
			gsh.changeGameState(GameStateHandler.ISLAND);
		}

		if(KeyHandler.isPressed(KeyHandler.SPACE) && attackTime <= 0) {
			attack = true;
			attackTime = 120;
			attackenemy();

		}

		if(attackTime > 0)
			attackTime-= PlayerData.attackSpeed;

		if(attackTime <= 0 && attack)
			attack = false;

		updateEntities();

		hud.update();
	}

	public void attackenemy(){
		System.out.println(camera.xPos + " " + camera.yPos + " " + camera.xDir + " " + camera.yDir);
		for(Entity e : enemies) {
			EntityEnemy en = (EntityEnemy)e;

			//front
			if(camera.xDir > 0 && camera.xPos+1.2 > e.worldPositionX && camera.xPos < e.worldPositionX &&
					camera.yDir >-0.5 && camera.yDir <0.5 && camera.yPos-0.5 < e.worldPositionY && camera.yPos+0.5 > e.worldPositionY) {
				en.hit();
			}
			//back
			if(camera.xDir < 0 && camera.xPos > e.worldPositionX && camera.xPos-1.2 < e.worldPositionX &&
					camera.yDir >-0.5 && camera.yDir <0.5 && camera.yPos-0.5 < e.worldPositionY && camera.yPos+0.5 > e.worldPositionY) {
				en.hit();
			}
			//right
			if(camera.yDir > 0 && camera.yPos+1.2 > e.worldPositionY && camera.yPos < e.worldPositionY &&
					camera.xDir >-0.5 && camera.xDir <0.5 && camera.xPos-0.5 < e.worldPositionX && camera.xPos+0.5 > e.worldPositionX) {
				en.hit();
			}
			//left
			if(camera.yDir < 0 && camera.yPos > e.worldPositionY && camera.yPos-1.2 < e.worldPositionY &&
					camera.xDir >-0.5 && camera.xDir <0.5 && camera.xPos-0.5 < e.worldPositionX && camera.xPos+0.5 > e.worldPositionX) {
				en.hit();
			}
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

	//stores all possible collectibles. 
	//picks out x collectibles. never the same.
	private EntityAid[] listWithAid = new EntityAid[]{new EntityFeather(0), new EntityGrail(0), new EntityCape(0), new EntityScythe(0)};
	
	private void generateAid() {

		Entity e = null;

		int turns = rand.nextInt(listWithAid.length); 
		//always a minimum of 3 helping items
		if(turns < 2)
			turns = 2;
		
//		//help for first step trough game. only have 1 aid
//		if(turns > listWithAid.length)
//			turns = listWithAid.length;

		while (turns > 0) {
			
			EntityAid aid;

			// an aid
			aid = listWithAid[rand.nextInt(listWithAid.length)];
			
			int x ;
			int y ;

			if(maze != null) {
				x = rand.nextInt(maze.getMazeSizeFixed()*3);
				y = rand.nextInt(maze.getMazeSizeFixed()*3);
			}else {
				x = rand.nextInt((int) (Math.sqrt(map.length)*3));
				y = rand.nextInt((int) (Math.sqrt(map.length)*3));
			}

			if(map[x][y] == 0) {

				boolean flag = false;
				for(Entity ent : entities) {
					if(ent.getWorldPositionX() != x+.5 && ent.getWorldPositionY() != y+.5) {
						flag = true;
					}else
						flag = false;
				}
				if(flag) {
					e = aid.setFirstPosition(x+.5, y+.5);
					entities.add(e);
					turns--;
					System.out.println("added aid " + e.getID() + " at " + e.worldPositionX+" "+e.worldPositionY  );
				}
			}
		}
	}

	private void generateKeys(int loops) {
		Entity e = null;

		int turns = loops;

		while (turns > 0) {

			int x ;
			int y ;

			if(maze != null) {
				x = rand.nextInt(maze.getMazeSizeFixed()*3);
				y = rand.nextInt(maze.getMazeSizeFixed()*3);
			}else {
				x = rand.nextInt((int) (Math.sqrt(map.length)*3));
				y = rand.nextInt((int) (Math.sqrt(map.length)*3));
			}

			if(map[x][y] == 0) {
				if(entities.isEmpty()) {

					e = new EntityKey(32, 0).setFirstPosition(x+.5, y+.5);
					entities.add(e);
					turns--;
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
						e = new EntityKey(32, 0).setFirstPosition(x+.5, y+.5);
						entities.add(e);
						turns--;
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

			int x ;
			int y ;

			if(maze != null) {
				x = rand.nextInt(maze.getMazeSizeFixed()*3);
				y = rand.nextInt(maze.getMazeSizeFixed()*3);
			}else {
				x = rand.nextInt((int) (Math.sqrt(map.length)*3));
				y = rand.nextInt((int) (Math.sqrt(map.length)*3));
			}

			if(map[x][y] == 0) {

				boolean flag = false;

				for(Entity ent : entities) {
					if(ent.getWorldPositionX() != x+.5 && ent.getWorldPositionY() != y+.5) {
						flag = true;
					}else
						flag = false;
				}

				if(flag) {
					e = new Ladder(0, 0).setFirstPosition(x+.5, y+.5);
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
			if(e instanceof Ladder) {
				Ladder ladder = (Ladder)e;
				ladder.update();

				if(PlayerData.currentlyCollectedCards.size() >= totalCollectables)
					ladder.setActive(true);

				if(ladder.isActive()) {
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
