package labyrinth3D.gamestates;

import static labyrinth3D.rscMngr.ImageLoader.backGroundMain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.engine.KeyHandler;
import labyrinth3D.game.entity.Player;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.gamestates.bounds.Doorway;

import static labyrinth3D.utility.Scale.*;

public class GameStateIsland extends GameState {

	private Rectangle[] boundingBox;

	private Rectangle leftWall;
	private Rectangle rightWall;
	private Rectangle bottomWall;
	private Rectangle topWall;

	private Doorway[] doors;

	private Player player;

	private int playerPositionX;
	private int playerPositionY;

	public GameStateIsland(GameStateHandler gsh) {
		this.gsh = gsh;

		player = new Player();

		playerPositionX = player.positionX;
		playerPositionY = player.positionY;

		boundingBox = new Rectangle[]{
				new Rectangle(playerPositionX+scale(24), playerPositionY-scale(10), scale(16), scale(2)), //up
				new Rectangle(playerPositionX+scale(24), playerPositionY+scale(8), scale(16), scale(2)), //down
				new Rectangle(playerPositionX+scale(22), playerPositionY-scale(8), scale(2), scale(16)), //left
				new Rectangle(playerPositionX+scale(40), playerPositionY-scale(8), scale(2), scale(16)), //right
		};

		doors = new Doorway[]{
				(Doorway) new Doorway(scale(65), Doorway.DOOR1,scale(150), scale(400)),
				(Doorway) new Doorway(scale(65), Doorway.DOORSMITH, scale(305), scale(400)),
				(Doorway) new Doorway(scale(65), Doorway.DOOR2, scale(470), scale(400)),
				(Doorway) new Doorway(scale(65), Doorway.DOOR3, scale(650), scale(400)),
				(Doorway) new Doorway(scale(65), Doorway.DOOR5, scale(800), scale(400))
		};

		leftWall = new Rectangle(0, 0, scale(50), GamePanel.H);
		rightWall = new Rectangle(GamePanel.W-scale(100), 0, scale(99), GamePanel.H);
		bottomWall = new Rectangle(0, GamePanel.H - scale(75), GamePanel.W, scale(50));
		topWall = new Rectangle(0, scale(410), GamePanel.W, scale(50));
	}

	float alpha = 1f;

	@Override
	public void draw(Graphics2D g) {

		super.draw(g);

		g.drawImage(backGroundMain, 0, 0, GamePanel.W, GamePanel.H, null);

		drawWallText(g);

		player.draw(g, scale(64), player.positionX, player.positionY - scale(58));

		//draws the player's bounding box
		for(Rectangle r : boundingBox) {
			g.setColor(Color.BLUE);
			g.draw(r);
		}

		//draws bounding box of map limits
		g.draw(bottomWall);
		g.draw(topWall);
		g.draw(leftWall);
		g.draw(rightWall);

		g.setColor(Color.orange);
		for(Doorway d : doors){
			g.draw(d.getBoundingBox());
		}

		//fade in effect
		if(alpha > 0) {
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, GamePanel.W, GamePanel.H);
		}
	}

	private void drawWallText(Graphics2D g) {
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, scale(45)));

		g.setColor(Color.black);
		g.drawString("I", scale(174), scale(301));
		g.drawString("II", scale(484), scale(301));
		g.drawString("III", scale(659), scale(301));
		g.drawString("V", scale(819), scale(301));

		g.setColor(Color.darkGray);
		g.drawString("I", scale(175), scale(300));
		g.drawString("II", scale(485), scale(300));
		g.drawString("III", scale(660), scale(300));
		g.drawString("V", scale(820), scale(300));

		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, scale(30)));
		g.setColor(Color.black);
		g.drawString("Smithy", scale(294), scale(351));
		g.setColor(Color.darkGray);
		g.drawString("Smithy", scale(295), scale(350));

	}

	@Override
	public void update() {
		super.update();

		if(alpha > 0)
			alpha-=0.005f;

		doPlayerMovement();
		//update after movement !
		player.update();

		if(doors[0].getBoundingBox().intersects(boundingBox[0]))
			if(KeyHandler.isPressed(KeyHandler.ENTER)|| KeyHandler.keyState[KeyHandler.UP]){
				gsh.changeGameState(GameStateHandler.MAZE_10);
			}

		if(doors[1].getBoundingBox().intersects(boundingBox[0]))
			if(KeyHandler.isPressed(KeyHandler.ENTER)|| KeyHandler.keyState[KeyHandler.UP]){
				PlayerData.positionForNextLevelX = 75;
				PlayerData.positionForNextLevelY = 350;
				gsh.changeGameState(GameStateHandler.SMITHY);
			}
	}

	private void doPlayerMovement() {

		if(KeyHandler.keyState[KeyHandler.RIGHT]){
			if(!boundingBox[3].intersects(rightWall)) {
				player.movePlayerRight(2);

				for(Rectangle r : boundingBox)
					r.x+=2;
			}
		}

		if(KeyHandler.keyState[KeyHandler.LEFT]){
			if(!boundingBox[2].intersects(leftWall)) {
				player.movePlayerLeft(2);

				for(Rectangle r : boundingBox)
					r.x-=2;
			}
		}

		if(KeyHandler.keyState[KeyHandler.UP]){
			if(!boundingBox[0].intersects(topWall)) {
				player.movePlayerUp(2);

				for(Rectangle r : boundingBox)
					r.y-=2;
			}
		}

		if(KeyHandler.keyState[KeyHandler.DOWN]){
			if(!boundingBox[1].intersects(bottomWall)) {
				player.movePlayerDown(2);
				for(Rectangle r : boundingBox)
					r.y+=2;
			}
		}
	}
}
