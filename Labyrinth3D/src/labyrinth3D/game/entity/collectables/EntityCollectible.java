package labyrinth3D.game.entity.collectables;


import labyrinth3D.game.entity.Entity;

public class EntityCollectible extends Entity{

	public static final int SPORT = 0;
	public static final int WARFSWORD = 1;
	public static final int FEATHER = 2;
	public static final int CAPE = 3;
	public static final int GRAIL = 4;
	public static final int SCYTHE = 5;

	public EntityCollectible(int size, int id) {
		super(size, id);
	}

	public void onPickUp() {
	}
}
