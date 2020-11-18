import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FilterClass {
  private static int largestWordLength = 0;
  private static Map<String, String[]> allBadWords = new HashMap<String, String[]>();

  public static String getCensoredText(final String input) {
    loadBadWords();
    if (input == null) {
      return "";
    }
    String modifiedInput = input;
    modifiedInput = modifiedInput.toLowerCase().replaceAll("[^a-zA-Z]", "");
    ArrayList<String> badWordsFound = new ArrayList<>();
    for (int start = 0; start < modifiedInput.length(); start++) {
      for (int offset = 1; offset < (modifiedInput.length() + 1 - start) && offset < largestWordLength; offset++) {
        String wordToCheck = modifiedInput.substring(start, start + offset);
        if (allBadWords.containsKey(wordToCheck)) {
          String[] ignoreCheck = allBadWords.get(wordToCheck);
          boolean ignore = false;
          for (int stringIndex = 0; stringIndex < ignoreCheck.length; stringIndex++) {
            if (modifiedInput.contains(ignoreCheck[stringIndex])) {
              ignore = true;
              break;
            }
          }
          if (!ignore) {
            badWordsFound.add(wordToCheck);
          }
        }
      }
    }
    String inputToReturn = input;
    for (String swearWord : badWordsFound) {
      char[] charsStars = new char[swearWord.length()];
      Arrays.fill(charsStars, '*');
      final String stars = new String(charsStars);
      inputToReturn = inputToReturn.replaceAll("(?i)" + swearWord, stars);
    }
    return inputToReturn;
  }

  private static void loadBadWords() {
    int CounterFlag = 0;
    try {
      FileReader fr = new FileReader("/home/hhimmmmii/Documents/Project/Database.csv");
      BufferedReader reader = new BufferedReader(fr);
      String CurrentLine = "";
      while ((CurrentLine = reader.readLine()) != null) {
        CounterFlag++;
        String[] content = null;
        try {
          if (1 == CounterFlag) {
            continue;
          }

          content = CurrentLine.split(",");
          if (content.length == 0) {
            continue;
          }

          final String Word = content[0];

          if (Word.startsWith("-----")) {
            continue;
          }

          if (Word.length() > largestWordLength) {
            largestWordLength = Word.length();
          }

          String[] IgnoreWordCombos = new String[] {};
          if (content.length > 1) {
            IgnoreWordCombos = content[1].split("_");
          }

          allBadWords.put(Word.replaceAll(" ", "").toLowerCase(), IgnoreWordCombos);
        } catch (Exception except) {
        }
      }
      fr.close();
      reader.close();
    } catch (IOException except) {
    } finally {

    }
  }

}
