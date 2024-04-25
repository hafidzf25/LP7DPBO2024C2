import javax .swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360,640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Startmenu menu = new Startmenu();
        frame.add(menu.getMainPanel());
        frame.setVisible(true);

        menu.getMulaiButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close startmenu
                frame.dispose();

                // Buat Object JPanel
                FlappyBird flappyBird = new FlappyBird();
                frame.add(flappyBird);
                frame.pack();
                flappyBird.requestFocus();
                frame.setVisible(true);
            }
        });
    }
}
