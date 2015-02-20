package gameEngine;

import gameHelper.Ball;
import gameHelper.Brick;
import gameHelper.Paddle;
import gameInit.Boundaries;
import gameInit.Constants;

import java.awt.Point;
import java.util.ArrayList;

/**
 * 
 * Class used for the Game movement computations
 *
 */

public class ComputeCoordinates implements Constants {
	private Ball ball;
	private Paddle paddle;
	private Brick[] bricks;
	private int gameFlag;
	private int currentSecond, currentMinute;
	private String timeForDisplayClock;
	private int timerTracker;
	private Boolean allBrickDestroyedFlag;
	private String controlPanelLayout;

	public Boolean getAllBrickDestroyedFlag() {
		return allBrickDestroyedFlag;
	}

	public void setAllBrickDestroyedFlag(Boolean allBrickDestroyedFlag) {
		this.allBrickDestroyedFlag = allBrickDestroyedFlag;
	}

	public int getCurrentSecond() {
		return currentSecond;
	}

	public void setCurrentSecond(int currentSecond) {
		this.currentSecond = currentSecond;
	}

	public int getCurrentMinute() {
		return currentMinute;
	}

	public void setCurrentMinute(int currentMinute) {
		this.currentMinute = currentMinute;
	}

	public String getTimeForDisplayClock() {
		return timeForDisplayClock;
	}

	public void setTimeForDisplayClock(String timeForDisplayClock) {
		this.timeForDisplayClock = timeForDisplayClock;
	}

	public int getTimerTracker() {
		return timerTracker;
	}

	public void setTimerTracker(int timerTracker) {
		this.timerTracker = timerTracker;
	}

	public int getGameFlag() {
		return gameFlag;
	}

	public void setGameFlag(int gameFlag) {
		this.gameFlag = gameFlag;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	public Brick[] getBricks() {
		return bricks;
	}

	public void setBricks(Brick[] bricks) {
		this.bricks = bricks;
	}

	public String getControlPanelLayout() {
		return controlPanelLayout;
	}

	public void setControlPanelLayout(String controlPanelLayout) {
		this.controlPanelLayout = controlPanelLayout;
	}

	public ComputeCoordinates() {

		this.ball = new Ball();
		this.paddle = new Paddle();
		this.bricks = new Brick[TOTAL_BRICKS];
		this.setGameFlag(1);
		gameInit();
		setAllBrickDestroyedFlag(true);
		setTimerTracker(0);
	}

	/*
	 * This constructor is only used by the JUnit test case to avoid the X11 -
	 * Bamboo Testing Failure Issue.
	 */
	public ComputeCoordinates(boolean withoutImageIcon) {

		this.ball = new Ball(withoutImageIcon);
		this.paddle = new Paddle(withoutImageIcon);
		this.bricks = new Brick[TOTAL_BRICKS];
		this.setGameFlag(1);
		gameInit(withoutImageIcon);
 		setAllBrickDestroyedFlag(true);
		setTimerTracker(0);
	}
	
	
	/*
	 * Initializes all the brick objects
	 */
	public void gameInit() {

		int k = 0;
		for (int i = 0; i < BRICK_ROWS; i++) {
			for (int j = 0; j < BRICK_COLUMNS; j++) {
				// Removing the hard coded values and linking it to the values
				// from the Contants.java
				bricks[k] = new Brick(j * LEFTMARGIN + LEFTOFFSET, i
						* TOPMARGIN + TOPOFFSET);
				k++;
			}
		}
	}

	/*
	 * This method is only used by the JUnit test case to avoid the X11 -
	 * Bamboo Testing Failure Issue.
	 */
	public void gameInit(boolean withoutImageIcon) {

		int k = 0;
		for (int i = 0; i < BRICK_ROWS; i++) {
			for (int j = 0; j < BRICK_COLUMNS; j++) {
				// Removing the hard coded values and linking it to the values
				// from the Contants.java
				bricks[k] = new Brick(j * LEFTMARGIN + LEFTOFFSET, i
						* TOPMARGIN + TOPOFFSET, withoutImageIcon);
				k++;
			}
		}
	}
	
	/*
	 * Create a list of the existing game properties to be used for Undo feature
	 */
	public ArrayList<Object> getUndoObjects() {
		ArrayList<Object> listObjects = new ArrayList<Object>();
		listObjects.add(this.getGameFlag());
		listObjects.add(this.getTimeForDisplayClock());
		return listObjects;
	}

	/*
	 * Create a list of the existing game properties to be used for Replay and Undo feature
	 */
	public ArrayList<Object> getListShapeObjects() {
		ArrayList<Object> listObjects = new ArrayList<Object>();
		listObjects.add(this.getGameFlag());
		listObjects.add(this.getBall());
		listObjects.add(this.getBricks());
		listObjects.add(this.getPaddle());
		listObjects.add(this.getTimeForDisplayClock());
		return listObjects;
	}

	/*
	 * Get the isDestoryed status of all the bricks
	 */
	public ArrayList<Boolean> getBrickFlags() {
		ArrayList<Boolean> brickFlags = new ArrayList<Boolean>();
		for (int i = 0; i < TOTAL_BRICKS; i++) {
			brickFlags.add(i, this.bricks[i].isDestroyed());
		}
		return brickFlags;
	}

	/*
	 * Create and return a object with all the existing game properties
	 */
	public StoreDimensions gameData() {
		StoreDimensions obj = new StoreDimensions(this.getBall().getX(), this
				.getBall().getY(), this.getPaddle().getX(), this.getPaddle()
				.getY(), this.getGameFlag(), this.getTimeForDisplayClock(),
				this.getBrickFlags(), this.getControlPanelLayout());
		return obj;
	}


	/*
	 * Reinitialize the with game with the properties passed as arguments
	 */
	public void saveDimensions(StoreDimensions obj) {
		getBall().setX(obj.getBallX());
		getBall().setY(obj.getBallY());
		getPaddle().setY(obj.getPaddleY());
		getPaddle().setX(obj.getPaddleX());
		setGameFlag(obj.getGameFlag());
		setTimeForDisplayClock(obj.getSetTimeForDisplayClock());

		ArrayList<Boolean> getBrickFlags = obj.getIsBrickDestroyed();

		for (int i = 0; i < TOTAL_BRICKS; i++) {
			this.bricks[i].setDestroyed(getBrickFlags.get(i));
		}
		setControlPanelLayout(obj.getControlPanelLayout());
	}

	/*
	 * Perform the movement of the objects in the Game i.e., ball and paddle.
	 */
	public void performGameMovement() {
		if (this.getGameFlag() == 1) {
			this.ball.move();
			this.paddle.move();
			checkCollision();
		}
	}

	/*
	 * Used to refresh the Display clock observer
	 */
	public void refresh() {
		setCurrentMinute(getCurrentMinute() + 1);
		setCurrentSecond(0);
	}

	/*
	 * Used to start the observer Display clock 
	 */
	public void start() {
		setCurrentMinute(getCurrentMinute() + 1);
	}

	/*
	 * Used to reset the observer Display clock 
	 */
	public void reset() {
		setCurrentMinute(-1);
		setCurrentSecond(0);
		setTimeForDisplayClock("00:00");
	}

	/* 
	 * Updates the observer Display clock by performing computations
	 * on the local timerTracker variable.
	 */
	public String updateDisplayClock() {
		setTimerTracker(getTimerTracker() + 1);
		if (getTimerTracker() >= 100) {
			if (getCurrentSecond() == 60) {
				refresh();
			}
			setTimeForDisplayClock(String.format("%02d:%02d",
					getCurrentMinute(), getCurrentSecond()));
			setCurrentSecond(getCurrentSecond() + 1);
			setTimerTracker(0);
		}
		return getTimeForDisplayClock();
	}

	/*
	 * Checks the collision of the ball with Brick and Paddle.
	 * Also monitors if the Game is completed by checking : 
	 * 1. All bricks destroyed. 
	 * 2. Ball dropped off
	 * 
	 */
	public void checkCollision() {

		// Check if ball is dropped
		if (ball.getRect().getMaxY() > Boundaries.BOTTOM) {
			//System.out.println("Ball dropped off.. Game Over");
			setGameFlag(2);
		}

		// Check if all the bricks are destroyed.
		setAllBrickDestroyedFlag(true);
		for (Brick b : bricks) {
			if (!b.isDestroyed()) {
				setAllBrickDestroyedFlag(false);
				break;
			}
		}

		// If all bricks are destroyed, user Wins ; Set GameFlag = 3 for
		// indicating the win
		if (getAllBrickDestroyedFlag()) {
			System.out.println("All bricks destroyed ! You Win !!");
			setGameFlag(3);
		}

		// Check for the collisions with Paddle and change the direction of the ball accordingly
		if ((ball.getRect()).intersects(paddle.getRect())) {
			int paddleLPos = (int) paddle.getRect().getMinX();
			int ballLPos = (int) ball.getRect().getMinX();
			int first = paddleLPos + 8;
			int second = paddleLPos + 16;
			int third = paddleLPos + 24;
			int fourth = paddleLPos + 32;

			if (ballLPos < first) {
				ball.setXDir(-1);
				ball.setYDir(-1);
			}

			if (ballLPos >= first && ballLPos < second) {
				ball.setXDir(-1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos >= second && ballLPos < third) {
				ball.setXDir(0);
				ball.setYDir(-1);
			}

			if (ballLPos >= third && ballLPos < fourth) {
				ball.setXDir(1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos > fourth) {
				ball.setXDir(1);
				ball.setYDir(-1);
			}
		}

		// Check for the collisions with the bricks. Destroy the brick change the direction of the ball accordingly
		for (int i = 0; i < this.bricks.length; i++) {
			if ((ball.getRect()).intersects(bricks[i].getRect())) {

				int ballLeft = (int) ball.getRect().getMinX();
				int ballHeight = (int) ball.getRect().getHeight();
				int ballWidth = (int) ball.getRect().getWidth();
				int ballTop = (int) ball.getRect().getMinY();

				Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
				Point pointLeft = new Point(ballLeft - 1, ballTop);
				Point pointTop = new Point(ballLeft, ballTop - 1);
				Point pointBottom = new Point(ballLeft, ballTop + ballHeight
						+ 1);

				if (!bricks[i].isDestroyed()) {
					if (bricks[i].getRect().contains(pointRight)) {
						ball.setXDir(-1);
					} else if (bricks[i].getRect().contains(pointLeft)) {
						ball.setXDir(1);
					}

					if (bricks[i].getRect().contains(pointTop)) {
						ball.setYDir(1);
					} else if (bricks[i].getRect().contains(pointBottom)) {
						ball.setYDir(-1);
					}
					bricks[i].setDestroyed(true);
				}
			}
		}
	}

}