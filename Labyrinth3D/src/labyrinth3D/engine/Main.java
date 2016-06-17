package labyrinth3D.engine;

import javax.swing.JFrame;

import javafx.application.Application;
import javafx.stage.Stage;




public class Main extends Application{

	public static GamePanel gamepanel = null;
	
	public static void main(String[] args) {
		launch();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		gamepanel = new GamePanel();
		
		JFrame frame = new JFrame("Labyrinth");
		frame.setContentPane(gamepanel);
		//set size before relative location, or it wont be centered
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
