import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {

	public static final int BOARD_SIZE = 4;
	boolean crossTurn = true;
	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];

	private static enum GameStatus {
		Incomplete, Xwins, Zwins, Tie
	}

	public TicTacToe() {
		super.setTitle("Tic Tac Toe");
		super.setSize(600, 600);
		GridLayout gl = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(gl);
		Font font = new Font("Comic Sans", 1, 100);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton();
				buttons[row][col] = button;
				button.setFont(font);
				button.addActionListener(this);
				super.add(button);
			}
		}

		super.setResizable(false);
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clkbtn = (JButton) e.getSource();
		this.makeMove(clkbtn);
		GameStatus gs = this.getGameStatus();
		if (gs != GameStatus.Incomplete) {
			this.declareWinner(gs);
			int choice = JOptionPane.showConfirmDialog(this, "Restart?");
			if (choice == JOptionPane.YES_OPTION) {
				for (int row = 0; row < BOARD_SIZE; row++) {
					for (int col = 0; col < BOARD_SIZE; col++) {
						buttons[row][col].setText("");
					}
				}
				crossTurn = true;
			} else {
				super.dispose();
			}
		}
	}

	private void makeMove(JButton clickedButton) {
		if (clickedButton.getText().length() > 0) {
			JOptionPane.showMessageDialog(clickedButton, "Invalid move!");
			return;
		}
		if (crossTurn) {
			clickedButton.setText("X");
		} else {
			clickedButton.setText("0");
		}
		crossTurn = !crossTurn;
	}

	private GameStatus getGameStatus() {
		String text1 = "", text2 = "";
		// test in rows
		int row = 0, col = 0;
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE - 1; col++) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();
				if (text1 != text2 || text1.length() == 0) {
					break;
				}
			}
			if (col == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.Xwins;
				} else {
					return GameStatus.Zwins;
				}
			}
		}
		// test in cols

		for (col = 0; col < BOARD_SIZE; col++) {
			for (row = 0; row < BOARD_SIZE - 1; row++) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row + 1][col].getText();
				if (text1 != text2 || text1.length() == 0) {
					break;
				}
			}
			if (row == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.Xwins;
				} else {
					return GameStatus.Zwins;
				}
			}
		}

		// test in diag1

		for (row = 0; row < BOARD_SIZE - 1; row++) {
			text1 = buttons[row][row].getText();
			text2 = buttons[row + 1][row + 1].getText();
			if (text1 != text2 || text1.length() == 0) {
				break;
			}
		}
		if (row == BOARD_SIZE - 1) {
			if (text1.equals("X")) {
				return GameStatus.Xwins;
			} else {
				return GameStatus.Zwins;
			}
		}

		// test in diag2

		for (row = 0; row < BOARD_SIZE - 1; row++) {
			text1 = buttons[row][BOARD_SIZE - 1 - row].getText();
			text2 = buttons[row + 1][BOARD_SIZE - 2 - row].getText();
			if (text1 != text2 || text1.length() == 0) {
				break;
			}
		}
		if (row == BOARD_SIZE - 1) {
			if (text1.equals("X")) {
				return GameStatus.Xwins;
			} else {
				return GameStatus.Zwins;
			}
		}

		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				if (buttons[row][col].getText().length() == 0) {
					return GameStatus.Incomplete;
				}
			}
		}
		return GameStatus.Tie;
	}

	private void declareWinner(GameStatus gs) {
		if (gs == GameStatus.Xwins) {
			JOptionPane.showMessageDialog(this, "X Wins!");
		} else if (gs == GameStatus.Zwins) {
			JOptionPane.showMessageDialog(this, "Z Wins!");
		} else if (gs == GameStatus.Tie) {
			JOptionPane.showMessageDialog(this, "It's a Tie!");
		}
	}

}
