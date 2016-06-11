package game.elements.hud;

import static gameStates.content.Images.hud_elements;
import game.Maze;
import gameStates.maze.Camera;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class MiniMap {

	ArrayList<MiniMapRectangle> path= new ArrayList<MiniMapRectangle>();

	MiniMapPlayerRectangle playerPos;
	
	int prevColX;
	int prevColY;
	
	private int size = 3;
	
	public MiniMap() {
	}
	
	public void draw(Graphics2D g){
		
		g.drawImage(hud_elements.getSubimage(64*9 -10 ,64-10,126+20,126+20),64*13-28,64*6-28 , null);
		
//		g.drawImage(HUD.getSubimage(64*6 ,64,Maze.getMapSize()*3,Maze.getMapSize()*3),64*13,64*6, null);

		for(MiniMapRectangle mmr : path)
			mmr.draw(g);
		
		if(playerPos != null)
			playerPos.draw(g);
			
	}

	public void update(Camera cam){
		
//		System.out.println(cam.getxPos()+" "+cam.getyPos());
		
//		int colX = (int) (cam.getxPos()/Maze.getTileSize());
//		int colY = (int) (cam.getyPos()/Maze.getTileSize());
		
		int colX = (int) (cam.getxPos()/3);
		int colY = (int) (cam.getyPos()/3);
		
//		System.out.println(colX + " "+ colY);
		
		if(prevColX < colX || prevColX > colX){
			MiniMapRectangle mmp = new MiniMapRectangle(colX*(3*size), colY*(3*size), Maze.numberGrid[colX][colY]);
			path.add(mmp);
			prevColX = colX;
		}
		if(prevColY < colY || prevColY > colY){
			MiniMapRectangle mmp = new MiniMapRectangle(colX*(3*size), colY*(3*size), Maze.numberGrid[colX][colY]);
			path.add(mmp);
			prevColY = colY;
		}
		
		playerPos = new MiniMapPlayerRectangle(colX*(3*size), colY*(3*size),Maze.numberGrid[colX][colY]);

	}

	public class MiniMapRectangle{
		Rectangle rect;
		Rectangle rect2;

		public MiniMapRectangle(int x, int y, int type) {
			
			int a=0;
			int b=0;

			if(type == 1){
				a = 1*size; b = 3*size;
				rect = new Rectangle(64*13+x+1*size, 64*6+y,a,b);
			}

			if(type == 0){
				a = 3*size; b = 1*size;
				rect = new Rectangle(64*13+x, 64*6+y+1*size,a,b);

			}

			if(type == 2 || type == 3){
				a = 3*size; b = 1*size;
				rect = new Rectangle(64*13+x, 64*6+y+1*size,a,b);
				a = 1*size; b = 2*size;
				if(type == 2)
					rect2 = new Rectangle(64*13+x+1*size, 64*6+y+1*size,a,b);
				if(type == 3)
					rect2 = new Rectangle(64*13+x+1*size, 64*6+y,a,b);
			}
			if(type == 4 || type == 5){
				a = 1*size; b = 3*size;
				rect = new Rectangle(64*13+x+1*size, 64*6+y,a,b);
				a = 2*size; b = 1*size;
				if(type == 4)
					rect2 = new Rectangle(64*13+x, 64*6+y+1*size,a,b);
				if(type == 5)
					rect2 = new Rectangle(64*13+x+1*size, 64*6+y+1*size,a,b);
			}

			if(type == 6 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(64*13+x+1*size, 64*6+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(64*13+x+1*size, 64*6+y+1*size,a,b);

			}

			if(type == 7 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(64*13+x, 64*6+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(64*13+x+1*size, 64*6+y+1*size,a,b);
			}

			if(type == 8 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(64*13+x, 64*6+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(64*13+x+1*size, 64*6+y,a,b);
			}

			if(type == 9 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(64*13+x+1*size, 64*6+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(64*13+x+1*size, 64*6+y,a,b);
			}

			if(type == 10 ){
				a = 3*size; b = 1*size;
				rect = new Rectangle(64*13+x, 64*6+y+1*size,a,b);
				a = 1*size; b = 3*size;
				rect2 = new Rectangle(64*13+x+1*size, 64*6+y,a,b);
			}

		}
		public void draw(Graphics2D g){
			g.setColor(Color.black);
			g.fill(rect);
			if(rect2 !=null)
				g.fill(rect2);

		}
	}

	public class MiniMapPlayerRectangle extends MiniMapRectangle{

		public MiniMapPlayerRectangle(int x, int y, int type) {
			super(x, y, type);
		}

		@Override
		public void draw(Graphics2D g){
			g.setColor(Color.red);
			g.fill(rect);
			if(rect2 !=null)
				g.fill(rect2);

		}
	}
}
