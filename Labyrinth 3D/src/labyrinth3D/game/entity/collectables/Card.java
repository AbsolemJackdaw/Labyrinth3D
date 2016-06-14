package labyrinth3D.game.entity.collectables;


import static labyrinth3D.game.entity.collectables.EntityCollectible.*;
import static labyrinth3D.rscMngr.ImageLoader.*;

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
		case SPORT:
			return sport;
		case WARFSWORD:
			return warfsword;
		case FEATHER:
			return feather;
		case GRAIL:
			return grail;
		case SCYTHE:
			return scythe;
		case CAPE:
			return cape;
		default: 
			return default_image;
		}
	}
}

