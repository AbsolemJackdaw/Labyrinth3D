package game.entity.Portal;

import java.awt.Graphics2D;

import game.entity.Entity;

public class Portal extends Entity {

	private boolean isActive;
	private int animationCounter = 0;
	private int animationTextureReference = 5;

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
	public int getTextureIndex() {

		if(isActive) {
			if(animationCounter%10 == 0) {
				animationTextureReference ++;
			}

			if(animationTextureReference > 7)
				animationTextureReference = 5;

			return animationTextureReference;
		}

		return 4;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
