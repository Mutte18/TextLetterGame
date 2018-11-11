import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Game extends JPanel implements Runnable {

    private int windowWidth = 800;
    private int windowHeight = 650;
    private int gameWidth = 500;
    private int gameHeight = 500;
    private JFrame jFrame;
    private boolean isRunning = true;
    private int rows = 10;
    private int cols = 10;
    private int squareSize = 50;
    private int gameMatrix[][] = new int[rows][cols];
    private String charMatrix[][] = new String[rows][cols];

    public Game() {
        createFrame();
        addGamePanel();
        printGameMatrix();
        handleKeyEvents();
        handleMouseEvents();
        gameLoop();
    }

    private void createFrame() {
        jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(windowWidth, windowHeight);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
    }

    private void addGamePanel() {
        this.setSize(gameWidth, gameHeight);
        this.setVisible(true);
        jFrame.add(this);
    }

    private void fillGameMatrix() {
        /*for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                gameMatrix[row][col] = 1;
            }
        }*/
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                charMatrix[row][col] = "a";
            }
        }
        repaint();
    }


    private void printGameMatrix() {
        /*for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print("[" + gameMatrix[row][col] + "]");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
        */

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print("[" + charMatrix[row][col] + "]");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
        repaint();
    }

    private void setSquareColor(int x, int y){
        if(x <= 9 && y <= 9) {
            Letter letter = new Letter("b", x, y, squareSize);
            //gameMatrix[y][x] = 1;
            charMatrix[y][x] = letter.getLetter();
            repaint();
        }
    }

    private void gameLoop() {
        while (isRunning) {
            //repaint();
        }
    }

    public void paint(Graphics g) {
        //g.setColor(Color.red);
        for (int i = 0; i < rows + 1; i++) {
            g.drawLine(0, squareSize * i, gameWidth, squareSize * i);

        }
        for (int i = 0; i < cols + 1; i++) {
            g.drawLine(squareSize * i, 0, squareSize * i, gameHeight);
        }
        /*g.drawLine(gameWidth, 100, gameWidth, gameHeight);
        g.drawLine(100, gameHeight, gameWidth, gameHeight);

        g.setColor(Color.black);
        */
        /*for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(gameMatrix[row][col] == 1) {
                    g.setColor(Color.red);
                    g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
                    g.setColor(Color.black);
                    g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
                    //System.out.println(col + " " + row);
                }
            }
        }*/

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(charMatrix[row][col] != null) {
                    g.setColor(Color.black);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                    g.drawString(charMatrix[row][col], col * squareSize, (row * squareSize) + (squareSize / 2) + 20);

                    //System.out.println(col + " " + row);
                }
            }
        }
        repaint();

    }


    private void handleKeyEvents() {
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        printGameMatrix();
                        break;
                    case KeyEvent.VK_X:
                        fillGameMatrix();
                        repaint();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void handleMouseEvents()
    {
        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {




            }

            @Override
            public void mousePressed(MouseEvent e) {
                int xOffset = 7;
                int yOffset = 30;
                int mouseX = (e.getX() - xOffset) / squareSize;
                int mouseY = (e.getY() - yOffset) / squareSize;
                setSquareColor(mouseX, mouseY);
                System.out.println("Supposed X and Y Coords " + mouseX + " " + mouseY);
                System.out.println("Actual X and Y: " + e.getX() + " " + e.getY());
                System.out.println("X and Y with Offset " + (e.getX() - xOffset) + " " + (e.getY() - yOffset));
                System.out.println("----------------------------------------------");
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
        });
    }


    @Override
    public void run() {

    }
}
