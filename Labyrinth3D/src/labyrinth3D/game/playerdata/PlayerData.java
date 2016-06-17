package labyrinth3D.game.playerdata;

import java.util.ArrayList;

import labyrinth3D.game.entity.collectables.Card;

public class PlayerData {


	public static ArrayList<Card> cards = new ArrayList<Card>();
	
	//resets everytime you exit a maze
	public static ArrayList<Card> currentlyCollectedCards = new ArrayList<Card>();

	public static boolean hasVisitedSmith;

	public static boolean hasWarfSword = false;
	
	public static int collectedKeys = 0;

	public static int attackSpeed = 5;
	
	public static int positionForNextLevelX = 0;
	public static int positionForNextLevelY = 0;

}
