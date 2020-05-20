package myminesweeper;
//
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MyMinesweeper extends JFrame implements Runnable {
	static MyMinesweeper mw;
	JPanel contentpane;

	/*
	 * MA�N
	 */
	public static void main(String[] args) {
		mw = new MyMinesweeper();
		final Thread minesweeper = new Thread(mw);
		minesweeper.start();
	}

	public MyMinesweeper() {
		ContentPane();
	}

	@Override
	public void run() {
		Create();
		Display();
	}

	public void Create() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(330, 330);
		setTitle("Mayın Tarlam");
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int centerX = dim.width / 2 - this.getSize().width / 2;
		final int centerY = dim.height / 2 - this.getSize().height / 2;
		setLocation(centerX, centerY);
	}

	public void Display() {
		setVisible(true);
		setResizable(false);
	}

	/*
	 * CONTENT_PANE
	 */
	public void ContentPane() {
		contentpane = new JPanel();
		// layout
		final GroupLayout layout = new GroupLayout(contentpane);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
		contentpane.setLayout(layout);
		// style
		contentpane.setBackground(Color.decode("#454545"));
		add(contentpane);
		// Modlar_---------------------------------------
		String head;
		String text;
		// Kalsik_Mod
		head = "Mayın Tarlası";
		text = "<html>"
				+ "<p> Oyunun amacı bir alanda mayınlara rastlamadan tüm boş kareleri bulmaktır. Karelere tıklayınca çıkan sayılar ise karenin etrafındaki mayın sayısının toplamını gösterir.</p>"
				+ "</html>";
		final ModBox classicmode = new ModBox(0, head, text);
		contentpane.add(classicmode);
		// easy
		final ModButton classic_easy = new ModButton(0, 0, "Kolay");
		classicmode.add(classic_easy);
		classic_easy.addAction(9, 9, 10);
		// medium
		final ModButton classic_medium = new ModButton(1, 0, "Orta");
		classicmode.add(classic_medium);
		classic_medium.addAction(16, 16, 40);
		// hard
		final ModButton classic_hard = new ModButton(2, 0, "Zor");
		classicmode.add(classic_hard);
		classic_hard.addAction(30, 16, 99);
		// custom
		final ModButton classic_custom = new ModButton(3, 0, "Özel");
		classicmode.add(classic_custom);
		classic_custom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final CustomGame customgame = new CustomGame();
                                dispose();
			}
		});
	}

	/*
	 * MODS
	 */
	public class ModBox extends JPanel {
		JLabel Head;
		JLabel Text;
		JPanel ModButtons;

		public ModBox(int i, String head, String text) {
			// layout
			final GroupLayout layout = new GroupLayout(this);
			layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
			layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
			setLayout(layout);
			// style
			setBounds((315 * i) + (15 * (i + 1)), 15, 315, 440);
			setBackground(Color.decode("#454545"));
			// add
			Head(head);
			Text(text);
			ModButtons();
		}

		public void Head(String head) {
			Head = new JLabel(head);
			// location
			Head.setBounds(15, 15, 285, 25);
			// style
			Head.setFont(new Font("Tahoma", Font.PLAIN, 20));
			Head.setForeground(Color.white);
			//
			add(Head);
		}

		public void Text(String text) {
			Text = new JLabel(text);
			// location
			Text.setBounds(15, 25, 285, 100);
			// style
			Text.setFont(new Font("Tahoma", Font.PLAIN, 14));
			Text.setForeground(Color.decode("#c2c2c2"));
			//
			add(Text);
		}

		public void ModButtons() {
			ModButtons = new JPanel();
			// style
			setBorder(new EmptyBorder(0, 0, 0, 0));
			//
			add(ModButtons);

		}
	}

	public class ModButton extends JButton {
		int cols;
		int rows;
		int totalMines;

		public ModButton(int i, int j, String text) {
			// location
			setBounds((i * 55) + (15 * (i + 1)), (260 - (j + 1 * 50)), 55, 50);
			// style
			setBackground(Color.decode("#353535"));
			setBorder(new LineBorder(Color.decode("#353535"), 1));
			setFont(new Font("Tahoma", Font.PLAIN, 14));
			setForeground(Color.white);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			setHorizontalAlignment(SwingConstants.CENTER);
			setVerticalAlignment(SwingConstants.CENTER);
			// text
			setText(text);
		}

		public void addAction(int cols, int rows, int totalMines) {
			this.cols = cols;
			this.rows = rows;
			this.totalMines = totalMines;
			// action
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final Game game = new Game(cols, rows, totalMines);
					dispose();
				}
			});
		}

	}

	public static class CustomGame extends JFrame {
		JPanel contentpane;

		//
		JLabel cols_num_label;
		JTextField cols_num;
		JLabel rows_num_label;
		JTextField rows_num;
		JLabel mines_num_label;
		JTextField mines_num;
		JButton ok;

		public CustomGame() {
			//
			setTitle("Özel Oyun Olu�tur");
			setResizable(false);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// location
			setSize(375, 275);
			final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			final int centerX = dim.width / 2 - this.getSize().width / 2;
			final int centerY = dim.height / 2 - this.getSize().height / 2;
			setLocation(centerX, centerY);
			//
			ContentPane();
			// label
			cols_num_label = new JLabel("Genişlik");
			cols_num_label.setBounds(25, 25, 100, 25);
			cols_num_label.setForeground(Color.white);
			cols_num_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentpane.add(cols_num_label);
			// text
			cols_num = new JTextField();
			cols_num.setColumns(10);
			cols_num.setBounds(125, 25, 200, 25);
			cols_num.setBackground(Color.decode("#555555"));
			cols_num.setBorder(new LineBorder(Color.decode("#252525"), 1));
			cols_num.setForeground(Color.white);
			cols_num.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentpane.add(cols_num);
			// label
			rows_num_label = new JLabel("Yükseklik");
			rows_num_label.setBounds(25, 75, 100, 25);
			rows_num_label.setForeground(Color.white);
			rows_num_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentpane.add(rows_num_label);
			// text
			rows_num = new JTextField();
			rows_num.setColumns(10);
			rows_num.setBounds(125, 75, 200, 25);
			rows_num.setBackground(Color.decode("#555555"));
			rows_num.setBorder(new LineBorder(Color.decode("#252525"), 1));
			rows_num.setForeground(Color.white);
			rows_num.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentpane.add(rows_num);
			// label
			mines_num_label = new JLabel("Mayın Sayısı");
			mines_num_label.setBounds(25, 125, 100, 25);
			mines_num_label.setForeground(Color.white);
			mines_num_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentpane.add(mines_num_label);
			// text
			mines_num = new JTextField();
			mines_num.setColumns(10);
			mines_num.setBounds(125, 125, 200, 25);
			mines_num.setBackground(Color.decode("#555555"));
			mines_num.setBorder(new LineBorder(Color.decode("#252525"), 1));
			mines_num.setForeground(Color.white);
			mines_num.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentpane.add(mines_num);
			// button
			ok = new JButton("Oluştur");
			ok.setBounds(125, 175, 200, 50);
			ok.setFont(new Font("Tahoma", Font.PLAIN, 14));
			ok.setBackground(Color.decode("#555555"));
			ok.setBorder(new LineBorder(Color.decode("#252525"), 1));
			ok.setForeground(Color.white);
			ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
			contentpane.add(ok);
			// action
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final int cols = Integer.parseInt(cols_num.getText());
					final int rows = Integer.parseInt(cols_num.getText());
					final int totalmines = Integer.parseInt(mines_num.getText());
					final Game game = new Game(cols, rows, totalmines);
					dispose();
					mw.dispose();
				}
			});
		}

		public void ContentPane() {
			contentpane = new JPanel();
			contentpane.setBackground(Color.decode("#454545"));
			add(contentpane);
			final GroupLayout layout = new GroupLayout(contentpane);
			layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
			layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
			contentpane.setLayout(layout);
		}

	}
}
