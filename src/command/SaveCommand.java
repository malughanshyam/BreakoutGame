package command;

import observerable.TimerObservable;

/**
 * Save Command
 *
 */
public class SaveCommand implements Command {
	private Object currReceiver;

	public SaveCommand(Object currReceiver) {
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
		((TimerObservable) currReceiver).saveGame();
	}

}
