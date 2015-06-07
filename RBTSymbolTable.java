import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 Symbol Table implemented with a Red Black Tree
 */
public class RBTSymbolTable<Key extends Comparable<Key>, Value> {

    private Node root;
    private int compares = 0;
    private int words = 0;
    ArrayList<Key> forIterating = new ArrayList();

    //For Red-Black implementation
    private static final boolean RED = true;
    private static final boolean BLACk = false;

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

    private boolean isRead(Node x) {
        if (x == null) {
            return false;
        }

        return x.color == RED;
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


    //Delete the smallest element
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table empty");
        root = deleteMin(root);
    }

    public Node deleteMin(Node current) {
        if (current.left == null) {
            return current.right;
        }

        current.left = deleteMin(current.left);
        current.size = size(current.left) + size(current.right) +1;

        return current;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table empty");
        root = deleteMax(root);
    }

    public Node deleteMax(Node current) {
        if (current.right == null) {
            return current.left;
        }

        current.right = deleteMax(current.right);
        current.size = size(current.left) + size(current.right) +1;

        return current;
    }

    public boolean contains(Key searchKey) {
        return get(searchKey) != null;
    }

    //Recursively search through the BST to find the matching Key
    //If the matching node contains two children, replace it with it's successor
    public void delete (Key toDelete) {
        root = delete (root, toDelete);
    }

    public Node delete (Node current, Key toDelete) {
        if (current == null) {
            return null;
        }

        int cmp = toDelete.compareTo(current.key);

        if (cmp < 0) {
            current.left = delete(current.left, toDelete);
        } else if (cmp > 0) {
            current.right = delete(current.right, toDelete);
        } else {
            if (current.right == null) {
                return current.left;
            } else if (current.left == null) {
                return current.right;
            } else {
                Node temp = current;
                current = min(temp.right);
                current.right = deleteMin(temp.right);
                current.left = temp.left;
            }
        }
        current.size = size(current.left) + size(current.right) + 1;
        return current;
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
//        RBTSymbolTable<Integer, String> testBSTST = new BSTSymbolTable<Integer, String>();
//
//        try {
//            testBSTST.deleteMin();
//            testBSTST.deleteMax();
//        } catch (NoSuchElementException e) {
//            StdOut.println(e.getMessage());
//        }
//
//
//        testBSTST.put(3, "Three");
//        testBSTST.put(1, "One");
//        testBSTST.put(2, "Two");
//        testBSTST.put(4, "Four");
//        testBSTST.put(1, "OneOne");
//        testBSTST.put(10, "Ten");
//
//        for (Integer myInt : testBSTST.keys()) {
//            StdOut.println(myInt + " " + testBSTST.get(myInt));
//        }
//        StdOut.println("Size: " + testBSTST.size());
//        StdOut.println();
//
//        testBSTST.delete(3);
//
//        for (Integer myInt : testBSTST.keys()) {
//            StdOut.println(myInt + " " + testBSTST.get(myInt));
//        }
//        StdOut.println("Size: " + testBSTST.size());
//        StdOut.println();
//
//        testBSTST.deleteMin();
//        testBSTST.deleteMax();
//
//        for (Integer myInt : testBSTST.keys()) {
//            StdOut.println(myInt + " " + testBSTST.get(myInt));
//        }
//        StdOut.println("Size: " + testBSTST.size());
//        StdOut.println();
//
//        testBSTST.put(3, "Three");
//        testBSTST.put(1, "One");
//        testBSTST.put(5, "Five");
//        testBSTST.put(10, "Ten");
//
//        for (Integer myInt : testBSTST.keys()) {
//            StdOut.println(myInt + " " + testBSTST.get(myInt));
//        }
//        StdOut.println("Size: " + testBSTST.size());
//        StdOut.println();
//        StdOut.println("Min: " + testBSTST.min().key);
//        StdOut.println("Max: " + testBSTST.max().key);
//        StdOut.println("Floor of 4: " + testBSTST.floor(4));
//        StdOut.println("Ceiling of 4: " + testBSTST.ceiling(4));
//        StdOut.println("Floor of 3: " + testBSTST.floor(3));
//        StdOut.println("Ceiling of 3: " + testBSTST.ceiling(3));
//        StdOut.println("Floor of 0: " + testBSTST.floor(0));
//        StdOut.println("Ceiling of 11: " + testBSTST.ceiling(11));
//        StdOut.println("Floor of 7: " + testBSTST.floor(7));
//        StdOut.println("Ceiling of 7: " + testBSTST.ceiling(7));
//        StdOut.println("Binary Tree contains 5: " + testBSTST.contains(5));
//        StdOut.println("Binary Tree contains 6: " + testBSTST.contains(6));
//        StdOut.println("Key at rank 3: " + testBSTST.select(3));
//        StdOut.println("Key at rank 5: " + testBSTST.select(5));
//        StdOut.println("Rank of key 3: " + testBSTST.rank(3));
//        StdOut.println("Rank of key 10: " + testBSTST.rank(10));
//

    }
}
