
// represents a Person with a user name and a list of buddies
class Person {

    String username;
    ILoBuddy buddies;

    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
    }

    // returns true if this Person has that as a direct buddy
    boolean hasDirectBuddy(Person that) {
        return this.buddies.in(that);
    }

    // returns the number of people who will show up at the party 
    // given by this person
    int partyCount(){
        return 1 + this.buddies.pc(new ConsLoBuddy(this, new MTLoBuddy()));
    }

    // returns the number of people that are direct buddies 
    // of both this and that person
    int countCommonBuddies(Person that) {
        return this.buddies.count(that.buddies);
    }

    // will the given person be invited to a party 
    // organized by this person?
    boolean hasDistantBuddy(Person that) {
        return this.hasDirectBuddy(that) || this.buddies.inAny(this, that);
    }
    
    void addBuddy(Person that) {
        this.buddies = new ConsLoBuddy(that, this.buddies);
    }

}
