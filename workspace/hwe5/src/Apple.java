import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;


//to represent a predicate for Apples
interface ISelectApple {

    // Return true if the given Apple
    // should be selected
    boolean apply(Apple a);
}

//select apples that are caught by basket
class Caught implements ISelectApple {
    Posn p;
    
    Caught(Posn p) {
        this.p = p;
    }
    
    //determines if apple is caught by basket
    public boolean apply(Apple a) {
        return (this.p.x >= a.ctr.x - a.radius) &&
                (this.p.x <= a.ctr.x + a.radius) &&
                (this.p.y >= a.ctr.y - a.radius) &&
                (this.p.y <= a.ctr.y + a.radius);
    }
}

//select apples that have reached the ground
class OnGround implements ISelectApple {
    
    //determines if apple is on the ground
    public boolean apply(Apple a) {
        return a.onTheGround();
    }
}

//to represent a list of apples
interface ILoApple {
    
    //returns true if any apple satisfies the predicate
    boolean anySuchApple(ISelectApple a);
    
    //filters out apples based on a predicate
    ILoApple filter(ISelectApple a);
    
    //causes all apples in a list to fall for one frame
    ILoApple fall();
    
    //makes the world scene with each apple in the list
    WorldScene makeScene(WorldScene w);
}

//to represent an empty list of apples
class MtLoApple implements ILoApple {
    
    //determines if any apples meet the predicate
    public boolean anySuchApple(ISelectApple a) {
        return false;
    }
    
    //filters out apples based on a predicate
    public ILoApple filter(ISelectApple a) {
        return new MtLoApple();
    }

    //causes all apples in a list to fall for one frame
    public ILoApple fall() {
        return new MtLoApple();
    }
    
    //makes the world scene with each apple in the list
    public WorldScene makeScene(WorldScene w) {
        return w;
    }
}

//to represent an apple and the next element
//in a List of apples
class ConsLoApple implements ILoApple {
    Apple first;
    ILoApple rest;
    
    ConsLoApple(Apple first, ILoApple rest) {
        this.first = first;
        this.rest = rest;
    }
    
    //determines if any apples meet the predicate
    public boolean anySuchApple(ISelectApple a) {
        if (a.apply(this.first)) {
            return true;
        }
        else {
            return this.rest.anySuchApple(a);
        }
    }
    
    //filters out apples based on a predicate
    public ILoApple filter(ISelectApple pick) {
        if (pick.apply(this.first)) {
            return this.rest.filter(pick);
        }
        else {
            return new ConsLoApple(this.first, this.rest.filter(pick));
        }
    }
    
    //causes all apples in a list to fall for one frame
    public ILoApple fall() {
        return new ConsLoApple(this.first.fall(), rest.fall());
    }
    
    //makes the world scene with each apple in the list
    public WorldScene makeScene(WorldScene w) {
        return rest.makeScene(w)
                .placeImageXY(this.first.appleImage(), 
                        this.first.ctr.x, this.first.ctr.y);
    }
}

//to represent an apple in our game
class Apple {
    Posn ctr;
    int radius;
    
    Apple(Posn ctr, int radius) {
        this.ctr = ctr;
        this.radius = radius;
    }
    
    //returns the image for an apple
    WorldImage appleImage() { 
        return new FromFileImage("small-red-apple.png");
    }
    
    //moves the apple downwards
    public Apple moveDown() {
        return new Apple(new Posn(this.ctr.x, this.ctr.y + 4), this.radius);
    }
    
    //determines if the apple has reached the ground
    public boolean onTheGround() {
        return (this.ctr.y + this.radius >= 400);
    }
    
    //either moves the apple downwards or
    //creates a new apple if it has reached the ground
    public Apple fall() {
        if (this.onTheGround()) {
            return new Apple(new Posn(new Random().nextInt(381) + 10, 
                    new Random().nextInt(90) + 10), this.radius);
        }
        else {
            return this.moveDown();
        }
    }
}

//to represent a basket in our game
class Basket {
    Posn ctr;
    int radius;
    
    Basket(Posn ctr, int radius) {
        this.ctr = ctr;
        this.radius = radius;
    }
    
    //returns the image for a basket
    WorldImage basketImage() { 
        return new FromFileImage("basket.png");
    }   
      
    //moves the basket in our world given
    //a corresponding key event
    public Basket moveOnKey(String ke) {
        if (ke.equals("left") && (ctr.x >= 30)) {
            return new Basket(new Posn(this.ctr.x - 10, this.ctr.y), this.radius);
        }
        else if (ke.equals("right") && (ctr.x <= 370)) {
            return new Basket(new Posn(this.ctr.x + 10, this.ctr.y), this.radius);
        } 
        else {
            return this;
        }
    }
}

//represents our game world
class AppleGame extends World {
    //number of apples caught
    int caught = 0;
    //number of apples missed
    int missed = 0;
    //width/height of our world
    int width = 400;
    int height = 400;
    //list of all currently active apples
    ILoApple appleList = new ConsLoApple(new Apple(new Posn(new Random().nextInt(381) + 10, 
            new Random().nextInt(90) + 10), 30),
            new ConsLoApple(new Apple(new Posn(new Random().nextInt(381) + 10, 
                    new Random().nextInt(90) + 10), 30),
                    new ConsLoApple(new Apple(new Posn(new Random().nextInt(381) + 10, 
                            new Random().nextInt(90) + 10), 30), new MtLoApple())));
    //our basket for catching apples
    Basket basket = new Basket(new Posn(200, 365), 45);
    
    //for initial game making
    AppleGame() {
        super();
    }
    
    //used after first instantiation
    AppleGame(int caught, int missed, ILoApple appleList, Basket basket) {
        super();
        this.caught = caught;
        this.missed = missed;
        this.appleList = appleList;
        this.basket = basket;
    }
    
    //the background for our game
    WorldImage background = 
            new FromFileImage("apple-tree.png");

    //makes the scene for our game
    public WorldScene makeScene() {
        return  appleList.makeScene(this
                .getEmptyScene()
                .placeImageXY(this.background, this.width / 2, this.height / 2)
                .placeImageXY(new TextImage("" + this.caught, 40, Color.blue), 370, 30)
                .placeImageXY(this.basket.basketImage(), this.basket.ctr.x, 
                        this.basket.ctr.y));
    }
    
    //determines whether the basket has caught any apples in our game
    public boolean caughtApple() {
        return this.appleList.anySuchApple(new Caught(this.basket.ctr));
    }
    
    //progresses the game. If the game has caught an apple it will 
    //add 1 to caught, delete it, and create a new one. If an apple has reached
    //the ground it will similarly add 1 to missed, delete it, and create a new one
    public AppleGame onTick() {
        if (this.caughtApple()) {
            return new AppleGame((this.caught + 1), this.missed,
                    new ConsLoApple(new Apple(new Posn(new Random().nextInt(381) + 10, 
                            new Random().nextInt(90) + 10), 30), 
                            this.appleList.filter(new Caught(this.basket.ctr)).fall()),
                    this.basket);
        } 
        else if (this.appleList.anySuchApple(new OnGround())) {
            return new AppleGame(this.caught, this.missed + 1, 
                    new ConsLoApple(new Apple(new Posn(new Random().nextInt(381) + 10, 
                            new Random().nextInt(90) + 10), 30), 
                            this.appleList.filter(new OnGround()).fall()),
                    this.basket);
        }
        else {
            return new AppleGame(this.caught, this.missed,
                    this.appleList.fall(),
                    this.basket);
        }
    }
    
    //sends any key events to modify objects affected by them
    public AppleGame onKeyEvent(String ke) {
        return new AppleGame(this.caught, this.missed, this.appleList, this.basket.moveOnKey(ke));
    }
    
    //ends the game once 10 apples are caught
    public WorldEnd worldEnds() {
        if (this.caught >= 10) {
            return new WorldEnd(true, this
                .getEmptyScene()
                .placeImageXY(this.background, this.width / 2, this.height / 2)
                .placeImageXY(new TextImage("You Win! Missed: " + this.missed, 
                        40, Color.blue), 200, 340));
                    
        } 
        else {
            return new WorldEnd(false, this.makeScene());
        }
    }
}

//for testing purposes
class AppleExamples {

    //example data
    Apple a1 = new Apple(new Posn(200, 40), 30);
    Apple a2 = new Apple(new Posn(100, 20), 30);
    Apple a3 = new Apple(new Posn(200, 390), 30);
    Apple a1down = new Apple(new Posn(200, 44), 30);
    WorldImage ia = new FromFileImage("small-red-apple.png");
    Basket b1 = new Basket(new Posn(200, 365), 45);
    Basket b1left = new Basket(new Posn(190, 365), 45);
    Basket b1right = new Basket(new Posn(210, 365), 45);
    WorldImage ib = new FromFileImage("basket.png");
    Caught c = new Caught(b1.ctr);
    OnGround g = new OnGround();
    ILoApple l = new ConsLoApple(a1, new ConsLoApple(a2, 
            new ConsLoApple(a3, new MtLoApple())));
    MtLoApple m = new MtLoApple();
    AppleGame w = new AppleGame(0, 0, l, b1);
    
    // tests the methods used to get images and scenes
    boolean testImages(Tester t) {
        return t.checkExpect(this.a1.appleImage(), this.ia)
                && t.checkExpect(this.a2.appleImage(), this.ia)
                && t.checkExpect(this.b1.basketImage(), this.ib);
    }
    
    // test the methods that move the apple and basket 
    // as well as any additional functions
    // used in the Apple and Basket classes 
    boolean testMovement(Tester t) {
        return t.checkExpect(this.a1.moveDown(), this.a1down)
                && t.checkExpect(this.a1.fall(), this.a1down)
                && t.checkExpect(this.a2.onTheGround(), false)
                && t.checkExpect(this.a3.onTheGround(), true)
                && t.checkExpect(this.b1.moveOnKey("right"), this.b1right)
                && t.checkExpect(this.b1.moveOnKey("left"), this.b1left);
    }
    
    // tests the methods of ILoApple
    // as well as those that use ISelectApple
    boolean testList(Tester t) {
        return t.checkExpect(new ConsLoApple(this.a1, this.m).fall(), 
                new ConsLoApple(this.a1down, this.m))
                && t.checkExpect(this.l.filter(this.c), 
                        new ConsLoApple(this.a1, new ConsLoApple(this.a2, this.m)))
                && t.checkExpect(this.l.filter(this.g), 
                        new ConsLoApple(this.a1, new ConsLoApple(this.a2, this.m)))
                && t.checkExpect(this.m.filter(this.c), this.m)
                && t.checkExpect(this.l.anySuchApple(this.c), true)
                && t.checkExpect(this.l.anySuchApple(this.g), true)
                && t.checkExpect(this.m.anySuchApple(this.c), false)
                && t.checkExpect(new ConsLoApple(this.a1, this.m).anySuchApple(this.c), false)
                && t.checkExpect(this.l.makeScene(this.w.getEmptyScene()), 
                        this.w.getEmptyScene().placeImageXY(this.ia, this.a3.ctr.x, this.a3.ctr.y)
                        .placeImageXY(this.ia, this.a2.ctr.x, this.a2.ctr.y)
                        .placeImageXY(this.ia, this.a1.ctr.x, this.a1.ctr.y));
    }
    
    AppleGame a = new AppleGame();
    
    // tests the methods of AppleGame
    Boolean testBigbang(Tester t) {
        return /*t.checkExpect(a.onTick(), new AppleGame(a.caught, a.missed,
                a.appleList.fall(), a.basket))
                && t.checkExpect(a.onTick(), a.onTick())
                && */
                // cannot check for functions that modify the world
                // since no two instantiations of the world are equal
                t.checkExpect(this.a.bigBang(400, 400, 0.05), true);
    }
}
