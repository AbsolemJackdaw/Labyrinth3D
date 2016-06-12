package game.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.gameBase.GamePanel;

import static gameStates.content.Images.*;
import static game.entity.collectables.EntityCollectible.*;

public class Card {

	private int identifier = 0;

	private int order = -1;

	//cards have a link to an entity, the object you can collect
	public Card(int id) {
		identifier = id;

	}

	public Card setPickedOrder(int i) {
		order = i;
		return this;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(card, 4 + order*32, GamePanel.H - 64 - 3, null);
		g.drawImage(getTextureFromId(identifier), 4 + order*32, GamePanel.H - 64 - 3 + 16, null);
	}

	public int getCardID(){
		return identifier;
	}

	public BufferedImage getTextureFromId(int id)
	{
		switch (id) {
		case STATUE:
			return statue;
		case HEAD:
			return dried_head;
		case UNICORN_BLOOD:
			return unicorn_blood;
		default: return null;
		}
	}
}

