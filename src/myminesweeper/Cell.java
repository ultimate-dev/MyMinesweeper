package myminesweeper;
//
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Cell extends JButton {
	boolean mine;
	boolean revealed;
	boolean flag;
	int neighbours;
	int col;
	int row;

	public Cell(int col, int row, Game game) {
		// location
		setBounds(25 * col, 25 * row, 25, 25);
		// style
		setBackground(Color.decode("#454545"));
		setBorder(new LineBorder(Color.decode("#252525"), 1));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		setFont(new Font("Tahoma", Font.BOLD, 15));
		// listener
		final CellListener listener = new CellListener(this, game);
		addMouseListener(listener);
		show();
		//
		this.col = col;
		this.row = row;
	}

	@Override
	public void show() {
		if (revealed) {
			if (mine) {
				setBackground(Color.red);
				setText("M");
			} else if (flag) {
				setBackground(Color.decode("#454545"));
				setText("");

			} else {
				setText("");
				setBackground(Color.gray);
				if (neighbours > 0) {
					setText("" + neighbours);
					onColor(neighbours);
				}
			}
		} else {
			if (flag) {
				setBackground(Color.yellow);
				setText("F");
			} else {
				setBackground(Color.decode("#454545"));
				setText("");
			}
		}
	}

	public void onColor(int i) {
		switch (i) {
		case 1:
			setForeground(Color.cyan);
			break;
		case 2:
			setForeground(new Color(0, 0, 255));
			break;
		case 3:
			setForeground(new Color(0, 128, 0));
			break;
		case 4:
			setForeground(new Color(255, 255, 0));
			break;
		case 5:
			setForeground(new Color(255, 165, 0));
			break;
		case 6:
			setForeground(new Color(75, 0, 130));
			break;
		case 7:
			setForeground(new Color(238, 130, 238));
			break;
		case 8:
			setForeground(new Color(255, 0, 0));
			break;
		default:
			break;
		}
	}

	public void reveal() {
		revealed = true;
		if (neighbours == 0) {
			neighboursReveal();
		}
		if (flag == true) {
			flag = false;
			Toolbar.onFlag(flag);

		}
		show();
	}

	public void neighboursReveal() {
		if (mine == false) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (col + i < Game.cols && col + i >= 0 && row + j < Game.rows && row + j >= 0) {
						if (Game.Cells[col + i][row + j].mine == false
								&& Game.Cells[col + i][row + j].revealed == false) {
							Game.Cells[col + i][row + j].reveal();
						}
					}
				}
			}
		}
	}

	public void putFlag() {
		if (!revealed) {
			flag = !flag;
			Toolbar.onFlag(flag);
			show();
		}
	}
}
