package game.entity.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.gameBase.GamePanel;
import engine.gameBase.KeyHandler;

public class Player {

	public static String PLAYERNAME = "";

	public int screenX;
	public int screenY;

	//map x and y start at 0 relative to the screen center
	public int mapX;
	public int mapY;

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private int playerDirection = 0;
	
	private Rectangle[] boundingBox; 

	BufferedImage playerSprite[] = new BufferedImage[4];

	
	ArrayList<Rectangle> colidingRectangles = new ArrayList<Rectangle>();

	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;

	boolean topHit;
	boolean botHit;
	boolean leftHit;
	boolean rightHit;
	
	public Player() {
		screenX = GamePanel.W/2;
		screenY = GamePanel.H/2;
		mapX = 32;
		mapY = 32;
		boundingBox = new Rectangle[]{
				new Rectangle(screenX+16+8, screenY-16+8-2, 16, 2), //up
				new Rectangle(screenX+16+8, screenY+16-16+8, 16, 2), //down
				new Rectangle(screenX+16+8-2, screenY-16+8, 2, 16), //left
				new Rectangle(screenX+16+16+8, screenY-16+8, 2, 16), //right
		};
	}

	public void draw(Graphics2D g){
		g.setColor(Color.white);
		//		g.drawImage(Loading.player, screenX+16, screenY-16, null);

//		g.setColor(Color.green);
//		g.fillRect(screenX+16+8, screenY-16+8, 16, 16);
		
		if(playerSprite[getPlayerDirection()] != null)
			g.drawImage(playerSprite[getPlayerDirection()], screenX+22, screenY-20, null);

		g.setColor(Color.blue);
		for(Rectangle r : boundingBox)
			g.draw(r);


	}

	/**0 up 1 down 2 left 3 right*/
	public Rectangle[] getBoundingBox(){
		return boundingBox;
	}

	public void update(){
		if(playerSprite[getPlayerDirection()] == null){
			String path = "saves/"+PLAYERNAME+"/"+PLAYERNAME +".png";

			for(int i = 0; i < playerSprite.length; i++){
				BufferedImage tempImg = null;

				try {
					tempImg = (ImageIO.read(new File(path))).getSubimage(0+ (20*i), 0, 20, 28);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(path);
				}

				if(tempImg != null)
					playerSprite[i]= tempImg;
			}
			
		}
		
		//make method update bounding box
		boundingBox = new Rectangle[]{
				new Rectangle(screenX+16+8, screenY-16+8-2, 16, 2), //up
				new Rectangle(screenX+16+8, screenY+16-16+8, 16, 2), //down
				new Rectangle(screenX+16+8-2, screenY-16+8, 2, 16), //left
				new Rectangle(screenX+16+16+8, screenY-16+8, 2, 16), //right
		};
	}

//	public void readFromSave(DataTag data) {
//		screenX = data.readInt("screenX");
//		screenY = data.readInt("screenY");
//		mapX = data.readInt("mapX");
//		mapY = data.readInt("mapY");
//	}
//
//	public void writeToSave(DataTag tag) {
//		tag.writeInt("screenX", screenX);
//		tag.writeInt("screenY", screenY);
//		tag.writeInt("mapX", mapX);
//		tag.writeInt("mapY", mapY);
//	}

	public int getPlayerDirection() {
		return playerDirection;
	}

	public void setPlayerDirection(int playerDirection) {
		this.playerDirection = playerDirection;
	}

	
	
	
	
	
	
	
	public void setTopHit(){topHit = true;}
	public void setBotHit(){botHit = true;}
	public void setLeftHit(){leftHit = true;}
	public void setRigthHit(){rightHit = true;}

	public boolean isFreeDown(){return !botHit;}
	public boolean isFreeUp(){return !topHit;}
	public boolean isFreeRight(){return !rightHit;}
	public boolean isFreeLeft(){return !leftHit;}

	
	private void calculateMovement(){

		boolean flag = true;
		if(!topHit && !botHit && !leftHit && !rightHit)
			left = right = up = down = true;

		if(topHit)
			left = right = down = flag;
		if(botHit)
			left = right = up = flag;

		if(leftHit)
			up = down = right = flag;
		if(rightHit)
			left = down = up = flag;

		if(topHit && rightHit){
			down = left = flag;
			right = up = !flag;
		}
		if(topHit && leftHit){
			down = right = flag;
			up = left = !flag;
		}
		if(botHit && rightHit){
			up = left = flag;
			down = right = !flag;
		}
		if(botHit && leftHit){
			up = right = flag;
			down = left = !flag;
		}
	}
	
	
	public void movePlayer() {

		calculateMovement();
		if(right)
			goRight();
		if(left)
			goLeft();
		if(down)
			goDown();
		if(up)
			goUp();

	}
	
	public void resetCollisionDetection(){
		left =right = up = down = false;
		topHit = botHit =leftHit = rightHit = false;
	}
	
	private void goRight(){
		if(KeyHandler.keyState[KeyHandler.RIGHT]){
			mapX+=2;
			setPlayerDirection(Player.RIGHT);
		}
	}

	private void goLeft(){
		if(KeyHandler.keyState[KeyHandler.LEFT]){
			mapX-=2;
			setPlayerDirection(Player.LEFT);
		}
	}

	private void goUp(){
		if(KeyHandler.keyState[KeyHandler.UP]){
			mapY-=2;
			setPlayerDirection(Player.UP);
		}
	}
	private void goDown(){
		if(KeyHandler.keyState[KeyHandler.DOWN]){
			mapY+=2;
			setPlayerDirection(Player.DOWN);
		}
	}
}
