interface IBook{
    int daysOverdue(int today);
    boolean isOverdue(int today);
    double computeFine(int today);
}

abstract class ABook implements IBook{
    String title;
    int dayTaken;
    ABook(String title, int dayTaken){
	this.title = title;
	this.dayTaken = dayTaken;
    }
    public int daysOverdue(int today){
	return (today - this.dayTaken - 14 );
    }
    public boolean isOverdue(int today){
	return (this.daysOverdue(today) > 0);
    }
    public double computeFine(int today){
	if(this.daysOverdue(today)>0){
	    return (.1 * this.daysOverdue(today));
	}else{
	    return 0;
	}
    }
}

class Book extends ABook{
    String author;
    Book(String title, int dayTaken, String author){
	super(title, dayTaken);
	this.author=author;
    }			   
}

class RefBook extends ABook{
    RefBook(String title, int dayTaken){
	super(title, dayTaken);
    }
    public int daysOverdue(int today){
	return (today - this.dayTaken - 2);
    }
}

class AudioBook extends ABook{
    String author;
    AudioBook(String title, int dayTaken, String author){
	super(title, dayTaken);
	this.author=author;
    }
    public double computeFine(int today){
	if(this.daysOverdue(today)>0){
	    return (.2 * this.daysOverdue(today));
	}else{
	    return 0;
	}
    }
}

class Driver{
    public static void main(String[] args){
	IBook b = new Book("Moby Dick", 5555, "Some dude");
	IBook r = new RefBook("CS 101", 5667);
	IBook a = new AudioBook("Fun Times with Morgan Freeman", 6558, "Zilby");
	System.out.println(b.daysOverdue(6555));
	System.out.println(r.daysOverdue(6667));
	System.out.println(a.daysOverdue(6658));
	System.out.println(b.isOverdue(6555));
	System.out.println(r.isOverdue(4667));
	System.out.println(a.isOverdue(6658));
	System.out.printf("%.1f\n", b.computeFine(6555));
	System.out.printf("%.1f\n", r.computeFine(4667));
	System.out.printf("%.1f\n", a.computeFine(6658));
    }
}
