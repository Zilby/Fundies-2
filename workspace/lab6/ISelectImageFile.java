// to represent a predicate for ImageFile-s
interface ISelectImageFile{
 
  // Return true if the given ImageFile
  // should be selected
  boolean apply(ImageFile f);
}