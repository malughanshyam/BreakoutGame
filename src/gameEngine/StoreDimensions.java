package gameEngine;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

/**
 * 
 * StoreDimensions stores game state.
 *
 */

public class StoreDimensions implements JSONStreamAware {

	int ballX;
	int ballY;
	int paddleX;
	int paddleY;
	ArrayList<Boolean> isBrickDestroyed;
	int gameFlag;
	String setTimeForDisplayClock;
	String controlPanelLayout;

	public String getControlPanelLayout() {
		return controlPanelLayout;
	}

	public void setControlPanelLayout(String controlPanelLayout) {
		this.controlPanelLayout = controlPanelLayout;
	}

	public int getGameFlag() {
		return gameFlag;
	}

	public void setGameFlag(int gameFlag) {
		this.gameFlag = gameFlag;
	}

	public String getSetTimeForDisplayClock() {
		return setTimeForDisplayClock;
	}

	public void setSetTimeForDisplayClock(String setTimeForDisplayClock) {
		this.setTimeForDisplayClock = setTimeForDisplayClock;
	}
/*
 * This constructor is for normal use, it store game state of every objects.
 */
	public StoreDimensions(int ballX, int ballY, int paddleX, int paddleY,
			int gameFlag, String timeForDisplayClock,
			ArrayList<Boolean> isBrickDestroyed, String controlPanelLayout) {
		this.ballX = ballX;
		this.ballY = ballY;
		this.paddleX = paddleX;
		this.paddleY = paddleY;
		this.isBrickDestroyed = isBrickDestroyed;
		setGameFlag(gameFlag);
		setSetTimeForDisplayClock(timeForDisplayClock);
		setControlPanelLayout(controlPanelLayout);
	}
	/*
	 * This constructor is for json decode use, it will store all data when decoded.
	 */
	public StoreDimensions() {
		this.ballX = 0;
		this.ballY = 0;
		this.paddleX = 0;
		this.paddleY = 0;
		this.isBrickDestroyed = null;
		this.gameFlag = 0;
		this.setTimeForDisplayClock = "";
		this.controlPanelLayout="";
	}
/*
 * (non-Javadoc)
 * @see org.json.simple.JSONStreamAware#writeJSONString(java.io.Writer)
 * write the store data into a LinkedHashMap and encode into a json string
 */
	public void writeJSONString(Writer out) throws IOException {
		LinkedHashMap<String, Serializable> map = new LinkedHashMap<String, Serializable>();
		map.put("ballX", this.ballX);
		map.put("ballY", this.ballY);
		map.put("paddleX", this.paddleX);
		map.put("paddleY", this.paddleY);
		map.put("gameFlag", this.gameFlag);
		map.put("timeForDisplayClock", this.setTimeForDisplayClock);
		map.put("isBrickDestroyed", this.isBrickDestroyed);
		map.put("controlPanelLayout", this.controlPanelLayout);
		JSONValue.writeJSONString(map, out);
	}
/*
 * parse json string and store
 */
	public void parseJSONString(JSONObject jsonObject) {
		this.ballX = Integer.parseInt(jsonObject.get("ballX").toString());
		this.ballY = Integer.parseInt(jsonObject.get("ballY").toString());
		this.paddleX = Integer.parseInt(jsonObject.get("paddleX").toString());
		this.paddleY = Integer.parseInt(jsonObject.get("paddleY").toString());
		this.gameFlag = Integer.parseInt(jsonObject.get("gameFlag").toString());
		this.setTimeForDisplayClock = (String) jsonObject
				.get("timeForDisplayClock");
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> arrayList = (ArrayList<Boolean>) jsonObject
				.get("isBrickDestroyed");
		this.isBrickDestroyed = arrayList;
		this.controlPanelLayout = (String) jsonObject
				.get("controlPanelLayout");
	}
/*
 * print all the stored data.
 */
	public void printDimensions() {
		System.out.println("ballX: " + this.ballX);
		System.out.println("ballY: " + this.ballY);
		System.out.println("paddleX: " + this.paddleX);
		System.out.println("paddleY: " + this.paddleY);
		System.out.println("gameFlag: " + this.gameFlag);
		System.out.println("Time: " + this.setTimeForDisplayClock);
		if (null != this.isBrickDestroyed)
			for (int i = 0; i < this.isBrickDestroyed.size(); i++) {
				System.out.println("brick " + i + " :"
						+ this.isBrickDestroyed.get(i));
			}
		System.out.println("controlPanelLayout: " + this.controlPanelLayout);
	}

	public int getBallX() {
		return ballX;
	}

	public void setBallX(int ballX) {
		this.ballX = ballX;
	}

	public int getBallY() {
		return ballY;
	}

	public void setBallY(int ballY) {
		this.ballY = ballY;
	}

	public int getPaddleX() {
		return paddleX;
	}

	public void setPaddleX(int paddleX) {
		this.paddleX = paddleX;
	}

	public int getPaddleY() {
		return paddleY;
	}

	public void setPaddleY(int paddleY) {
		this.paddleY = paddleY;
	}

	public ArrayList<Boolean> getIsBrickDestroyed() {
		return isBrickDestroyed;
	}

	public void setIsBrickDestroyed(ArrayList<Boolean> isBrickDestroyed) {
		this.isBrickDestroyed = isBrickDestroyed;
	}
}
