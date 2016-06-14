package labyrinth3D.game.entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import labyrinth3D.rscMngr.ImageLoader;
import labyrinth3D.rscMngr.Texture;

public class Entity {

	//position in the world. should be withing map's borders;
	public double worldPositionX,worldPositionY;

	protected int size;
	protected int id;

	//wether the entity has to be deleted on update
	boolean remove;
	
	public Entity(int size, int id) {
		this.size = size;
		this.id = id;

	}

	public void draw(Graphics2D g){
//		g.setColor(Color.WHITE);
//		g.fill(getBoundingBox());
//		g.drawImage(getTexture(), posX, posY, null);
		
	}
	
	public void update(){
		
	}
	
	public BufferedImage getImage(){
		return ImageLoader.default_image;
	}
	
	public int getID(){
		return id;
	}

	public Entity setFirstPosition(double x, double y){
		
		worldPositionX = x;
		worldPositionY = y;
		
		return this;
	}
	
	public Point getMapPosition(){
		return new Point((int)worldPositionX,(int)worldPositionY);
	}
	
	public double getWorldPositionX() {
		return worldPositionX;
	}
	
	public double getWorldPositionY() {
		return worldPositionY;
	}
	
	public void setMapPosition(int x, int y){
		worldPositionX = x ;
		worldPositionY = y ;
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
//	}
	
	public Entity setPositionUnrelativeToCenter(int x , int y){
		return this;
	}
	
	public Texture getTexture() {
		return Texture.tex_default;
	}
	
	public double getScaleX() {
		return 1;
	}
	
	public double getScaleY() {
		return 1;
	}
	
	public double getMoveOffset() {
		return 0.0d;
	}
}
