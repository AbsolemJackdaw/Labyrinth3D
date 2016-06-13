package labyrinth3D.game.entity.collectables;

import java.awt.image.BufferedImage;

import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.ImageLoader.Texture;

import static labyrinth3D.rscMngr.ImageLoader.*;

public class EntityWeapon extends EntityCollectible {

	public EntityWeapon(int size, int id) {
		super(size, id);
	}

	@Override
	public BufferedImage getImage() {
		return warfsword;
	}
	
	@Override
	public void onPickUp() {
		super.onPickUp();
		
		PlayerData.hasWarfSword = true;
	}
	
	@Override
	public Texture getTexture() {
		return Texture.tex_warfsword;
	}
}
