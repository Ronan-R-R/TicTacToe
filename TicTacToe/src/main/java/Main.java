import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Tic Tac Toe game with PvP and PvE modes
public class Main extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private JLabel turnLabel;
    private int turn;
    private boolean playerVsPlayer;
    private boolean playerTurn;
    private int player1Turns;
    private int player2Turns;

    public Main() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        turnLabel = new JLabel("");
        add(panel, BorderLayout.CENTER);
        add(turnLabel, BorderLayout.NORTH);

        chooseMode();

        resetGame();
    }

    // Method to choose game mode
    private void chooseMode() {
        Object[] options = {"Player vs Player", "Player vs AI"};
        int choice = JOptionPane.showOptionDialog(this, "Choose Game Mode", "Mode Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            playerVsPlayer = true;
        } else {
            playerVsPlayer = false;
        }
    }

    // Method to reset the game
    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(UIManager.getColor("Button.background"));
            }
        }
        turn = 1;
        playerTurn = true;
        if (playerVsPlayer)
            turnLabel.setText("Player 1's turn");
        else
            turnLabel.setText("Your turn");
        player1Turns = 0;
        player2Turns = 0;
    }

    // Method to check for a win
    private void checkWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals("")) {
                highlightWinningCells(i, 0, i, 1, i, 2);
                gameOver(board[i][0]);
                return;
            }
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals("")) {
                highlightWinningCells(0, i, 1, i, 2, i);
                gameOver(board[0][i]);
                return;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals("")) {
            highlightWinningCells(0, 0, 1, 1, 2, 2);
            gameOver(board[0][0]);
            return;
        }
        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals("")) {
            highlightWinningCells(0, 2, 1, 1, 2, 0);
            gameOver(board[0][2]);
            return;
        }
        if (player1Turns + player2Turns == 9) {
            gameOver("tie");
            return;
        }
    }

    // Method to highlight winning cells
    private void highlightWinningCells(int x1, int y1, int x2, int y2, int x3, int y3) {
        if (buttons[x1][y1].getText().equals("X")) {
            buttons[x1][y1].setBackground(Color.GREEN);
            buttons[x2][y2].setBackground(Color.GREEN);
            buttons[x3][y3].setBackground(Color.GREEN);
        } else if (buttons[x1][y1].getText().equals("O")) {
            buttons[x1][y1].setBackground(Color.RED); // Change AI win color to red
            buttons[x2][y2].setBackground(Color.RED);
            buttons[x3][y3].setBackground(Color.RED);
        }
    }

    // Method to handle game over
    private void gameOver(String winner) {
        if (winner.equals("X")) {
            turnLabel.setText("Player 1 wins!");
        } else if (winner.equals("O")) {
            if (playerVsPlayer)
                turnLabel.setText("Player 2 wins!");
            else
                turnLabel.setText("AI wins!");
        } else {
            turnLabel.setText("It's a tie!");
        }

        int option = JOptionPane.showConfirmDialog(this, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    // Method to handle AI move
    private void aiMove() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText();
            }
        }

        // Check for AI win
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals("O") && board[i][2].isEmpty()) {
                buttons[i][2].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[i][1].equals(board[i][2]) && board[i][1].equals("O") && board[i][0].isEmpty()) {
                buttons[i][0].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[i][0].equals(board[i][2]) && board[i][0].equals("O") && board[i][1].isEmpty()) {
                buttons[i][1].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[0][i].equals(board[1][i]) && board[0][i].equals("O") && board[2][i].isEmpty()) {
                buttons[2][i].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[1][i].equals(board[2][i]) && board[1][i].equals("O") && board[0][i].isEmpty()) {
                buttons[0][i].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[0][i].equals(board[2][i]) && board[0][i].equals("O") && board[1][i].isEmpty()) {
                buttons[1][i].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
        }

        // Check for player win and block
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals("X") && board[i][2].isEmpty()) {
                buttons[i][2].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[i][1].equals(board[i][2]) && board[i][1].equals("X") && board[i][0].isEmpty()) {
                buttons[i][0].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[i][0].equals(board[i][2]) && board[i][0].equals("X") && board[i][1].isEmpty()) {
                buttons[i][1].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[0][i].equals(board[1][i]) && board[0][i].equals("X") && board[2][i].isEmpty()) {
                buttons[2][i].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[1][i].equals(board[2][i]) && board[1][i].equals("X") && board[0][i].isEmpty()) {
                buttons[0][i].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
            if (board[0][i].equals(board[2][i]) && board[0][i].equals("X") && board[1][i].isEmpty()) {
                buttons[1][i].setText("O");
                player2Turns++;
                playerTurn = true;
                turnLabel.setText("Player 1's turn");
                checkWin();
                return;
            }
        }

        // If AI can't win or block, it plays randomly
        int x, y;
        do {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
        } while (!buttons[x][y].getText().isEmpty());
        buttons[x][y].setText("O");
        player2Turns++;
        playerTurn = true;
        turnLabel.setText("Player 1's turn");
        checkWin();
    }

    // Method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            if (playerVsPlayer) {
                if (playerTurn) {
                    button.setText("X");
                    player1Turns++;
                    turnLabel.setText("Player 2's turn");
                } else {
                    button.setText("O");
                    player2Turns++;
                    turnLabel.setText("Player 1's turn");
                }
                turn++;
                playerTurn = !playerTurn;
                button.setEnabled(false); // Disable button after selection
                checkWin();
            } else {
                if (playerTurn) {
                    button.setText("X");
                    player1Turns++;
                    turnLabel.setText("AI's turn");
                    turn++;
                    playerTurn = !playerTurn;
                    button.setEnabled(false); // Disable button after selection
                    checkWin();
                    aiMove();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setLocationRelativeTo(null); // Center the main window
            game.setVisible(true);
        });
    }
}
