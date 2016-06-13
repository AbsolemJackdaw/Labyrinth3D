package labyrinth3D.engine.gameBase;


import java.awt.Graphics2D;

import labyrinth3D.gamestates.GameStateIsland;
import labyrinth3D.gamestates.GameStateMaze3D;
import labyrinth3D.gamestates.GameStateMenu;
import labyrinth3D.gamestates.GameStateSmithy;

public class GameStateHandler {

	public int currentGameState;

	public static final int MENU = 0;
	public static final int GAME = 1;
	public static final int NAMING = 2;
	public static final int CREATION = 3;
	public static final int ISLAND = 4;
	public static final int MAZE_10 = 5;
	public static final int MAZE_20 = 6;
	public static final int MAZE_50 = 7;
	public static final int SMITHY = 8;

	public static final GameState[] states = new GameState[10];

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
		case MAZE_10:
			states[state] = new GameStateMaze3D(this, 10, 3);
			break;
		case ISLAND:
			states[state] = new GameStateIsland(this);
			break;
		case SMITHY:
			states[state] = new GameStateSmithy(this);
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
