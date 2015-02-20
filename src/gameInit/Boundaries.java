package gameInit;

/**
 * 
 * Interface having all the Constants used by the Game
 *
 */

public interface Boundaries {

	int WIDTH = Constants.WIN_WIDTH;
	int HEIGHT = Constants.WIN_HEIGHT;
	int BOTTOM = (Constants.WIN_HEIGHT - 150);
	int PADDLE_RIGHT = (Constants.WIN_WIDTH - 10);
	int PADDLE_LEFT = 0;
	int BALL_RIGHT = (Constants.WIN_WIDTH - 22);
}
