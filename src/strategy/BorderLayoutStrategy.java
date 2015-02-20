package strategy;

import gameEngine.GameDriver;

/**
 * 
 * Class for the BorderLayoutStrategy. Used for changing the layout of the
 * Control Panel
 * 
 */
public class BorderLayoutStrategy implements LayoutManagerStrategy {

	@Override
	public void layout(GameDriver gameDriver) {
		gameDriver.setBorderLayout();
	}

}
