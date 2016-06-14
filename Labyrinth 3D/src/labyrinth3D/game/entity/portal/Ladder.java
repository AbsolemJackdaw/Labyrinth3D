package labyrinth3D.game.entity.portal;


import java.awt.Graphics2D;

import labyrinth3D.game.entity.Entity;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.ImageLoader.Texture;

public class Ladder extends Entity {

	private boolean isActive;
	private Texture progress[] = new Texture[]{Texture.tex_ladder, Texture.tex_ladder_sport1, Texture.tex_ladder_sport2, Texture.tex_ladder_sport3};

	public Ladder(int size, int id) {
		super(size, id);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

	}

	@Override
	public void update() {
		super.update();
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
