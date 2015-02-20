package gameInit;

import java.awt.Image;
import java.awt.Rectangle;

/**
 * 
 * Dimensions that stores all the coordinates of the object
 *
 */

public class Dimensions {
	public int x;
	public int y;
	public int width;
	public int height;
	public Image image;

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
}
