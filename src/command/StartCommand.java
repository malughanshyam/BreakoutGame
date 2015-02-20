package command;

import observerable.TimerObservable;

/**
 * 
 * Start Command
 *
 */

public class StartCommand implements Command {

	private Object currReceiver;

	public StartCommand(Object currReceiver) {
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
		//set the game flag to true, and update everything
		((TimerObservable) currReceiver).setGameFlag(true);
		((TimerObservable) currReceiver).computeAndNotify();
	}

}
