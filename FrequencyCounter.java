/**
  Test client for various Symbol Table implementations
 */
public class FrequencyCounter {
    public static void main(String args[]) {
        //Cut off for the length of word
        int minLength = Integer.parseInt(args[0]);

        //Symbol Tables
        //Linked List implementation
        LLSymbolTable<String, Integer> llst = new LLSymbolTable<String, Integer>();

        //Ordered Parallel Arrays implementation
        BSSymbolTable<String, Integer> bsst = new BSSymbolTable<String, Integer>();


        //Build Symbol Tables
        //Read each string in. If it's long enough, try and add it to the Symbol Table.
        //If it doesn't already exists, add it, if it does increment the count for it
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (word.length() >= minLength) {

                if(!llst.contains(word)) {
                    llst.put(word, 1);
                } else {
                    llst.put(word, llst.get(word)+1);
                }

                if(!bsst.contains(word)) {
                    bsst.put(word, 1);
                } else {
                    bsst.put(word, llst.get(word)+1);
                }
            }
        }

        StdOut.println("Linked List Implementation");
        StdOut.println("Compares: " + llst.compares());
        StdOut.println("Total Words: " + llst.words());
        StdOut.println("Distinct Words: " + llst.size());

        String max = "";
        llst.put(max,0);
        for(String word : llst.keys()) {
            if (llst.get(word) > llst.get(max)) {
                max = word;
            }
        }
        StdOut.println("Most Frequent Word: " + max + " " + llst.get(max));

        StdOut.println();
        StdOut.println("Ordered Array Implementation");
        StdOut.println("Compares: " + bsst.compares());
        StdOut.println("Total Words: " + bsst.words());
        StdOut.println("Distinct Words: " + bsst.size());

        max = "";
        bsst.put(max,0);
        for(String word : bsst.keys()) {
            if (bsst.get(word) > bsst.get(max)) {
                max = word;
            }
        }
        StdOut.println("Most Frequent Word: " + max + " " + bsst.get(max));
    }
}

