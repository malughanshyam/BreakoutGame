package observerable;

import gameEngine.ComputeCoordinates;
import gameEngine.GameDriver;
import gameEngine.SaveLoadReplay;
import gameEngine.StoreDimensions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Timer;

import org.json.simple.parser.ParseException;

import strategy.BorderLayoutStrategy;
import strategy.Context;
import strategy.FlowLayoutStrategy;

import java.util.LinkedList;

/**
 * 
 * Timer Observable, driving game engine
 *
 */

public class TimerObservable extends Observable {

	private ComputeCoordinates computeCoordinatesObj;
	private Timer timer;
	private LinkedList<Object> CommandHistoryList = new LinkedList<Object>();
	private LinkedList<Object> ReplayList = new LinkedList<Object>();
	boolean gameFlag = true;
	private int replayFrameCounter;
	private GameDriver gameDriver;
	int temporayCounter = 0;
	private ArrayList<Object> shapeObjects;
	private SaveLoadReplay saveLoadReplay;
	ArrayList<Object> test = new ArrayList<Object>();

	/*
	 * Getters and Setters
	 */
	public SaveLoadReplay getSaveLoadReplay() {
		return saveLoadReplay;
	}

	public void setSaveLoadReplay(SaveLoadReplay saveLoadReplay) {
		this.saveLoadReplay = saveLoadReplay;
	}

	public boolean isGameFlag() {
		return gameFlag;
	}

	public void setGameFlag(boolean gameFlag) {
		this.gameFlag = gameFlag;
	}

	public LinkedList<Object> getHistoricCommandList() {
		return CommandHistoryList;
	}

	public ArrayList<Object> getShapeObjects() {
		return shapeObjects;
	}

	public void setShapeObjects(ArrayList<Object> shapeObjects) {
		this.shapeObjects = shapeObjects;
	}

	public ComputeCoordinates getComputeCoordinatesObj() {
		return computeCoordinatesObj;
	}

	public void setComputeCoordinatesObj(
			ComputeCoordinates computeCoordinatesObj) {
		this.computeCoordinatesObj = computeCoordinatesObj;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void setGameDriver(GameDriver gameDriver) {
		this.gameDriver = gameDriver;
	}

	public GameDriver getGameDriver() {
		return gameDriver;
	}

	/*
	 * TimerObservable constructor for normal use
	 */
	public TimerObservable() {
		this.computeCoordinatesObj = new ComputeCoordinates();
		this.timer = new Timer(8, null);
		this.replayFrameCounter = 0;
		this.saveLoadReplay = new SaveLoadReplay();

	}

	/*
	 * TimerObservable constructor for junit test only, without x11
	 */
	public TimerObservable(boolean withoutImageIcon) {
		this.computeCoordinatesObj = new ComputeCoordinates(withoutImageIcon);
		this.timer = new Timer(5, null);
		this.replayFrameCounter = 0;
		this.saveLoadReplay = new SaveLoadReplay();
		this.saveLoadReplay.setWithoutX11(withoutImageIcon);
	}

	/*
	 * timer action, which updates the game state of every object and performs play and replay
	 */
	public void computeAndNotify() {
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (getComputeCoordinatesObj().getGameFlag() != 1) {
					// Stop the timer after the Game is over
					getTimer().stop();
				}
				if (gameFlag) {
					// if game is on, just store game state and update display
					// of every object
					if (temporayCounter % 10 == 0) {
						CommandHistoryList.add(getComputeCoordinatesObj()
								.gameData());
					}

					ReplayList.add(getComputeCoordinatesObj().gameData());
					getComputeCoordinatesObj().performGameMovement();
					getComputeCoordinatesObj().updateDisplayClock();

					shapeObjects = getComputeCoordinatesObj()
							.getListShapeObjects();
					setChanged();
					notifyObservers(shapeObjects);
					temporayCounter++;
				} else {
					// Added this section to Replay even if the Game is Over
					if (!getTimer().isRunning()) {
						getTimer().start();
					}

					StoreDimensions storeDimensions;
					if (replayFrameCounter < ReplayList.size()) {

						storeDimensions = (StoreDimensions) ReplayList
								.get(replayFrameCounter);
						getComputeCoordinatesObj().saveDimensions(
								storeDimensions);
						// setChanged();
						shapeObjects = getComputeCoordinatesObj()
								.getListShapeObjects();

						if (getGameDriver().getControlPanelLayoutText() != storeDimensions
								.getControlPanelLayout()) {
							changeLayout();
							getGameDriver().setControlPanelLayoutText(
									storeDimensions.getControlPanelLayout());

						}

						setChanged();
						notifyObservers(shapeObjects);
						replayFrameCounter++;
					} else {
						replayFrameCounter = 0;
						setGameFlag(true);
						timer.stop();
					}
				}
			}
		});
		timer.setDelay(5);
		timer.restart();
	}

	/*
	 * Undo: return to former game state
	 */
	public void undoLastMovment() {
		this.timer.stop();
		if (CommandHistoryList.size() != 0) {
			StoreDimensions storeDimensions = (StoreDimensions) this.CommandHistoryList
					.removeLast();

			getComputeCoordinatesObj().saveDimensions(storeDimensions);
			ReplayList.add(storeDimensions);
			shapeObjects = getComputeCoordinatesObj().getUndoObjects();

			if (getGameDriver().getControlPanelLayoutText() != storeDimensions
					.getControlPanelLayout()) {
				changeLayout();
				getGameDriver().setControlPanelLayoutText(
						storeDimensions.getControlPanelLayout());

			}

			setChanged();
			notifyObservers(shapeObjects);
		}
	}

	public void pauseGame() {
		this.getTimer().stop();
	}

	public void resumeGame() {
		this.getTimer().setDelay(5);
		this.getTimer().restart();
	}

	/*
	 * Load the json replay file into the game engine for replay or play
	 */
	public void loadGame() {
		try {
			this.ReplayList = this.saveLoadReplay.LoadReplay();
			StoreDimensions storeDimensions;
			storeDimensions = (StoreDimensions) ReplayList.getLast();
			getComputeCoordinatesObj().saveDimensions(storeDimensions);
			// Read just Display clock timer after a game is loaded so that
			// display clock can continue from the respective point.
			reAdjustGameClock();
			shapeObjects = getComputeCoordinatesObj().getListShapeObjects();

			if (getGameDriver().getControlPanelLayoutText() != storeDimensions
					.getControlPanelLayout()) {
				changeLayout();
				getGameDriver().setControlPanelLayoutText(
						storeDimensions.getControlPanelLayout());

			}

			setChanged();
			notifyObservers(shapeObjects);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * When a replay file is loaded, the display of game timer needs to be
	 * readjust.
	 */
	private void reAdjustGameClock() {
		String dispClockText = getComputeCoordinatesObj()
				.getTimeForDisplayClock();

		if (dispClockText == null) {
			getComputeCoordinatesObj().setCurrentMinute(0);
			getComputeCoordinatesObj().setCurrentSecond(0);
			getComputeCoordinatesObj().setTimeForDisplayClock("00:00");
		} else if (dispClockText.contains(":")) {
			String[] hhmm = dispClockText.split(":");
			getComputeCoordinatesObj().setCurrentMinute(
					Integer.parseInt(hhmm[0]));
			getComputeCoordinatesObj().setCurrentSecond(
					Integer.parseInt(hhmm[1]));
		}
	}

	/*
	 * Save current game into a json replay file for later play or replay
	 */
	public void saveGame() {
		try {
			this.saveLoadReplay.SaveReplay(this.ReplayList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Change the layout of the buttons in the control panel
	 */
	public void changeLayout() {
		if (getGameDriver().getControlPanelLayoutText().equalsIgnoreCase(
				"BorderLayout")) {
			getGameDriver().setControlPanelLayoutText("FlowLayout");
			Context context = new Context(new FlowLayoutStrategy());
			context.setLayout(getGameDriver());
			getComputeCoordinatesObj().setControlPanelLayout("FlowLayout");
			getGameDriver().validate();
		} else if (getGameDriver().getControlPanelLayoutText()
				.equalsIgnoreCase("FlowLayout")) {
			getGameDriver().setControlPanelLayoutText("BorderLayout");
			Context context = new Context(new BorderLayoutStrategy());
			context.setLayout(getGameDriver());
			getComputeCoordinatesObj().setControlPanelLayout("BorderLayout");
			getGameDriver().validate();

		}
	}
}
