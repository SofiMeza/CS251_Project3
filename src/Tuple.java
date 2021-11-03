/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 * <p>
 * TODO: Complete implementation of Tuple.Java
 *
 * @author Shivam Bairoloya, Sofia Meza
 * @username sbairoli, meza8
 * @sources -, TODO: add your sources here
 */

public class Tuple<Item extends Comparable<Item>> {

    private Item[] items;

    /**
     * Initialize items
     *
     * @param items the initial value
     */
    public Tuple(Item[] items) {
        this.items = items;
    }

    /**
     * Getter
     *
     * @return items
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * Setter
     *
     * @param items to set
     */
    public void setItems(Item[] items) {
        this.items = items;
    }

    /**
     * Returns a String representation of the items
     *
     * @return toString representation
     */
    @Override
    public String toString() {
        StringBuilder x = new StringBuilder("");
        for (int i = 0; i < items.length; i++) {
            x.append(items[i]);
        }
        return x.toString();
    }

    /**
     * Lexicographically compares the items
     *
     * @param toCompare items to compare with
     * @return -1 if less 0 if it is the same and 1 if it is greater
     */
    public int compareTo(Tuple<Item> toCompare) {
        //TODO: Implement compareTo
        for (int i = 0; i <this.items.length && i < toCompare.items.length; i++) {
            if (this.items[i].compareTo(toCompare.items[i]) == 0) { //element at index is equal
                continue;
            } else if (this.items[i].compareTo(toCompare.items[i]) > 0) {
                return 1;
            } else if (this.items[i].compareTo(toCompare.items[i]) < 0) {
                return -1;
            }
        }

        //if string start the same and one ends ("hello" , "helloWorld")
        if (this.items.length > toCompare.items.length) {
            return 1;
        } else if (this.items.length < toCompare.items.length) {
            return -1;
        }
        return 0;
    }

    /**
     * For manual testing
     * @param args
     */
    public static void main(String[] args) {

    }
}
