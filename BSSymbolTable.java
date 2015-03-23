/**
 Symbol Table implementation using ordered parallel arrays and binary search
 */
public class BSSymbolTable<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int size = 0;
    private int capacity = 1000;

    public BSSymbolTable(int size) {
        keys = (Key[]) new Comparable[size];
        vals = (Value[]) new Object[size];
    }

    public int size() {
        return size;
    }

    //Returns the value associated with the passed in key
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }

        //Find the index of the key, if it is in the array
        int i = rank(key);

        if (i < size && keys[i].compareTo(key) == 0) {
            return vals[i];
        } else {
            return null;
        }
    }

    boolean isEmpty() {
        return size > 0;
    }

    //Returns the index of the key.
    //If the key is not in the array, it returns the number of keys that are smaller than it.
    //USes Binary Search to find the proper place.
    public int rank (Key key) {
        return rank(key, 0, keys.length-1);
    }

    public int rank (Key key, int lo, int hi) {
        if (hi < lo) {
            return lo;
        }

        int mid = lo + (hi - lo) /2;

        int cmp = key.compareTo(keys[mid]);

        if (cmp < 0) { //If key is in left half of array
            return rank(key, lo, mid-1);
        } else if (cmp > 0) { //If key is in right half of array
            return (rank(key, mid+1, hi));
        } else {  //key is at mid
            return mid;
        }

    }

    public void put(Key newKey, Value newVal) {
        //The position of the key if it already exists or the number of keys smaller than it
        int i = rank(newKey);

        //Key exists in array, update associated value
        if (i < size && keys[i].compareTo(newKey) == 0) {
            vals[i] = newVal;
            return;
        }

        //Key doesn't exist, shift all items over by 1 and place key into array
        for (int j = size; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = newKey;
        vals[i] = newVal;
        size++;
    }

    //public boolean contains(Key key) {}

    //public Key min()

    //public Key max()

    //public Key floor(Key key)

    //public Key ceiling(Key key)

    //public Key select(int toSelect)

    //public void deleteMin()

    //public void deleteMax()

    //public Iterable<Key> keys(Key lo, Key hi)

    //public Iterable<Key> keys()

}
