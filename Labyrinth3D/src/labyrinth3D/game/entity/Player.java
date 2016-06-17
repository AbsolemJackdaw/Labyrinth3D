package labyrinth3D.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.ImageLoader;

public class Player {

	public int positionX;
	public int positionY;

	private int animationTimer;
	private int animationIndex;

	private boolean walking;

	private boolean facingRight = true;

	BufferedImage playerImg;

	public Player(){

		positionX = PlayerData.positionForNextLevelX;
		positionY = PlayerData.positionForNextLevelY;

	}

	/**Player's update should be called AFTER player movement is done*/
	public void update(){

		if(walking)
			animationTimer ++;
		else
			animationTimer = 0;

		walking = false;

	}

	public void draw(Graphics2D g, int scale, int x, int y) {
		playerImg = ImageLoader.player;

		if(animationTimer > 0) {
			if(animationTimer % 10 == 0)
				animationIndex++;
			if(animationIndex > ImageLoader.player_walking.length-1)
				animationIndex = 0;

			playerImg = ImageLoader.player_walking[animationIndex];

		}

		if(facingRight)
			g.drawImage(playerImg, x, y, scale, scale, null);
		else
			g.drawImage(playerImg, x + scale, y, -scale, scale, null);


	}

	public void movePlayerRight(int x) {
		positionX+=x;
		walking = true;

		facingRight = true;
	}

	public void movePlayerLeft(int x) {
		positionX-=x;
		walking = true;
		facingRight = false;
	}

	public void movePlayerUp(int y) {
		positionY -=y;
		walking = true;
	}

	public void movePlayerDown(int y) {
		positionY +=y;
		walking = true;
	}
}
