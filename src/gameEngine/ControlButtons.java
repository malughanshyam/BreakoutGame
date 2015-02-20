package gameEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import observerable.TimerObservable;
import observers.DisplayClock;
import observers.GameBoard;
import command.ChangeLayoutCommand;
import command.Command;
import command.LoadCommand;
import command.PauseCommand;
import command.ReplayCommand;
import command.ResumeCommand;
import command.SaveCommand;
import command.StartCommand;
import command.UndoCommand;

/*
 * Class for the Control Panel and buttons. 
 */
public class ControlButtons extends JPanel {
	private static final long serialVersionUID = 1L;
	private Command theCommand;
	private int gameState;
	private GameDriver gameDriver;
	private GameBoard game;
	private DisplayClock clock;
	private TimerObservable timerObs;
	private boolean isPaused;
	private boolean isStart;

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public TimerObservable getTimerObs() {
		return timerObs;
	}

	public void setTimerObs(TimerObservable timerObs) {
		this.timerObs = timerObs;
	}

	public GameBoard getGame() {
		return game;
	}

	public void setGame(GameBoard game) {
		this.game = game;
	}

	public DisplayClock getClock() {
		return clock;
	}

	public void setClock(DisplayClock clock) {
		this.clock = clock;
	}

	public void press() {
		theCommand.execute();
	}

	public void setGameDriver(GameDriver gameDriver) {
		this.gameDriver = gameDriver;
	}

	public GameDriver getGameDriver() {
		return gameDriver;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public Command getTheCommand() {
		return theCommand;
	}

	public void setTheCommand(Command theCommand) {
		this.theCommand = theCommand;
	}

	/* 
	 * Buttons for different features
	 */
	JButton st_but = new JButton("Start");
	JButton st_pse = new JButton("Pause");
	JButton st_undo = new JButton("Undo");
	JButton st_replay = new JButton("Replay");
	JButton st_load = new JButton("Load");
	JButton st_save = new JButton("Save");
	JButton st_layout = new JButton("Change Layout");
	private JButton[] buttonArray = new JButton[7];
	
	public JButton[] getButtonArray() {
		return buttonArray;
	}

	/*
	 * Creating an array of buttons. This is used by the BorderLayout
	 */
	public void setButtonArray() {
		this.buttonArray[0] = st_but;
		this.buttonArray[1] = st_pse;
		this.buttonArray[2] = st_undo;
		this.buttonArray[3] = st_replay;
		this.buttonArray[4] = st_save;
		this.buttonArray[5] = st_load;
		this.buttonArray[6] = st_layout;
	}

	private JFileChooser fc;

	public ControlButtons(final GameBoard game) {
		setStart(false);
		setPaused(false);
		
		// Create a file chooser
		fc = new JFileChooser();
		// set file filter
		fc.setAcceptAllFileFilterUsed(false);
		FileFilter filter = new FileNameExtensionFilter("Breakout JSON file",
				"json");
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		
		st_pse.setEnabled(false);
		st_undo.setEnabled(false);
		st_replay.setEnabled(false);
		st_save.setEnabled(false);
		st_load.setEnabled(true);
		st_layout.setEnabled(false);

		/*
		 * Functionality for the Start and Restart button
		 */
		st_but.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				st_pse.setEnabled(true);
				st_undo.setEnabled(true);
				st_replay.setEnabled(true);
				st_save.setEnabled(true);
				st_layout.setEnabled(true);
				game.requestFocusInWindow();

				if (st_but.getText().equals("Restart")) {
					gameDriver.getTimerObs().getComputeCoordinatesObj().setGameFlag(0);
					st_but.setText("Start");
					st_pse.setText("Pause");
					//timerObs.deleteObserver(getClock());
					timerObs.getTimer().stop();
					timerObs = new TimerObservable();
					setTimerObs(timerObs);
					
					getTimerObs().setGameDriver(gameDriver);	     
	        		getTimerObs().getComputeCoordinatesObj().setControlPanelLayout(gameDriver.getControlPanelLayoutText());
	        		
					timerObs.addObserver((Observer) gameDriver.getGameBoard());
					timerObs.addObserver((Observer) gameDriver
							.getDisplayClock());
				}

				if (st_but.getText().equals("Start")) {
					
					StartCommand startCmd;
					startCmd = new StartCommand(timerObs);
					timerObs.addObserver((Observer) gameDriver.getGameBoard());
					timerObs.addObserver((Observer) gameDriver
							.getDisplayClock());
					gameDriver.getControlButtons().setTheCommand(startCmd);
					gameDriver.getControlButtons().press();
					st_but.setText("Restart");
				}
			}

		});

		/*
		 * Functionality for the Pause and Resume button
		 */
		st_pse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				st_but.setEnabled(true);
				
				/* 
				 * Disable the pause button when the Game is in "Non Running" mode.
				 * Game Flag = 0 -> Initial State before starting
				 * Game Flag = 1 -> Game running
				 * Game Flag = 2 -> Game over - Lost Game
				 * Game Flag = 3 -> Game over - Won Game
				 */
				if (timerObs.getComputeCoordinatesObj().getGameFlag() == 1) {
					st_pse.setEnabled(true);
				}
				else
				{
					st_pse.setEnabled(false);
				}
				

				if (st_pse.getText().equals("Pause")) {
					PauseCommand pauseCmd;
					pauseCmd = new PauseCommand(timerObs);
					gameDriver.getControlButtons().setTheCommand(pauseCmd);
					gameDriver.getControlButtons().press();
					st_pse.setText("Resume");
				} else {
					ResumeCommand resumeCmd;
					resumeCmd = new ResumeCommand(timerObs);
					timerObs.addObserver((Observer) gameDriver.getGameBoard());
					timerObs.addObserver((Observer) gameDriver
							.getDisplayClock());
					gameDriver.getControlButtons().setTheCommand(resumeCmd);
					gameDriver.getControlButtons().press();
					game.requestFocusInWindow();
					st_pse.setText("Pause");
				}
			}
		});

		/*
		 * Functionality for the Undo button
		 */
		st_undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				st_but.setEnabled(true);
				st_pse.setEnabled(true);
				st_undo.setEnabled(true);
				st_replay.setEnabled(true);
				st_pse.setText("Resume");
				isStart = false;
				UndoCommand undoCmd;
				undoCmd = new UndoCommand(timerObs);
				gameDriver.getControlButtons().setTheCommand(undoCmd);
				gameDriver.getControlButtons().press();
			}
		});

		/*
		 * Functionality for the Replay button
		 */
		st_replay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				st_but.setEnabled(true);
				st_pse.setText("Resume");
				st_pse.setEnabled(true);
				st_undo.setEnabled(false);
				st_replay.setEnabled(false);
				isStart = false;
				ReplayCommand replyCmd;
				replyCmd = new ReplayCommand(timerObs);
				gameDriver.getControlButtons().setTheCommand(replyCmd);
				gameDriver.getControlButtons().press();
				st_undo.setEnabled(true);
				st_replay.setEnabled(true);
				
				if (timerObs.getComputeCoordinatesObj().getGameFlag() != 1) {
					st_pse.setEnabled(false);
				}

			}
		});
		
		/*
		 * Functionality for the Replay button
		 */
		st_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Pausing the game when the load button is clicked
				//So that the game remains paused on the clicks of Load button
				st_pse.setText("Pause");
				st_pse.doClick();
				
				// Handle open button action.
				int returnVal = fc.showOpenDialog(ControlButtons.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile().getAbsoluteFile();
					timerObs.getSaveLoadReplay().setReplayFileName(
							file.getPath());
					LoadCommand loadCommand = new LoadCommand(timerObs);
					gameDriver.getControlButtons().setTheCommand(loadCommand);
					gameDriver.getControlButtons().press();
					st_replay.setEnabled(true);
					timerObs.addObserver((Observer) gameDriver.getGameBoard());
					timerObs.addObserver((Observer) gameDriver.getDisplayClock());
					
				}
				if (st_pse.getText().equals("Pause") || st_pse.isEnabled()==false){
					st_pse.setEnabled(true);
					st_but.doClick();
					gameDriver.repaint();
					st_pse.setText("Pause");
					st_pse.doClick();

				}
				fc.setSelectedFile(null);
			}
		});
		
		/*
		 * Functionality for the Save button
		 */
		st_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Pausing the game when the load button is clicked
				//So that the game remains paused on the clicks of Save button
				st_pse.setText("Pause");
				st_pse.doClick();

						
				int returnVal = fc.showSaveDialog(ControlButtons.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile().getAbsoluteFile();
					
					String filepath = file.getPath();
					String extension = "";
					int i = filepath.lastIndexOf('.');
					if (i > 0) {
						extension = filepath.substring(i + 1);
					}
					if ("" == extension) {
						timerObs.getSaveLoadReplay().setReplayFileName(
								filepath + ".json");
					} else
						timerObs.getSaveLoadReplay()
								.setReplayFileName(filepath);
					SaveCommand saveCommand = new SaveCommand(timerObs);
					gameDriver.getControlButtons().setTheCommand(saveCommand);
					gameDriver.getControlButtons().press();
					
				}
				fc.setSelectedFile(null);
			}
		});

		/*
		 * Functionality for the Change Layout button
		 */
		st_layout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				st_pse.setText("Pause");
				st_pse.doClick();
				ChangeLayoutCommand changeLayoutCommand;
				changeLayoutCommand = new ChangeLayoutCommand(timerObs);
				gameDriver.getControlButtons().setTheCommand(changeLayoutCommand);
				gameDriver.getControlButtons().press();
				game.requestFocusInWindow();
			}
		});
	}
}
