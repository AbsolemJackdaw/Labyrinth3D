package gameStates;

import static gameStates.content.Images.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.gameBase.GamePanel;
import engine.gameBase.GameState;
import engine.gameBase.GameStateHandler;
import engine.gameBase.KeyHandler;
import game.player.PlayerData;
import gameStates.content.Images;

public class GameStateSmithy extends GameState{

	private int playerPositionX = 75;
	private int playerPositionY = 350;

	private boolean facingRight = true;

	private Rectangle interactSmith;

	float alpha = 1f;

	public GameStateSmithy(GameStateHandler gsh) {
		this.gsh = gsh;

		interactSmith = new Rectangle(500, 80, 512, 512);
	}

	private double bob = 0;
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

		if(playerPositionX < -180) {
			gsh.changeGameState(GameStateHandler.ISLAND);
		}

		if(playerPositionX + 100 > interactSmith.x -250) {
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

		g.drawImage(smithyBackground, 0, 0, null);

		g.drawImage(shadow, 540-(int)bob, 520 , 400+(int)bob*2, 30, null);

		g.drawImage(smith, 500, 20 + (int)bob , 512, 512, null);


		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 20));

		//		g.setColor(Color.black);
		//		g.draw(interactSmith);

		if(facingRight)
			g.drawImage(player, playerPositionX, playerPositionY-60, 256, 256, null);
		else
			g.drawImage(player, playerPositionX+256, playerPositionY-60, -256, 256, null);

		bob = Math.cos(bobbing*4)*10;

		if(canTalk) {
			if(!displayText)
				g.drawImage(bubble, 500, (int)bob, 128, 128, null);
			if(displayText) {
				g.setColor(new Color(0f, 0f, 0f));
				g.drawImage(bubble_empty, 300, -20, 320, 192, null);
				g.drawString("In exchange for found items,", 350, 70);
				g.drawString("I could give you aid ...", 350, 90);

			}
		}

		if(alpha > 0) {
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, GamePanel.W, GamePanel.H);
		}
	}

	private void doPlayerMovement() {
		if(KeyHandler.keyState[KeyHandler.RIGHT]){
			facingRight = true;
			if(playerPositionX + 180 < interactSmith.x) {
				playerPositionX+=5;
			}
		}

		if(KeyHandler.keyState[KeyHandler.LEFT]){
			facingRight = false;
			playerPositionX-=5;
		}
	}
}
