package labyrinth3D.rscMngr;


public class Texture {
	public int[] pixels;
	public int SIZE;

	public Texture() {

	}

	public Texture(int size, int[] pixelArray) {
		SIZE = size;
		pixels = pixelArray;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public int getSIZE() {
		return SIZE;
	}
}
