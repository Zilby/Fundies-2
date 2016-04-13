import tester.*;

// runs tests for the buddies problem
public class ExamplesBuddies {
    Person Ann = new Person("Ann");
    Person Bob = new Person("Bob");
    Person Cole = new Person("Cole");
    Person Dan = new Person("Dan");
    Person Ed = new Person("Ed");
    Person Fay = new Person("Fay");
    Person Gabi = new Person("Gabi");
    Person Hank = new Person("Hank");
    Person Jan = new Person("Jan");
    Person Kim = new Person("Kim");
    Person Len = new Person("Len");
    
    void initData() {
        this.Ann.addBuddy(this.Bob);
        this.Ann.addBuddy(this.Cole);
        this.Bob.addBuddy(this.Ann);
        this.Bob.addBuddy(this.Ed);
        this.Bob.addBuddy(this.Hank);
        this.Cole.addBuddy(this.Dan);
        this.Dan.addBuddy(this.Cole);
        this.Ed.addBuddy(this.Fay);
        this.Fay.addBuddy(this.Ed);
        this.Fay.addBuddy(this.Gabi);
        this.Gabi.addBuddy(this.Ed);
        this.Gabi.addBuddy(this.Fay);
        this.Jan.addBuddy(this.Kim);
        this.Jan.addBuddy(this.Len);
        this.Kim.addBuddy(this.Jan);
        this.Kim.addBuddy(this.Len);
        this.Len.addBuddy(this.Jan);
        this.Len.addBuddy(this.Kim);
    }
    
    void testDirect(Tester t) {
        initData();
        t.checkExpect(this.Ann.hasDirectBuddy(this.Cole), true);
        t.checkExpect(this.Ann.hasDirectBuddy(this.Hank), false);
    }
    
    void testCount(Tester t) {
        initData();
        t.checkExpect(this.Kim.countCommonBuddies(this.Len), 1);
        t.checkExpect(this.Ann.countCommonBuddies(this.Bob), 0);
        t.checkExpect(this.Cole.countCommonBuddies(this.Dan), 0);
        t.checkExpect(this.Fay.countCommonBuddies(this.Gabi), 1);
    }
}