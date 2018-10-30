/**
 * Name: sloanwoodberry
 * Lab Name: driver
 * Lab Purpose
 * Date: 10/16/18
 * Collaborators: None
 */
public class driver {
    public static void main(String[] args) {
        RedBlackBST<Integer,String> rbBST;
        RBNode<Integer, String> one= new RBNode<>(20,"dumb",3,false);
        RBNode<Integer, String> left= new RBNode<>(8,"dumber",1,false);
        RBNode<Integer, String> right= new RBNode<>(12,"dumbest",1,true);
        one.setRight(right);
        one.setLeft(left);
        RBNode<Integer, String> leftRight= new RBNode<>(9,"dumbest",1,true);
        left.setRight(leftRight);
        rbBST= new RedBlackBST<>(one);
        System.out.println(rbBST.isEmpty());
        System.out.println(rbBST.isRBT());
        rbBST.rotateRight(one);
        System.out.println(rbBST.gParent(left));
        rbBST.put(11, "happy");
        System.out.println(rbBST.isRBT());
        System.out.println(rbBST.get(11));
        rbBST.delete(11);
        System.out.println(rbBST.get(11));
    }
}
