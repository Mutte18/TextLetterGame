import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    private Letter charMatrix[][] = new Letter[rows][cols];
    private String alphabet = "abcdefghijklmnopqrstuvwxzåäö";
    private String currentWord = "";
    private ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<Letter> currentlyMarkedWords = new ArrayList<>();
    private int gameScore = 0;

    public Game() {
        createFrame();
        addGamePanel();
        fillGameMatrix();
        handleKeyEvents();
        handleMouseEvents();
        loadWordList();
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

    private void loadWordList() {
        File file = new File("src/englishWordList.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
                /*Random r = new Random();
                char currentLetter = alphabet.charAt(r.nextInt(alphabet.length()));
                charMatrix[row][col] = new Letter(String.valueOf(currentLetter), (col * squareSize), (row * squareSize), squareSize,
                        col * squareSize + 20, (row * squareSize) + (squareSize / 2) + 20);
                        */
                generateRandomLetter(row, col);
            }
        }
        repaint();
    }

    private void generateRandomLetter(int row, int col) {
        Random r = new Random();
        char currentLetter = alphabet.charAt(r.nextInt(alphabet.length()));
        charMatrix[row][col] = new Letter(String.valueOf(currentLetter), (col * squareSize), (row * squareSize), squareSize,
                col * squareSize + 20, (row * squareSize) + (squareSize / 2) + 20);
    }

    private void removeMarkedLetters(boolean validWord) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Letter currentTarget = charMatrix[row][col];
                if (currentTarget.getIsMarked()) {
                    if (validWord) {
                        generateRandomLetter(row, col);
                    } else {
                        currentTarget.setMarked(false);
                    }
                }
            }
        }
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
                System.out.print("[" + charMatrix[row][col].getLetter() + "]");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------");

    }

    private void searchForChar(String searchChar, Letter charMatrix[][]) {

        label:
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Letter currentTarget = charMatrix[row][col];
                if (currentTarget.getLetter().contains(searchChar) && !currentTarget.getIsMarked()) {
                    currentTarget.setMarked(true);
                    System.out.println(searchChar + " ===== " + charMatrix[row][col].getLetter());
                    addToCurrentWord(currentTarget);
                    break label;
                }
            }
        }
    }

    private void setSquareColor(int x, int y) {
        if (x <= 9 && y <= 9) {
            charMatrix[y][x] = new Letter("b", x, y, squareSize, 0, 0);
            repaint();
        }
    }

    private void gameLoop() {
        while (isRunning) {
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.red);
        for (int i = 0; i < rows + 1; i++) {
            g.drawLine(0, squareSize * i, gameWidth, squareSize * i);

        }
        for (int i = 0; i < cols + 1; i++) {
            g.drawLine(squareSize * i, 0, squareSize * i, gameHeight);
        }

        g.drawLine(100, 550, 500, 550);
        g.drawString(currentWord, 100, 550);
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
                if (charMatrix[row][col] != null) {
                    g.setColor(Color.black);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                    charMatrix[row][col].paintComponent(g);
                    //g.drawString(charMatrix[row][col].getLetter(), col * squareSize + 20, (row * squareSize) + (squareSize / 2) + 20);

                    //System.out.println(col + " " + row);
                }
            }
        }


    }

    private void addToCurrentWord(Letter currentLetter) {
        currentWord += currentLetter.getLetter();
        currentlyMarkedWords.add(currentLetter);
    }

    private void removeLastAddedLetterFromWord() {
        if(!currentlyMarkedWords.isEmpty()) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
            currentlyMarkedWords.get(currentlyMarkedWords.size()-1).setMarked(false);
            currentlyMarkedWords.remove(currentlyMarkedWords.size()-1);
        }
    }

    private boolean checkIfValidWord(String suggestedWord) {
        if (wordList.contains(suggestedWord)) {
            System.out.println("SUCCESS!!!");
            gameScore += suggestedWord.length();
            wordList.remove(suggestedWord);


        }
        /*
        Here I will send in the word and check through my word list. If the word matches to a word it is valid.
        Points will be awarded, and true will be returned so new letters will show up

        If it is not valid then false will be returned which in turn will just remove the markers (green stuff)
        and not show new letters.
         */

        return true;
    }

    private Letter getLetter(int mouseX, int mouseY) {
        return charMatrix[mouseY][mouseX];
    }

    private void markCurrentLetter(Letter letter, Boolean value) {
        letter.setMarked(value);
    }

    private void deSelectLetter(){

    }

    private void selectLetter(Letter letter){
        letter.setMarked(true);
        currentlyMarkedWords.add(letter);
    }



    private void handleKeyEvents() {
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                searchForChar(String.valueOf(e.getKeyChar()), charMatrix);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        System.out.println(currentWord);
                        removeMarkedLetters(checkIfValidWord(currentWord));
                        currentWord = "";
                        break;
                    case KeyEvent.VK_SHIFT:
                        printGameMatrix();
                        break;
                    case KeyEvent.VK_CONTROL:
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

    private void handleMouseEvents() {
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
                Letter currentLetter;
                if (mouseX <= 9 && mouseY <= 9) {
                    currentLetter = getLetter(mouseX, mouseY);

                    switch (e.getButton()) {
                        case 1:
                            if (!currentLetter.getIsMarked()) {
                                addToCurrentWord(currentLetter);
                                markCurrentLetter(currentLetter, true);
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            removeLastAddedLetterFromWord();
                            break;
                    }

                }


                //setSquareColor(mouseX, mouseY);
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
