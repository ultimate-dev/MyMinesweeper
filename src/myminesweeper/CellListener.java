package myminesweeper;
//
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class CellListener extends MouseAdapter {

	Cell cell;
	Game game;

	public CellListener(Cell cell, Game game) {
		this.cell = cell;
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!cell.mine) {
				cell.reveal();
				int totalreveal = 0;
				for (int col = 0; col < Game.cols; col++) {
					for (int row = 0; row < Game.rows; row++) {
						if (Game.Cells[col][row].revealed) {
							totalreveal++;
						}
					}
				}
				if (totalreveal == (Game.cols * Game.rows) - Game.totalmines) {
					final String message = "<html>"
							+ "<p>Oyun Başarılı Bir şekilde Bitirldi.</p><p>Yeni Oyun Başlatılıyor...</p>" + "</html>";
					Message(message);
				}
			} else {
				final String message = "<html>" + "<p>Mayına Bastınız.</p><p>Yeni Oyun Başlatılıyor...</p>" + "</html>";
				Message(message);
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			cell.putFlag();
		}

	}

	public void Message(String message) {
		JOptionPane.showMessageDialog(game, message);
		game.dispose();
		game.toolbar.task.cancel();
		final Game game = new Game(Game.cols, Game.rows, Game.totalmines);
	}

}
