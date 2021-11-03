import java.util.ArrayList;
import java.util.List;

/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 2
 * <p>
 * TODO: Complete implementation of AVL.Java
 *
 * @author Shivam Bairoloya, Sofia Meza
 * @username sbairoli, meza8
 * @sources -, TODO: add your sources here
 */
public class AVL {

    /**
     * Insert a tuple in the tree
     *
     * @param node The root of the tree
     * @param tuple The data to be inserted
     * @return root of the tree
     */
    public Node insert(Node node, Tuple tuple) {
        //Step 1: Insert like in normal BST
        if(node == null) { //Base case
            return (new Node(tuple));
        }

        if (node.data.compareTo(tuple) == -1) { //node.data < tuple then insert right
            node.right = insert(node.right, tuple);
        } else if (node.data.compareTo(tuple) == 1){ //node.data > tuple then insert left
            node.left = insert(node.left, tuple);
        } else { // node.data == tuple (don't insert)
            return node; //return the root
        }

        //max height (ternary condition)
        node.height = (height(node.left) > height(node.right) ? height(node.left) : height(node.right)) + 1;
        int balance = balanceFactor(node);

        //Only 3 cases:
        //left heavy tree (balance > 1) : right or leftRight rotate
        //right heavy tree (balance < -1): left of rightLeft rotate)
        //Balanced tree: no rotations
        if (balance > 1) { //left heavy tree

            if (tuple.compareTo(node.left.data) == -1) { //if tuple < node.left.data
                return rightRotate(node);
            }
            if (tuple.compareTo(node.left.data) == 1) { //if tuple > node.left.data
                return leftRightRotate(node);
            }
        }
        if (balance < -1) { //right heavy tree
            if (tuple.compareTo(node.right.data) == -1) { //if tuple < node.right.data
                return rightLeftRotate(node);
            }
            if (tuple.compareTo(node.right.data) == 1) { //if tuple > node.right.data
                return leftRotate(node);
            }
        }
        return node; //gets here if no changes were made
    }

    /**
     * Performs left rotation on a given
     * node
     *
     * @param node The root of the tree
     * @return root of the tree
     */
    private Node leftRotate (Node node) {
        Node toBeRoot = node.right;
        Node toChangePointer = node.right.left;

        toBeRoot.left = node;
        node.right = toChangePointer;

        node.height = (height(node.left) > height(node.right) ? height(node.left) : height(node.right)) + 1;
        toBeRoot.height = (height(toBeRoot.left) > height(toBeRoot.right) ? height(toBeRoot.left) : height(toBeRoot.right)) + 1;
        //No need to change height for toChangePoiner cause it stays the same

        return toBeRoot;
    }

    /**
     * Performs left right rotation on a given
     * node
     *
     * @param node The root of the tree
     * @return root of the tree
     */
    private Node leftRightRotate (Node node) {

        /*
        Node toBeRoot = node.left.right;
        Node toChangePointer = node.left.right.left;

        toBeRoot.left = node.left;
        node.left.right = toChangePointer;

        node.left.height = (height(node.left.left) > height(node.left.right) ? height(node.left.left) : height(node.left.right)) + 1;
        toBeRoot.height = (height(toBeRoot.left) > height(toBeRoot.right) ? height(toBeRoot.left) : height(toBeRoot.right)) + 1;
        //No need to change height for toChangePoiner cause it stays the same

        node.left = toBeRoot;

         */
        node.left = leftRotate(node.left);

        return rightRotate(node);
    }

    /**
     * Performs right rotation on a given
     * node
     *
     * @param node The root of the tree
     * @return root of the tree
     */
    private Node rightRotate (Node node) {
        Node toBeRoot = node.left;
        Node toChangePointer = node.left.right;

        toBeRoot.right = node;
        node.left = toChangePointer;

        node.height = (height(node.left) > height(node.right) ? height(node.left) : height(node.right)) + 1;
        toBeRoot.height = (height(toBeRoot.left) > height(toBeRoot.right) ? height(toBeRoot.left) : height(toBeRoot.right)) + 1;
        //No need to change height for toChangePoiner cause it stays the same

        return toBeRoot;
    }

    /**
     * Performs right left rotation on a given
     * node
     *
     * @param node The root of the tree
     * @return root of the tree
     */
    private Node rightLeftRotate (Node node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    /**
     * Returns the height of a particular node
     * Returns -1 if the node is null
     *
     * @param node the node
     * @return the height
     */
    public int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    /**
     * Returns the balance factor of a particular node
     *
     * @param node
     * @return the balance factor
     */
    public int balanceFactor(Node node) {
        //assume node is not null
        return (height(node.left) - height(node.right));
    }

    /**
     * Returns the preorder traversal
     *
     * @param node
     * @return
     */
    public List<Node> preorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        if (node.left != null) {
            nodes.addAll(preorder(node.left));
        }
        if (node.right != null) {
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }


    /**
     * Returns the inorder traversal
     *
     * @param node
     * @return
     */
    public List<Node> inorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        if (node.left != null) {
            nodes.addAll(inorder(node.left));
        }
        nodes.add(node);
        if (node.right != null) {
            nodes.addAll(inorder(node.right));
        }
        return nodes;
    }

    /**
     * Returns the level order traversal
     *
     * @param node
     * @return
     */
    public String levelOrder(Node node) {
        int height = height(node);
        List<List<Node>> levels = new ArrayList<>();
        for (int i = 1; i <= height + 1; i++) {
            levels.add(levelOrderHelper(node, i));
        }
        StringBuilder out = new StringBuilder();
        int i = 0;
        for (List<Node> level : levels) {
            out.append("Level ").append(i).append(": ");
            for (Node x : level) {
                out.append("|").append(x).append("|");
            }
            out.append("\n");
            i++;
        }
        return out.toString();
    }

    /**
     * Recursive function to aid level order traversal
     *
     * @param curr
     * @param level
     * @return
     */
    private List<Node> levelOrderHelper(Node curr, int level) {
        List<Node> currLevel = new ArrayList<>();
        if (curr == null) {
            currLevel.add(null);
            return currLevel;
        }
        if (level == 1) {
            currLevel.add(curr);
            return currLevel;
        }

        currLevel.addAll(levelOrderHelper(curr.left, level - 1));
        currLevel.addAll(levelOrderHelper(curr.right, level - 1));
        return currLevel;
    }

    /**
     * For manual testing
     * @param args
     */
    public static void main(String[] args) {
        AVL avl = new AVL();

        Character[] arr1 = new Character[1];
        arr1[0] = 'a';
        Character[] arr2 = new Character[1];
        arr2[0] = 'b';
        Character[] arr3 = new Character[1];
        arr3[0] = 'c';

        Tuple tuple1 = new Tuple(arr1);
        Tuple tuple2 = new Tuple(arr2);
        Tuple tuple3 = new Tuple(arr3);

        Node root = new Node(tuple1);

        root = avl.insert(root, tuple1);
        root = avl.insert(root, tuple2);
        root = avl.insert(root, tuple3);
    }
}