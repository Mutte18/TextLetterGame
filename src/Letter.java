public class Letter {
    private String letter;
    private int xPos;
    private int yPos;
    private int squareSize;
    public Letter(String letter, int xPos, int yPos, int squareSize){
        this.letter = letter;
        this.xPos = xPos;
        this.yPos = yPos;
        this.squareSize = squareSize;
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
}
