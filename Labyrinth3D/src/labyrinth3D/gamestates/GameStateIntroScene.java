package labyrinth3D.gamestates;

import java.awt.Graphics2D;

import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.javafx.VideoPlayer;

public class GameStateIntroScene extends GameState{

	private int clipTimer = 0;
	private int clipTrack = 0;
	
	public GameStateIntroScene(GameStateHandler gsh) {
		this.gsh = gsh;
		
		VideoPlayer.playVideo("/clips/intro.mp4");

	}


	@Override
	public void update() {

//		clipTimer++;
//		
//		if( clipTrack >= 191 || KeyHandler.isPressed(KeyHandler.ENTER)) {
//			
//			PlayerData.positionForNextLevelX = GamePanel.W/2;
//			PlayerData.positionForNextLevelY = GamePanel.H/2 + scale(200);
//			gsh.changeGameState(GameStateHandler.CRASH);
//		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		
//		if(clipTimer % 3 == 0) {
//			clipTrack++;
//		}
//		
//		if(clipTrack < 191)
//		
//		g.drawImage(ImageLoader.intro[clipTrack], 0, 0, GamePanel.W, GamePanel.H, null);

	}
}
