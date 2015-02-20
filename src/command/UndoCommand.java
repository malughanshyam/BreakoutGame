package command;

import observerable.TimerObservable;

/**
 * 
 * Undo Command 
 *
 */

public class UndoCommand implements Command{

private Object currReceiver;
	
	public UndoCommand(Object currReceiver) 
	{
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
		((TimerObservable) currReceiver).undoLastMovment();
	}
	

}
