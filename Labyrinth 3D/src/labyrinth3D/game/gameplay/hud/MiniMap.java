package labyrinth3D.game.gameplay.hud;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import labyrinth3D.game.gen.GenMaze;
import labyrinth3D.gamestates.maze3D.Camera;

import static labyrinth3D.gamestates.content.Images.*;

public class MiniMap {

	ArrayList<MiniMapRectangle> path= new ArrayList<MiniMapRectangle>();

	MiniMapPlayerRectangle playerPos;

	int prevColX;
	int prevColY;

	private int size = 3;

	private float offSetY = -60;
	private float offSetX = -60;

	public MiniMap() {
	}

	public float getOffSetX() {
		return offSetX;
	}
	public float getOffSetY() {
		return offSetY;
	}

	public void setOffSetX(float offSetX) {
		this.offSetX += offSetX;
	}
	public void setOffSetY(float offSetY) {
		this.offSetY += offSetY;
	}

	public void draw(Graphics2D g){
		//TODO 20 should be replaced with mapsize*2
		g.drawImage(hud_elements.getSubimage(64*9 -10 ,64-10,126+20,126+21),(64*15-20)+(int)offSetX,(64*8-20)+(int)offSetY , null); 

		for(MiniMapRectangle mmr : path)
			mmr.draw(g);

		if(playerPos != null)
			playerPos.draw(g);

	}

	public void update(Camera cam){

		int colX = (int) (cam.getxPos()/3);
		int colY = (int) (cam.getyPos()/3);

		if(prevColX < colX || prevColX > colX){
			MiniMapRectangle mmp = 
					new MiniMapRectangle(colX*(3*size), colY*(3*size), GenMaze.numberGrid[colX][colY]);
			path.add(mmp);
			prevColX = colX;
		}
		if(prevColY < colY || prevColY > colY){
			MiniMapRectangle mmp = new MiniMapRectangle(colX*(3*size), colY*(3*size), GenMaze.numberGrid[colX][colY]);
			path.add(mmp);
			prevColY = colY;
		}

		playerPos = new MiniMapPlayerRectangle(colX*(3*size), colY*(3*size),GenMaze.numberGrid[colX][colY]);

	}

	public class MiniMapRectangle{
		Rectangle rect;
		Rectangle rect2;

		int posX;
		int posY;
		int id;

		public MiniMapRectangle(int x, int y, int type) {
			posX=x;posY=y;id=type;

			int a=0;
			int b=0;

			int xp = (64*14)+5;
			int yp = (64*7)+5 ;

			if(type == 1){
				a = 1*size; b = 3*size;
				rect = new Rectangle(xp+x+1*size, yp+y,a,b);
			}

			if(type == 0){
				a = 3*size; b = 1*size;
				rect = new Rectangle(xp+x, yp+y+1*size,a,b);

			}

			if(type == 2 || type == 3){
				a = 3*size; b = 1*size;
				rect = new Rectangle(xp+x, yp+y+1*size,a,b);
				a = 1*size; b = 2*size;
				if(type == 2)
					rect2 = new Rectangle(xp+x+1*size, yp+y+1*size,a,b);
				if(type == 3)
					rect2 = new Rectangle(xp+x+1*size, yp+y,a,b);
			}
			if(type == 4 || type == 5){
				a = 1*size; b = 3*size;
				rect = new Rectangle(xp+x+1*size, yp+y,a,b);
				a = 2*size; b = 1*size;
				if(type == 4)
					rect2 = new Rectangle(xp+x, yp+y+1*size,a,b);
				if(type == 5)
					rect2 = new Rectangle(xp+x+1*size, yp+y+1*size,a,b);
			}

			if(type == 6 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(xp+x+1*size, yp+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(xp+x+1*size, yp+y+1*size,a,b);

			}

			if(type == 7 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(xp+x, yp+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(xp+x+1*size, yp+y+1*size,a,b);
			}

			if(type == 8 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(xp+x, yp+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(xp+x+1*size, yp+y,a,b);
			}

			if(type == 9 ){
				a = 2*size; b = 1*size;
				rect = new Rectangle(xp+x+1*size, yp+y+1*size,a,b);
				a = 1*size; b = 2*size;
				rect2 = new Rectangle(xp+x+1*size, yp+y,a,b);
			}

			if(type == 10 ){
				a = 3*size; b = 1*size;
				rect = new Rectangle(xp+x, yp+y+1*size,a,b);
				a = 1*size; b = 3*size;
				rect2 = new Rectangle(xp+x+1*size, yp+y,a,b);
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
