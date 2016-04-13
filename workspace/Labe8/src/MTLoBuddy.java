
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
    MTLoBuddy() {}

    public boolean in(Person that) {
        return false;
    }
    
    public int count(ILoBuddy that) {
        return 0;
    }
    
    public boolean inAny(Person initial, Person that) {
        return false;
    }
    
    public ILoBuddy remove(Person that) {
        return this;
    }
    
    public int pc(ConsLoBuddy invited) {
        return 0;
    }
}
