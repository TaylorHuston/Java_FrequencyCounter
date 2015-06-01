import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
   Symbol Table implemented with an unordered Linked List
 */
public class LLSymbolTable<Key, Value> {
    private Node head;
    private int size = 0;
    private int compares = 0;
    private int words = 0;
    private ArrayList<Key> forIterating = new ArrayList();

    private class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    //Method that returns the value associated with a passed in key
    public Value get(Key toGet) {
        //Iterate through LL, if a matching key is found return the associated value
        for (Node position = head; position != null; position = position.next) {
            if (toGet.equals(position.key)) {
                return position.val;
            }
        }
        return null;
    }

    //Returns whether the key the linked list contains the key or not
    public boolean contains(Key searchKey) {
        return (get(searchKey) != null);
    }

    //Method that inserts a new key-val pair into linked list
    public void put(Key newKey, Value newVal) {
        words++;

        //Iterate through the LL, if key exists update associated value, if not add it to the beginning of the LL
        for (Node position = head; position != null; position = position.next) {
            compares++;
            if (newKey.equals(position.key)) {
                position.val = newVal;
                return;
            }
        }
        head = new Node(newKey, newVal, head);
        size++;
        forIterating.add(newKey);
    }

    public void delete(Key toDelete) {
        if (contains(toDelete) == false) {
            throw new NoSuchElementException("Key does not exist in Symbol Table");

        }
    }

    public Iterable<Key> keys() {
        return forIterating;
    }

    public int size() {
        return size;
    }

    public int compares() {
        return compares;
    }

    public int words() {
        return words;
    }
}
