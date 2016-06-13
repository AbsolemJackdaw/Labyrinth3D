package labyrinth3D.game.playerdata;


import static labyrinth3D.game.entity.collectables.EntityCollectible.HEAD;
import static labyrinth3D.game.entity.collectables.EntityCollectible.STATUE;
import static labyrinth3D.game.entity.collectables.EntityCollectible.UNICORN_BLOOD;
import static labyrinth3D.gamestates.content.ImageLoader.card;
import static labyrinth3D.gamestates.content.ImageLoader.dried_head;
import static labyrinth3D.gamestates.content.ImageLoader.statue;
import static labyrinth3D.gamestates.content.ImageLoader.unicorn_blood;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import labyrinth3D.engine.GamePanel;

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

