import java.util.ArrayList;

/**
 Symbol Table implemented as a hash using an array of linked lists
 Based on Algorithms, 4th Ed by Robert Sedgewick | Kevin Wayne
 */
public class SeparateChainingHashST<Key, Value> {
    private int tableSize;

    private LLSymbolTable<Key, Value>[] hashST;

    ArrayList<Key> forIterating = new ArrayList();

    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int size) {
        this.tableSize = size;

        hashST = (LLSymbolTable<Key, Value>[]) new LLSymbolTable[size];

        for (int i=0; i < size; i++) {
            hashST[i] = new LLSymbolTable();
        }
    }

    //Return an integer to be used as an array index based on the hashcode of the key
    private int hash(Key toHash) {
        return ((toHash.hashCode() & 0x7fffffff) % tableSize);
    }

    public void put(Key newKey, Value newVal) {
        hashST[hash(newKey)].put(newKey, newVal);
    }

    public Value get(Key searchKey) {
        return (Value) hashST[hash(searchKey)].get(searchKey);
    }

    public Iterable<Key> keys() {
        forIterating = new ArrayList();

        for (int i=0; i < tableSize; i++) {
            for (Key key : hashST[i].keys()) {
                forIterating.add(key);
            }

        }
        return forIterating;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<Integer, String> testHash = new SeparateChainingHashST<Integer, String>(17);

        testHash.put(3, "Three");
        testHash.put(1, "One");
        testHash.put(2, "Two");
        testHash.put(4, "Four");
        testHash.put(1, "OneOne");
        testHash.put(10, "Ten");

        for (Integer myInt : testHash.keys()) {
            StdOut.println(myInt + " " + testHash.get(myInt));
        }
    }
}
