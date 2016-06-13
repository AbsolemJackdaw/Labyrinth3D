package labyrinth3D.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import labyrinth3D.engine.gameBase.GamePanel;


public class Animation {

	private int index;
	private int counter = 0;
	private int speed; 

	private int length;

	BufferedImage[] images;

	private int x;
	private int y;
	int mapX;
	int mapY;

	public Animation(BufferedImage[] list, int speed) {
		this.speed = speed;
		this.length = list.length;
		index = 0;
		images = list.clone();
	}

	public void draw(Graphics2D g){
		g.drawImage(images[index], x, y, null);
	}

	public BufferedImage getCurrentTexture(){
		return images != null ? images[index] : null;
	}

	public void update(){
		counter ++;

		if(speed % counter == 0){
			if(index+1 < length)
				index++;
			else
				index = counter = 0;
		}
	}

	private void setPosition(){
		x = GamePanel.W/2 + mapX;
		y = GamePanel.H/2 + mapY;
	}

	public void setMapPosition(int x, int y){
		mapX = x ;
		mapY = y ;
		setPosition();
	}

}
