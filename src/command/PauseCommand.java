package command;

import observerable.TimerObservable;

/**
 * 
 * Pause command
 *
 */

public class PauseCommand implements Command {

	private Object currReceiver;

	public PauseCommand(Object currReceiver) {
		if (currReceiver instanceof TimerObservable)
			this.currReceiver = currReceiver;
	}

	public Object getCurrReceiver() {
		return currReceiver;
	}

	public void setCurrReceiver(Object currReceiver) {
		this.currReceiver = currReceiver;
	}

	@Override
	public void execute() {
		((TimerObservable) currReceiver).pauseGame();
	}
}
