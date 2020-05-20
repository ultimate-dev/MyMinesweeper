package myminesweeper;
//
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Toolbar extends JPanel {
	TimerTask task;
	Timer timer;
	static JLabel passingtime;
	static JLabel remainingmine;
	//
	Box box;
	//
	static int totalmines;

	public Toolbar(int width, int totalmines) {
		Toolbar.totalmines = totalmines;
		// layout
		final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(layout);
		box = Box.createHorizontalBox();
		add(box);
		// location
		setBounds(0, 0, width, 70);
		// style
		setBackground(new Color(0, 0, 0, 0));
		setBorder(new EmptyBorder(15, 15, 15, 15));
		// content
		remainingMine();
		box.add(Box.createHorizontalGlue());
		passingTime();
	}

	public void passingTime() {
		passingtime = new JLabel("" + totalmines);
		// style
		passingtime.setOpaque(true);
		passingtime.setForeground(Color.white);
		passingtime.setBackground(Color.decode("#454545"));
		passingtime.setMaximumSize(new Dimension(100, 40));
		passingtime.setPreferredSize(new Dimension(100, 40));
		passingtime.setHorizontalAlignment(SwingConstants.CENTER);
		passingtime.setVerticalAlignment(SwingConstants.CENTER);
		passingtime.setBorder(new LineBorder(Color.decode("#252525"), 1));
		passingtime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		// timer
		timer = new Timer();

		task = new TimerTask() {
			int sayac = 0;

			@Override
			public void run() {
				sayac++;
				passingtime.setText("" + sayac);
			}
		};

		timer.schedule(task, 0, 1000);
		//
		box.add(passingtime);
	}

	public void remainingMine() {
		remainingmine = new JLabel("" + totalmines);
		// style
		remainingmine.setOpaque(true);
		remainingmine.setForeground(Color.white);
		remainingmine.setBackground(Color.decode("#454545"));
		remainingmine.setMaximumSize(new Dimension(100, 40));
		remainingmine.setPreferredSize(new Dimension(100, 40));
		remainingmine.setHorizontalAlignment(SwingConstants.CENTER);
		remainingmine.setVerticalAlignment(SwingConstants.CENTER);
		remainingmine.setBorder(new LineBorder(Color.decode("#252525"), 1));
		remainingmine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//
		box.add(remainingmine);
	}

	@Override
	public void show() {

	}

	static public void onFlag(boolean flag) {
		if (flag) {
			totalmines--;
		} else {
			totalmines++;

		}
		remainingmine.setText("" + totalmines);
	}

}
