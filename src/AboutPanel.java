import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AboutPanel extends JFrame {

    public AboutPanel(Tetris tetris) {
        setResizable(false);

        setTitle("About");
        setSize(400, 200);
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
        textPane.setText("""
                Тетрис
                Управление:
                ← переместить влево; → переместить вправо
                ↑ повернуть влево; ↓ повернуть вправо
                пробел - мгновенное падение
                                
                Created by Чистяков Константин
                    студент группы ЕТ-311
                Исходный код на GitHub: https://github.com/ArribaSon/Tetris
                """);
        add(textPane, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }

}
