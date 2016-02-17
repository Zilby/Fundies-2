
public class GivenKind implements ISelectImageFile{
    ImageFile kind;
    
    GivenKind(ImageFile k) {
        this.kind = k;
    }
    
    public boolean apply(ImageFile f) {
        return this.kind.equals(f.kind);
      }
}
