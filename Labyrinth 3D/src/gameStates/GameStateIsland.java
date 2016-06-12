package gameStates;

import static gameStates.content.Images.backGroundMain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.gameBase.GamePanel;
import engine.gameBase.GameState;
import engine.gameBase.GameStateHandler;
import engine.gameBase.KeyHandler;
import gameStates.content.Doorway;
import gameStates.content.Images;

public class GameStateIsland extends GameState {

	private int playerPositionX = 80;
	private int playerPositionY = 480;

	private Rectangle[] boundingBox;

	private Rectangle leftWall;
	private Rectangle rightWall;
	private Rectangle bottomWall;
	private Rectangle topWall;

	private boolean facingRight = true;

	private Doorway[] doors = new Doorway[]{
			(Doorway) new Doorway(65, Doorway.DOOR1,150, 400),
			(Doorway) new Doorway(65, Doorway.DOORSMITH, 305, 400),
			(Doorway) new Doorway(65, Doorway.DOOR2, 470, 400),
			(Doorway) new Doorway(65, Doorway.DOOR3, 650, 400),
			(Doorway) new Doorway(65, Doorway.DOOR5, 800, 400)
	};

	public GameStateIsland(GameStateHandler gsh) {
		this.gsh = gsh;

		boundingBox = new Rectangle[]{
				new Rectangle(playerPositionX+16+8, playerPositionY-16+8-2, 16, 2), //up
				new Rectangle(playerPositionX+16+8, playerPositionY+16-16+8, 16, 2), //down
				new Rectangle(playerPositionX+16+8-2, playerPositionY-16+8, 2, 16), //left
				new Rectangle(playerPositionX+16+16+8, playerPositionY-16+8, 2, 16), //right
		};

		leftWall = new Rectangle(0, 0, 50, GamePanel.H);
		rightWall = new Rectangle(GamePanel.W-100, 0, 99, GamePanel.H);
		bottomWall = new Rectangle(0, GamePanel.H-75, GamePanel.W, 50);
		topWall = new Rectangle(0, 410, GamePanel.W, 50);
	}

	float alpha = 1f;

	@Override
	public void draw(Graphics2D g) {

		super.draw(g);

		g.drawImage(backGroundMain, 0, 0, null);

		drawWallText(g);

		if(facingRight)
			g.drawImage(Images.player, playerPositionX, playerPositionY-60, null);
		else
			g.drawImage(Images.player, playerPositionX+64, playerPositionY-60, -64, 64, null);

		//		draws the player's bounding box
		//		for(Rectangle r : boundingBox) {
		//			g.setColor(Color.BLUE);
		//			g.draw(r);
		//		}

		//draws bounding box of map limits
		//		g.draw(bottomWall);
		//		g.draw(topWall);
		//		g.draw(leftWall);
		//		g.draw(rightWall);

		//		g.setColor(Color.orange);
		//		for(Doorway d : doors){
		//			g.draw(d.getBoundingBox());
		//		}

		//fade in effect
		if(alpha > 0) {
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, GamePanel.W, GamePanel.H);
		}
	}

	private void drawWallText(Graphics2D g) {
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 45));

		g.setColor(Color.black);
		g.drawString("I", 174, 301);
		g.drawString("II", 484, 301);
		g.drawString("III", 659, 301);
		g.drawString("V", 819, 301);

		g.setColor(Color.darkGray);
		g.drawString("I", 175, 300);
		g.drawString("II", 485, 300);
		g.drawString("III", 660, 300);
		g.drawString("V", 820, 300);

		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 30));
		g.setColor(Color.black);
		g.drawString("Smithy", 294, 351);
		g.setColor(Color.darkGray);
		g.drawString("Smithy", 295, 350);

	}

	@Override
	public void update() {
		super.update();

		if(alpha > 0)
			alpha-=0.005f;

		doPlayerMovement();

		if(doors[0].getBoundingBox().intersects(boundingBox[0]))
			if(KeyHandler.isPressed(KeyHandler.ENTER)|| KeyHandler.isPressed(KeyHandler.UP)){
				//set gamestate to game, according to door, generate multiple floors
				gsh.changeGameState(GameStateHandler.MAZE_10);
			}

		if(doors[1].getBoundingBox().intersects(boundingBox[0]))
			if(KeyHandler.isPressed(KeyHandler.ENTER)|| KeyHandler.isPressed(KeyHandler.UP)){
				//set gamestate to game, according to door, generate multiple floors
				gsh.changeGameState(GameStateHandler.SMITHY);
			}
	}

	private void doPlayerMovement() {
		if(KeyHandler.keyState[KeyHandler.RIGHT]){
			facingRight = true;
			if(!boundingBox[3].intersects(rightWall)) {
				playerPositionX+=2;

				for(Rectangle r : boundingBox)
					r.x+=2;
			}
		}

		if(KeyHandler.keyState[KeyHandler.LEFT]){
			facingRight = false;
			if(!boundingBox[2].intersects(leftWall)) {
				playerPositionX-=2;

				for(Rectangle r : boundingBox)
					r.x-=2;
			}
		}

		if(KeyHandler.keyState[KeyHandler.UP]){
			if(!boundingBox[0].intersects(topWall)) {
				playerPositionY-=2;

				for(Rectangle r : boundingBox)
					r.y-=2;
			}
		}
		if(KeyHandler.keyState[KeyHandler.DOWN]){
			if(!boundingBox[1].intersects(bottomWall)) {
				playerPositionY+=2;

				for(Rectangle r : boundingBox)
					r.y+=2;
			}
		}
	}
}
