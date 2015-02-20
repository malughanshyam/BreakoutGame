package gameEngine;

import gameInit.Constants;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import observerable.TimerObservable;
import observers.DisplayClock;
import observers.GameBoard;
import strategy.Context;
import strategy.FlowLayoutStrategy;

/**
 * 
 * Main class of the Breakout Game
 *
 */
public class GameDriver extends JFrame implements Constants{
	
	private static final long serialVersionUID = 1L;
		private GameBoard game;
	    private MenuBoard menu;
	    private ControlButtons controlButtons;
	    private DisplayClock clock;
	    private TimerObservable timerObs;	    
	    private String controlPanelLayoutText;
	    
	    public TimerObservable getTimerObs() {
			return timerObs;
		}

		public void setTimerObs(TimerObservable timerObs) {
			this.timerObs = timerObs;
		}

		public GameBoard getGameBoard()
	    {
	    	return game;
	    }
	    public void setGame(GameBoard game) {
			this.game = game;
		}

		public void setClock(DisplayClock clock) {
			this.clock = clock;
		}

		public MenuBoard getMenuBoard()
	    {
	    	return menu;
	    }
	    public ControlButtons getControlButtons()
	    {
	    	return controlButtons;
	    }
	    public DisplayClock getDisplayClock()
	    {
	    	return clock;
	    }
	    
		public String getControlPanelLayoutText() {
			return controlPanelLayoutText;
		}

		public void setControlPanelLayoutText(String controlPanelLayoutText) {
			this.controlPanelLayoutText = controlPanelLayoutText;
		}

		
	    /* 
	     * Initialize the different components of the game
	     */
	    public GameDriver() {
	    	setControlPanelLayoutText("FlowLayout");
	        game = new GameBoard();
	        timerObs= new TimerObservable();
	        clock = new DisplayClock();
            menu = new MenuBoard();
            controlButtons = new ControlButtons(game);
            controlButtons.setButtonArray();
            initUI(game, menu, clock);
            Context context = new Context(new FlowLayoutStrategy());
            context.setLayout(this);
	     }

        private void initUI(GameBoard game, MenuBoard menu, DisplayClock clock) {
            setLayout(new BorderLayout());
            add(game);
            add(menu, BorderLayout.SOUTH);
            add(clock, BorderLayout.NORTH);
            pack();
            setTitle("Breakout");
            setSize(WIN_WIDTH, WIN_HEIGHT);
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        /*
         * Changes the control panel to Flow Layout
         */
	    public void setFlowLayout()
	    {	
	    	menu.removeAll();
	    	FlowLayout fLayout = new FlowLayout();
			controlButtons.setLayout(fLayout);
			fLayout.setHgap(5);
			fLayout.setVgap(30);
			fLayout.setAlignment(FlowLayout.CENTER);
			repaint();
			JButton[] buttonArray = controlButtons.getButtonArray();
			controlButtons.add(buttonArray[0]);
			controlButtons.add(buttonArray[1]);
			controlButtons.add(buttonArray[2]);
			controlButtons.add(buttonArray[3]);
			controlButtons.add(buttonArray[4]);
			controlButtons.add(buttonArray[5]);
			controlButtons.add(buttonArray[6]);
	    	menu.add(controlButtons);	    	
	    }
	    
        /*
         * Changes the control panel to Border Layout
         */
	    public void setBorderLayout()
	    {
	    	menu.removeAll();	    	
	    	JButton[] buttonArray = controlButtons.getButtonArray();
			JPanel flowNorthPanel = new JPanel();
			flowNorthPanel.add(buttonArray[2]);
			flowNorthPanel.add(buttonArray[3]);			
			JPanel flowSouthPanel = new JPanel();
			flowSouthPanel.add(buttonArray[4]);
			flowSouthPanel.add(buttonArray[5]);
			menu.add(flowNorthPanel,BorderLayout.NORTH);
	        menu.add(buttonArray[0],BorderLayout.WEST);
	        menu.add(buttonArray[1],BorderLayout.CENTER);
	        menu.add(buttonArray[6],BorderLayout.EAST);
	        menu.add(flowSouthPanel,BorderLayout.SOUTH);	        
	    }
	    
	    /*
	     * Main Method
	     */
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	GameDriver gameDriver = new GameDriver();  
	            	gameDriver.setVisible(true);
	        		gameDriver.getControlButtons().setGameDriver(gameDriver);
	        		gameDriver.getTimerObs().setGameDriver(gameDriver);	     
	        		gameDriver.getTimerObs().getComputeCoordinatesObj().setControlPanelLayout(gameDriver.getControlPanelLayoutText());
	        		gameDriver.getControlButtons().setClock(gameDriver.getDisplayClock());
	        		gameDriver.getControlButtons().setGame(gameDriver.getGameBoard());
	        		gameDriver.getControlButtons().setTimerObs(gameDriver.getTimerObs());
	            }
	        });
	    }
	}

	
	

