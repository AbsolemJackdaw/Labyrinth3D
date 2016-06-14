package labyrinth3D.rscMngr;


public class Texture {
	public int[] pixels;
	public int SIZE;

	public Texture() {

	}

	public Texture(int size) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public int getSIZE() {
		return SIZE;
	}
}
