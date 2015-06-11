/**
 Test client for various Symbol Table implementations
 Based on Algorithms, 4th Ed by Robert Sedgewick | Kevin Wayne
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

        //Binary Search Tree implementation
        BSTSymbolTable<String, Integer> bstst = new BSTSymbolTable<String, Integer>();

        //Binary Search Tree implementation
        RBTSymbolTable<String, Integer> rbtst = new RBTSymbolTable<String, Integer>();


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
                    bsst.put(word, bsst.get(word)+1);
                }

                if(!bstst.contains(word)) {
                    bstst.put(word, 1);
                } else {
                    bstst.put(word, bstst.get(word)+1);
                }

                if(!rbtst.contains(word)) {
                    rbtst.put(word, 1);
                } else {
                    rbtst.put(word, rbtst.get(word)+1);
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

        StdOut.println();
        StdOut.println("Binary Search Tree Implementation");
        StdOut.println("Compares: " + bstst.compares());
        StdOut.println("Total Words: " + bstst.words());
        StdOut.println("Distinct Words: " + bstst.size());

        max = "";
        bstst.put(max,0);
        for(String word : bstst.keys()) {
            if (bstst.get(word) > bstst.get(max)) {
                max = word;
            }
        }
        StdOut.println("Most Frequent Word: " + max + " " + bstst.get(max));

        StdOut.println();
        StdOut.println("Red-Black Tree Implementation");
        StdOut.println("Compares: " + rbtst.compares());
        StdOut.println("Total Words: " + rbtst.words());
        StdOut.println("Distinct Words: " + rbtst.size());

        max = "";
        rbtst.put(max,0);
        for(String word : rbtst.keys()) {
            if (rbtst.get(word) > rbtst.get(max)) {
                max = word;
            }
        }
        StdOut.println("Most Frequent Word: " + max + " " + rbtst.get(max));
    }
}

