package labyrinth3D.game.entity.collectables;

import static labyrinth3D.rscMngr.ImageLoader.warfsword;

import java.awt.image.BufferedImage;

import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.rscMngr.Texture;

public class EntityWeapon extends EntityCollectible {

	public EntityWeapon(int size) {
		super(size, WARFSWORD);
	}

	@Override
	public BufferedImage getImage() {
		return warfsword;
	}
	
	@Override
	public void onPickUp() {
		
		PlayerData.hasWarfSword = true;
	}
	
	@Override
	public Texture getTexture() {
		return Texture.tex_warfsword;
	}
}
