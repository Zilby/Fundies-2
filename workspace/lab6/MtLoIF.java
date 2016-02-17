
// Represents an empty list of ImageFiles
class MtLoIF implements ILoIF {

    MtLoIF() { }

    // Does this empty list contain that ImageFile   
    public boolean contains(ImageFile that) { 
        return false;
    }
    public ILoIF filter(ISelectImageFile pick) {
        return new MtLoIF();
    }
    
    public boolean allSmallerThan40000(SmallImageFile file) {
        return true;
    }

    public boolean allNamesShorterThan4(NameShorterThan4 file) {
        return true;
    }

    public boolean allSuchImageFile (ISelectImageFile file) {
        return true;
    }

    
    
    
    public ILoIF filterSmallerThan40000() {
        return new MtLoIF();
    }
    public ILoIF filterNamesShorterThan4 () {
        return new MtLoIF();        
    }
    public int countSmallerThan40000() {
        return 0;
    }
} 
