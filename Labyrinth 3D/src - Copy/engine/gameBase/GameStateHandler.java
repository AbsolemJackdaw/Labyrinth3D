package engine.gameBase;

import gameStates.GameStateMaze3D;
import gameStates.GameStateMenu;

import java.awt.Graphics2D;

public class GameStateHandler {

	public int currentGameState;

	public static final int MENU = 0;
	public static final int GAME = 1;
	public static final int NAMING = 2;
	public static final int CREATION = 3;
	public static final int ISLAND = 4;
	public static final int MAZE = 5;

	public static final GameState[] states = new GameState[6];

	public GameStateHandler() {
		currentGameState = MENU;

		loadState(MENU);
	}

	public int getCurrentGameState() {
		return currentGameState;
	}


	public void draw(Graphics2D g) {
		states[currentGameState].draw(g);
	}

	public void update() {
		states[currentGameState].update();
	}

	public void changeGameState(int state) {
		unloadState(currentGameState);
		currentGameState = state;
		loadState(state);
	}

	private void unloadState(int state){
		states[state] = null;
	}

	private void loadState(int state){
		switch(state){
		case MENU:
			states[state] = new GameStateMenu(this);
			break;
		case MAZE:
			states[state] = new GameStateMaze3D(this);
			break;
			
//		case GAME:
//			states[state] = new Game(this);
//			break;
//		case CREATION:
//			states[state] = new CharacterCreation(this);
//			break;
//		case NAMING:
//			states[state] = new NamePanel(this);
//			break;
//		case ISLAND:
//			states[state] = new Island(this);
//			break;
		}
	}
}
