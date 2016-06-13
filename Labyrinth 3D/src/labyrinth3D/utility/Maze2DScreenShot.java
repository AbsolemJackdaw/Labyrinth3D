package labyrinth3D.utility;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class Maze2DScreenShot {

	
	int mazeSizeFixed;
	BufferedImage[][] labyrinthGrid;
	int tileSize;
	
	public Maze2DScreenShot(int mazeSizeFixed, BufferedImage[][] labyrinthGrid, int tileSize) {
	
		this.mazeSizeFixed = mazeSizeFixed;
		this.labyrinthGrid = labyrinthGrid;
		this.tileSize = tileSize;
		
		printScreen();
		
	}
	
	private void draw(Graphics2D g){
		for(int x = 0; x < mazeSizeFixed;x++){
			for(int y = 0; y < mazeSizeFixed;y++){

				BufferedImage wall= labyrinthGrid[x][y];

				g.drawImage(wall, 
						x*tileSize , 
						y*tileSize , null);

			}
		}
	}
	
	private void printScreen(){
		Dimension size = new Dimension(mazeSizeFixed*tileSize, mazeSizeFixed*tileSize);
		BufferedImage img = new BufferedImage (size.width, size.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = img.getGraphics ();
		draw((Graphics2D) g);
		g.dispose ();
		try
		{
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minutes = cal.get(Calendar.MINUTE);
			int seconds = cal.get(Calendar.SECOND);

			String s = String.valueOf(year)+ "y" +
					String.valueOf(month+1) + "m" +
					String.valueOf(day) + "d" +
					String.valueOf(hour) + "h" + 
					String.valueOf(minutes) + "m" + 
					String.valueOf(seconds)+ "s.png";

			String dir = "End Results";
			File f = new File(dir);
			if(!f.exists())
				f.mkdir();

			ImageIO.write (img, "png", new File(dir + File.separator + s));

			System.out.println("printed screen");
		}
		catch (IOException ex)
		{
			ex.printStackTrace ();
			System.out.println("printing screen failed");
		}
	}
}
