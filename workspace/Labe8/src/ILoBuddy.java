
// represents a list of Person's buddies
interface ILoBuddy {
    boolean in(Person that);
    int count(ILoBuddy that);
    boolean inAny(Person initial, Person that);
    ILoBuddy remove(Person that);
    int pc(ConsLoBuddy invited);
}
