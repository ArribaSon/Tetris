import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel {

    private final int boardWidth = 10;
    private final int boardHeight = 22;
    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isPaused = false;
    private int numberLinesRemoved;
    private int currentX = 0;
    private int currentY = 0;
    private final JLabel statusBar;
    private Shape currentShape;
    private TypeShape[] board;

    private final int[] scores;

    public GameField(Tetris parent) {

        setFocusable(true);
        requestFocus();
        numberLinesRemoved = 0;
        statusBar = parent.getStatusBar();
        addKeyListener(new TAdapter());

        scores = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    void start() {

        currentShape = new Shape();
        board = new TypeShape[boardWidth * boardHeight];

        clearBoard();
        newPiece();

        timer = new Timer(300, new GameCycle());
        timer.start();
    }

    void newGame() {
        dropDown();
        clearBoard();
        newScore();

        currentShape.setRandomShape();
        timer.start();

        statusBar.setText(Integer.toString(numberLinesRemoved));
        if(isPaused){
            pause();
        }
        repaint();
    }

    public void pause() {

        isPaused = !isPaused;

        if (isPaused) {

            statusBar.setText("paused");
        } else {

            statusBar.setText(String.valueOf(numberLinesRemoved));
        }

        repaint();
    }

    private void draw(Graphics g) {

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - boardHeight * rectangleHeight();

        for (int i = 0; i < boardHeight; i++) {

            for (int j = 0; j < boardWidth; j++) {

                TypeShape shape = shapeAt(j, boardHeight - i - 1);

                if (shape != TypeShape.NoShape) {

                    drawRectangle(g, j * rectangleWidth(),
                            boardTop + i * rectangleHeight(), shape);
                }
            }
        }

        if (currentShape.getShape() != TypeShape.NoShape) {

            for (int i = 0; i < 4; i++) {

                int x = currentX + currentShape.getX(i);
                int y = currentY - currentShape.getY(i);

                drawRectangle(g, x * rectangleWidth(),
                        boardTop + (boardHeight - y - 1) * rectangleHeight(),
                        currentShape.getShape());
            }
        }
    }

    private void dropDown() {
        if(!isPaused){
            int newY = currentY;

            while (newY > 0) {

                if (!move(currentShape, currentX, newY - 1)) {

                    break;
                }

                newY--;
            }

            pieceDropped();
        }
    }

    private void clearBoard() {

        for (int i = 0; i < boardHeight * boardWidth; i++) {

            board[i] = TypeShape.NoShape;
        }
    }

    private void pieceDropped() {

        for (int i = 0; i < 4; i++) {

            int x = currentX + currentShape.getX(i);
            int y = currentY - currentShape.getY(i);
            board[(y * boardWidth) + x] = currentShape.getShape();
        }

        removeLines();

        if (!isFallingFinished) {

            newPiece();
        }
    }

    private boolean move(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; i++) {

            int x = newX + newPiece.getX(i);
            int y = newY - newPiece.getY(i);

            if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {

                return false;
            }

            if (shapeAt(x, y) != TypeShape.NoShape) {

                return false;
            }

            if (isPaused) {

                return false;
            }
        }

        currentShape = newPiece;
        currentX = newX;
        currentY = newY;

        repaint();

        return true;
    }

    private void removeLines() {

        int numFullLines = 0;

        for (int i = boardHeight - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < boardWidth; j++) {

                if (shapeAt(j, i) == TypeShape.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                numFullLines++;

                for (int k = i; k < boardHeight - 1; k++) {
                    for (int j = 0; j < boardWidth; j++) {
                        board[(k * boardWidth) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numberLinesRemoved += numFullLines;

            statusBar.setText(String.valueOf(numberLinesRemoved));
            isFallingFinished = true;
            currentShape.setShape(TypeShape.NoShape);
        }
    }

    private void drawRectangle(Graphics g, int x, int y, TypeShape shape) {

        Color[] colors = {
                new Color(0, 0, 0),
                new Color(218, 21, 21),
                new Color(16, 201, 16),
                new Color(55, 55, 196),
                new Color(180, 180, 32),
                new Color(162, 26, 162),
                new Color(25, 182, 182),
                new Color(51, 82, 15)
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, rectangleWidth() - 2, rectangleHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + rectangleHeight() - 1, x, y);
        g.drawLine(x, y, x + rectangleWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + rectangleHeight() - 1,
                x + rectangleWidth() - 1, y + rectangleHeight() - 1);
        g.drawLine(x + rectangleWidth() - 1, y + rectangleHeight() - 1,
                x + rectangleWidth() - 1, y + 1);
    }

    //Игровой цикл
    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            gameCycle();
        }
    }

    private void gameCycle() {

        update();
        repaint();
    }

    private void update() {

        if (isPaused) {

            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    private void newPiece() {

        currentShape.setRandomShape();
        currentX = boardWidth / 2 + 1;
        currentY = boardHeight - 1 + currentShape.getMinY();

        if (!move(currentShape, currentX, currentY)) {

            currentShape.setShape(TypeShape.NoShape);
            timer.stop();

            String msg = String.format("Game over. Score: %d", numberLinesRemoved);
            statusBar.setText(msg);

            newScore();
        }
    }

    private void newScore(){
        for(int i = 0; i < scores.length; i++){
            if(scores[i] < numberLinesRemoved){
                for(int j = scores.length - 1; j > i; j--){
                    scores[j] = scores[j-1];
                }
                scores[i] = numberLinesRemoved;
                break;
            }
        }
        numberLinesRemoved = 0;
    }

    private void oneLineDown() {

        if (!move(currentShape, currentX, currentY - 1)) {

            pieceDropped();
        }
    }

    //Привязка клавиш
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (currentShape.getShape() == TypeShape.NoShape) {

                return;
            }

            int keycode = e.getKeyCode();

            switch (keycode) {

                case KeyEvent.VK_P -> pause();
                case KeyEvent.VK_LEFT -> move(currentShape, currentX - 1, currentY);
                case KeyEvent.VK_RIGHT -> move(currentShape, currentX + 1, currentY);
                case KeyEvent.VK_DOWN -> move(currentShape.rotateRight(), currentX, currentY);
                case KeyEvent.VK_UP -> move(currentShape.rotateLeft(), currentX, currentY);
                case KeyEvent.VK_SPACE -> dropDown();
                case KeyEvent.VK_D -> oneLineDown();
            }
        }
    }

    //Геттеры
    private int rectangleWidth() {

        return (int) getSize().getWidth() / boardWidth;
    }

    private int rectangleHeight() {

        return (int) getSize().getHeight() / boardHeight;
    }

    private TypeShape shapeAt(int x, int y) {

        return board[(y * boardWidth) + x];
    }

    public int[] getScores() {

        return scores;
    }
}
