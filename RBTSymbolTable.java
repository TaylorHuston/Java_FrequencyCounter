import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 Symbol Table implemented with a Left Leaning Red Black Tree
 Based on Algorithms, 4th Ed by Robert Sedgewick | Kevin Wayne
 */
public class RBTSymbolTable<Key extends Comparable<Key>, Value> {

    private Node root;
    private int compares = 0;
    private int words = 0;
    ArrayList<Key> forIterating = new ArrayList();

    //For Red-Black implementation
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;
        boolean color; //Color of the link from the parent to this node

        public Node(Key key, Value val, int size, boolean color) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = color;
        }
    }

    /**********
     * Red-Black Helper Functions
     **********/

    //Is this node Red?
    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }

        return x.color == RED;
    }

    //Make right-leaning link lean left
    private Node rotateLeft(Node toRotate) {
        Node temp = toRotate.right;
        toRotate.right = temp.left;
        temp.left = toRotate;
        temp.color = toRotate.color;
        toRotate.color = RED;
        temp.size = toRotate.size;
        toRotate.size = 1 + size(toRotate.left) + size(toRotate.right);
        return temp;
    }

    //Make left-leaning link lean right
    private Node rotateRight(Node toRotate) {
        Node temp = toRotate.left;
        toRotate.left = temp.right;
        temp.right = toRotate;
        temp.color = toRotate.color;
        toRotate.color = RED;
        temp.size = toRotate.size;
        toRotate.size = 1 + size(toRotate.left) + size(toRotate.right);
        return temp;
    }

    //Change a node with two red links to a node with two black links
    private void flipColors(Node toFlip) {
        toFlip.color = !toFlip.color;
        toFlip.left.color = !toFlip.left.color;
        toFlip.right.color = !toFlip.right.color;
    }

    //Assume that current is red and both current.left and current.left.left are black,
    //make current.left or one of it's children red
    private Node moveRedLeft(Node current) {
        flipColors(current);

        if (isRed(current.right.left)) {
            current.right = rotateRight(current.right);
            current = rotateLeft(current);
            flipColors(current);
        }

        return current;
    }

    //Assume that current is red and both current.left and current.right.left are black,
    //make current.right or one of it's children red
    private Node moveRedRight(Node current) {
        flipColors(current);

        if (isRed(current.left.left)) {
            current = rotateRight(current);
        }

        return current;
    }

    //Ensure red-black balance
    private Node balance(Node current) {
        if (isRed(current.right)) {
            current = rotateLeft(current);
        }

        if (isRed(current.left) && isRed(current.left.left)) {
            current = rotateRight(current);
        }

        if (isRed(current.left) && isRed(current.right)) {
            flipColors(current);
        }

        current.size = size(current.left) + size(current.right) +1;

        return current;
    }


    /**********
     * Search/Retrieval Functions
     **********/
    //Return the node with the matching searchKey
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

    //Recursively traverse as far left as you can
    public Node min() {
        return min(root);
    }

    public Node min(Node current) {
        if (current.left == null) {
            return current;
        } else {
            return min(current.left);
        }
    }

    //Recursively travel as far right as you can
    public Node max() {
        return max(root);
    }

    public Node max(Node current) {
        if (current.right == null) {
            return current;
        } else {
            return max(current.right);
        }
    }

    //Recursively traverse until you find the largest key that is <= the search key
    public Key floor(Key searchKey) {
        Node floor = floor(root, searchKey);
        if (floor == null) {
            return null;
        } else {
            return floor.key;
        }
    }

    public Node floor(Node current, Key searchKey) {
        if (current == null) {
            return null;
        }

        int cmp = searchKey.compareTo(current.key);

        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return floor(current.left, searchKey);
        }

        Node floor = floor(current.right, searchKey);
        if (floor != null) {
            return floor;
        } else {
            return current;
        }
    }

    //Recursively traverse until you find the smallest key that is >= the search key
    public Key ceiling(Key searchKey) {
        Node ceiling = ceiling(root, searchKey);
        if (ceiling == null) {
            return null;
        } else {
            return ceiling.key;
        }
    }

    public Node ceiling(Node current, Key searchKey) {
        if (current == null) {
            return null;
        }

        int cmp = searchKey.compareTo(current.key);

        if (cmp == 0) {
            return current;
        } else if (cmp > 0) {
            return ceiling(current.right, searchKey);
        }

        Node ceiling = ceiling(current.left, searchKey);
        if (ceiling != null) {
            return ceiling;
        } else {
            return current;
        }
    }

    //Find the key with rank N (meaning the key where exactly N other keys are smaller)
    public Key select(int selectN) {
        return select(root, selectN).key;
    }

    public Node select(Node current, int selectN) {
        if (current == null) {
            return null;
        }

        int t = size(current.left);

        if (t > selectN) {
            return select(current.left, selectN);
        } else if ( t < selectN) {
            return select(current.right, selectN-t-1);
        } else  {
            return current;
        }
    }

    //How many keys are < searchKey?
    public int rank (Key searchKey) {
        return rank(searchKey, root);
    }

    public int rank (Key searchKey, Node current) {
        if (current == null) {
            return 0;
        }

        int cmp = searchKey.compareTo(current.key);

        if (cmp < 0) {
            return rank(searchKey, current.left);
        } else if (cmp > 0) {
            return 1 + size(current.left) + rank(searchKey, current.right);
        } else {
            return size(current.left);
        }
    }


    /**********
     * Insertion Functions
     **********/
    //Put works by recursively either creating a new node if needed, or assigning each node to itself
    public void put(Key key, Value newVal) {
        words++;
        root = put(root, key, newVal);
        root.color = BLACK;
    }

    //Recursively inserts a new node, then rotates as needed, passing RED links up the chain
    public Node put(Node current, Key searchKey, Value newVal) {
        compares++;

        if (current == null) {
            return new Node(searchKey, newVal, 1, RED);
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

        //Fix RED links
        if (isRed(current.right) && !isRed(current.left)) {
            current = rotateLeft(current);
        }
        if (isRed(current.left) && isRed(current.left.left)) {
            current = rotateRight(current);
        }
        if (isRed(current.left) && isRed(current.right)) {
            flipColors(current);
        }

        current.size = size(current.left) + size(current.right) +1;

        return current;

    }


    /**********
     * Deletion functions
     **********/
    //Delete the smallest element
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Red Black tree is empty");

        //If both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    public Node deleteMin(Node current) {
        if (current.left == null) {
            return null;
        }

        if (!isRed(current.left) && !isRed(current.left.left)) {
            current = moveRedLeft(current);
        }

        current.left = (deleteMin(current.left));

        return balance(current);
    }

    //Delete largest element
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table empty");

        //If both children of root are black, set root to red
        if (!isRed(root.left) && isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);

        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node deleteMax(Node current) {
        if (isRed(current.left)) {
            current = rotateRight(current);
        }

        if (current.right == null) {
            return null;
        }

        if (!isRed(current.right) && !isRed(current.right.left)) {
            current = moveRedRight(current);
        }

        current.right = deleteMax(current.right);

        return balance(current);
    }

    public boolean contains(Key searchKey) {
        return get(searchKey) != null;
    }


    public void delete (Key toDelete) {
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, toDelete);
        if (!isEmpty()) root.color = BLACK;
    }

    public Node delete (Node current, Key toDelete) {
        if (toDelete.compareTo(current.key) < 0)  {

            if (!isRed(current.left) && !isRed(current.left.left)) {
                current = moveRedLeft(current);
            }

            current.left = delete(current.left, toDelete);
        }
        else {
            if (isRed(current.left)) {
                current = rotateRight(current);
            }

            if (toDelete.compareTo(current.key) == 0 && (current.right == null)) {
                return null;
            }

            if (!isRed(current.right) && !isRed(current.right.left)) {
                current = moveRedRight(current);
            }

            if (toDelete.compareTo(current.key) == 0) {
                Node temp = min(current.right);
                current.key = temp.key;
                current.val = temp.val;
                current.val = get(current.right, min(current.right).key);
                current.key = min(current.right).key;
                current.right = deleteMin(current.right);
            } else {
                current.right = delete(current.right, toDelete);
            }
        }

        return balance(current);
    }


    public Iterable<Key> keys() {
        forIterating = new ArrayList();

        inOrder(root);

        return forIterating;
    }

    private Key inOrder (Node current) {
        if(current.left != null) {
            inOrder(current.left);
        }

        forIterating.add(current.key);

        if (current.right != null) {
            inOrder(current.right);
        }


        return current.key;
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

    public boolean isEmpty() {
        return (root == null);
    }

    //Test client for BST Symbol Table
    public static void main(String[] args) {
        RBTSymbolTable<Integer, String> testRBT = new RBTSymbolTable<Integer, String>();

        try {
            testRBT.deleteMin();
            testRBT.deleteMax();
        } catch (NoSuchElementException e) {
            StdOut.println(e.getMessage());
        }


        testRBT.put(3, "Three");
        testRBT.put(1, "One");
        testRBT.put(2, "Two");
        testRBT.put(4, "Four");
        testRBT.put(1, "OneOne");
        testRBT.put(10, "Ten");

        for (Integer myInt : testRBT.keys()) {
            StdOut.println(myInt + " " + testRBT.get(myInt));
        }
        StdOut.println("Size: " + testRBT.size());
        StdOut.println();

        testRBT.delete(3);

        for (Integer myInt : testRBT.keys()) {
            StdOut.println(myInt + " " + testRBT.get(myInt));
        }
        StdOut.println("Size: " + testRBT.size());
        StdOut.println();

        testRBT.deleteMin();
        testRBT.deleteMax();

        for (Integer myInt : testRBT.keys()) {
            StdOut.println(myInt + " " + testRBT.get(myInt));
        }
        StdOut.println("Size: " + testRBT.size());
        StdOut.println();

        testRBT.put(3, "ThreeThree");
        testRBT.put(1, "One");
        testRBT.put(5, "Five");
        testRBT.put(10, "Ten");

        for (Integer myInt : testRBT.keys()) {
            StdOut.println(myInt + " " + testRBT.get(myInt));
        }
        StdOut.println("Size: " + testRBT.size());
        StdOut.println();
        StdOut.println("Min: " + testRBT.min().key);
        StdOut.println("Max: " + testRBT.max().key);
        StdOut.println("Floor of 4: " + testRBT.floor(4));
        StdOut.println("Ceiling of 4: " + testRBT.ceiling(4));
        StdOut.println("Floor of 3: " + testRBT.floor(3));
        StdOut.println("Ceiling of 3: " + testRBT.ceiling(3));
        StdOut.println("Floor of 0: " + testRBT.floor(0));
        StdOut.println("Ceiling of 11: " + testRBT.ceiling(11));
        StdOut.println("Floor of 7: " + testRBT.floor(7));
        StdOut.println("Ceiling of 7: " + testRBT.ceiling(7));
        StdOut.println("Binary Tree contains 5: " + testRBT.contains(5));
        StdOut.println("Binary Tree contains 6: " + testRBT.contains(6));
        StdOut.println("Key at rank 3: " + testRBT.select(3));
        StdOut.println("Key at rank 5: " + testRBT.select(5));
        StdOut.println("Rank of key 3: " + testRBT.rank(3));
        StdOut.println("Rank of key 10: " + testRBT.rank(10));


    }
}
