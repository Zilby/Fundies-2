
public class NameShorterThan4 implements ISelectImageFile {
    public boolean apply(ImageFile f) {
        return f.name.length() < 4;
      }
}
