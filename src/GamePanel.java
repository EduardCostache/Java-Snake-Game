import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH = 700;
    static final int HEIGHT = 700;
    static final int UNIT_SIZE = 25;
    static final int UNITS = (WIDTH * HEIGHT) / UNIT_SIZE;
    static int FRAME;

    final int[] x = new int[UNITS];
    final int[] y = new int[UNITS];
    int bodyParts = 6;
    int appleX;
    int appleY;
    int score = 0;

    boolean running = false;

    Timer timer;
    Random random;
    ReadOptions options;
    LoadSounds sounds;
    Walls wall1, wall2;

    char direction = 'R';

    GamePanel() {
        options = new ReadOptions();
        random = new Random();
        sounds = new LoadSounds();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(56,56,56));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        FRAME = Integer.parseInt(options.getOption("SPEED"));
        timer = new Timer(FRAME, this);
        timer.start();
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
            if(options.getOption("GRID").equals("true")) {
                g.setColor(new Color(108, 102, 102, 143));
                for (int i = 1; i < WIDTH / UNIT_SIZE; i++) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
                }
            }
            //Draw the apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Draw wall
            if(options.getOption("WALLS").equals("true")) {
                wall1 = new Walls(10, 10, 10, 20, UNIT_SIZE, g);
                wall2 = new Walls(5, 5, 26, 5, UNIT_SIZE, g);
            }

            //Draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 255, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else if (i == bodyParts - 1) {
                    g.setColor(new Color(16, 45, 97, 255));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(0, random.nextInt((255 - 100) + 1)+100, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.GREEN);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score, (WIDTH - metrics.stringWidth("Score: " + score)), 50);
        }
        else{
            sounds.playGameOverSound();
            gameOver(g);
        }
    }

    public void gameOver(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (WIDTH - metrics.stringWidth("Game Over!"))/2, HEIGHT/2);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Score: " + score, (WIDTH - metrics.stringWidth("Score: " + score))/2+100, HEIGHT/2+100);
    }

    public void checkCollisions() {
        // Collide with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }

        // Collide with walls
        if(options.getOption("WALLS").equals("true")) {
            if (y[0] >= (wall1.getStartY() * UNIT_SIZE) && (y[0] <= (wall1.getEndY() * UNIT_SIZE)))
                if (x[0] >= (wall1.getStartX() * UNIT_SIZE) && (x[0] <= (wall1.getEndX() * UNIT_SIZE)))
                    running = false;

            if (y[0] >= (wall2.getStartY() * UNIT_SIZE) && (y[0] <= (wall2.getEndY() * UNIT_SIZE)))
                if (x[0] >= (wall2.getStartX() * UNIT_SIZE) && (x[0] <= (wall2.getEndX() * UNIT_SIZE)))
                    running = false;
        }

        // Collide with borders
        if (x[0] > WIDTH) {
            x[0] = 0;
        }
        if (x[0] < 0) {
            x[0] = WIDTH;
        }
        if (y[0] > HEIGHT) {
            y[0] = 0;
        }
        if (y[0] < 0) {
            y[0] = HEIGHT;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            sounds.playAppleSound();
            newApple();
            bodyParts++;
            score++;
        }
    }

    public void newApple() {
        appleX = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
            }
        }
    }
}
