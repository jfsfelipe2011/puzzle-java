import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, MouseListener, MouseMotionListener {
    public static int WIDTH = 288;
    public static int HEIGHT = 288;
    public static int SCALE = 2;
    public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public Tabuleiro tabuleiro;
    public static boolean selected = false;
    public static int previousx = 0, previousy = 0;
    public static int nextx = -1, nexty = -1;
    public static Spritesheet spritesheet;


    public Game() {
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        spritesheet = new Spritesheet("/spritesheet.png");

        tabuleiro = new Tabuleiro();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle("Puzzle");
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        new Thread(game).start();
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        g.setColor(new Color(28, 201, 148));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        tabuleiro.render(g);

        g = bufferStrategy.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        g.dispose();
        bufferStrategy.show();
    }

    public void update() {
        tabuleiro.update();

        if (previousx < 0 || previousx >= Tabuleiro.GRID_SIZE * Tabuleiro.WIDTH
                || previousy < 0 || previousy >= Tabuleiro.GRID_SIZE * Tabuleiro.HEIGHT) {
            nextx = -1;
            nexty = -1;
            selected = false;
        }

        if (selected && (nextx != -1 && nexty != -1)) {

            if (nextx < 0 || nextx >= Tabuleiro.GRID_SIZE * Tabuleiro.WIDTH
                    || nexty < 0 || nexty >= Tabuleiro.GRID_SIZE * Tabuleiro.HEIGHT) {
                nextx = -1;
                nexty = -1;
                selected = false;
            }

            int posx1 = previousx / Tabuleiro.GRID_SIZE;
            int posy1 = previousy / Tabuleiro.GRID_SIZE;

            int posx2 = nextx / Tabuleiro.GRID_SIZE;
            int posy2 = nexty / Tabuleiro.GRID_SIZE;

            if ((posx2 == posx1 + 1 || posx2 == posx1 - 1) &&
                    posy2 == posy1 || posy2 == posy1 - 1 || posy2 == posy1 + 1 ) {

                if ((posx2 >= posx1 + 1 || posx2 <= posx1 - 1)
                        && (posy2 >= posy1 + 1 || posy2 <= posy1 - 1)) {
                    return;
                }

                int val1 = Tabuleiro.TABULEIRO[posx2][posy2];
                int val2 = Tabuleiro.TABULEIRO[posx1][posy1];

                Tabuleiro.TABULEIRO[posx1][posy1] = val1;
                Tabuleiro.TABULEIRO[posx2][posy2] = val2;

                nextx = -1;
                nexty = -1;
                selected = false;
            }
        }
    }

    @Override
    public void run() {
        double fps = 60.0;
        while (true) {
            update();
            render();
            try {
                Thread.sleep((int) (1000/fps));
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!selected) {
            selected = true;
            previousx = e.getX() / 2 - 24;
            previousy = e.getY() / 2 - 24;
        } else {
            nextx = e.getX() / 2 - 24;
            nexty = e.getY() / 2 - 24;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
