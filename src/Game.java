import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

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
    private String currentWord = "";
    private ArrayList<Letter> currentlyMarkedWords = new ArrayList<>();
    private int gameScore = 0;
    private WordHandler wordHandler;

    public Game() {
        createFrame();
        addGamePanel();
        wordHandler = new WordHandler();
        fillGameMatrix();
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
        Letter letter = null;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                letter = charMatrix[row][col];

                if (letter != null && !letter.getMarked()) {
                    generateRandomLetter(row, col);
                } else if (letter == null) {
                    generateRandomLetter(row, col);
                }
            }
        }
        repaint();
    }

    private void generateRandomLetter(int row, int col) {
        char currentLetter = wordHandler.getRandomCharacterFromAlphabet();
        charMatrix[row][col] = new Letter(String.valueOf(currentLetter), (col * squareSize), (row * squareSize), squareSize,
                col * squareSize + 20, (row * squareSize) + (squareSize / 2) + 20);
    }

    private boolean checkIfWordCanBeCompleted(Letter[][] charMatrix, String currentWord) {
        HashMap<String, Integer> characterCount = countCharactersInWord(currentWord);
        Letter letter;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                letter = charMatrix[row][col];
                if (characterCount.containsKey(letter.getLetter())) {
                    int currentValue = characterCount.get(letter.getLetter());
                    int decrementedValue = currentValue -1;
                    if (decrementedValue <= 0) {
                        characterCount.remove(letter.getLetter());
                        letter.setCounted(true);
                        if(characterCount.size() == 0)
                            return false;
                    } else {
                        characterCount.put(letter.getLetter(), currentValue - 1);
                        letter.setCounted(true);
                    }
                }

            }
        }
        if (characterCount.size() == 0) {
            System.out.println("IT CAN BE DONE!!!");
            return false;
        }
        return true;

    }

    private HashMap<String, Integer> countCharactersInWord(String currentWord) {
        int[] count = new int[256];
        int len = currentWord.length();
        HashMap<String, Integer> charOccurance = new HashMap<>();
        for (int i = 0; i < len; i++) {
            count[currentWord.charAt(i)]++;
            charOccurance.put(String.valueOf(currentWord.charAt(i)), count[currentWord.charAt(i)]);
        }
        System.out.println("Hej");
        return charOccurance;
    }

    private void removeMarkedLetters(boolean validWord) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Letter currentTarget = charMatrix[row][col];
                if (currentTarget.getMarked()) {
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
                if (currentTarget.getLetter().contains(searchChar) && !currentTarget.getMarked()) {
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

    private void drawGameLines(Graphics g) {
        for (int i = 0; i < rows + 1; i++) {
            g.drawLine(0, squareSize * i, gameWidth, squareSize * i);
        }
        for (int i = 0; i < cols + 1; i++) {
            g.drawLine(squareSize * i, 0, squareSize * i, gameHeight);
        }


    }

    private void drawWordToFind(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Try to find this word: ", windowWidth - 250, 75);
        g.setColor(Color.GREEN);
        g.drawString(wordHandler.getFirstWord(), windowWidth - 150, 100);
    }

    private void drawCurrentlyWrittenWord(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(100, 550, 500, 550);
        g.drawString(currentWord, 100, 550);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameLines(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (charMatrix[row][col] != null) {
                    g.setColor(Color.black);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
                    charMatrix[row][col].paintComponent(g);
                }
            }
        }
        drawWordToFind(g);
        drawCurrentlyWrittenWord(g);

    }

    private void addToCurrentWord(Letter currentLetter) {
        currentWord += currentLetter.getLetter();
        currentlyMarkedWords.add(currentLetter);
    }

    private void removeLastAddedLetterFromWord() {
        if (!currentlyMarkedWords.isEmpty()) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
            currentlyMarkedWords.get(currentlyMarkedWords.size() - 1).setMarked(false);
            currentlyMarkedWords.remove(currentlyMarkedWords.size() - 1);
        }
    }


    private Letter getLetter(int mouseX, int mouseY) {
        return charMatrix[mouseY][mouseX];
    }

    private void markCurrentLetter(Letter letter, Boolean value) {
        letter.setMarked(value);
    }

    private void selectLetter(Letter letter) {
        letter.setMarked(true);
        currentlyMarkedWords.add(letter);
    }


    private void handleKeyEvents() {
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (currentWord.length() < wordHandler.getFirstWord().length()) {
                    searchForChar(String.valueOf(e.getKeyChar()), charMatrix);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        System.out.println(currentWord);
                        removeMarkedLetters(wordHandler.checkIfValidWord(currentWord));
                        currentlyMarkedWords.clear();

                        currentWord = "";
                        break;
                    case KeyEvent.VK_SHIFT:
                        printGameMatrix();
                        break;
                    case KeyEvent.VK_CONTROL:
                        checkIfWordCanBeCompleted(charMatrix, wordHandler.getFirstWord());
                        //fillGameMatrix();
                        repaint();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        removeLastAddedLetterFromWord();
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
                            if (!currentLetter.getMarked() && currentWord.length() < wordHandler.getFirstWord().length()) {
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
