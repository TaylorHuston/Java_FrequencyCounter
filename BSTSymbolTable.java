import java.util.ArrayList;
import java.util.Iterator;

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
}
