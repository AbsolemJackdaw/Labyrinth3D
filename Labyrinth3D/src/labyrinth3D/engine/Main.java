package labyrinth3D.engine;

import javax.swing.JFrame;

import javafx.application.Application;
import javafx.stage.Stage;
import labyrinth3D.utility.Window;




public class Main extends Application{

	public static GamePanel gamepanel = null;
	
	public static void main(String[] args) {
		launch();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		new Window();
		
		gamepanel = new GamePanel();
		
		JFrame frame = new JFrame("Labyrinth");
		frame.setContentPane(gamepanel);
		
		//fullscreen without borders
		frame.setUndecorated(true);
		//set size before relative location, or it wont be centered
		frame.setResizable(false);
		frame.requestFocusInWindow();
		//fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
