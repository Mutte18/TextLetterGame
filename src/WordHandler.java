import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class WordHandler {
    private String alphabet = "abcdefghijklmnopqrstuvwyxzåäö";
    private ArrayList<String> wordList = new ArrayList<>();


    public WordHandler(){
        loadWordList();
        shuffleWordList(wordList);

    }

    public String getFirstWord(){
        return wordList.get(0);
    }

    public char getRandomCharacterFromAlphabet(){
        Random r = new Random();
        return alphabet.charAt(r.nextInt(alphabet.length()));
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

    public void shuffleWordList(ArrayList<String> wordList)
    {
        Collections.shuffle(wordList);
    }

    public boolean checkIfValidWord(String suggestedWord) {
        if (wordList.contains(suggestedWord)) {
            System.out.println("SUCCESS!!!");
            //gameScore += suggestedWord.length();
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


}
