package gameHelper;

import gameInit.Boundaries;
import gameInit.Dimensions;

import javax.swing.ImageIcon;

/**
 * 
 * Brick class to be used for creating brick objects in the game.
 *
 */

public class Brick extends Dimensions implements Boundaries {

	String brickie = "img/blue.png";

	boolean destroyed;
	ImageIcon myImageicon;

	public Brick(int x, int y) {
		this.x = x;
		this.y = y;

		myImageicon = new ImageIcon(getClass().getClassLoader().getResource(
				brickie));
		image = myImageicon.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);

		destroyed = false;
	}

	/*
	 * This constructor is only used by the JUnit test case to avoid the X11 -
	 * Bamboo Testing Failure Issue.
	 */
	public Brick(int x, int y, boolean withoutImageIcon) {
		this.x = x;
		this.y = y;
		if (withoutImageIcon) {
			width = 70;
			height = 20;
		} else {
			myImageicon = new ImageIcon(getClass().getClassLoader()
					.getResource(brickie));
			image = myImageicon.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
		}
		destroyed = false;
	}

	/*
	 * Getters and Setters
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

}
