// Assignment 4
// Nameth Kyle    
// kname5     
// Zilbersher Alexander  
// zilby

import tester.*;

// to represent different files in a computer
interface IFile {
    
    // compute the size of this file
    int size();
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    int downloadTime(int rate);
    
    // returns the owner of the file
    String getOwner();
    
    // is the owner of this file the same 
    // as the owner of the given file?
    boolean sameOwner(IFile that);
}

// creates the abstract class for a file
abstract class AFile implements IFile {
    String name;
    String owner;
    
    AFile(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }
    
    // all size computations are different, therefore we must call size
    // abstract and write the computations specifically in each class
    public abstract int size();
    
    // all download times computations are the same, therefore we can create
    // the proper function in this abstract class and have all classes
    // implement the function. This method will divide the size by the given
    // rate (in bytes per second) and will produce the download time. 
    public int downloadTime(int rate) {
        return ((this.size() + 9) / rate);
    }
    
    // returns the owner of the file for each of the file types
    public String getOwner() {
        return this.owner;
    }
    
    // is the owner of this file the same as the owner of the given file? 
    // Since the computation can be done in the same manner for all file types, 
    // we can put the method directly in this abstract class. 
    public boolean sameOwner(IFile that) {
        return (this.owner.equals(that.getOwner()));
    }
       
}

// to represent a text file
class TextFile extends AFile {
    int length;   // in bytes
    
    TextFile(String name, String owner, int length) {
        super(name, owner);
        this.length = length;
    }
    
    // compute the size of this file
    public int size() {
        return this.length;
    }  
    
}

//to represent an image file
class ImageFile extends AFile { 
    int width;   // in pixels
    int height;  // in pixels
    
    ImageFile(String name, String owner, int width, int height) {
        super(name, owner);
        this.width = width;
        this.height = height;
    }
    
    // compute the size of this file
    public int size() {
        return this.width * this.height;
    }  
}


//to represent an audio file
class AudioFile extends AFile {
    int speed;   // in bytes per second
    int length;  // in seconds of recording time
    
    AudioFile(String name, String owner, int speed, int length) {
        super(name, owner);
        this.speed = speed;
        this.length = length;
    }
    
    // compute the size of this file
    public int size() {
        return this.speed * this.length;
    }  
}

class ExamplesFiles {
    
    IFile text1 = new TextFile("English paper", "Maria", 1234);
    IFile picture = new ImageFile("Beach", "Maria", 400, 200);
    IFile song = new AudioFile("Help", "Pat", 200, 120);
    IFile text2 = new TextFile("Science Report", "Steve", 2400);
    IFile graph = new ImageFile("Profits", "Dan", 300, 500);
    IFile song2 = new AudioFile("Thunderstruck", "AC/DC", 170, 200);
    
    // test the method size for the classes that represent files
    boolean testSize(Tester t) {
        return
                t.checkExpect(this.text1.size(), 1234) &&
                t.checkExpect(this.picture.size(), 80000) &&
                t.checkExpect(this.song.size(), 24000) &&
                t.checkExpect(this.text2.size(), 2400) &&
                t.checkExpect(this.graph.size(), 150000) &&
                t.checkExpect(this.song2.size(), 34000);
    }
    
    // test the download time method
    boolean testdownloadTime(Tester t) {
        return
                t.checkExpect(this.text1.downloadTime(10), 124) &&
                t.checkExpect(this.picture.downloadTime(100), 800) &&
                t.checkExpect(this.song.downloadTime(240), 100) &&
                t.checkExpect(this.text2.downloadTime(15), 160) &&
                t.checkExpect(this.graph.downloadTime(3000), 50) &&
                t.checkExpect(this.song2.downloadTime(400), 85);              
    }
    
    // tests the helper method
    boolean testgetOwner(Tester t) {
        return
                t.checkExpect(this.text1.getOwner(), "Maria") &&
                t.checkExpect(this.picture.getOwner(), "Maria") &&
                t.checkExpect(this.song.getOwner(), "Pat") &&
                t.checkExpect(this.text2.getOwner(), "Steve") &&
                t.checkExpect(this.graph.getOwner(), "Dan") &&
                t.checkExpect(this.song2.getOwner(), "AC/DC");
    }
    
    // test the same owner method
    boolean testsameOwner(Tester t) {
        return
                t.checkExpect(this.text1.sameOwner(this.picture), true) &&
                t.checkExpect(this.text1.sameOwner(this.song2), false) &&
                t.checkExpect(this.text2.sameOwner(this.graph), false);
    }
}