import tester.*;

//To represent a binary search tree
public abstract class ABST<T> {
    
    //comparator for how the tree is ordered
    IComparator<T> order;
    
    ABST(IComparator<T> order) {
        this.order = order;
    }
    
    //for inserting new elements into the tree
    ABST<T> insert(T t) {
        return new Node<T>(order, t);
    }
    
    //for getting the left-most element of a tree
    T getLeftmost() {
        throw new RuntimeException("No leftmost item of an empty tree");
    }
    
    //for getting everything but the left-most element of a tree
    ABST<T> getRight() {
        throw new RuntimeException("No right of an empty tree");
    }
    
    //determines if this tree is the same as the given tree
    boolean sameTree(ABST<T> a) {
        return a.empty();
    }
    
    //assists the same tree method by allowing it to retrieve data from the given tree
    boolean sameTreeHelp(Node<T> n) {
        return false;
    }
    
    //determines if this tree has the same data as the given tree
    boolean sameData(ABST<T> a) {
        return a.empty();
    }
    
    //builds a list using the data from this tree
    IList<T> buildList(IList<T> l) {
        return l;
    }
    
    //determines if the given list has the same data as this tree
    boolean sameAsList(IList<T> l) {
        return sameTree(l.buildTree(new Leaf<T>(order)));
    }
    
    //returns whether this tree has any elements
    boolean empty() {
        return true;
    }
}

//used to compare objects
interface IComparator<T> {
    
    //compares two objects giving a positive number
    //if the first is greater, a negative if the second
    //is greater, and 0 if they are the same
    int compare(T t1, T t2);
}

//represents an empty binary search tree
class Leaf<T> extends ABST<T> {
    Leaf(IComparator<T> order) {    
        super(order);
    }
}

//represents a single node of a binary search tree
class Node<T> extends ABST<T> {
    
    //the node's data
    T data;
    //the left node (must come before this node)
    ABST<T> left;
    //the right node (must come after this node)
    ABST<T> right;
    
    //for faster initialization
    Node(IComparator<T> order, T data) {
        super(order);
        this.data = data;
        this.left = new Leaf<T>(order);
        this.right = new Leaf<T>(order);
    }
    
    //for more precise initialization
    Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
        super(order);
        this.data = data;
        this.left = left;
        this.right = right;
    }
    
    //for inserting new elements into the tree
    public ABST<T> insert(T t) {
        if (this.order.compare(t, this.data) < 0) {
            return new Node<T>(this.order, this.data, this.left.insert(t), this.right);  
        }
        else {
            return new Node<T>(this.order, this.data, this.left, this.right.insert(t));
        }
    }
    
    //for getting the left-most element of a tree
    public T getLeftmost() {
        if (this.left.empty()) {
            return this.data;
        }
        else {
            return this.left.getLeftmost();
        }
    }
    
    //for getting everything but the left-most element of a tree
    public ABST<T> getRight() {
        if (this.left.empty()) {
            if (this.right.empty()) {
                return new Leaf<T>(order);
            }
            else {
                return this.right;
            }
        }
        else {
            return new Node<T>(this.order, this.data, this.left.getRight(), this.right);
        }
    }
    
    //determines if this tree is the same as the given tree
    public boolean sameTree(ABST<T> a) {
        if (a.empty()) {
            return false;
        }
        else {
            return a.sameTreeHelp(this);
        }
    }
    
    //assists the same tree method by allowing it to retrieve data from the given tree
    public boolean sameTreeHelp(Node<T> n) {
        if (n.order.compare(this.data, n.data) == 0) {
            return n.left.sameTree(this.left) && n.right.sameTree(this.right);
        }
        else {
            return false;
        }
    }
    
    //determines if this tree has the same data as the given tree
    //based upon the given order IComparator in a   
    public boolean sameData(ABST<T> a) {
        if (a.empty()) {
            return false;
        }
        else if (this.order.compare(a.getLeftmost(), this.getLeftmost()) == 0) {
            return this.getRight().sameData(a.getRight());
        }
        else {
            return false;
        }
    }
    
    //builds a list using the data from this tree
    public IList<T> buildList(IList<T> l) {
        return new Cons<T>(this.data, this.right.buildList(this.left.buildList(l)));
    }
    
    //returns whether this tree has any elements
    public boolean empty() {
        return false;
    }
}

//represents a book
class Book {
    //represents the title, author and price of a book
    String title;
    String author;
    int price;
    
    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

//compares books by their titles
class BooksByTitle implements IComparator<Book> {
    public int compare(Book t1, Book t2) { 
        return t1.title.compareTo(t2.title);
    }
}

//compares books by their authors
class BooksByAuthor implements IComparator<Book> {
    public int compare(Book t1, Book t2) { 
        return t1.author.compareTo(t2.author);
    }
}

//compares books by their prices
class BooksByPrice implements IComparator<Book> {
    public int compare(Book t1, Book t2) {
        if (t1.price > t2.price) {
            return 1;
        }
        else if (t1.price < t2.price) {
            return -1;
        }
        else {
            return 0;
        }
    }
}

//represents a list of objects
interface IList<T> {
    //builds a tree from a list
    ABST<T> buildTree(ABST<T> a);
    
   //Helper function to double dispatch Comparator
    boolean isSortedHelp(IComparator<T> c, T t);
    
    //confirms whether this list is sorted
    boolean isSorted(IComparator<T> c);
    
}

//represents an empty list
class Empty<T> implements IList<T> {
  //builds a tree from a list
    public ABST<T> buildTree(ABST<T> a) {
        return a;
    }
    
    //Helper function to double dispatch Comparator
    public boolean isSortedHelp(IComparator<T> c, T t) {
        return true;
    }
    
    //confirms whether this list is sorted
    public boolean isSorted(IComparator<T> c) {
        return true;
    }
}

//represents a list with an element of a list
class Cons<T> implements IList<T> {
    //The data of this node
    T first;
    //the rest of the list
    IList<T> rest;
    
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    
  //builds a tree from a list
    public ABST<T> buildTree(ABST<T> a) {
        return this.rest.buildTree(a.insert(this.first));
    }
    
  //Helper function to double dispatch Comparator
    public boolean isSortedHelp(IComparator<T> c, T t) {
        return 0 < c.compare(t, this.first);
    }
    
    //confirms whether this list is sorted
    public boolean isSorted(IComparator<T> c) {
        return this.rest.isSortedHelp(c, this.first) && this.rest.isSorted(c);
    }
}

//for testing purposes
class Examples {
    Book a = new Book("wharble", "blarble", 345);
    Book b = new Book("flabb", "gerbile", 543);
    Book c = new Book("dingy", "yoooooo", 2345);
    Book d = new Book("dingy", "ccacaa", 722);
    Book e = new Book("weeeooo", "gerbile", 4);
    Book f = new Book("tttiiiaaa", "nocom", 245);
    Book g = new Book("boots", "qwqeee", 432);
    Book h = new Book("mmmmmmm", "felix", 345);
    
    BooksByTitle tt = new BooksByTitle();
    BooksByAuthor aa = new BooksByAuthor();
    BooksByPrice pp = new BooksByPrice();
    
    ABST<Book> b1;
    ABST<Book> b2;
    ABST<Book> b3;
    IList<Book> l1; 
    IList<Book> l2;
    
    void initData() {
        b1 = new Leaf<Book>(tt);
        b2 = new Leaf<Book>(aa);
        b3 = new Leaf<Book>(pp);
        l1 = new Cons<Book>(a, new Cons<Book>(b, new Cons<Book>(c, 
                new Cons<Book>(d, new Empty<Book>()))));
        l2 = new Cons<Book>(e, new Cons<Book>(d, new Cons<Book>(c, 
                new Cons<Book>(f, new Cons<Book>(a, new Cons<Book>(b, 
                        new Cons<Book>(h, new Empty<Book>())))))));
    }
    
    void testInsert(Tester t) {
        initData();
        t.checkExpect(b1.insert(a), new Node<Book>(tt, a, 
                new Leaf<Book>(tt), new Leaf<Book>(tt)));
        t.checkExpect(b1.insert(a).insert(b).insert(c).insert(d), 
                new Node<Book>(tt, a, 
                        new Node<Book>(tt, b, 
                                new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                        new Node<Book>(tt, d, new Leaf<Book>(tt), 
                                                new Leaf<Book>(tt))), 
                                new Leaf<Book>(tt)), new Leaf<Book>(tt)));
        t.checkExpect(b2.insert(a).insert(b).insert(c).insert(d).insert(e)
                .insert(f).insert(g).insert(h), 
                new Node<Book>(aa, a, new Leaf<Book>(aa), 
                        new Node<Book>(aa, b, 
                                new Node<Book>(aa, d, new Leaf<Book>(aa), 
                                        new Node<Book>(aa, h, new Leaf<Book>(aa), 
                                                new Leaf<Book>(aa))), 
                                new Node<Book>(aa, c, 
                                        new Node<Book>(aa, e, new Leaf<Book>(aa), 
                                                new Node<Book>(aa, f, new Leaf<Book>(aa), 
                                                        new Node<Book>(aa, g, new Leaf<Book>(aa), 
                                                                new Leaf<Book>(aa)))), 
                                        new Leaf<Book>(aa)))));
    }   
    
    void testLeftRight(Tester t) {
        initData();
        t.checkException(new RuntimeException("No leftmost item of an empty tree"), 
                this.b1, "getLeftmost"); 
        t.checkException(new RuntimeException("No right of an empty tree"), 
                this.b1, "getRight");
        t.checkExpect(b3.insert(b).insert(f).insert(e).insert(a).getLeftmost(), 
                new Node<Book>(pp, e, new Leaf<Book>(pp), new Leaf<Book>(pp)).data);
        t.checkExpect(b3.insert(b).insert(f).insert(e).insert(a).getRight(), 
                new Node<Book>(pp, b, new Node<Book>(pp, f,  
                        new Leaf<Book>(pp),
                        new Node<Book>(pp, a, new Leaf<Book>(pp), new Leaf<Book>(pp))), 
                        new Leaf<Book>(pp)));
        t.checkExpect(b2.insert(b).insert(c).insert(d)
                .insert(e).insert(f).insert(g).insert(h).getLeftmost(),
                new Node<Book>(aa, d, new Leaf<Book>(aa), 
                        new Node<Book>(aa, h, new Leaf<Book>(aa), new Leaf<Book>(aa))).data);
        t.checkExpect(b2.insert(b).insert(c).insert(d)
                .insert(e).insert(f).insert(g).insert(h).getRight(),
                new Node<Book>(aa, b, 
                        new Node<Book>(aa, h, new Leaf<Book>(aa), new Leaf<Book>(aa)), 
                        new Node<Book>(aa, c, 
                                new Node<Book>(aa, e, new Leaf<Book>(aa), 
                                        new Node<Book>(aa, f, new Leaf<Book>(aa), 
                                                new Node<Book>(aa, g, new Leaf<Book>(aa), 
                                                        new Leaf<Book>(aa)))), 
                                new Leaf<Book>(aa))));
    }
    
    void testSameTree(Tester t) {
        initData();
        t.checkExpect(b1.sameTreeHelp(new Node<Book>(tt, a)), false);
        t.checkExpect(new Node<Book>(tt, a).sameTree(b1), false);
        t.checkExpect(new Node<Book>(tt, a).sameData(b1), false);
        t.checkExpect(b1.insert(a).insert(b).insert(c).insert(d).sameTree(
                new Node<Book>(tt, a, 
                        new Node<Book>(tt, b, 
                                new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                        new Node<Book>(tt, d, new Leaf<Book>(tt), 
                                                new Leaf<Book>(tt))), 
                                new Leaf<Book>(tt)), new Leaf<Book>(tt))), true);
        t.checkExpect(b1.insert(a).insert(f).insert(c).insert(d).sameTree(
                new Node<Book>(tt, a, 
                        new Node<Book>(tt, b, 
                                new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                        new Node<Book>(tt, d, new Leaf<Book>(tt), 
                                                new Leaf<Book>(tt))), 
                                new Leaf<Book>(tt)), new Leaf<Book>(tt))), false);
        t.checkExpect(b2.insert(a).insert(b).insert(c).insert(d).insert(e)
                .insert(f).insert(g).insert(h).sameTree(
                        new Node<Book>(aa, a, new Leaf<Book>(aa), 
                                new Node<Book>(aa, b, 
                                        new Node<Book>(aa, d, new Leaf<Book>(aa), 
                                                new Node<Book>(aa, h, new Leaf<Book>(aa), 
                                                        new Leaf<Book>(aa))), 
                                        new Node<Book>(aa, c, 
                                                new Node<Book>(aa, e, new Leaf<Book>(aa), 
                                                        new Node<Book>(aa, f, new Leaf<Book>(aa), 
                                                                new Node<Book>(aa, g, 
                                                                        new Leaf<Book>(aa), 
                                                                        new Leaf<Book>(aa)))), 
                                                new Leaf<Book>(aa))))), true);
        t.checkExpect(b2.insert(a).insert(b).insert(c).insert(d).insert(e)
                .insert(f).insert(g).insert(h).sameTree(
                        new Node<Book>(tt, a, 
                                new Node<Book>(tt, b, 
                                        new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                                new Node<Book>(tt, d, new Leaf<Book>(tt), 
                                                        new Leaf<Book>(tt))), 
                                        new Leaf<Book>(tt)), new Leaf<Book>(tt))), false);
    }
    
    void testSameData(Tester t) {
        initData();
        t.checkExpect(b2.insert(a).insert(b).insert(c).insert(d).sameData(
                b2.insert(c).insert(d).insert(a).insert(b)), true);
        t.checkExpect(b2.insert(a).insert(b).insert(c).insert(d).sameData(
                b2.insert(c).insert(e).insert(a).insert(f)), false);
        t.checkExpect(b2.insert(a).insert(b).insert(c).insert(d).sameData(
                b2.insert(c).insert(d).insert(a).insert(b).insert(a)), false);
    }
    
    void testBuildSame(Tester t) {
        initData();
        t.checkExpect(l1.isSorted(aa), false);
        t.checkExpect(new Cons<Book>(b, new Cons<Book>(a, new Empty<Book>())).isSorted(aa), true);
        t.checkExpect(l1.buildTree(new Leaf<Book>(tt)), new Node<Book>(tt, a, 
                new Node<Book>(tt, b, 
                        new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                new Node<Book>(tt, d, new Leaf<Book>(tt), new Leaf<Book>(tt))), 
                        new Leaf<Book>(tt)), new Leaf<Book>(tt)));
        t.checkExpect(new Node<Book>(tt, a, 
                new Node<Book>(tt, b, 
                        new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                new Node<Book>(tt, d, new Leaf<Book>(tt), new Leaf<Book>(tt))), 
                        new Leaf<Book>(tt)), new Leaf<Book>(tt))
                .buildList(new Empty<Book>()), l1);
        t.checkExpect(new Node<Book>(tt, a, 
                new Node<Book>(tt, b, 
                        new Node<Book>(tt, c, new Leaf<Book>(tt), 
                                new Node<Book>(tt, d, new Leaf<Book>(tt), new Leaf<Book>(tt))), 
                        new Leaf<Book>(tt)), new Leaf<Book>(tt)).sameAsList(l1), true);
    }
}