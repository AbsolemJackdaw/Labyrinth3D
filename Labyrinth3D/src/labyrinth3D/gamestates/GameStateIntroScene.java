package labyrinth3D.gamestates;

import static labyrinth3D.utility.Scale.scale;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.engine.KeyHandler;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.javafx.VideoPlayer;

public class GameStateIntroScene extends GameState{

	public GameStateIntroScene(GameStateHandler gsh) {
		this.gsh = gsh;
		VideoPlayer.addVideo("/clips/Intro_seq.mp4");
		VideoPlayer.playVideo();
	}


	@Override
	public void update() {

		if( VideoPlayer.isVideoStopped() || KeyHandler.isPressed(KeyHandler.ENTER)) {
			
			VideoPlayer.endVideo();
			PlayerData.positionForNextLevelX = GamePanel.W/2;
			PlayerData.positionForNextLevelY = GamePanel.H/2 + scale(200);
			gsh.changeGameState(GameStateHandler.CRASH);
		}
	}
}
