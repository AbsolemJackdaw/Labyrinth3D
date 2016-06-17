package labyrinth3D.gamestates;

import static labyrinth3D.utility.Scale.scale;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.engine.GameState;
import labyrinth3D.engine.GameStateHandler;
import labyrinth3D.engine.KeyHandler;
import labyrinth3D.rscMngr.ImageLoader;
import labyrinth3D.rscMngr.TextureLoader;

public class GameStateMenu extends GameState {

	Font fontTitle;
	Font font;

	private int titlePosX = GamePanel.W/2 - 100;
	private int titlePosY = 200;

	private double alpha;
	private boolean counting;
	private float alpha2;

	private boolean doneLoading;

	BufferedImage bg;
	
	public GameStateMenu(GameStateHandler gsh) {

		System.out.println("launched menu");

		counting = true;
		this.gsh = gsh;

		fontTitle = new Font("Arial", Font.ITALIC, scale(50));
		font = new Font("Arial", Font.BOLD, scale(20));
		
		load();
	}

	private void load(){

		new SwingWorker<Integer, Void>() {

			@Override
			protected Integer doInBackground() {

				try {
					new ImageLoader().load();
					new TextureLoader().loadTextures();

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("texture error. shutting down");
					System.exit(0);
				}
				return null;
			}

			@Override
			protected void done() {
				super.done();
				System.out.println("images done loading");
				doneLoading = true;
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
		if(doneLoading)
			g.drawString("Press Enter to Start", titlePosX, titlePosY+scale(50));
		else
			g.drawString("Loading...", titlePosX+scale(50), titlePosY+scale(50));

	}

	@Override
	public void update() {

		if(bg == null){
			bg = loadBG();
		}

		if(KeyHandler.isPressed(KeyHandler.ENTER) && doneLoading){
//			PlayerData.positionForNextLevelX = GamePanel.W/2;
//			PlayerData.positionForNextLevelY = GamePanel.H/2 + scale(200);
			
			gsh.changeGameState(GameStateHandler.INTRO);
			
//			gsh.changeGameState(GameStateHandler.MAZE_10);
		}

		//		//assuring save folder creation
		//		File f = new File("saves");
		//		if(!f.exists()){
		//			f.mkdir();
		//		}
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
