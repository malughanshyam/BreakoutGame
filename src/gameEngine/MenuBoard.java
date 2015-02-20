package gameEngine;



import gameInit.Constants;

import java.awt.*;

import javax.swing.JPanel;

/**
 * 
 * Panel for menu board
 *
 */
public class MenuBoard extends JPanel implements Constants {

	private static final long serialVersionUID = 1L;

    public MenuBoard() {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(5);
        layout.setVgap(5);
        this.setLayout(layout);
        //setBackground(Color.black);
    }
}
