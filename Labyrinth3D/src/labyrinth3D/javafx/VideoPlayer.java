package labyrinth3D.javafx;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.Main;

public class VideoPlayer {


	public static void playVideo(String fileName){

		String path = VideoPlayer.class.getClass().getResource(fileName).toExternalForm();
		
		Media m = new Media(path);
		MediaPlayer player = new MediaPlayer(m);
		MediaView viewer = new MediaView(player);

		StackPane root = new StackPane();
		root.getChildren().add(viewer);
		
		Main.gameStage.setScene(new Scene(root, GamePanel.W, GamePanel.H));
		Main.gameStage.show();
		
		player.play();

	}

}
