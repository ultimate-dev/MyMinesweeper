package myminesweeper;
//
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Game extends JFrame {
	// menu
	JMenuBar menubar;
	JMenu oyun_menu;
	JMenuItem yeni;
	JMenuItem kolay;
	JMenuItem orta;
	JMenuItem zor;
	JMenuItem ozel;
	// content
	JPanel contentpane;
	JPanel grid;
	Toolbar toolbar;
	// create_cells
	static Cell[][] Cells;
	static int cols;
	static int rows;
	static int totalmines;
	// generate_frame
	int width;
	int height;
	int containerX = 6;
	int containerY = 29;
	int menuHeight = 23;
	int toolbarHeight = 70;

	public Game(int cols, int rows, int totalmines) {
		// create
		Game.cols = cols;
		Game.rows = rows;
		Game.totalmines = totalmines;
		// frame_size
		width = (cols * 25);
		height = (rows * 25);
		// content
		ContentPane();
		Grid();
		ToolBar();
		MenuBar();
		// cells
		Cells = addCell();
		totalMine();
		countNeighbours();
		// frame
		generate();
		display();
	}

	/*
	 * Generate
	 */
	public void generate() {
		setTitle("Mayın Tarlası");
		setSize(width + containerX, height + containerY + toolbarHeight + menuHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// locaiton
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int centerX = dim.width / 2 - this.getSize().width / 2;
		final int centerY = dim.height / 2 - this.getSize().height / 2;
		setLocation(centerX, centerY);

	}

	public void display() {
		// dispalay
		setVisible(true);
		setResizable(false);
	}

	/*
	 * Content
	 */

	public void MenuBar() {
		menubar = new JMenuBar();
		setJMenuBar(menubar);
		// oyun_menu
		oyun_menu = new JMenu("Oyun");
		menubar.add(oyun_menu);
		// _Yeni
		yeni = new JMenuItem("Yeni");
		oyun_menu.add(yeni);
		yeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Game game = new Game(cols, rows, totalmines);
				toolbar.task.cancel();
				dispose();
			}
		});
		// --sperator
		oyun_menu.add(new JSeparator());
		// _Kolay
		kolay = new JMenuItem("Kolay");
		oyun_menu.add(kolay);
		kolay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Game game = new Game(9, 9, 10);
				toolbar.task.cancel();
				dispose();
			}
		});
		// _Orta
		orta = new JMenuItem("Orta");
		oyun_menu.add(orta);
		orta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Game game = new Game(16, 16, 40);
				toolbar.task.cancel();
				dispose();
			}
		});
		// _Zor
		zor = new JMenuItem("Zor");
		oyun_menu.add(zor);
		zor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Game game = new Game(30, 16, 99);
				toolbar.task.cancel();
				dispose();
			}
		});
		// _Ozel
		ozel = new JMenuItem("Ozel");
		oyun_menu.add(ozel);
		ozel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final MyMinesweeper.CustomGame customgame = new MyMinesweeper.CustomGame();
				toolbar.task.cancel();
                                dispose();
			}
		});
	}

	public void ContentPane() {
		contentpane = new JPanel();
		// layout
		final GroupLayout layout = new GroupLayout(contentpane);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, width, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING).addGap(0, height + toolbarHeight, Short.MAX_VALUE));
		contentpane.setLayout(layout);
		contentpane.setBackground(Color.decode("#555555"));
		add(contentpane);
	}

	public void Grid() {
		grid = new JPanel();
		// layout
		final GroupLayout layout = new GroupLayout(grid);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, width, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, height, Short.MAX_VALUE));
		grid.setLayout(layout);
		contentpane.add(grid);
		// style
		grid.setBackground(Color.red);
		// location
		grid.setBounds(0, toolbarHeight, width, height);

	}

	public void ToolBar() {
		toolbar = new Toolbar(width, totalmines);
		contentpane.add(toolbar);
	}
	/*
	 * Cells
	 */

	public Cell[][] addCell() {
		final Cell[][] Cells = new Cell[cols][rows];
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				Cells[col][row] = new Cell(col, row, this);
				grid.add(Cells[col][row]);
			}
		}
		return Cells;
	}

	/*
	 * Neighbors
	 */
	public void countNeighbours() {
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				if (Cells[col][row].mine) {
					Cells[col][row].neighbours = -1;
					Cells[col][row].show();
				} else {
					//
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							if (col + i < cols && col + i >= 0 && row + j < rows && row + j >= 0) {
								if (Cells[col + i][row + j].mine) {
									Cells[col][row].neighbours++;
									Cells[col][row].show();
								}
							}
						}
					}
					//
				}
			}
		}
	}

	/*
	 * Mines
	 */
	public void totalMine() {
		final Random rand = new Random();
		int mine = 0;
		while (mine < totalmines) {
			final int col = rand.nextInt(cols);
			final int row = rand.nextInt(rows);
			if (!Cells[col][row].mine) {
				Cells[col][row].mine = true;
				Cells[col][row].show();
				mine++;
			}
		}
	}

}
