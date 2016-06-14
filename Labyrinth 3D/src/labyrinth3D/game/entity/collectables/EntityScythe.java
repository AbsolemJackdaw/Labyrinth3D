package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.Texture;
import labyrinth3D.rscMngr.TextureLoader;

public class EntityScythe extends EntityAid {

	public EntityScythe(int size) {
		super(size, SCYTHE);
	}

	@Override
	public Texture getTexture() {
		return TextureLoader.tex_scythe;
	}
}
