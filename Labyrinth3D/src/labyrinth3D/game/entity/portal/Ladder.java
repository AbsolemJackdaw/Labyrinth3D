package labyrinth3D.game.entity.portal;


import labyrinth3D.game.entity.Entity;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

public class Ladder extends Entity {

	private boolean isActive;
	private Texture progress[] = new Texture[]{TextureLoader.tex_ladder, TextureLoader.tex_ladder_sport1, TextureLoader.tex_ladder_sport2, TextureLoader.tex_ladder_sport3};

	public Ladder(int size, int id) {
		super(size, id);
	}

	@Override
	public Texture getTexture() {

		return progress[PlayerData.collectedKeys];
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public double getMoveOffset() {
		return -64;
	}
	
	@Override
	public double getScaleY() {
		return 0.8;
	}
}
