package labyrinth3D.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utility {

	public static final Font FONT_HEADER_SMALL = new Font("Constantia", Font.PLAIN, 18);

	
	 /**
	 * rotates a bufferedimage.
	 * 
	 * returns the rotated instance
	 * 
	 * image has to be square
	 */
	public static BufferedImage rotateImage(BufferedImage item, double rotation){
		return rotateImage(item, rotation, item.getWidth() + 20, 20/2);
	}

	public static BufferedImage rotateImage(BufferedImage item, double rotation, int size, int extra){

		//create blank canvas that is bigger the the image drawn.
		BufferedImage canvas = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

		//get grapchics from the canvas
		Graphics2D g2d = (Graphics2D) canvas.getGraphics();

		//		g2d.setColor(new Color(0,0,1,0.5f));
		//		g2d.fillRect(0, 0, size, size);

		//rotate canvas internally
		if(rotation < 0)
			g2d.rotate(-Math.toRadians(rotation), size/2, size/2);
		else
			g2d.rotate(Math.toRadians(rotation), size/2, size/2);

		//		g2d.setColor(new Color(0,1,0,0.5f));
		//		g2d.fillRect(0, 0, size, size);
		//		
		//		g2d.drawRect(0, size/2, size, 1);
		//		g2d.drawRect(size/2, 0, 1, size);

		//draw image centered, extra/2
		g2d.drawImage(item, extra, extra, null);

		return canvas;
	}
	
	/**Draws a centered string where x and y are the center of that string*/
	public static void drawCenteredStringWithShadow(Graphics2D g, String text, Font f, int x, int y, Color top, Color shadow){

		g.setFont(f);
		
		FontMetrics metrics = g.getFontMetrics(f);

		int textWidth = metrics.stringWidth(text);
		int textHeight = metrics.getHeight();

		for(int i = 0; i < 2; i++){

			if(i == 0){
				g.setColor(shadow);
				g.drawString(text, x - (textWidth/2) + 1, y - (textHeight/2) + 1);

			}
			else{
				g.setColor(top);
				g.drawString(text, x - (textWidth/2), y - (textHeight/2));
			}
		}
	}

	
	public static void startLoadingIcon(){
//		GamePanelExtended.drawLoadingIcon = true;
	}
	
	public static void stopLoadingIcon(){
//		GamePanelExtended.drawLoadingIcon = false;
	}
}
