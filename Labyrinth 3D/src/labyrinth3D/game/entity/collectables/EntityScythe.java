package labyrinth3D.game.entity.collectables;

import labyrinth3D.rscMngr.Texture;

public class EntityScythe extends EntityAid {

	public EntityScythe(int size) {
		super(size, SCYTHE);
	}

	@Override
	public Texture getTexture() {
		return Texture.tex_scythe;
	}
}
