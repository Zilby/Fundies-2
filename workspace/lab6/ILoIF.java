/*
 *         +----------------------------------+
 *         |               ILoIF              |
 *         +----------------------------------+
 *         | boolean contains(ImageFile that) |
 *         +--------------+-------------------+
 *                       / \
 *                      +---+
 *                        |
 *          +-------------+-----------+
 *          |                         |
 *  +---------------+        +-------------------+
 *  |     MTLoIF    |        |     ConsLoIF      |
 *  +---------------+        +-------------------+
 *  +---------------+     +--| ImageFile  first  |
 *                        |  | ILoIF      rest   |
 *                        |  +-------------------+
 *                        v
 *               +---------------+
 *               | ImageFile     |
 *               +---------------+
 *               | String name   |
 *               | int    width  |
 *               | int    height |
 *               | String kind   |
 *               +---------------+
 */

// Represents a list of ImageFiles
public interface ILoIF {

  // Does this list contain that ImageFile
  public boolean contains(ImageFile that);
  public ILoIF filter(ISelectImageFile pick);
  public boolean allSmallerThan40000(SmallImageFile file);
  public boolean allNamesShorterThan4(NameShorterThan4 file);
  public boolean allSuchImageFile (ISelectImageFile file);

  
  public ILoIF filterSmallerThan40000();
  public ILoIF filterNamesShorterThan4 ();
  public int countSmallerThan40000();
}
