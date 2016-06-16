package labyrinth3D.game.entity.portal;


import labyrinth3D.game.entity.Entity;
import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

public class Portal extends Entity {

	private boolean isActive;
	private int animationCounter = 0;
	private int animationTextureReference = 0;
	
	private Texture animation[] = new Texture[]{TextureLoader.tex_portal_1, TextureLoader.tex_portal_2, TextureLoader.tex_portal_3};

	public Portal(int size, int id) {
		super(size, id);
	}

	@Override
	public void update() {
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

		return TextureLoader.tex_portal;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
