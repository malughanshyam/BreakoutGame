
package observers;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 * Display clock, it is an observer managed by TimerObservable
 */
public class DisplayClock extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	JLabel time = new JLabel("00:00");
	
	public DisplayClock() {
		this.add(time);
		time.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		time.setForeground(Color.black);
		//this.setBackground(Color.black);
	}
	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * update the display of the clock
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object objList) {
		for (Object obj : (ArrayList<Object>) objList) {
			if (obj instanceof Number && (Integer) obj == 2)
				time.setText("00:00");
			else if (obj instanceof String) {
				time.setText(obj.toString());
			}
		}
	}
}