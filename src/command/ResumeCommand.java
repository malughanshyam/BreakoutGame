package command;

import observerable.TimerObservable;

/**
 * 
 * Resume command
 *
 */

public class ResumeCommand implements Command {

	private Object currReceiver;

	public ResumeCommand(Object currReceiver) {
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
		((TimerObservable) currReceiver).resumeGame();
	}
}
