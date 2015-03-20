/**
  Test client for various Symbol Table implementations
 */
public class FrequencyCounter {
    public static void main(String args[]) {
        int minLength = Integer.parseInt(args[0]); //Cut off for the length of word
        LLSymbolTable<String, Integer> st = new LLSymbolTable<String, Integer>();

        while (!StdIn.isEmpty()) { //Build Symbol Table
            String word = StdIn.readString();
            if (word.length() >= minLength) { //If the word meets the minimum length
                if(!st.contains(word)) { //If the word hasn't already been added
                    st.put(word, 1);
                } else {
                    st.put(word, st.get(word)+1); //Increment the count for that word
                }
            }
        }

        String max = "";
        st.put(max,0);
        for(String word : st.keys()) {
            if (st.get(word) ? st.get(max)) {
                max = word;
            }
        }
        StdOut.println(max + " " + st.get(max));
        }
    }
}
