package labyrinth3D.gamestates;

import static labyrinth3D.utility.Scale.scale;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.engine.KeyHandler;
import labyrinth3D.game.entity.Player;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.gamestates.bounds.Doorway;
import labyrinth3D.rscMngr.ImageLoader;

public class GameStateIntro extends GameState {

	private Player player;

	private Rectangle[] boundingBox;

	private Rectangle leftWall;
	private Rectangle rightWall;
	private Rectangle bottomWall;
	private Line2D.Double boardingWall;

	private Doorway door;

	float alpha = 1f;

	public GameStateIntro(GameStateHandler gsh) {
		this.gsh = gsh;
		player = new Player();

		boundingBox = new Rectangle[]{
				new Rectangle(player.positionX+scale(24), player.positionY-scale(10), scale(16), scale(2)), //up
				new Rectangle(player.positionX+scale(24), player.positionY+scale(8), scale(16), scale(2)), //down
				new Rectangle(player.positionX+scale(22), player.positionY-scale(8), scale(2), scale(16)), //left
				new Rectangle(player.positionX+scale(40), player.positionY-scale(8), scale(2), scale(16)), //right
		};

		leftWall = new Rectangle(scale(460), 0, scale(50), GamePanel.H);
		rightWall = new Rectangle(GamePanel.W-scale(390), 0, scale(50), GamePanel.H);
		bottomWall = new Rectangle(0, GamePanel.H - scale(70), GamePanel.W, scale(50));
//		boardingWall = new Rectangle(0, GamePanel.H - scale(70), GamePanel.W, scale(50));
		boardingWall = new Line2D.Double(scale(600), scale(432), scale(650), scale(450));
		
		door = new Doorway(scale(128), 0, GamePanel.W/2 - 4, GamePanel.H/2 - scale(20));
	}

	@Override
	public void update() {
		
		if(alpha > 0)
			alpha-=0.0025f;
		
		boardingWall = new Line2D.Double(scale(600), scale(432), scale(635), scale(495)) ; //new Rectangle(scale(600), scale(432), scale(30), scale(30));
		
		doPlayerMovement();
		player.update();
		
		if(boundingBox[1].intersects(door.getBoundingBox())){
			PlayerData.positionForNextLevelX = scale(80);
			PlayerData.positionForNextLevelY = scale(490);
			gsh.changeGameState(GameStateHandler.ISLAND);

		}

	}

	@Override
	public void draw(Graphics2D g) {

		g.drawImage(ImageLoader.crashBackGround, 0, 0, GamePanel.W, GamePanel.H, null);
		float scaled = ( 
				//relate player position to middle > max 
				((float)player.positionY - (float)GamePanel.H/2)
				/ 
				//calc max
				((float)GamePanel.H - scale(250)))
				//multiply by scale needed
				*2f;

		float scaled2 = 64f*scaled;
		int scale = (int)(scaled2);

		g.setColor(Color.blue);
		g.draw(leftWall);
		g.draw(rightWall);
		g.draw(bottomWall);
		g.draw(boardingWall);
		g.draw(door.getBoundingBox());

		for(Rectangle r : boundingBox)
			g.draw(r);

		player.draw(g, scale(scale), player.positionX + scale(34 - (scale/2)) , player.positionY- scale(scale - 10));
		
		if(alpha > 0) {
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, GamePanel.W, GamePanel.H);
		}
	}

	private void doPlayerMovement(){

		if(KeyHandler.keyState[KeyHandler.RIGHT]){
			if(!boundingBox[3].intersects(rightWall) && !boundingBox[3].intersectsLine(boardingWall)) {
				player.movePlayerRight(1);

				for(Rectangle r : boundingBox)
					r.x+=1;
			}
		}

		if(KeyHandler.keyState[KeyHandler.LEFT]){
			if(!boundingBox[2].intersects(leftWall)) {
				player.movePlayerLeft(1);

				for(Rectangle r : boundingBox)
					r.x-=1;
			}
		}

		if(KeyHandler.keyState[KeyHandler.UP]){
			if(!boundingBox[1].intersects(door.getBoundingBox()) && !boundingBox[0].intersectsLine(boardingWall)) {
				player.movePlayerUp(1);

				for(Rectangle r : boundingBox)
					r.y-=1;
			}
		}

		if(KeyHandler.keyState[KeyHandler.DOWN]){
			if(!boundingBox[1].intersects(bottomWall)) {
				player.movePlayerDown(1);
				for(Rectangle r : boundingBox)
					r.y+=1;
			}
		}
	}
}
