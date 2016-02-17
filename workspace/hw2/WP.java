import tester.*;

// to represent one item
interface IItem {
    
    // to represent the size of an image
    int imageSize();

    // to represent the length of the text
    int textLength();

    // to represent the string list of image names and types
    String images();
}

//to represent a Text
class Text implements IItem {
    
    // represents the contents of a text
    String contents;

    Text(String contents) {
        this.contents = contents;
    }

    // returns zero for the size of an image
    public int imageSize() {
        return 0;
    }

    // returns the string length of the contents
    public int textLength() {
        return this.contents.length();
    }

    // returns the string for image filename and type (nul)
    public String images() {
        return "";
    }
}

//to represent an Image
class Image implements IItem {
    
    // represents the filename of an Image
    String filename;

    // represents the file size of an Image
    int size;

    // represents the .filetype of an Image
    String filetype;

    Image(String filename, int size, String filetype) {
        this.filename = filename;
        this.size = size;
        this.filetype = filetype;
    }

    // returns the size of the image
    public int imageSize() {
        return this.size;
    }

    // returns the string length of the filename and the file type
    public int textLength() {
        return (this.filename.length() + this.filetype.length());
    }

    // returns the filename and filetype strings in one list
    public String images() {
        return (this.filename + "." + this.filetype);
    }
}

//to represent a Link
class Link implements IItem {

    // represents the name of a Link
    String name;

    // represents the WebPage the Link links to
    WebPage page;

    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }

    // returns the size of all images on the webpage
    public int imageSize() {
        return this.page.totalImageSize();
    }

    // returns the string length of the name of the link and the text on webpage
    public int textLength() {
        return this.name.length() + this.page.textLength();
    }

    // returns the list of image names and types of the webpage
    public String images() {
        return this.page.images();
    }
}

//to represent a List of Items
interface ILoItem {

    // to represent the total size of all Images
    int totalImageSize();

    // to represent the total length of all text
    int textLength();

    // to represent the Sting of filenames and types
    String images();
}

//to represent an empty list of items
class MtLoItem implements ILoItem {

    // returns zero for the length of a size of an empty list
    public int totalImageSize() {
        return 0;
    }

    // returns zero for the length of an empty list
    public int textLength() {
        return 0;
    }

    // returns an empty string for the empty list
    public String images() {
        return "";
    }
}

//to represent a list of items
class ConsLoItem implements ILoItem {
  

    // returns the size of the first image in a list plus the size
    // of the rest of the images in that list
    public int totalImageSize() {
        return this.first.imageSize() + this.rest.totalImageSize();
    }

    // returns the length of the first piece of text in a list plus
    // the length of the rest of the text in that list
    public int textLength() {
        return (this.first.textLength() + this.rest.textLength());
    }

    // returns the String of the first image's filename and filetype with the
    // rest of the image's filenames and types
    public String images() {
        
        if (this.rest.images().equals("")) {
            return this.first.images() + this.rest.images();
        } 
        else if (this.first.images().equals("")) {
            return this.rest.images(); 
        } 
        else {
            return (this.first.images() + ", " + this.rest.images());
        }
    }

    // represents the first item in the ConsLoItem
    IItem first;

    // represents the rest of the items in the ConsLoItem
    ILoItem rest;

    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }
}

//to represent a webpage
class WebPage {

    // represents the webpage's url
    String url;

    // represents the title of the webpage
    String title;

    //represents the contents of the webpage
    ILoItem items;

    WebPage(String url, String title, ILoItem items) {
        this.url = url;
        this.title = title;
        this.items = items;
    }

    // calculates the total image size of everything in the list of Items
    int totalImageSize() {
        return this.items.totalImageSize();
    }

    // calculates the total string length of the url and title of
    // the website plus the text of everything in the list of Items
    int textLength() {
        return this.title.length() 
                + this.items.textLength();
    }

    // returns the list of filename and filetype for images in the list of items
    String images() {
        return this.items.images();
    }
}

/*
 * A web site with at least one text, two images, three pages and four links
 * 
 */

// contains all examples for the program
class ExamplesWebPage {

    // defines the items used in the webpages    
    IItem htdptxt = new Text("How to Design Programs");
    IItem htdpimg = new Image("htdp", 4300, "tiff");

    // builds the list of items for the HtDP webpage
    ILoItem mtlist = new MtLoItem();
    ILoItem htdplisthalf = new ConsLoItem(this.htdpimg, this.mtlist);
    ILoItem htdplist = new ConsLoItem(this.htdptxt, this.htdplisthalf);
    WebPage htdp = new WebPage("htdp.org", "HtDP", this.htdplist);
    
    
    // builds the list of items for the OOD webpage
    IItem backtofuture = new Link("Back to the Future", this.htdp);
    IItem oodtxt = new Text("Stay classy, Java");    
    ILoItem oodlista = new ConsLoItem(this.backtofuture, this.mtlist);
    ILoItem oodlist = new ConsLoItem(this.oodtxt, this.oodlista);    
    WebPage ood = new WebPage("ccs.neu.edu/OOD", "OOD", this.oodlist);

    
    // builds the list of items for the Fundies webpage
    IItem hsh = new Text("Home sweet home");
    IItem thestaff = new Text("The staff");
    IItem lab = new Image("wvh-lab", 400, "png");
    IItem profs = new Image("profs", 240, "jpeg");
    IItem lookback = new Link("A Look Back", this.htdp);
    IItem lookforward = new Link("A Look Ahead", this.ood);
    
    
    ILoItem funlist0 = new MtLoItem();
    ILoItem funlista = new ConsLoItem(this.lookforward, funlist0);
    ILoItem funlistb = new ConsLoItem(this.lookback, this.funlista);
    ILoItem funlistc = new ConsLoItem(this.profs, this.funlistb);
    ILoItem funlistd = new ConsLoItem(this.thestaff, this.funlistc);
    ILoItem funliste = new ConsLoItem(this.lab, this.funlistd);
    ILoItem funlist = new ConsLoItem(this.hsh, this.funliste);

    // builds the webpage for Fundies
    WebPage fundiesWP = 
            new WebPage("ccs.neu.edu/Fundies2", "Fundies II", this.funlist);


   // tests for the method totalImageSize
    boolean testtotalImageSize(Tester t) {
        return t.checkExpect(this.mtlist.totalImageSize(), 0) &&
           t.checkExpect(this.ood.totalImageSize(), 4300) &&
           t.checkExpect(this.htdp.totalImageSize(), 4300) &&
           t.checkExpect(this.fundiesWP.totalImageSize(), 9240);
    }

    // tests for the method textLength
    boolean testtextLength(Tester t) {
        return t.checkExpect(this.mtlist.textLength(), 0) &&
           t.checkExpect(this.hsh.textLength(), 15) &&
           t.checkExpect(this.lab.textLength(), 10) &&
           t.checkExpect(this.htdpimg.textLength(), 8) &&
           t.checkExpect(this.htdptxt.textLength(), 22) &&
           t.checkExpect(this.htdplist.textLength(), 30) &&
           t.checkExpect(this.fundiesWP.textLength(), 182);
    }

    // tests for the method images
    boolean testimage(Tester t) {
        return t.checkExpect(this.lab.images(), "wvh-lab.png") &&
               t.checkExpect(this.funliste.images(), 
                        "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff");
    }
}
