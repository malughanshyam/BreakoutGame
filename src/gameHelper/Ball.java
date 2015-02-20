package gameHelper;

import gameInit.Boundaries;
import gameInit.Constants;
import gameInit.Dimensions;

import javax.swing.ImageIcon;

/**
 * 
 * Class for the Ball
 * 
 */

public class Ball extends Dimensions implements Boundaries {
	private int xdir;
	private int ydir;
	protected String ball_path = "img/fire_ball.png";
	ImageIcon myImageicon;

	public Ball() {
		xdir = 1;
		ydir = -1;
		myImageicon = new ImageIcon(getClass().getClassLoader().getResource(
				ball_path));
		image = myImageicon.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		resetState();
	}

	/*
	 * This constructor is only used by the JUnit test case to avoid the X11 -
	 * Bamboo Testing Failure Issue.
	 */
	public Ball(boolean withoutImageIcon) {
		xdir = 1;
		ydir = -1;
		if (withoutImageIcon) {
			width = 16;// image.getWidth(null);
			height = 16;// image.getHeight(null);
		} else {
			myImageicon = new ImageIcon(getClass().getClassLoader()
					.getResource(ball_path));
			image = myImageicon.getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
		}
		resetState();
	}

	/*
	 * Moves the ball in the direction of xdir and ydir
	 */
	public void move() {

		x += xdir;
		y += ydir;

		if (x == 0) {
			setXDir(1);
		}

		if (x == Boundaries.BALL_RIGHT) {
			setXDir(-1);
		}

		if (y == 0) {
			setYDir(1);
		}
	}

	/*
	 * Resets the ball coordinates
	 */
	public void resetState() {
		x = Constants.WIN_WIDTH / 2;
		y = 450;
	}

	/*
	 * Setters and Getters
	 */
	public void setXDir(int x) {
		xdir = x;
	}

	public int getXDir() {
		return xdir;
	}

	public void setYDir(int y) {
		ydir = y;
	}

	public int getYDir() {
		return ydir;
	}

}
