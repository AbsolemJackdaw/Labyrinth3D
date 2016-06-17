package labyrinth3D.javafx;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.Main;

public class VideoPlayer {

	private static MediaPlayer mediaPlayer = null;
	private static MediaView viewer = null;
	private static StackPane root;
	
	public static void addVideo(String fileName){

		String path = VideoPlayer.class.getClass().getResource(fileName).toExternalForm();
		
		Media m = new Media(path);
		mediaPlayer = new MediaPlayer(m);
		viewer = new MediaView(mediaPlayer);

		mediaPlayer.setCycleCount(1);
		
		root = new StackPane();
		root.getChildren().add(viewer);
		
		Main.gamepanel.setScene(new Scene(root, GamePanel.W, GamePanel.H));

	}
	
	public static void playVideo() {
		mediaPlayer.play();
	}
	
	public static boolean isPlaying() {
		return mediaPlayer == null ? false : mediaPlayer.getStatus().equals(Status.PLAYING);
	}
	
	public static boolean isVideoStopped() {
		return mediaPlayer == null ? false : mediaPlayer.getCurrentCount() > 0 ; //getStatus().equals(Status.STOPPED);
	}
	
	public static void endVideo(){
		mediaPlayer.stop();
		mediaPlayer = null;
		viewer = null;
		Main.gamepanel.setScene(null);
	}
}
