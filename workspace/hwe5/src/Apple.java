import tester.*;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.Random;

interface ILoApple {
	ILoApple filter (ISelectApple a);
	ILoApple fall();
	boolean anySuchApple (ISelectApple a);
	WorldScene makeScene(WorldScene w);
}

//to represent a predicate for Apples
interface ISelectApple{

	// Return true if the given Apple
	// should be selected
	boolean apply(Apple a);
}

class Caught implements ISelectApple{
    Posn p;
    
    Caught(Posn p) {
        this.p = p;
    }
    
    public boolean apply(Apple a) {
        return (this.p.x >= a.ctr.x - a.radius) &&
                (this.p.x <= a.ctr.x + a.radius) &&
                (this.p.y >= a.ctr.y - a.radius) &&
                (this.p.y <= a.ctr.y + a.radius);
    }
}

class onGround implements ISelectApple{
    
    onGround() {
    }
    
    public boolean apply(Apple a) {
        return a.onTheGround();
    }
}

class MtLoApple implements ILoApple{
	MtLoApple(){}
	
	public boolean anySuchApple(ISelectApple a){
		return false;
	}
	
	public ILoApple filter(ISelectApple a){
		return new MtLoApple();
	}

	public ILoApple fall(){
		return new MtLoApple();
	}
	
	public WorldScene makeScene(WorldScene w){
		return w;
	}
}

class ConsLoApple implements ILoApple{
	Apple first;
	ILoApple rest;
	
	ConsLoApple(Apple first, ILoApple rest){
		this.first = first;
		this.rest = rest;
	}
	
	public boolean anySuchApple (ISelectApple a) {
        if (a.apply(this.first)) {
            return true;
        }
        else {
        	return this.rest.anySuchApple(a);
        }
    }
	
	public ILoApple filter(ISelectApple pick) {
        if (pick.apply(this.first)) {
        	return this.rest.filter(pick);
        }
        else {
        	return new ConsLoApple (this.first, this.rest.filter(pick));
        }
    }
	
	public ILoApple fall(){
		return new ConsLoApple(this.first.fall(), rest.fall());
	}
	
	public WorldScene makeScene(WorldScene w){
		return rest.makeScene(w)
				.placeImageXY(this.first.appleImage(), this.first.ctr.x, this.first.ctr.y);
	}
}

class Apple {
    Posn ctr;
    int radius;
    
    Apple(Posn ctr, int radius) {
        this.ctr = ctr;
        this.radius = radius;
    }
    
    WorldImage appleImage(){ 
            return new FromFileImage("small-red-apple.png");
    }
    
    public Apple moveDown() {
        return new Apple(new Posn (this.ctr.x, this.ctr.y + 4), this.radius);
    }
    
    public boolean onTheGround() {
       return (this.ctr.y + this.radius >= 400);
    }
    
    public Apple fall() {
        if (this.onTheGround()) {
            return new Apple (new Posn (new Random().nextInt(381) + 10, 
            		new Random().nextInt(90) + 10), this.radius);
        }
        else {
            return this.moveDown();
        }
    }
}

class Basket {
    Posn ctr;
    int radius;
    
    Basket(Posn ctr, int radius) {
        this.ctr = ctr;
        this.radius = radius;
    }
    
    WorldImage basketImage(){ 
        return new FromFileImage("basket.png");
    }	
      
    public Basket moveOnKey(String ke) {
        if (ke.equals("left") && (ctr.x >= 30)) {
            return new Basket(new Posn (this.ctr.x - 10, this.ctr.y), this.radius);
        }
        else if (ke.equals("right") && (ctr.x <= 370)) {
            return new Basket(new Posn (this.ctr.x + 10, this.ctr.y), this.radius);
        }
        else 
            return this;            
    }
}

class AppleGame extends World {
    int caught = 0;
    int missed = 0;
    int width = 400;
    int height = 400;
    //Apple apple = new Apple(new Posn (new Random().nextInt(381) + 10, 
    //		new Random().nextInt(90) + 10), 30);
    ILoApple appleList = new ConsLoApple(new Apple(new Posn (new Random().nextInt(381) + 10, 
    		new Random().nextInt(90) + 10), 30),
    		new ConsLoApple(new Apple(new Posn (new Random().nextInt(381) + 10, 
    	    		new Random().nextInt(90) + 10), 30),
    				new ConsLoApple(new Apple(new Posn (new Random().nextInt(381) + 10, 
    			    		new Random().nextInt(90) + 10), 30), new MtLoApple())));
    Basket basket = new Basket(new Posn(200,365), 45);
    
    AppleGame() {
    	super();
    }
    
    AppleGame(int caught, int missed, ILoApple appleList, Basket basket) {
    	super();
        this.caught = caught;
        this.missed = missed;
        this.appleList = appleList;
        this.basket = basket;
    }
    
    WorldImage background = 
            new FromFileImage("apple-tree.png");

    public WorldScene makeScene() {
        return  appleList.makeScene(this
        		.getEmptyScene()
                .placeImageXY(this.background, this.width / 2, this.height / 2)
                .placeImageXY(new TextImage("" + this.caught, 40, Color.blue), 370, 30)
                .placeImageXY(this.basket.basketImage(), this.basket.ctr.x, 
                		this.basket.ctr.y));
    }
    
    public boolean caughtApple() {
        return this.appleList.anySuchApple(new Caught(this.basket.ctr));
    }
    
    public AppleGame onTick() {
        if (this.caughtApple()) {
            return new AppleGame ((this.caught + 1), this.missed,
                    new ConsLoApple (new Apple (new Posn (new Random().nextInt(381) + 10, 
                    		new Random().nextInt(90) + 10), 30), 
                    		this.appleList.filter(new Caught(this.basket.ctr)).fall()),
                    this.basket);
        }else if (this.appleList.anySuchApple(new onGround())){
        	return new AppleGame (this.caught, this.missed + 1, 
        			new ConsLoApple (new Apple (new Posn (new Random().nextInt(381) + 10, 
                    		new Random().nextInt(90) + 10), 30), 
                    		this.appleList.filter(new onGround()).fall()),
                    this.basket);
        }
        else {
            return new AppleGame (this.caught, this.missed, 
            		this.appleList.fall(),
                    this.basket);
        }
    }
    
    public AppleGame onKeyEvent(String ke) {
        return new AppleGame (this.caught, this.missed, this.appleList, this.basket.moveOnKey(ke));
    }
    
    public WorldEnd worldEnds() {
    	if (this.caught >= 10){
    		return new WorldEnd(true, this
                .getEmptyScene()
                .placeImageXY(this.background, this.width / 2, this.height / 2)
                .placeImageXY(new TextImage("You Win! Missed: " + this.missed, 40, Color.blue),200,340));
    				
    	}else{
    	return new WorldEnd(false, this.makeScene());
    	}
    }
}

class AppleExamples {

	AppleGame a1 = new AppleGame();
	boolean runAnimation = this.a1.bigBang(400, 400, 0.05);
	
	//Still need tester functions for the individual functions

}
