package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.gameBase.GamePanel;
import game.Maze;

public class Entity {

	//position on the map
	int posMapX;
	int posMapY;

	//position on screen
	protected int posX;
	protected int posY;

	protected int size;
	protected int id;

	//wether the entity has to be deleted on update
	boolean remove;
	
	public Entity(int size, int id) {
		this.size = size;
		this.id = id;

	}

	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fill(getBoundingBox());
		g.drawImage(getTexture(), posX, posY, null);
		
	}
	
	public void update(){
		
	}
	
	public BufferedImage getTexture(){
		return null;
	}
	
	public int getID(){
		return id;
	}
	public Rectangle getBoundingBox(){
		return new Rectangle(posX, posY, size, size);
	}

	public Entity setFirstPosition(int x, int y){
		posMapX = x + Maze.getTileSize()/2 - (size/2);
		posMapY = y - Maze.getTileSize()/2 + (size/2);
		setPosition();
		return this;
	}
	
//	private Point getPosition(){
//		return new Point(posX, posY);
//	}
	
	private void setPosition(){
		posX = GamePanel.W/2 + posMapX;
		posY = GamePanel.H/2 + posMapY;
	}
	
	public Point getMapPosition(){
		return new Point(posMapX, posMapY);
	}
	
	public void setMapPosition(int x, int y){
		posMapX = x ;
		posMapY = y ;
		setPosition();
	}
	
	public void remove(){
		remove = true;
	}
	
	public boolean hasToBeRemoved(){
		return remove;
	}

//	public void writeToSave(DataTag entityTag) {
//		entityTag.writeInt("x", posMapX);
//		entityTag.writeInt("y", posMapX);
//
//	}
	
	public Entity setPositionUnrelativeToCenter(int x , int y){
		posMapX = posX = x ;
		posMapY = posY = y ;
		return this;
	}
}
