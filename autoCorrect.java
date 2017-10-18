import java.util.ArrayList;
import java.util.HashMap;

class autoCorrect {

    /*
     Returns an object with each unique word in the input as a key,
     and the count of the number of occurances of that word as the value.
     (HINT: the code `text.toLowerCase().match(/[a-z]+/g)` will return an array
     of all lowercase words in the string.)
    */
    /*public static HashMap<Character, Integer> getWordsCounts(String text) {
        HashMap<Character, Integer> result = new HashMap<Character, Integer>();
        char[] formattedWord = text.toLowerCase().toCharArray();

        for(int i=0; i<formattedWord.length; i++) {
            if (!result.containsKey(formattedWord[i])) {
                result.put(formattedWord[i], 1);
            } else {
                int count = result.get(formattedWord[i]);
                result.replace(formattedWord[i], count, ++count);
            }
        }

        return result;
    }*/

    /*
     Returns the set of all strings 1 edit distance away from the input word.
     This consists of all strings that can be created by:
        - Adding any one character (from the alphabet) anywhere in the word.
        - Removing any one character from the word.
        - Transposing (switching) the order of any two adjacent characters in a word.
        - Substituting any character in the word with another character.
    */
    public static ArrayList<String> editDistance1(String word) {
        ArrayList<String> results = new ArrayList<String>();
        String formattedWord = word.toLowerCase();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        //Adding any one character (from teh alphabet) anywhere in the word.
        for(int i=0; i <= formattedWord.length(); i++) {
            for(int j=0; j < alphabet.length(); j++) {
                String newWord = formattedWord.substring(0,i) + alphabet.charAt(j) + formattedWord.substring(i, formattedWord.length());
                results.add(newWord);
            }
        }

        //Removing any one character from the words
        if(word.length() > 1) {
            for(int i=0; i<formattedWord.length(); i++) {
                String newWord = formattedWord.substring(0, i) + formattedWord.substring(i+1, formattedWord.length());
                results.add(newWord);
            }
        }

        //Transposing (switching) the order of anyt wo adjacent characters in a word.
        if(word.length() > 1) {
            for (int i=0; i<word.length() - 1; i++) {
                String newWord = formattedWord.substring(0, i) + formattedWord.charAt(i+1)  +formattedWord.charAt(i) + formattedWord.substring(i+2, formattedWord.length());
                results.add(newWord);
            }
        }

        //Substituting any character in the word with another character.
        for (int i=0; i<word.length(); i++) {
            for(int j=0; j<alphabet.length(); j++) {
                String newWord = formattedWord.substring(0,i) + alphabet.charAt(j) + formattedWord.substring(i+1, formattedWord.length());
                results.add(newWord);
            }
        }
        return results;
    }

    /* Given a word, attempts to correct the spelling of that word.
    - First, if the word is a known word, return the word.
    - Second, if the word has any known words edit-distance 1 away, return the one with
      the highest frequency, as recorded in NWORDS.
    - Third, if the word has any known words edit-distance 2 away, return the one with
      the highest frequency, as recorded in NWORDS. (HINT: what does applying
      "editDistance1" *again* to each word of its own output do?)
    - Finally, if no good replacements are found, return the word.
    */
    public static String correctWord (String toBeChecked) {
        
        toBeChecked = toBeChecked.toLowerCase();
        ArrayList<String> commandPool = getCommandPool();
        final String defaultresult = "No Such Command";
        String result = "";
        
        if (commandPool.contains(toBeChecked)) {
                return toBeChecked;
        }
        
        for (String command : commandPool) {
            if(command.charAt(0) != toBeChecked.charAt(0)) continue;
            result = checkMisspeltWords(command, toBeChecked);
            if (!result.equals(defaultresult)) {
                return result;
            }
        }
        
        return result;
    }

    public static String checkMisspeltWords (String command, String input) {
        //System.out.println(command);
        //System.out.println(input);
        ArrayList<String> editDistance1Words_search = editDistance1(command);
        ArrayList<ArrayList<String>> editDistance2Words_search = new ArrayList<ArrayList<String>>();
        for (String editDistance1Word_search : editDistance1Words_search) {
            editDistance2Words_search.add(editDistance1(editDistance1Word_search));
        }

        if(editDistance1Words_search.contains(input)) {
            return command;
        }
        
        for(ArrayList<String> editDistance2Word_search : editDistance2Words_search) {
            if(editDistance2Word_search.contains(input)) return command;
        }

        return "No Such Command";
    }

    public static ArrayList<String> getCommandPool () {
        
            ArrayList<String> commandPool = new ArrayList<String>();
            commandPool.add("find");
            commandPool.add("add");
            commandPool.add("search");
            commandPool.add("delete");
            commandPool.add("deleteTag");
            commandPool.add("clear");
            commandPool.add("edit");
            commandPool.add("exit");
            commandPool.add("help");
            commandPool.add("history");
            commandPool.add("list");
            commandPool.add("multifilter");
            commandPool.add("redo");
            commandPool.add("select");
            commandPool.add("undo");
        
            return commandPool;
    }

    public static void main (String[] args) {
        /*//Test 1
        HashMap<Character, Integer> WORDS_COUNT = getWordsCounts("someRandomTestText");

        WORDS_COUNT.entrySet().parallelStream()
        .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
         
        //Test 2
        ArrayList<String> WORDS_POOL = editDistance1("someRandomTestText");
        WORDS_POOL.stream().forEach(e -> System.out.println(e));*/

        System.out.println(correctWord("sreach"));

    }
}
