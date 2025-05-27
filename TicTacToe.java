// import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicTacToe extends JFrame {

    public static void main(String[] args) {
        new TicTacToe();
    }

    JButton players = new JButton("Player vs Player");
    JButton computer = new JButton("Player vs Computer");
    
    public TicTacToe(){

        setTitle("Tic-Tac-Toe");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel home = new JPanel(); 

        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setAlignmentX(Component.CENTER_ALIGNMENT);

   
        players.setAlignmentX(Component.CENTER_ALIGNMENT);
        computer.setAlignmentX(Component.CENTER_ALIGNMENT);

        home.add(Box.createVerticalGlue()); // push buttons to center vertically
        home.add(players);
        home.add(Box.createRigidArea(new Dimension(0, 20))); // spacing between buttons
        home.add(computer);
        home.add(Box.createVerticalGlue());

        players.addActionListener(e -> new PlayersFrame());
        computer.addActionListener(e -> new ComputerFrame());

        add(home, BorderLayout.CENTER);
        setVisible(true);
        
    }
}

class PlayersFrame extends JFrame implements ActionListener{
    JButton[][] buttons = new JButton[3][3];
    JButton restart = new JButton("Restart");

    char player1 = 'X';
    char player2 = 'O';
    char turn = player1; 
    boolean gameOver = false;

    public PlayersFrame(){

        setTitle("Tic Tac Toe - Player1 vs Player2");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel board = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                board.add(buttons[i][j]);
            }
        }

        restart.addActionListener(e -> resetGame());

        add(board, BorderLayout.CENTER);
        add(restart, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton clicked = (JButton) e.getSource();
        if (clicked.getText().equals("")) {
            clicked.setText(String.valueOf(turn));

            if (checkWinner(turn)) {
                JOptionPane.showMessageDialog(this, turn+" Player Wins!");
                gameOver = true;
            } else if (isFull()) {
                JOptionPane.showMessageDialog(this, "It's a Draw!");
                gameOver = true;
            } else {
                if(turn == player1)
                    turn = player2;
                else
                    turn = player1;
            }
        }
    }    

    boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals(""))
                    return false;
        return true;
    }

    public boolean checkWinner(char symbol) {
        for (int i = 0; i < 3; i++)
            if (buttons[i][0].getText().equals(String.valueOf(symbol)) &&
                buttons[i][1].getText().equals(String.valueOf(symbol)) &&
                buttons[i][2].getText().equals(String.valueOf(symbol)))
                return true;

        for (int j = 0; j < 3; j++)
            if (buttons[0][j].getText().equals(String.valueOf(symbol)) &&
                buttons[1][j].getText().equals(String.valueOf(symbol)) &&
                buttons[2][j].getText().equals(String.valueOf(symbol)))
                return true;

        if (buttons[0][0].getText().equals(String.valueOf(symbol)) &&
            buttons[1][1].getText().equals(String.valueOf(symbol)) &&
            buttons[2][2].getText().equals(String.valueOf(symbol)))
            return true;

        if (buttons[0][2].getText().equals(String.valueOf(symbol)) &&
            buttons[1][1].getText().equals(String.valueOf(symbol)) &&
            buttons[2][0].getText().equals(String.valueOf(symbol)))
            return true;

        return false;
    }

    public void resetGame() {
       
        gameOver = false;

        for(int i=0; i<3; i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        
        throw new UnsupportedOperationException("Unimplemented method 'resetGame'");
    }

    public static void main(String[] args) {
        new PlayersFrame();
    }
}

class ComputerFrame extends JFrame implements ActionListener{

    JButton[][] buttons = new JButton[3][3]; 
    JButton restart = new JButton("Restart");

    char player = 'X';
    char comp = 'O';
    boolean gameOver = false;

    public ComputerFrame(){

        setTitle("Tic Tac Toe - Player vs Computer");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel board = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                board.add(buttons[i][j]);
            }
        }

        restart.addActionListener(e -> resetGame());

        add(board, BorderLayout.CENTER);
        add(restart, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton clicked = (JButton) e.getSource();
        if (clicked.getText().equals("")) {
            clicked.setText(String.valueOf(player));
            if (checkWinner(player)) {
                JOptionPane.showMessageDialog(this, player+"Player Wins!");
                gameOver = true;
            } else if (isFull()) {
                JOptionPane.showMessageDialog(this, "It's a Draw!");
                gameOver = true;
            } else {
                computerMove();
            }
        }
    }

    void computerMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText(String.valueOf(comp)); // try move
                    int score = minimax(false);
                    buttons[i][j].setText(""); // undo move

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        buttons[bestRow][bestCol].setText(String.valueOf(comp));

        if (checkWinner(comp)) {
            JOptionPane.showMessageDialog(this, "Computer Wins!");
            gameOver = true;
        } else if (isFull()) {
            JOptionPane.showMessageDialog(this, "It's a Draw!");
            gameOver = true;
        }
    }

    int minimax(boolean isMaximizing) {
        if (checkWinner(comp)) return +10;
        if (checkWinner(player)) return -10;
        if (isFull()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setText(String.valueOf(comp));
                        int score = minimax(false);
                        buttons[i][j].setText("");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        buttons[i][j].setText(String.valueOf(player));
                        int score = minimax(true);
                        buttons[i][j].setText("");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals(""))
                    return false;
        return true;
    }

    public boolean checkWinner(char symbol) {
        for (int i = 0; i < 3; i++)
            if (buttons[i][0].getText().equals(String.valueOf(symbol)) &&
                buttons[i][1].getText().equals(String.valueOf(symbol)) &&
                buttons[i][2].getText().equals(String.valueOf(symbol)))
                return true;

        for (int j = 0; j < 3; j++)
            if (buttons[0][j].getText().equals(String.valueOf(symbol)) &&
                buttons[1][j].getText().equals(String.valueOf(symbol)) &&
                buttons[2][j].getText().equals(String.valueOf(symbol)))
                return true;

        if (buttons[0][0].getText().equals(String.valueOf(symbol)) &&
            buttons[1][1].getText().equals(String.valueOf(symbol)) &&
            buttons[2][2].getText().equals(String.valueOf(symbol)))
            return true;

        if (buttons[0][2].getText().equals(String.valueOf(symbol)) &&
            buttons[1][1].getText().equals(String.valueOf(symbol)) &&
            buttons[2][0].getText().equals(String.valueOf(symbol)))
            return true;

        return false;
    }

    public void resetGame() {
       
        gameOver = false;

        for(int i=0; i<3; i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        
        throw new UnsupportedOperationException("Unimplemented method 'resetGame'");
    }

    public static void main(String[] args){
        new ComputerFrame();
    }
}