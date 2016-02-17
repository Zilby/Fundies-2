
// Represents a nonempty list of ImageFiles
public class ConsLoIF implements ILoIF {

    public ImageFile first;
    public ILoIF rest;

    public ConsLoIF(ImageFile first, ILoIF rest) {
        this.first=first;
        this.rest=rest;
    }

    // Does this nonempty list contain that ImageFile   
    public boolean contains(ImageFile that) { 
        return (this.first.sameImageFile(that) ||
                this.rest.contains(that));
    } 
    public ILoIF filter(ISelectImageFile pick) {
        if (pick.apply(this.first)) {
            return new ConsLoIF (this.first, this.rest.filter(pick));
        }
        else {
            return this.rest.filter(pick);
        }
    }
    
    public boolean allSmallerThan40000(SmallImageFile file) {
        if (file.apply(this.first)) {
            return this.rest.allSmallerThan40000(file);
        }
        else {
            return false;
        }
    }
    
    public boolean allNamesShorterThan4(NameShorterThan4 file) {
        if (file.apply(this.first)) {
            return this.rest.allNamesShorterThan4(file);
        }
        else {
            return false;
        }
    }
    
    public boolean allSuchImageFile (ISelectImageFile file) {
        if (file.apply(this.first)) {
            return this.rest.allSuchImageFile(file);
        }
        else {
            return false;
        }
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
