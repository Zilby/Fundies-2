// CS 2510 Fall 2014
// Assignment 3

//import tester.*;

// to represent a list of Strings
interface ILoString {
    // combine all Strings in this list into one
    String combine();
    
    // gets the string value of this ILoString (if any)
    String getFirst();
    
    // gets the next ILoString in this ILoString (if any)
    ILoString getRest();
    
    // sorts the list of Strings alphabetically,
    // treats all Strings as lowercase
    ILoString sort();
    
    // inserts a String into a list, sorting it
    // alphabetically
    ILoString insert(String s);
    
    // determines whether a list is ordered alphabetically,
    // treats all Strings as lowercase
    boolean isSorted();
    
    // returns whether or not this ILoString is the end of
    // the list
    boolean ended();
    
    // takes an ILoString and produces a new ILoString where
    // the first, third, fifth... elements are from this list,
    // and the second, fourth, sixth... elements are from the given list.
    ILoString interleave(ILoString l);
    
    // takes two sorted ILoStrings and combines them into
    // one sorted ILoString
    ILoString merge(ILoString l);
    
    // reverses the order of the Strings in this ILoString
    ILoString reverse();
    
    // assists the reverse() function by inserting a given
    // String into the back of the list
    ILoString reverseInsert(String s);
    
    // determines whether an ILoString is composed only of pairs of Strings
    boolean isDoubledList();
    
    // determines whether an ILoString is the same when observed from the
    // front or back of the list
    boolean isPalindrome();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString(){}
    
    // combine all Strings in this list into one
    public String combine() {
       return "";
    }

    // gets the string value of this ILoString (if any)
    public String getFirst(){
	return "";
    }

    // gets the next value in this ILoString (if any)
    public ILoString getRest(){
	return this;
    }

    // sorts the list of Strings alphabetically,
    // treats all Strings as lowercase
    public ILoString sort(){
	return this;
    }

    // inserts a String into a list, sorting it
    // alphabetically
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

    public boolean isDoubledList(){
	return true;
    }

    public boolean isPalindrome(){
	return true;
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

    // gets the string value of this ILoString (if any)
    public String getFirst(){
	return this.first;
    }

    // gets the next ILoString in this ILoString (if any)
    public ILoString getRest(){
	return this.rest;
    }

    // sorts the list of Strings alphabetically,
    // treats all Strings as lowercase
    public ILoString sort(){
	return this.rest.sort().insert(this.first);
    }

    // inserts a String into a list, sorting it
    // alphabetically
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

    public boolean isDoubledList(){
	if (this.first.toLowerCase().compareTo(this.rest.getFirst().toLowerCase()) == 0) {
	    return this.rest.getRest().isDoubledList();
	}else{
	    return false;
	}
    }

    public boolean isPalindrome(){
	return this.interleave(this.reverse()).isDoubledList();
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

	ILoString doubleTrue = new ConsLoString("la ",
				 new ConsLoString("la ",
				   new ConsLoString("de ",
				     new ConsLoString("de ",
				       new ConsLoString("da ",
					 new ConsLoString("da ", new MtLoString()))))));

	ILoString doubleFalse = new ConsLoString("la ",
				  new ConsLoString("la ",
				    new ConsLoString("de ",
				      new ConsLoString("de ",
					new ConsLoString("da ", new MtLoString())))));

	ILoString p1 = new ConsLoString("la ",
			 new ConsLoString("de ",
		       	   new ConsLoString("da ",
	       		     new ConsLoString("da ",
       			       new ConsLoString("de ",
				 new ConsLoString("la ", new MtLoString()))))));

	ILoString p2 = new ConsLoString("la ",
			 new ConsLoString("de ",
		       	   new ConsLoString("da ",
	       		     new ConsLoString("de ",
       			       new ConsLoString("la ", new MtLoString())))));
	
	//System.out.println(mary.sort().combine());
	//System.out.println(mary.isSorted());
	//System.out.println(mary.sort().isSorted());
	//System.out.println(mary.interleave(joseph).combine());
	//System.out.println(joseph.interleave(mary).combine());
	//System.out.println(mary.sort().merge(joseph.sort()).combine());
	//System.out.println(mary.reverse().combine());
	//System.out.println(doubleTrue.isDoubledList());
	//System.out.println(mary.isDoubledList());
	//System.out.println(doubleFalse.isDoubledList());
	//System.out.println(mary.isPalindrome());
	//System.out.println(p1.isPalindrome());
	//System.out.println(p2.isPalindrome());
    }
    // test the method combine for the lists of Strings
    /*boolean testCombine(Tester t){
        return 
            t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
    }
    */
}

