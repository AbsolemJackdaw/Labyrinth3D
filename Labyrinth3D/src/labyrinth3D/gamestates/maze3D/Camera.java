package labyrinth3D.gamestates.maze3D;

import java.awt.event.KeyEvent;


public class Camera{
	
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public final double MOVE_SPEED = .04;
	public final double ROTATION_SPEED = .033;
	
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
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0)
				yPos+=yDir*MOVE_SPEED;
		}
		if(keyState[Back]) {
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)] == 0)
				yPos-=yDir*MOVE_SPEED;
		}
		if(keyState[StrafeRight]) {
			if(map[(int)(xPos + yDir * (MOVE_SPEED/2))][(int)yPos] == 0) {
				xPos+=yDir*(MOVE_SPEED/2);
			}
			if(map[(int)xPos][(int)(yPos - xDir * (MOVE_SPEED/2))] == 0)
				yPos-=xDir*(MOVE_SPEED/2);
		}
		if(keyState[StrafeLeft]) {
			if(map[(int)(xPos - yDir * (MOVE_SPEED/2))][(int)yPos] == 0) {
				xPos-=yDir*(MOVE_SPEED/2);
			}
			if(map[(int)xPos][(int)(yPos + xDir * (MOVE_SPEED/2))] == 0)
				yPos+=xDir*(MOVE_SPEED/2);
		}
		
		//turning
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

	public static final int NUM_KEYS = 8;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int Forward = 0;
	public static int Back = 1;
	public static int Left = 2;
	public static int Right = 3;
	public static int StrafeLeft = 4;
	public static int StrafeRight = 5;
	public static int T = 6;
	public static int Map = 7;
	
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

		if (i == KeyEvent.VK_T)
			keyState[T] = b;
		else if (i == KeyEvent.VK_LEFT){
			keyState[Left] = b;
		}else if (i == KeyEvent.VK_RIGHT){
			keyState[Right] = b;
		}
		
		else if (i == KeyEvent.VK_Z || i == KeyEvent.VK_W){
			keyState[Forward] = b;
		}else if (i == KeyEvent.VK_Q|| i == KeyEvent.VK_A){
			keyState[StrafeLeft] = b;
		}else if (i == KeyEvent.VK_S){
			keyState[Back] = b;
		}else if (i == KeyEvent.VK_D){
			keyState[StrafeRight] = b;
		}
		
		else if (i == KeyEvent.VK_F){
			keyState[Map] = b;
		}
	}	
}
