package gameStates.content;

import static gameStates.content.Images.walls;
import engine.gameBase.GamePanel;
import game.entity.player.Player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Map_old {

	// position
	private double x;
	private double y;

	//how many wall blocks it can take
	public static int mapSizeX = 10;
	public static int mapSizeY = 10;
	public static int mapSize = mapSizeX*mapSizeY;

	public static int tileSize = 64;

	public static int worldSizeX = mapSizeX*tileSize;
	public static int worldSizeY = mapSizeY*tileSize;

	public Map_old() {
		
	}

	public void draw(Graphics2D g, Player p) {

		for(int x = -3; x <=3;x++){
			for(int y = -3; y <=3;y++){

				int colX = (int) (p.mapX/tileSize)+x;
				int colY = (int) (p.mapY/tileSize)+y;

				int xa = 0;
				int ya = 0;

				BufferedImage wall= null;

				if(colX <0 || colX >= Map_old.mapSizeX || colY <0 || colY >= Map_old.mapSizeY){
					wall = walls[11];
					g.drawImage(wall, 
							colX*tileSize + ((int)this.x-GamePanel.W/2) + GamePanel.W/2, 
							colY*tileSize + ((int)this.y-GamePanel.H/2) + GamePanel.H/2 - tileSize/2, null);
				}

				if(colX>0 && colX < Map_old.mapSizeX)
					xa=colX;
				if(colY>0 && colY < Map_old.mapSizeY)
					ya = colY;

				if (wall == null)
					wall = LoadMaze_old.labyrinthGrid[xa][ya];

				g.drawImage(wall, 
						xa*tileSize + ((int)this.x-GamePanel.W/2) + GamePanel.W/2, 
						ya*tileSize + ((int)this.y-GamePanel.H/2) + GamePanel.H/2 - tileSize/2, null);

			}
		}
	}

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
