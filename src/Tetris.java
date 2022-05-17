import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {

    private final JLabel statusBar;
    private final GameField gameField;

    public Tetris() {

        setResizable(false);

        statusBar = new JLabel("0");
        add(statusBar, BorderLayout.SOUTH);

        gameField = new GameField(this);
        add(gameField);
        gameField.start();

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton newGame = new JButton("New Game");
        newGame.setFocusable(false);
        newGame.addActionListener(e -> gameField.newGame());
        toolBar.add(newGame);

        JButton pause = new JButton("Pause");
        pause.setFocusable(false);
        pause.addActionListener(e -> {
            getGameField().pause();
        });
        toolBar.add(pause);

        JButton highScore = new JButton("High score");
        highScore.setFocusable(false);
        highScore.addActionListener(e -> {
            HighScorePanel highScorePanel = new HighScorePanel(gameField.getScores(), this);
            highScorePanel.setVisible(true);
        });
        toolBar.add(highScore);

        JButton about = new JButton("About");
        about.setFocusable(false);
        about.addActionListener(e -> {
            AboutPanel aboutPanel = new AboutPanel(this);
            aboutPanel.setVisible(true);
        });
        toolBar.add(about);

        JButton exit = new JButton("Exit");
        exit.setFocusable(false);
        exit.addActionListener(e -> System.exit(0));
        toolBar.add(exit);

        add(toolBar, BorderLayout.NORTH);


        setTitle("Tetris");
        setSize(300, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }

    JLabel getStatusBar() {

        return statusBar;
    }

    public GameField getGameField() {
        return gameField;
    }
}
