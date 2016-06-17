package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

public class EntityGrail extends EntityAid {

	public EntityGrail(int size) {
		super(size, GRAIL);
	}

	@Override
	public Texture getTexture() {
		return TextureLoader.tex_grail;
	}
}
