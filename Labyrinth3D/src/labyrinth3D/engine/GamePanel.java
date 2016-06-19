package labyrinth3D.engine;


import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import labyrinth3D.gamestates.maze3D.Camera;
import labyrinth3D.javafx.VideoPlayer;
import labyrinth3D.utility.Window;

@SuppressWarnings("serial")
public class GamePanel extends JFXPanel implements Runnable, KeyListener{

	public static int W = Window.getWidth();
	public static int H = Window.getHeight();

	// game thread
	private Thread thread;
	private boolean running;
	//frames per second
	//	private final int FPS = 60;
	//	private final long targetTime = 1000 / FPS;

	// image to get graphics from
	protected BufferedImage image;
	protected Graphics2D g;

	protected static int[] pixels;

	private GameStateHandler ghs;

	private static Robot robot;//mouse and keyboard helper
	private static JFrame topFrame;

	public static Cursor blankCursor;

	public GamePanel() {

		setPreferredSize(new Dimension(W, H));
		setFocusable(true);
		requestFocus();

		// Transparent 16 x 16 pixel cursor image.
		//		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);

		// Create a new blank cursor.
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		setCursor(blankCursor);

		System.out.println("GamePanel : Initializing game");
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void keyPressed(KeyEvent key) {
		KeyHandler.keySet(key.getKeyCode(), true);
		Camera.keySet(key.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent key) {
		KeyHandler.keySet(key.getKeyCode(), false);
		Camera.keySet(key.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent key) {
	}

	@Override
	public void run() {

		init();

		//		runGameLoop();
		runComplexLoop();

	}

	//finish drawing cycle
	private void drawToScreen() {

		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, W, H, null);
		g2.dispose();

	}

	//start Drawing cycle
	protected void draw() {
		ghs.draw(g);
	}

	protected void update() {
		if(this.hasFocus()) {
			ghs.update();
			KeyHandler.update();
		}
	}

	private void init() {

		System.out.println("launching...");

		image = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		running = true;

		ghs = new GameStateHandler();

		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		System.out.println(topFrame + " " + robot);

	}

	public static int[] getScreenPixels(){
		return pixels;
	}

	private void runComplexLoop() {
		//Best Update System I found on the net !
		//http://entropyinteractive.com/2011/02/game-engine-design-the-game-loop/
		//thanksx1000 to this dude, as well as cuddos

		// convert the time to seconds
		double lastTime = (double)System.nanoTime() / 1000000000.0;
		double maxTimeDiff = 0.5;
		int skippedFrames = 1;
		int maxSkippedFrames = 5;
		double targetUpdates = 1.0/60.0;

		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while(running)
		{
			// convert the time to seconds
			double currTime = (double)System.nanoTime() / 1000000000.0;

			if((currTime - lastTime) > maxTimeDiff)
				lastTime = currTime;

			if(currTime >= lastTime){

				// assign the time for the next update
				lastTime += targetUpdates;
				update();
				updates++;

				if((currTime < lastTime) || (skippedFrames > maxSkippedFrames)){

					if(!VideoPlayer.isPlaying()) {
						draw();
						drawToScreen();
					}
					skippedFrames = 1;
					frames++;
				}
				else
					skippedFrames++;
			}else{
				// calculate the time to sleep
				int sleepTime = (int)(1000.0 * (lastTime - currTime));

				// sanity check
				if(sleepTime > 0)
				{
					// sleep until the next update
					try{
						Thread.sleep(sleepTime);
					}catch(InterruptedException e){
					}
				}
			}

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;

			}
		}
	}
}
