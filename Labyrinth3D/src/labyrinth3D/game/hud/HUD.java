package labyrinth3D.game.hud;

import java.awt.Graphics2D;

import labyrinth3D.engine.GamePanel;
import labyrinth3D.game.playerdata.PlayerData;
import labyrinth3D.gamestates.GameStateMaze3D;
import labyrinth3D.gamestates.maze3D.Camera;
import labyrinth3D.rscMngr.ImageLoader;

public class HUD {

	MiniMap minimap;
	GameStateMaze3D gs;

	public HUD(GameStateMaze3D gs) {
		minimap = new MiniMap();
		this.gs = gs;
	}

	public MiniMap getMinimap() {
		return minimap;
	}

	public void draw(Graphics2D g) {

		if(PlayerData.hasWarfSword) {

			g.drawImage(
					ImageLoader.fp_weapons.getSubimage(0, 0, 297, 576), 
					650-gs.attackTime, 55 + gs.attackTime*2, 
					297 - gs.attackTime*2, 576 - gs.attackTime*2, 
					null);

		}

		if(!gs.debug)
			if(Camera.isPressed(Camera.Map))
				minimap.draw(g);

		g.drawImage(ImageLoader.hud, 0, 0, GamePanel.W, GamePanel.H, null);
	}

	public void update() {

		if(!gs.debug)
			minimap.update(gs.camera);
	}
}
