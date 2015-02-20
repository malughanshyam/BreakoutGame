package strategy;

import gameEngine.GameDriver;
/*
 * flowlayout strategy
 */
public class FlowLayoutStrategy implements LayoutManagerStrategy {

	@Override
	public void layout(GameDriver gameDriver) {
		gameDriver.setFlowLayout();
	}
}
