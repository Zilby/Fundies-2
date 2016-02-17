import tester.*;  

// Test the use of function objects with lists of ImageFile-s
public class ExamplesImageFile {

    public ExamplesImageFile() { }

    // Sample data to be used for all tests
    public ImageFile img1 = new ImageFile("dog", 300, 200, "jpg");
    public ImageFile img2 = new ImageFile("cat", 200, 200, "png");
    public ImageFile img3 = new ImageFile("bird", 250, 200, "jpg");
    public ImageFile img4 = new ImageFile("horse", 300, 300, "giff");
    public ImageFile img5 = new ImageFile("goat", 100, 200, "giff");
    public ImageFile img6 = new ImageFile("cow", 150, 200, "jpg");
    public ImageFile img7 = new ImageFile("snake", 200, 300, "jpg");

    //empty list
    public ILoIF mt= new MtLoIF();

    // ImageFile list -- all Images
    public ILoIF imglistall = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img2,
                new ConsLoIF(this.img3, 
                    new ConsLoIF(this.img4, 
                        new ConsLoIF(this.img5, 
                            new ConsLoIF(this.img6, 
                                new ConsLoIF(this.img7, this.mt))))))); 

    // ImageFile list - short names (less than 4 characters)
    public ILoIF imglistshortnames = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img2, 
                new ConsLoIF(this.img6, this.mt))); 

    // ImageFile list - small size (< 40000)
    public ILoIF imglistsmall = 
        new ConsLoIF(this.img5, 
            new ConsLoIF(this.img6, this.mt));

    // ImageFile list - small size (< 40000)
    public ILoIF imglistsmall2 = 
        new ConsLoIF(this.img5, 
            new ConsLoIF(this.img6, this.mt));

    // ImageFile list -- large images
    public ILoIF imglistlarge = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img2,
                new ConsLoIF(this.img3, this.mt))); 
    
    // ImageFile list - jpg files
    public ILoIF imglistjpgs = 
        new ConsLoIF(this.img1, 
            new ConsLoIF(this.img3, 
                new ConsLoIF(this.img6, 
                        new ConsLoIF(this.img7, this.mt)))); 
    
 // ImageFile list - giff
    public ILoIF imglistgif = 
        new ConsLoIF(this.img4, 
            new ConsLoIF(this.img5, this.mt));

    // A sample test method
    public boolean testSize(Tester t){
        return (t.checkExpect(this.img1.size(), 60000) &&
                t.checkExpect(this.img2.size(), 40000));
    }

    // A sample test method
    public boolean testContains(Tester t){
        return (t.checkExpect(this.imglistsmall.contains(this.img3), false) &&
                t.checkExpect(this.imglistsmall.contains(this.img6), true));
    }   
     
    ISelectImageFile smallFiles = new SmallImageFile();
    ISelectImageFile FourFiles = new NameShorterThan4();
    ISelectImageFile givenFiles = new GivenKind(img4);
     
    // test the method filter on small image files
    boolean testFilter(Tester t){
      return
      t.checkExpect(mt.filter(this.smallFiles),
                    this.mt) &&
      t.checkExpect(imglistall.filter(this.smallFiles),
                    this.imglistsmall);
    }
    
    boolean testAllSuch(Tester t) {
        return
                t.checkExpect(imglistshortnames.allSuchImageFile(this.FourFiles), 
                              true) &&
                /*t.checkExpect(imglistjpgs.allSuchImageFile(this.givenFiles), 
                              true) &&
                t.checkExpect(imglistgif.allSuchImageFile(this.givenFiles), 
                              true); */
                t.checkExpect (img1.kind.equals(img7.kind), true);
    }
}