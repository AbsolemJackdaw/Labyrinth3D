package labyrinth3D.gamestates;

import java.awt.Color;

import static labyrinth3D.utility.Scale.*;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.engine.KeyHandler;
import labyrinth3D.game.entity.Player;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.ImageLoader;


public class GameStateSmithy extends GameState{

	private Rectangle interactSmith;

	float alpha = 1f;
	
	private Player player;

	public GameStateSmithy(GameStateHandler gsh) {
		this.gsh = gsh;
		
		player = new Player();
		
		interactSmith = new Rectangle(scale(500), scale(80), scale(512), scale(512));
	}

	private double bobbing;
	boolean canTalk;
	private boolean displayText;

	@Override
	public void update() {
		super.update();

		bobbing += 0.025D;

		if(alpha > 0)
			alpha-=0.005f;

		doPlayerMovement();

		player.update();
		
		if(player.positionX < -scale(180)) {
			PlayerData.positionForNextLevelX = scale(315);
			PlayerData.positionForNextLevelY = scale(480);
			gsh.changeGameState(GameStateHandler.ISLAND);
		}

		if(player.positionX + scale(100) > interactSmith.x -scale(125)) {
			canTalk = true;
		}else {
			canTalk = false;
		}

		if(canTalk) {
			if(KeyHandler.isPressed(KeyHandler.ENTER)) {

				if(displayText) {
					//	open shop;
				}

				if(!PlayerData.hasVisitedSmith) {
					PlayerData.hasVisitedSmith = true;
					displayText = true;
				}else {
					//open shop
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

		double bob = Math.cos(bobbing)*20;

		g.drawImage(ImageLoader.smithyBackground, 0, 0, GamePanel.W, GamePanel.H, null);

		g.drawImage(ImageLoader.shadow, scale(540)-(int)bob, scale(520) , scale(400)+(int)bob*2, scale(30), null);

		g.drawImage(ImageLoader.smith, scale(500), scale(20) + (int)bob , scale(512), scale(512), null);


		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, scale(20)));

		//		g.setColor(Color.black);
		//		g.draw(interactSmith);

		player.draw(g, scale(256), player.positionX, scale(player.positionY-60));

		bob = Math.cos(bobbing*4)*10;

		if(canTalk) {
			if(!displayText)
				g.drawImage(ImageLoader.bubble, scale(500), (int)bob, scale(128), scale(128), null);
			if(displayText) {
				g.setColor(new Color(0f, 0f, 0f));
				g.drawImage(ImageLoader.bubble_empty, scale(300), -scale(20), scale(320), scale(192), null);
				g.drawString("In exchange for found items,", scale(350), scale(70));
				g.drawString("I could give you aid ...", scale(350), scale(90));

			}
		}

		if(alpha > 0) {
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, GamePanel.W, GamePanel.H);
		}
	}

	private void doPlayerMovement() {
		if(KeyHandler.keyState[KeyHandler.RIGHT]){
			if(player.positionX + scale(180) < interactSmith.x) {
				player.movePlayerRight(scale(5));
			}
		}

		if(KeyHandler.keyState[KeyHandler.LEFT]){
			player.movePlayerLeft(scale(5));
		}
	}
}
