import java.util.*;
import java.io.*;
public class l3{

    // to represent a book in a bookstore
    class Book {
	String title;
	Author author;
	int price;
 
	// the constructor
	Book(String title, Author author, int price) {
	    this.title = title;
	    this.author = author;
	    this.price = price;
	}
    }
 
    // to represent a author of a book in a bookstore
    class Author {
	String name;
	int yob;
 
	// the constructor
	Author(String name, int yob) {
	    this.name = name;
	    this.yob = yob;
	}
    }

    // examples and tests for the classes that represent
    // books and authors
    class ExamplesBooks{
	ExamplesBooks() {}
	
	Author pat = new Author("Pat Conroy", 1948);
	Book beaches = new Book("Beaches", this.pat, 20);
    }
    
    /*
      Fields:
      this.title... String
      this.author.. Author
      this.price... int
      Methods:
      this.salePrice(int)...  int
      this.sameAuthor(Book).. boolean

      Methods for fields. 
      this.author.??

      Author
      ------
      Fields:
      this.name... String
      this.yob.... int
      Methods:
      this.sameAuthor(Author)... boolean
    */

    //is this Author the same as that one?
    boolean sameAuthor(Author that){
	return
	    this.name.equals(that.name) &&
	    this.yob==that.yob;
    }


    boolean testSame(Tester t){
	return
	    t.checkExpect(this.htdp.sameAuthor(this.htdc),
			  true) &&
	    t.checkExpect(this.htdp.sameAuthor(this.pride),
			  false) &&
	    t.checkExpect(this.jane.sameAuthor(this.fellieson),
			  false);
    }
}
