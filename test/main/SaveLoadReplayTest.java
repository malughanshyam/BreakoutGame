package main;

import static org.junit.Assert.*;
import gameEngine.SaveLoadReplay;
import gameEngine.StoreDimensions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit test case for Class SaveLoadReplay, test functions besides getter and
 * setter.
 *
 */

public class SaveLoadReplayTest {
	private SaveLoadReplay saveLoadReplay = new SaveLoadReplay();
	private LinkedList<Object> ReplayList = new LinkedList<Object>();
	private final String json = "{\"ballX\":0,\"ballY\":0,\"paddleX\":0,\"paddleY\":0,\"gameFlag\":0,\"timeForDisplayClock\":\"00:00\",\"isBrickDestroyed\":[false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false],\"controlPanelLayout\":\"FlowLayout\"}";

	/**
	 * @throws java.lang.Exception
	 *             Add only two elements into the replay list
	 */
	@Before
	public void setUp() throws Exception {
		this.saveLoadReplay.setWithoutX11(true);
		ArrayList<Boolean> isBrickDestroyed = new ArrayList<Boolean>(
				Arrays.asList(false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false));
		ReplayList.add(new StoreDimensions(0, 0, 0, 0, 0, "00:00",
				isBrickDestroyed, "FlowLayout"));
		ReplayList.add(new StoreDimensions(0, 0, 0, 0, 0, "00:01",
				isBrickDestroyed, "FlowLayout"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.ReplayList.clear();
	}

	/**
	 * Test method for
	 * {@link gameEngine.SaveLoadReplay#decode(java.lang.Object)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testDecode() throws ParseException {
		ArrayList<Boolean> isBrickDestroyed = new ArrayList<Boolean>(
				Arrays.asList(false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false));
		StoreDimensions sd = new StoreDimensions(0, 0, 0, 0, 0, "00:00",
				isBrickDestroyed, "FlowLayout");
		JSONParser parser = new JSONParser();
		StoreDimensions storedimensions = this.saveLoadReplay.decode(parser
				.parse(this.json));
		assertEquals(sd.getBallX(), storedimensions.getBallX());
		assertEquals(sd.getBallY(), storedimensions.getBallY());
		assertEquals(sd.getPaddleX(), storedimensions.getPaddleX());
		assertEquals(sd.getPaddleY(), storedimensions.getPaddleY());
		assertEquals(sd.getGameFlag(), storedimensions.getGameFlag());
		assertEquals(sd.getSetTimeForDisplayClock(),
				storedimensions.getSetTimeForDisplayClock());
		assertEquals(sd.getIsBrickDestroyed(),
				storedimensions.getIsBrickDestroyed());
		assertEquals(sd.getControlPanelLayout(),
				storedimensions.getControlPanelLayout());
	}

	/**
	 * Test method for
	 * {@link gameEngine.SaveLoadReplay#encode(gameEngine.StoreDimensions)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testEncode() throws IOException {
		ArrayList<Boolean> isBrickDestroyed = new ArrayList<Boolean>(
				Arrays.asList(false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false, false, false, false, false, false, false,
						false, false));
		assertEquals(this.json + "\r\n",
				this.saveLoadReplay.encode(new StoreDimensions(0, 0, 0, 0, 0,
						"00:00", isBrickDestroyed, "FlowLayout")));
	}

	/**
	 * Test method for {@link gameEngine.SaveLoadReplay#LoadReplay()}.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void testSaveLoadReplay() throws IOException, ParseException {
		// save
		this.saveLoadReplay.setReplayFileName("junit.json");
		this.saveLoadReplay.SaveReplay(ReplayList);
		// load
		this.saveLoadReplay.setReplayFileName("junit.json");
		this.ReplayList = this.saveLoadReplay.LoadReplay();
		// System.out.println(this.ReplayList.size());
		assertTrue("Load messages are the same as Save messages",
				this.ReplayList.size() == 2);
	}
}
