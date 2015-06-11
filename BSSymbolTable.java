import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 Symbol Table implementation using ordered parallel arrays and binary search
 Based on Algorithms, 4th Ed by Robert Sedgewick | Kevin Wayne
 */
public class BSSymbolTable<Key extends Comparable<Key>, Value> {
    private static int init_capacity = 2;

    private Key[] keys;
    private Value[] vals;
    private int size = 0;
    private int compares = 0;
    private int words = 0;


    public BSSymbolTable(int size) {
        keys = (Key[])   new Comparable[size];
        vals = (Value[]) new Object[size];
    }

    public BSSymbolTable() {
        this(init_capacity);
    }

    //resizing the array
    private void resize(int capacity) {
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    //Return the size of the Symbol Table
    public int size() {
        return size;
    }

    //Is the Symbol Table empty?
    boolean isEmpty() {
        return size <= 0;
    }

    //Returns the index of the key.
    //If the key is not in the array, it returns the number of keys that are smaller than it.
    //Uses Binary Search to find the proper place.
    public int rank (Key key) {
        return rank(key, 0, size-1);
    }

    public int rank (Key key, int lo, int hi) {
        compares++;
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

    //Returns the value associated with the passed in key
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }

        //Find the index of the key, if it is in the array
        int i = rank(key);

        if (i < size && keys[i].compareTo(key) == 0) { //If the key exists, returns it's associated value
            return vals[i];
        } else {
            return null;
        }
    }

    //Place a new key-value pair into Symbol Table
    public void put(Key newKey, Value newVal) {
        words++;

        //The position where the key should be
        int i = rank(newKey);

        //Key exists in array, update associated value
        if (i < size && keys[i].compareTo(newKey) == 0) {
            vals[i] = newVal;
            return;
        }

        //If you've reached current capacity, double the size of the array
        if (size == keys.length) {
            resize(2*keys.length);
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

    //Return true of toFind is in the array
    public boolean contains(Key toFind) {
        return get(toFind) != null;
    }

    //Return the key in rank toFind
    public Key select (int toFind) {
        if (toFind < 0 || toFind > size) {
            return null;
        } else {
            return keys[toFind];
        }
    }

    //Return the minimum key
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol Table is empty");
        }
        return keys[0];
    }

    //return the maximum key
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol Table is empty");
        }
        return keys[size-1];
    }

    //Return largest key that is <= to toFind
    public Key floor(Key toFind) {
        //Find the position of the object
        int i = rank(toFind);

        if (i < size && toFind.compareTo(keys[i]) == 0) { //if toKeys is in array, return it
            return keys[i];
        } else if (i == 0) { //if toKeys is smaller than everything in the array, it has no floor
            return null;
        } else {  //Return the largest key that is < toFind
            return keys[i-1];
        }
    }

    //Return smallest key that is >= to toFind
    public Key ceiling(Key toFind) {
        //Find the position of the object
        int i = rank(toFind);

        if (i == size) { //if toKeys is larger than everything in the array, it has no ceiling
            return null;
        } else {  //Return the key in position i, which will either be toFind or it's ceiling
            return keys[i];
        }
    }

    public void delete(Key toDelete) {
        if (isEmpty()) {
            return;
        }

        //Find the position of the object to delete
        int i = rank(toDelete);

        //If key exists, remove it by shifting all other keys over
        if (i < size && toDelete.compareTo(keys[i]) == 0) {
            for (int j = i; j < size-1; j++) {
                keys[j] = keys[j+1];
                vals[j] = vals[j+1];
            }
            size--;
            keys[size] = null;
            vals[size] = null;
        }

        //If array is 1/4th full, resize it
        if (size > 0 && size == keys.length/4) {
            resize(keys.length/2);
        }
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol Table is empty");
        }
        delete(keys[0]);
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol Table is empty");
        }
        delete(keys[size-1]);
    }

    public Iterable<Key> keys() {
        ArrayList<Key> forIterating = new ArrayList();
        for (int i = 0; i < size; i++) {
            forIterating.add(keys[i]);
        }
        return forIterating;
    }

    public int compares() {
       return compares;
    }

    public int words() {
        return words;
    }

    //Test Client
    public static void main(String[] args) {
        BSSymbolTable<Integer, String> testBSST = new BSSymbolTable<Integer, String>();

        try {
            testBSST.deleteMin();
            testBSST.deleteMax();
        } catch (NoSuchElementException e) {
            StdOut.println(e.getMessage());
        }


        testBSST.put(3, "Three");
        testBSST.put(1, "One");
        testBSST.put(2, "Two");
        testBSST.put(4, "Four");
        testBSST.put(1, "OneOne");

        for (Integer myInt : testBSST.keys()) {
            StdOut.println(myInt + " " + testBSST.get(myInt));
        }
        StdOut.println("Size: " + testBSST.size);
        StdOut.println();

        testBSST.delete(3);

        for (Integer myInt : testBSST.keys()) {
            StdOut.println(myInt + " " + testBSST.get(myInt));
        }
        StdOut.println("Size: " + testBSST.size);
        StdOut.println();

        testBSST.deleteMin();
        testBSST.deleteMax();

        for (Integer myInt : testBSST.keys()) {
            StdOut.println(myInt + " " + testBSST.get(myInt));
        }
        StdOut.println("Size: " + testBSST.size);
        StdOut.println();

        testBSST.put(3, "Three");
        testBSST.put(1, "One");
        testBSST.put(5, "Five");

        for (Integer myInt : testBSST.keys()) {
            StdOut.println(myInt + " " + testBSST.get(myInt));
        }
        StdOut.println("Size: " + testBSST.size);
        StdOut.println("Min: " + testBSST.min());
        StdOut.println("Max: " + testBSST.max());
        StdOut.println("Floor of 4: " + testBSST.floor(4));
        StdOut.println("Ceiling of 4: " + testBSST.ceiling(4));
        StdOut.println("Floor of 3: " + testBSST.floor(3));
        StdOut.println("Ceiling of 3: " + testBSST.ceiling(3));
        StdOut.println("Floor of 0: " + testBSST.floor(0));
        StdOut.println("Ceiling of 6: " + testBSST.ceiling(6));
        StdOut.println("Array contains 5: " + testBSST.contains(5));
        StdOut.println("Array contains 6: " + testBSST.contains(6));
        StdOut.println("Key at rank 3: " + testBSST.select(3));


    }

}
