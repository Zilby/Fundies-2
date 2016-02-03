// CS 2510 Fall 2014
// Assignment 3

//import tester.*;

// to represent a list of Strings
interface ILoString {
    // combine all Strings in this list into one
    String combine();
    String getFirst();
    ILoString getRest();
    ILoString sort();
    ILoString insert(String s);
    boolean isSorted();
    boolean ended();
    ILoString interleave(ILoString l);
    ILoString merge(ILoString l);
    ILoString reverse();
    ILoString reverseInsert(String s);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString(){}
    
    // combine all Strings in this list into one
    public String combine() {
       return "";
    }

    public String getFirst(){
	return "";
    }

    public ILoString getRest(){
	return this;
    }
    
    public ILoString sort(){
	return this;
    }

    public ILoString insert(String s){
	return new ConsLoString(s, this);
    }

    public boolean isSorted(){
	return true;
    }

    public boolean ended(){
	return true;
    }

    public ILoString interleave(ILoString l){
	return l;
    }

    public ILoString merge(ILoString l){
	return l;
    }

    public ILoString reverse(){
	return this;
    }

    public ILoString reverseInsert(String s){
	return new ConsLoString(s, this);
    }
	
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    
    ConsLoString(String first, ILoString rest){
        this.first = first;
        this.rest = rest;  
    }
    
    // combine all Strings in this list into one
    public String combine(){
        return this.first.concat(this.rest.combine());
    }

    public String getFirst(){
	return this.first;
    }

    public ILoString getRest(){
	return this.rest;
    }

    public ILoString sort(){
	return this.rest.sort().insert(this.first);
    }

    public ILoString insert(String s){
	if (this.first.toLowerCase().compareTo(s.toLowerCase()) < 0) {
	    return new ConsLoString(this.first, this.rest.insert(s));
	}
	else {
	    return new ConsLoString(s, this);
	}
    }

    public boolean ended(){
	return false;
    }
    
    public boolean isSorted(){    
	if ((!this.rest.ended()) &&
	    (this.first.toLowerCase().compareTo(this.rest.getFirst().toLowerCase()) > 0)) {
	    return false;
	}else{
	    return this.rest.isSorted();
	}
    }

    public ILoString interleave(ILoString l){
	if (!l.ended()){
	    this.rest = new ConsLoString(l.getFirst(), this.rest.interleave(l.getRest()));
	} return this;
    }

    public ILoString merge(ILoString l){
	return this.rest.merge(l.insert(this.first));
    }

    public ILoString reverse(){
	return this.rest.reverse().reverseInsert(this.first);
    }

    public ILoString reverseInsert(String s){
	return new ConsLoString(this.first, this.rest.reverseInsert(s));
    }
    
}

// to represent examples for lists of strings

class Driver{
    public static void main(String[] args){
    
	ILoString mary = new ConsLoString("Mary ",
					  new ConsLoString("had ",
							   new ConsLoString("a ",
									    new ConsLoString("little ",
											     new ConsLoString("lamb.", new MtLoString())))));

	ILoString joseph = new ConsLoString("jj ",
					  new ConsLoString("ate ",
							   new ConsLoString("bagels ", new MtLoString())));
	
	//System.out.println(mary.sort().combine());
	//System.out.println(mary.isSorted());
	//System.out.println(mary.sort().isSorted());
	//System.out.println(mary.interleave(joseph).combine());
	//System.out.println(joseph.interleave(mary).combine());
	//System.out.println(mary.sort().merge(joseph.sort()).combine());
	//System.out.println(mary.reverse().combine());
    }
    // test the method combine for the lists of Strings
    /*boolean testCombine(Tester t){
        return 
            t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
    }
    */
}

