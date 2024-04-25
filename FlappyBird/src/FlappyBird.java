import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;
    boolean statusGame = true;
    String gameOverText = "Permainan Berakhir";
    String restartText = "R untuk Restart";

    // Player
    int playerStartPosX = frameHeight / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;
    Timer gameLoop;
    int gravity = 1;

    // Image Attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // Pipes attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;
    Timer pipesCooldown;

    // JLabel untuk Skor
    JLabel scoreLabel;
    private int score = 0;

    // Constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(360, 640));
        setFocusable(true);
        addKeyListener(this);

        // Load Images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bintang.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        pipesCooldown.start();

        // Inisialisasi JLabel untuk skor
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE); // Atur warna teks
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Atur jenis font dan ukuran
        scoreLabel.setBounds(10, frameHeight - 30, 200, 20); // Atur posisi dan ukuran
        add(scoreLabel); // Tambahkan JLabel ke dalam panel
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        if (statusGame){
            scoreLabel.setBounds(10, frameHeight - 30, 200, 20);
        }

        if (!statusGame) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(gameOverText, frameWidth / 2 - g.getFontMetrics().stringWidth(gameOverText) / 2, frameHeight / 2);
            g.setColor(Color.WHITE);
            g.drawString(restartText, frameWidth / 2 - g.getFontMetrics().stringWidth(restartText) / 2, frameHeight / 2 + 80);
        }
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage, "Upper");
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage, "Lower");
        pipes.add(lowerPipe);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // Kalo lewat dari JFrame bawah
        if (player.getPosY() > frameHeight) {
            gameLoop.stop();
            pipesCooldown.stop();
            statusGame = false;
        }

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
            System.out.println("Posisi" + player.getPosY() + "Pipa" + pipe.getPosX() + " " + pipe.getPosY());

            if (player.getPosX() < pipe.getPosX() + pipe.getWidth() && player.getPosX() + playerWidth -3 > pipe.getPosX() && player.getPosY() < pipe.getPosY() + pipe.getHeight() && player.getPosY() + playerHeight + 5 > pipe.getPosY()) {
                statusGame = false;
                gameLoop.stop();
                pipesCooldown.stop();
            }

            // Periksa jika burung melewati pipa
            if (player.getPosX() > pipe.getPosX() + pipe.getWidth() && !pipe.isPassed() && pipe.getStatus() == "Lower") {
                pipe.setPassed(true); // Set pipa menjadi sudah dilewati
                score++;
                scoreLabel.setText("Score: " + score);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
        }
        if (!statusGame) {
            if(e.getKeyCode() == KeyEvent.VK_R) {
                score = 0;
                scoreLabel.setText("Score: " + score);
                player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
                pipes = new ArrayList<Pipe>();
                statusGame = true;

                gameLoop = new Timer(1000/60, this);
                gameLoop.start();

                pipesCooldown = new Timer(1500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        placePipes();
                    }
                });
                pipesCooldown.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
