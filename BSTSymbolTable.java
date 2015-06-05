import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 Symbol Table implemented with a Binary Search Tree
 */
public class BSTSymbolTable<Key extends Comparable<Key>, Value> {

    private Node root;
    private int compares = 0;
    private int words = 0;
    ArrayList<Key> forIterating = new ArrayList();

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            forIterating.add(key);
        }
    }

    public Value get(Key searchKey) {
        return get(root, searchKey);
    }

    public Value get(Node current, Key searchKey) {
        compares++;

        if (current == null) {
            return null;
        }

        int cmp = searchKey.compareTo(current.key);

        //Use compare value to traverse through tree
        if (cmp < 0) {
            return get(current.left, searchKey);
        } else if (cmp > 0) {
            return get(current.right, searchKey);
        } else {
            return current.val;
        }
    }

    //Put works by recursively either creating a new node if needed, or assigning each node to itself
    public void put(Key key, Value newVal) {
        words++;
        root = put(root, key, newVal);
    }

    public Node put(Node current, Key searchKey, Value newVal) {
        compares++;

        //Search to see if searchKey exists in BST, if not add it
        if (current == null) {
            return new Node(searchKey, newVal, 1);
        }

        int cmp = searchKey.compareTo(current.key);

        //Use compare value to traverse through tree
        if (cmp < 0) {
            current.left = put(current.left, searchKey, newVal);
        } else if (cmp > 0) {
            current.right = put(current.right, searchKey, newVal);
        } else {
            current.val = newVal;
        }
        current.size = size(current.left) + size(current.right) +1;

        return current;

    }

    public Value min() {
        return min(root);

    }

    public Value min(Node current) {
        if (current.left == null) {
            return current.val;
        } else {
            return min(current.left);
        }
    }

    public boolean contains(Key searchKey) {
        return get(searchKey) != null;
    }

    public Iterable<Key> keys() {
        return forIterating;
    }

    public int size() {
        return size(root);
    }

    public int size(Node x) {
        if (x == null) {
            return 0;
        }

        else return x.size;
    }

    public int compares() {
        return compares;
    }

    public int words() {
        return words;
    }

    //Test client for BST Symbol Table
    public static void main(String[] args) {
        BSTSymbolTable<Integer, String> testBSTST = new BSTSymbolTable<Integer, String>();

//        try {
//            testBSST.deleteMin();
//            testBSST.deleteMax();
//        } catch (NoSuchElementException e) {
//            StdOut.println(e.getMessage());
//        }


        testBSTST.put(3, "Three");
        testBSTST.put(1, "One");
        testBSTST.put(2, "Two");
        testBSTST.put(4, "Four");
        testBSTST.put(1, "OneOne");

        for (Integer myInt : testBSTST.keys()) {
            StdOut.println(myInt + " " + testBSTST.get(myInt));
        }
        StdOut.println("Size: " + testBSTST.size());
        StdOut.println();

        //testBSTST.delete(3);

        for (Integer myInt : testBSTST.keys()) {
            StdOut.println(myInt + " " + testBSTST.get(myInt));
        }
        StdOut.println("Size: " + testBSTST.size());
        StdOut.println();

//        testBSTST.deleteMin();
//        testBSTST.deleteMax();

        for (Integer myInt : testBSTST.keys()) {
            StdOut.println(myInt + " " + testBSTST.get(myInt));
        }
        StdOut.println("Size: " + testBSTST.size());
        StdOut.println();

        testBSTST.put(3, "Three");
        testBSTST.put(1, "One");
        testBSTST.put(5, "Five");

        for (Integer myInt : testBSTST.keys()) {
            StdOut.println(myInt + " " + testBSTST.get(myInt));
        }
        StdOut.println("Size: " + testBSTST.size());
        StdOut.println("Min: " + testBSTST.min());
//        StdOut.println("Max: " + testBSTST.max());
//        StdOut.println("Floor of 4: " + testBSTST.floor(4));
//        StdOut.println("Ceiling of 4: " + testBSTST.ceiling(4));
//        StdOut.println("Floor of 3: " + testBSTST.floor(3));
//        StdOut.println("Ceiling of 3: " + testBSTST.ceiling(3));
//        StdOut.println("Floor of 0: " + testBSTST.floor(0));
//        StdOut.println("Ceiling of 6: " + testBSTST.ceiling(6));
//        StdOut.println("Array contains 5: " + testBSTST.contains(5));
//        StdOut.println("Array contains 6: " + testBSTST.contains(6));
//        StdOut.println("Key at rank 3: " + testBSTST.select(3));


    }
}
