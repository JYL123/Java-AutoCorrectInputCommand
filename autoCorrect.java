import java.util.ArrayList;
import java.util.HashMap;

class autoCorrect {

    /*
     Returns an object with each unique word in the input as a key,
     and the count of the number of occurances of that word as the value.
     (HINT: the code `text.toLowerCase().match(/[a-z]+/g)` will return an array
     of all lowercase words in the string.)
     
     Optional method, you can consider utilizing this method but it is not included in this project.
    */
    public static HashMap<Character, Integer> getWordsCounts(String text) {
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
    }

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
    public static String correctWord (String word) {

        word = word.toLowerCase();
        ArrayList<String> COMMANDS_POOL = new ArrayList<String>();
        COMMANDS_POOL.add("find");
        COMMANDS_POOL.add("add");
        COMMANDS_POOL.add("search");
        COMMANDS_POOL.add("delete");
        ArrayList<String> ADD_WORDS = editDistance1("someRandomTestText");

        if( COMMANDS_POOL.contains(word)) {
           return word;
        }

        ArrayList<String> editDistance1Words_add = editDistance1("add");
        ArrayList<ArrayList<String>> editDistance2Words_add = new ArrayList<ArrayList<String>>();
        for (String editDistance1Word_add : editDistance1Words_add) {
            editDistance2Words_add.add(editDistance1(editDistance1Word_add));
        }

        if(editDistance1Words_add.contains(word)) {
            return "add";
        }

        for(ArrayList<String> editDistance2Word_add : editDistance2Words_add) {
            if(editDistance2Word_add.contains(word)) return "add";
        }

        ArrayList<String> editDistance1Words_delete = editDistance1("delete");
        ArrayList<ArrayList<String>> editDistance2Words_delete = new ArrayList<ArrayList<String>>();
        for (String editDistance1Word_delete : editDistance1Words_delete) {
            editDistance2Words_delete.add(editDistance1(editDistance1Word_delete));
        }

        if(editDistance1Words_delete.contains(word)) {
            return "delete";
        }

        for(ArrayList<String> editDistance2Word_delete : editDistance2Words_delete) {
            if(editDistance2Word_delete.contains(word)) return "delete";
        }

        ArrayList<String> editDistance1Words_search = editDistance1("search");
        ArrayList<ArrayList<String>> editDistance2Words_search = new ArrayList<ArrayList<String>>();
        for (String editDistance1Word_search : editDistance1Words_search) {
            editDistance2Words_search.add(editDistance1(editDistance1Word_search));
        }

        if(editDistance1Words_search.contains(word)) {
            return "search";
        }
        
        for(ArrayList<String> editDistance2Word_search : editDistance2Words_search) {
            if(editDistance2Word_search.contains(word)) return "search";
        }
        
        
        return "no such command";
    }

    public static void main (String[] args) {
        /*//Test 1
        HashMap<Character, Integer> WORDS_COUNT = getWordsCounts("someRandomTestText");

        WORDS_COUNT.entrySet().parallelStream()
        .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
         
        //Test 2
        ArrayList<String> WORDS_POOL = editDistance1("someRandomTestText");
        WORDS_POOL.stream().forEach(e -> System.out.println(e));*/

        System.out.println(correctWord("sercah"));

    }
}
