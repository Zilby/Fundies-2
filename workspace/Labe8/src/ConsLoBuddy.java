// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

    Person first;
    ILoBuddy rest;

    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }
    
    public boolean in(Person that) {
        if (this.first.equals(that)) {
            return true;
        }
        else {
            return this.rest.in(that);
        }
    }

    public int count(ILoBuddy that) {
        if (that.in(this.first)) {
            return 1 + this.rest.count(that);
        }
        else {
            return this.rest.count(that);
        }
    }
    
    public boolean inAny (Person initial, Person that) {
        if (this.first.equals(that)) {
            return true;
        }
        else if (this.first.hasDirectBuddy(initial)){
            return this.rest.inAny(initial, that) || 
                    this.first.hasDirectBuddy(that) || 
                    this.first.buddies.remove(initial).inAny(this.first, that);
        }
        else {
            return this.rest.inAny(initial, that) ||
                    this.first.hasDistantBuddy(that);
        }
    }
    
    public ILoBuddy remove(Person that) {
        if (this.first.equals(that)){
            return this.rest;
        }
        else {
            return new ConsLoBuddy(this.first, this.rest.remove(that));
        }
    }
    
    public int pc(ConsLoBuddy invited) {
        if (invited.in(this.first)) {
            return this.rest.pc(invited);
        }
        else {
            return 1 + (this.rest.pc(new ConsLoBuddy(this.first, invited)))
                    + this.first.buddies.pc(new ConsLoBuddy(this.first, invited));
        }
    }
}
