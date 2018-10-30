public class RBNode<Key extends Comparable<Key>, Value> {

    public Key key;
    public Value value;
    public RBNode<Key, Value> left;
    public RBNode<Key, Value> right;
    public int size;
    public boolean color; //red=true black=false


    public RBNode(Key key, Value value, int size,boolean color) {
        this.key = key;
        this.value = value;
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return "RBNode{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    public boolean getColor(){return color;}
    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public RBNode<Key, Value> getLeft() {
        return left;
    }

    public void setLeft(RBNode<Key, Value> left) {
        this.left = left;
    }

    public RBNode<Key, Value> getRight() {
        return right;
    }

    public void setRight(RBNode<Key, Value> right) {
        this.right = right;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
