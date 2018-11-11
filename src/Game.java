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
    private int rows = 6;
    private int cols = 6;
    private int squareSize = 50;
    private int gameMatrix[][] = new int[rows][cols];

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
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                gameMatrix[row][col] = 1;
            }
        }
    }


    private void printGameMatrix() {
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                System.out.print("[" + gameMatrix[row][col] + "]");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
    }

    private void setSquareColor(int x, int y){
        gameMatrix[y][x] = 1;
        repaint();
    }

    private void gameLoop() {
        while (isRunning) {
            //repaint();
        }
    }

    public void paint(Graphics g) {
        //g.setColor(Color.red);
        for (int i = 0; i < rows; i++) {
            g.drawLine(squareSize, squareSize + (squareSize * i), gameWidth + squareSize, squareSize + (squareSize * i));

        }
        for (int i = 0; i < cols; i++) {
            g.drawLine(squareSize + (squareSize * i), squareSize, squareSize + (squareSize * i), gameHeight + squareSize);
        }
        /*g.drawLine(gameWidth, 100, gameWidth, gameHeight);
        g.drawLine(100, gameHeight, gameWidth, gameHeight);

        g.setColor(Color.black);
        */
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if(gameMatrix[row][col] == 1) {
                    g.setColor(Color.red);
                    g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
                    g.setColor(Color.black);
                    g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
                    System.out.println(col + " " + row);
                }
            }
        }
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

                int mouseX = (e.getX() + squareSize) / squareSize;
                int mouseY = (e.getY() + squareSize) / squareSize;
                //setSquareColor(mouseX, mouseY);
                System.out.println(mouseX + " " + mouseY);
                System.out.println(e.getX() + " " + e.getY());


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
        });
    }


    @Override
    public void run() {

    }
}
