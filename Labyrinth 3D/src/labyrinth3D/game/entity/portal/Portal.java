package labyrinth3D.game.entity.portal;


import java.awt.Graphics2D;

import labyrinth3D.game.entity.Entity;
import labyrinth3D.rscMngr.Texture;

public class Portal extends Entity {

	private boolean isActive;
	private int animationCounter = 0;
	private int animationTextureReference = 0;
	
	private Texture animation[] = new Texture[]{Texture.tex_portal_1, Texture.tex_portal_2, Texture.tex_portal_3};

	public Portal(int size, int id) {
		super(size, id);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

	}

	@Override
	public void update() {
		super.update();

		if(isActive)
			animationCounter++;
	}

	@Override
	public Texture getTexture() {

		if(isActive) {
			if(animationCounter%10 == 0) {
				animationTextureReference ++;
			}

			if(animationTextureReference > 2)
				animationTextureReference = 0;

			return animation[animationTextureReference];
		}

		return Texture.tex_portal;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
