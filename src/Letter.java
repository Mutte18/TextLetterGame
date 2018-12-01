import java.awt.*;

public class Letter {
    private String letter;
    private int xPos;
    private int yPos;
    private int letterXPos;
    private int letterYPos;
    private int squareSize;
    private boolean isMarked = false;
    private boolean isCounted = false;

    public Letter(String letter, int xPos, int yPos, int squareSize, int letterXPos, int letterYPos) {
        this.letter = letter;
        this.xPos = xPos;
        this.yPos = yPos;
        this.squareSize = squareSize;
        this.letterXPos = letterXPos;
        this.letterYPos = letterYPos;
    }

    public String getLetter() {
        return letter;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public boolean getMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public void paintComponent(Graphics g) {
        if (isMarked) {
            g.setColor(Color.green);
            g.fillRect(xPos, yPos, squareSize, squareSize);
            g.setColor(Color.black);
            g.drawRect(xPos, yPos, squareSize, squareSize);
        }
        else if(isCounted)
        {
            g.setColor(Color.red);
            g.fillRect(xPos, yPos, squareSize, squareSize);
            g.setColor(Color.black);
            g.drawRect(xPos, yPos, squareSize, squareSize);
        }
        g.drawString(letter, letterXPos, letterYPos);

    }

    public boolean isCounted() {
        return isCounted;
    }

    public void setCounted(boolean counted) {
        isCounted = counted;
    }
}
