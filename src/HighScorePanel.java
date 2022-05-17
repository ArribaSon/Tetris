import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class HighScorePanel  extends JFrame {

    public HighScorePanel(int[] scores, Tetris tetris){
        setResizable(false);

        setTitle("Top");
        setSize(200, 200);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                tetris.getGameField().pause();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                tetris.getGameField().pause();
                setVisible(false);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);


        StringBuilder string = new StringBuilder();

        for(int i = 0; i < scores.length; i++){
            string.append(scores[i]).append("\n");
        }

        textPane.setText(string.toString());
        add(textPane,BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }
}
