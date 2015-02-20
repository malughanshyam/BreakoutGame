package strategy;

import gameEngine.GameDriver;
/*
 * Set current layout to layout manager and let it take effect
 */
public class Context {
	private LayoutManagerStrategy layoutManager;

	public Context(LayoutManagerStrategy layoutManager) {
		this.layoutManager = layoutManager;
	}

	public void setLayout(GameDriver gameDriver) {
		layoutManager.layout(gameDriver);
	}
}
