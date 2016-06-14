package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.ImageLoader.Texture;

public class EntityGrail extends EntityAid {

	public EntityGrail(int size) {
		super(size, GRAIL);
	}

	@Override
	public Texture getTexture() {
		return Texture.tex_grail;
	}
}
