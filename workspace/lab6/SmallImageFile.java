
public class SmallImageFile implements ISelectImageFile {
    /* Return true if the given ImageFile is smaller than 40000 */
    public boolean apply(ImageFile f) {
      return f.height * f.width < 40000;
    }
}
