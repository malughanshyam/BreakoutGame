/**
 * 
 */
package command;

import observerable.TimerObservable;

/**
 * 
 * Load Command
 *
 */
public class LoadCommand implements Command {
	private Object currReceiver;
	public LoadCommand(Object currReceiver){
		if(currReceiver instanceof TimerObservable)
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
		((TimerObservable) currReceiver).loadGame();
	}

}
