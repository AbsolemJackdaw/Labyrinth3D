package labyrinth3D.engine;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import labyrinth3D.gamestates.maze3D.Camera;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static int W = 1280; //64*16 & 64*9
	public static int H = 720;

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

	public GamePanel() {

		setPreferredSize(new Dimension(W, H));
		setFocusable(true);
		requestFocus();

		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
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

		//Best Update System I found on the net !
		//http://entropyinteractive.com/2011/02/game-engine-design-the-game-loop/
		//thanksx1000 to this dude, as well as cuddos

		// convert the time to seconds
		double nextTime = (double)System.nanoTime() / 1000000000.0;
		double maxTimeDiff = 0.5;
		int skippedFrames = 1;
		int maxSkippedFrames = 5;
		double delta = 1.0/60.0;

		while(running)
		{
			// convert the time to seconds
			double currTime = (double)System.nanoTime() / 1000000000.0;
			if((currTime - nextTime) > maxTimeDiff)
				nextTime = currTime;

			if(currTime >= nextTime){

				// assign the time for the next update
				nextTime += delta;
				update();

				if((currTime < nextTime) || (skippedFrames > maxSkippedFrames))
				{
					draw();
					drawToScreen();
					skippedFrames = 1;
				}
				else
				{
					skippedFrames++;
				}
			}
			else
			{
				// calculate the time to sleep
				int sleepTime = (int)(1000.0 * (nextTime - currTime));
				// sanity check
				if(sleepTime > 0)
				{
					// sleep until the next update
					try
					{
						Thread.sleep(sleepTime);
					}
					catch(InterruptedException e)
					{
						// do nothing
					}
				}
			}
		}
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
		ghs.update();
		KeyHandler.update();
		//		MouseHandler.update();
	}

	private void init() {

		System.out.println("launching...");

		image = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		running = true;

		ghs = new GameStateHandler();

	}

	public static int[] getScreenPixels(){
		return pixels;
	}
}
