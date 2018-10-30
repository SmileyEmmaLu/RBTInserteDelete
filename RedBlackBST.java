
import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private boolean RED = true;
    private boolean BLACK = false;

    private RBNode root;     // root of the BST
  
    /**
     * Initializes an empty symbol table.
     */
    public RedBlackBST() {

    }
    public RedBlackBST(RBNode n) {

        root=n;
    }

    /***************************************************************************
     *  RBNode helper methods.
     ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(RBNode x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(RBNode x) {
        if (x == null) return 0;
        return x.size;
    }


    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    /**
     * Is this symbol table empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


    /***************************************************************************
     *  Standard BST search.
     ***************************************************************************/

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(RBNode x, Key key) {
        while (x != null) {
            int cmp = key.compareTo((Key)x.getKey());
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return(Value) x.value;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /***************************************************************************
     *  Red-black tree insertion.
     ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
        balance(root);
        // assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private RBNode put(RBNode h, Key key, Value val) {
        if (root.equals(null)){
            root=h;
        root.setKey(key);
        root.setValue(val);
        }
        if (h.getRight().equals(null)&&((h.getKey()).compareTo(key)<0)){
            RBNode q= new RBNode(key,val,0,true);
            h.setRight(q);
            return h;
        }
        if (h.getLeft().equals(null)&&((h.getKey()).compareTo(key)>0)){
            RBNode q= new RBNode(key,val,0,true);
            h.setLeft(q);
            return h;
        }
        if (h.getKey().compareTo(key)>0){
            h.setLeft(put(h.getLeft(),key,val));
        }
        if (h.getKey().compareTo(key)<0){
            h.setRight(put(h.getRight(),key,val));
        }


        return h;
    }

    /***************************************************************************
     *  Red-black tree deletion.
     ***************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private RBNode deleteMin(RBNode h) {
        if(h == root){
            rotateLeft(h);
        }


        return balance(h);
    }


    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the maximum key rooted at h
    private RBNode deleteMax(RBNode h) {
        /**deleteMax**/

        return balance(h);
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private RBNode delete(RBNode h, Key key) {
        // assert get(h, key) != null;
       RBNode q= max(h.getLeft());
       h.setValue(q.getValue());
       h.setKey(q.getKey());
        max(h.getLeft()).setValue(null);

        /**delete**/
        
        return balance(h);
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/

    // make a left-leaning link lean to the right
    public RBNode rotateRight(RBNode h) {
        RBNode orphan=h.getLeft().getRight();
        RBNode Rotated= h.getLeft();
        h.setLeft(orphan);
       Rotated.setRight(h);

        root=Rotated;
        // assert (h != null) && isRed(h.left);
        /**ROTATE RIGHT**/
        return Rotated;
    }

    // make a right-leaning link lean to the left
    private RBNode rotateLeft(RBNode h) {
        RBNode orphan= h.getRight().getLeft();
        RBNode Rotated= h.getRight();
        h.setRight(orphan);
        Rotated.setLeft(h);
        root=Rotated;
        // assert (h != null) && isRed(h.right);
        /**ROTATE LEFT**/
        
        return Rotated;
    }

    // flip the colors of a node and its two children
    private void flipColors(RBNode h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        
        //HINT: NOT RECURSIVE
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private RBNode moveRedLeft(RBNode h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);


        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private RBNode moveRedRight(RBNode h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        
        return h;
    }

    // restore red-black tree invariant
    private RBNode balance(RBNode h) {
        // assert (h != null);
        //rotate right or left or recolor the nodes appropriately
        //update h's size
        return h;
    }


    /***************************************************************************
     *  Utility functions.
     ***************************************************************************/

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(RBNode x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
    public boolean isRBT(){
        return isRBT(root);
    }
    
    private boolean isRBT(RBNode n){
        if (n.equals(null)){return true;}

        if((n.getLeft().getKey().compareTo(n.getRight().getKey())<0)
                ||((n.getLeft().getKey()).compareTo(n.getKey())>0)&&(n.getRight().getKey().compareTo(n.getKey()))<0){
            return false;
        }
       if (!n.getLeft().equals(null)&&!n.getRight().equals(null)) {
           if (n.getColor() && (n.getLeft().getColor() || n.getRight().getColor())) {
               return false;
           }
       }
        if (n.getLeft().getSize()!=n.getRight().getSize()){return false;}
        return isRBT(n.getLeft())&&isRBT(n.getRight());


    }

    public boolean gParent(RBNode n){
        RBNode q;
        if (n.getLeft()!=null){
            if (n.getLeft()!=null){return true;}
            if (n.getRight()!=null){return true;}
        }
        if (n.getRight()!=null){
            if (n.getLeft()!=null){return true;}
            if (n.getRight()!=null){return true;}
        }
        return false;}

    /***************************************************************************
     *  Ordered symbol table methods.
     ***************************************************************************/

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return(Key)min(root).key;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private RBNode min(RBNode x) {
        // assert x != null;
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return (Key)max(root).key;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private RBNode max(RBNode x) {
        // assert x != null;
        if (x.right == null) return x;
        else return max(x.right);
    }


}

