package command;

import observerable.TimerObservable;

/**
 * 
 * Change Layout Command
 *
 */

public class ChangeLayoutCommand implements Command{

	private Object currReceiver;
	
	public ChangeLayoutCommand(Object currReceiver) 
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
		((TimerObservable) currReceiver).changeLayout();
	}
}
