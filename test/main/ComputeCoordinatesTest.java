package main;

import static org.junit.Assert.*;
import gameEngine.StoreDimensions;

import java.util.ArrayList;
import java.util.Arrays;

import observerable.TimerObservable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/*
 * Junit test case for Class ComputeCoordinates, test only updateDisplayClock and saveDimensions
 */
public class ComputeCoordinatesTest {
	private TimerObservable timerObs = new TimerObservable(true);

	@Before
	public void setUp() throws Exception {
		if (!this.timerObs.getTimer().isRunning()) {
			this.timerObs.getTimer().start();
		}
		this.timerObs.computeAndNotify();
	}

	@After
	public void tearDown() throws Exception {
		if (this.timerObs.getTimer().isRunning()) {
			this.timerObs.getTimer().stop();
		}
	}

	@Test
	public void testupdateDisplayClock() {
		String display;
		for (int i = 1; i <= 1000; i++) {
			display = this.timerObs.getComputeCoordinatesObj()
					.updateDisplayClock();
			if (i % 100 == 0) {
				assertEquals("00:0" + (i / 100 - 1), display);
			}
		}
	}

	@Test
	public void testSaveDimensions() {
		ArrayList<Boolean> isBrickDestroyed = new ArrayList<Boolean>(
				Arrays.asList(false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false));
		StoreDimensions storeDimensions = new StoreDimensions(0, 0, 0, 0, 0,
				"00:00", isBrickDestroyed, "FlowLayout");
		this.timerObs.getComputeCoordinatesObj()
				.saveDimensions(storeDimensions);
		assertEquals(0, this.timerObs.getComputeCoordinatesObj().getBall()
				.getX());
		assertEquals(0, this.timerObs.getComputeCoordinatesObj().getBall()
				.getY());
		assertEquals(0, this.timerObs.getComputeCoordinatesObj().getPaddle()
				.getX());
		assertEquals(0, this.timerObs.getComputeCoordinatesObj().getPaddle()
				.getY());
		assertEquals(0, this.timerObs.getComputeCoordinatesObj().getGameFlag());
		assertEquals("00:00", this.timerObs.getComputeCoordinatesObj()
				.getTimeForDisplayClock());
		if (null != this.timerObs.getComputeCoordinatesObj().getBrickFlags())
			for (int i = 0; i < this.timerObs.getComputeCoordinatesObj()
					.getBrickFlags().size(); i++) {
				assertEquals(false, this.timerObs.getComputeCoordinatesObj()
						.getBrickFlags().get(i));
			}
		assertEquals("FlowLayout", this.timerObs.getComputeCoordinatesObj()
				.getControlPanelLayout());
	}

}
