/**
  Test client for various Symbol Table implementations
 */
public class FrequencyCounter {
    public static void main(String args[]) {
        //Cut off for the length of word
        int minLength = Integer.parseInt(args[0]);

        //Symbol Tables
        //Linked List implementation
        LLSymbolTable<String, Integer> st = new LLSymbolTable<String, Integer>();

        //Build Symbol Table
        //Read each string in. If it's long enough, try and add it to the Symbol Table.
        //If it doesn't already exists, add it, if it does increment the count for it
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (word.length() >= minLength) {
                if(!st.contains(word)) {
                    st.put(word, 1);
                } else {
                    st.put(word, st.get(word)+1);
                }
            }
        }

        String max = "";
        st.put(max,0);
        for(String word : st.keys()) {
            if (st.get(word) > st.get(max)) {
                max = word;
            }
        }
        StdOut.println("Max: " + max + " " + st.get(max));
    }
}

