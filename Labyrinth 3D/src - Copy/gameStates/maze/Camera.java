package gameStates.maze;

import java.awt.event.KeyEvent;


public class Camera{
	
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
//	private static boolean left, right, forward, back, enter;
	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;
	
	public Camera(double x, double y, double xd, double yd, double xp, double yp) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
	}
	
	public double getxPos() {
		return xPos;
	}
	public double getyPos() {
		return yPos;
	}
	
	public void update(int[][] map) {
		if(keyState[Forward]) {
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
				xPos+=xDir*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
				yPos+=yDir*MOVE_SPEED;
		}
		if(keyState[Back]) {
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
				yPos-=yDir*MOVE_SPEED;
		}
		if(keyState[Right]) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
		}
		if(keyState[Left]) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
		}
	}

	public static final int NUM_KEYS = 5;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int Forward = 0;
	public static int Back = 1;
	public static int Left = 2;
	public static int Right = 3;
	public static int Enter = 4;

	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++)
			if (keyState[i])
				return true;
		return false;
	}

	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}

	public static boolean isHeld(int i) {
		return keyState[i] && prevKeyState[i];
	}

	public static void keySet(int i, boolean b){

		if (i == KeyEvent.VK_A)
			keyState[Enter] = b;
		else if (i == KeyEvent.VK_UP){
			keyState[Forward] = b;
		}else if (i == KeyEvent.VK_DOWN){
			keyState[Back] = b;
		}else if (i == KeyEvent.VK_LEFT){
			keyState[Left] = b;
		}else if (i == KeyEvent.VK_RIGHT){
			keyState[Right] = b;
		}
	}	
}
