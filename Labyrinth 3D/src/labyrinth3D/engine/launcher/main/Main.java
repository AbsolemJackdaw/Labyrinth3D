package labyrinth3D.engine.launcher.main;

import javax.swing.JFrame;

import labyrinth3D.engine.gameBase.GamePanel;



public class Main {

	
	public static int W = GamePanel.W*12;
	public static int H = GamePanel.H*10;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Labyrinth");
		frame.setContentPane(new GamePanel());
		//set size before relative location, or it wont be centered
		frame.setSize(W, H);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
