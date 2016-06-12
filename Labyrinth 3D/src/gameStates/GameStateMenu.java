package gameStates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import utility.Utility;
import engine.gameBase.GamePanel;
import engine.gameBase.GameState;
import engine.gameBase.GameStateHandler;
import engine.gameBase.KeyHandler;
import gameStates.content.Images;
import gameStates.maze.Texture;


public class GameStateMenu extends GameState {

	Font fontTitle = new Font("Arial", Font.ITALIC, 50);
	Font font = new Font("Arial", Font.BOLD, 20);

	private int titlePosX = GamePanel.W/2 - 100;
	private int titlePosY = 200;

	private double alpha;
	private boolean counting;
	private float alpha2;

	BufferedImage bg;

	public GameStateMenu(GameStateHandler gsh) {
		
		System.out.println("launched menu");
		
		counting = true;
		this.gsh = gsh;
		
		load();
	}
	
	private void load(){
		
		//dont start or stop icon. it doesn't exist yet ! it exists AFTER this
//		Utility.startLoadingIcon();

		new SwingWorker<Integer, Void>() {

			@Override
			protected Integer doInBackground() {

				try {
					new Images().load();
					new Texture().loadTextures();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void done() {
				super.done();
				Utility.stopLoadingIcon();
			}
		}.execute();
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.W, GamePanel.H);

		if(alpha2 < 1f)
			alpha2 += 0.005f;

		if(bg != null)
		{
			g.setComposite(AlphaComposite.SrcOver.derive(Math.min(1.0f, alpha2)));
			g.drawImage(bg, 0, 0, GamePanel.W, GamePanel.H, null);
		}
		g.setComposite(AlphaComposite.SrcOver.derive(1.0f));

		g.setFont(fontTitle);
		g.setColor(Color.white);
		g.drawString("Labyrinth", titlePosX-1, titlePosY +1);
		g.setColor(Color.darkGray);
		g.drawString("Labyrinth", titlePosX, titlePosY);

		g.setFont(font);

		if(alpha >= 1D)
			counting = false;
		if(alpha <= 0.1D)
			counting = true;

		if(counting)
			alpha+=.01D;
		else
			alpha-=.01D;

		Color c = new Color(1f,1f,1f,(float)alpha);

		g.setColor(c);
		g.drawString("Press Enter to Start", titlePosX, titlePosY+50);
	}

	@Override
	public void update() {

		if(bg == null){
			bg = loadBG();
		}

		if(KeyHandler.isPressed(KeyHandler.ENTER)){
//			gsh.changeGameState(GameStateHandler.NAMING);
			gsh.changeGameState(GameStateHandler.MAZE_10);

		}

		//assuring save folder creation
		File f = new File("saves");
		if(!f.exists()){
			f.mkdir();
		}
	}


	private BufferedImage loadBG(){
		BufferedImage tempImg = null;
		try {
			tempImg = ImageIO.read(this.getClass().getResourceAsStream("/background/menuScreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(tempImg != null)
			return tempImg;

		System.out.println("menu bg could not be loaded");
		return null;

	}
}
