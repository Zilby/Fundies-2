import java.util.*;
import tester.Tester;

public class Stack<T> {
    Deque<T> contents = new Deque<T>();

    // adds an item to the head of the list
    void push(T item) {
        contents.addAtHead(item);
    }

    boolean isEmpty() {
        return contents.size() == 0;
    }

    // removes and returns the head of the list
    T pop() {
        return contents.removeFromHead();
    }
}

class Queue<T> {
    Deque<T> contents = new Deque<T>();

    // adds an item to the tail of the list
    void enqueue(T item) {
        contents.addAtTail(item);
    }

    boolean isEmpty() {
        return contents.size() == 0;
    }

    // removes and returns the tail of the list
    T dequeue() {
        return contents.removeFromTail();
    }
}

// Using two for-each loops and either a stack or a queue as
// a temporary storage space (only one of them will work),
// define the method that reverses an ArrayList:
class Utils {
    <T> ArrayList<T> reverse(ArrayList<T> source) {
        Stack<T> temp = new Stack<T>();
        ArrayList<T> result = new ArrayList<T>();
        for (T t : source) {
            temp.push(t);
        }
        for (T t : source) {
            result.add(temp.pop());
        }
        return result;
    }
}

interface INumBinTree extends Iterable<Integer>{
    boolean isLeaf();

    NumNode asNode();

    boolean validBST();

    INumBinTree insertBST(int num);
    
    Iterator<Integer> iterator();
}

class NumNode implements INumBinTree {
    int value;
    INumBinTree left;
    INumBinTree right;

    NumNode(int value) {
        this.value = value;
        this.left = new NumLeaf();
        this.right = new NumLeaf();
    }

    NumNode(int value, INumBinTree left, INumBinTree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return false;
    }

    public NumNode asNode() {
        return this;
    }

    public boolean validBST() {
        if (left.isLeaf() || left.asNode().value < this.value) {
            if (right.isLeaf() || right.asNode().value > this.value) {
                return left.validBST() && right.validBST();
            }
        }
        return false;
    }

    public INumBinTree insertBST(int num) {
        if (num < this.value) {
            if (this.left.isLeaf()) {
                this.left = new NumNode(num);
            } else {
                this.left.insertBST(num);
            }
        } else {
            if (this.right.isLeaf()) {
                this.right = new NumNode(num);
            } else {
                this.right.insertBST(num);
            }
        }
        return this;
    }
    
    public Iterator<Integer> iterator() {
        return new BreadthFirstIterator(this);
    }
}

class NumLeaf implements INumBinTree {
    public boolean isLeaf() {
        return true;
    }

    public NumNode asNode() {
        throw new RuntimeException("Not a node");
    }

    public boolean validBST() {
        return true;
    }

    public INumBinTree insertBST(int num) {
        return new NumNode(num);
    }
    
    public Iterator<Integer> iterator() {
        return new BreadthFirstIterator(this);
    }
}

class BreadthFirstIterator implements Iterator<Integer> {
    Queue<NumNode> todoList = new Queue<NumNode>();

    BreadthFirstIterator(INumBinTree t) {
        if (!t.isLeaf())
            this.todoList.enqueue(t.asNode());
    }

    // returns true if there's at least one value left in this iterator
    public boolean hasNext() {
        return todoList.isEmpty();
    }

    // returns the next value and advances the iterator
    public Integer next() {
        if (this.hasNext()) {
            NumNode temp = todoList.dequeue();
            if (!temp.right.isLeaf()) {
                todoList.enqueue(temp.right.asNode());
            }
            if (!temp.left.isLeaf()) {
                todoList.enqueue(temp.left.asNode());
            }
            return temp.value;
        }
        throw new IndexOutOfBoundsException("No more next");
    }

    // IGNORE THIS FOR NOW
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

class Examples {
    Utils u = new Utils();
    ArrayList<String> a = new ArrayList<String>();
    ArrayList<String> b = new ArrayList<String>();

    // resets to initial conditions
    void initData() {
        a.add("p");
        a.add("a");
        a.add("s");
        a.add("d");
        a.add("g");
        a.add("e");
        a.add("w");
        a.add("i");
        b.add("i");
        b.add("w");
        b.add("e");
        b.add("g");
        b.add("d");
        b.add("s");
        b.add("a");
        b.add("p");
    }

    void test(Tester t) {
        initData();
        t.checkExpect(u.reverse(this.a), this.b);
    }
}