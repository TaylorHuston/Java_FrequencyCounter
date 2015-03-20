import java.util.NoSuchElementException;

/**
   Symbol Table implemented with an unordered Linked List
 */
public class LLSymbolTable<Key, Value> {
    private Node head;
    private int size = 0;

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
        //Iterate through the LL, if key exists update associated value, if not add i to the beginning of the LL
        for (Node position = head; position != null; position = position.next) {
            if (newKey.equals(position.key)) {
                position.val = newVal;
                return;
            }
        }
        head = new Node(newKey, newVal, head);
        size++;
    }

    public void delete(Key toDelete) {
        if (contains(toDelete) == false) {
            throw new NoSuchElementException("Key does not exist in Symbol Table");

        }
    }
}
