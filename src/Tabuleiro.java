import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {
    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    public static int[][] TABULEIRO;
    public static int DOCE_0 = 0;
    public static int DOCE_1 = 1;
    public static int DOCE_2 = 2;
    public static int GRID_SIZE = 40;
    public BufferedImage DOCE_0_SPRITE = Game.spritesheet.getSprite(272, 919, 153, 134);
    public BufferedImage DOCE_1_SPRITE = Game.spritesheet.getSprite(276, 1087, 155, 122);
    public BufferedImage DOCE_2_SPRITE = Game.spritesheet.getSprite(254, 1257, 147, 122);

    public Tabuleiro() {
        TABULEIRO = new int[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                TABULEIRO[x][y] = new Random().nextInt(3);
            }
        }
    }

    public void update() {
        ArrayList<Candy> candies = new ArrayList<>();

        for (int yy = 0; yy < HEIGHT; yy++) {
            if (candies.size() == 3) {
                for (int i = 0; i < candies.size(); i++) {
                    int xtemp = candies.get(i).x;
                    int ytemp = candies.get(i).y;

                    TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                }
                candies.clear();
                System.out.println("Ponto");
                return;
            }
            candies.clear();

            for (int xx = 0; xx < WIDTH; xx++) {
                int cor = TABULEIRO[xx][yy];

                if (candies.size() == 3) {
                    for (int i = 0; i < candies.size(); i++) {
                        int xtemp = candies.get(i).x;
                        int ytemp = candies.get(i).y;

                        TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                    }
                    candies.clear();
                    System.out.println("Ponto");
                    return;
                }

                if (candies.size() == 0) {
                    candies.add(new Candy(xx, yy, cor));
                } else if (candies.size() > 0) {
                    if (candies.get(candies.size() - 1).CANDY_TYPE == cor) {
                        candies.add(new Candy(xx, yy, cor));
                    } else {
                        candies.clear();
                        candies.add(new Candy(xx, yy, cor));
                    }
                }
            }
        }

        // delimiter
        candies = new ArrayList<>();

        for (int xx = 0; xx < WIDTH; xx++) {
            if (candies.size() == 3) {
                for (int i = 0; i < candies.size(); i++) {
                    int xtemp = candies.get(i).x;
                    int ytemp = candies.get(i).y;

                    TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                }
                candies.clear();
                System.out.println("Ponto");
                return;
            }
            candies.clear();

            for (int yy = 0; yy < HEIGHT; yy++) {
                int cor = TABULEIRO[xx][yy];

                if (candies.size() == 3) {
                    for (int i = 0; i < candies.size(); i++) {
                        int xtemp = candies.get(i).x;
                        int ytemp = candies.get(i).y;

                        TABULEIRO[xtemp][ytemp] = new Random().nextInt(3);
                    }
                    candies.clear();
                    System.out.println("Ponto");
                    return;
                }

                if (candies.size() == 0) {
                    candies.add(new Candy(xx, yy, cor));
                } else if (candies.size() > 0) {
                    if (candies.get(candies.size() - 1).CANDY_TYPE == cor) {
                        candies.add(new Candy(xx, yy, cor));
                    } else {
                        candies.clear();
                        candies.add(new Candy(xx, yy, cor));
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                g.setColor(Color.WHITE);
                g.drawRect(x * GRID_SIZE + 24, y * GRID_SIZE + 24, GRID_SIZE, GRID_SIZE);

                int doce = TABULEIRO[x][y];

                if (doce == DOCE_0) {
                    g.drawImage(DOCE_0_SPRITE, x * GRID_SIZE + 12 + 20, y * GRID_SIZE + 12 + 20, 25, 25, null);
                } else if (doce == DOCE_1) {
                    g.drawImage(DOCE_1_SPRITE, x * GRID_SIZE + 12 + 20, y * GRID_SIZE + 12 + 20, 25, 25, null);
                } else if (doce == DOCE_2) {
                    g.drawImage(DOCE_2_SPRITE, x * GRID_SIZE + 12 + 20, y * GRID_SIZE + 12 + 20, 25, 25, null);
                }


                if (Game.selected) {
                    int posx = Game.previousx / GRID_SIZE;
                    int posy = Game.previousy / GRID_SIZE;

                    g.setColor(Color.BLACK);
                    g.drawRect(posx * GRID_SIZE + 24, posy * GRID_SIZE + 24, GRID_SIZE, GRID_SIZE);
                }
            }
        }
    }
}
