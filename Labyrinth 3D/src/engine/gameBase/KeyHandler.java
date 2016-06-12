package engine.gameBase;

import java.awt.event.KeyEvent;

public class KeyHandler {
	public static final int NUM_KEYS = 10;

	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];

	public static int UP = 0;
	public static int DOWN = 1;
	public static int LEFT = 2;
	public static int RIGHT = 3;
	public static int SPACE = 4;
	public static int ENTER = 5;
	public static int BACKSPACE = 6;
	public static int SHIFT = 7;
	public static int AnyOtherKey = 8;

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

		if (i == KeyEvent.VK_ENTER)
			keyState[ENTER] = b;
		else if (i == KeyEvent.VK_Z){
			keyState[UP] = b;
		}else if (i == KeyEvent.VK_S){
			keyState[DOWN] = b;
		}else if (i == KeyEvent.VK_Q){
			keyState[LEFT] = b;
		}else if (i == KeyEvent.VK_D){
			keyState[RIGHT] = b;
		}else if (i == KeyEvent.VK_SPACE){
			keyState[SPACE] = b;
		}else if (i == KeyEvent.VK_BACK_SPACE){
			keyState[BACKSPACE] = b;
		}else if (i == KeyEvent.VK_SHIFT){
			keyState[SHIFT] = b;
		}else{
			keyState[AnyOtherKey] = b;
			if(b)
				setOtherKey(KeyEvent.getKeyText(i));
		}
	}

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++)
			prevKeyState[i] = keyState[i];
	}

	private static String key;
	public static void setOtherKey(String s){
		key = s;
//		System.out.println(key);
	}
	public static String getOtherKey(){
		return key;
	}
}
