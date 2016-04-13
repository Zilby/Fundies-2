import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

interface IPred<T> {
    boolean apply(T t);
}

class Utilities {
    <T> ArrayList<T> filter(ArrayList<T> arr, IPred<T> pred) {
        ArrayList<T> temp = new ArrayList<T>();
        for (T t: arr) {
            if (pred.apply(t)) {
                temp.add(t);
            }
        }
        arr=temp;
        return arr;
    }
}

//Represents an individual tile
class Tile {
    Tile(int value) {
        this.value=value;
    }
    // The number on the tile.  Use 0 to represent the hole
    int value; 
    // Draws this tile onto the background at the specified logical coordinates
    WorldImage drawAt(int col, int row, WorldImage background) { 
        if (value != 0) {
            Posn p = new Posn((col*30) + 15, (row*30) + 15);
            background.movePinholeTo(p);
            return new OverlayImage(new OverlayImage(new TextImage(value + "", Color.black), 
                    new RectangleImage(28, 28, "solid", Color.green)), background);
        }
        return background;
    }
}

class FifteenGame extends World {
    int width = 120;
    int height = 120;
    ArrayList<ArrayList<Tile>> tiles;
    FifteenGame() {
        super();
        tiles = buildTiles();
        
    }
// represents the rows of tiles
    ArrayList<ArrayList<Tile>> buildTiles() {
        ArrayList<ArrayList<Tile>> t = new ArrayList<ArrayList<Tile>>();
        int value = 0;
        for (int i=0 ; i<4 ; i++) {
            t.add(new ArrayList<Tile>());
        }
        for (ArrayList<Tile> a : t) {
            for (int i=0 ; i<4 ; i++) {
                a.add(new Tile(value));
                value++;
            }
        }
        return t;
    }
// draws the game
    WorldImage back = new EmptyImage();
    
    public WorldImage makeImage() { 
        int col = 0;
        int row = 0;
        for (ArrayList<Tile> a : tiles) {
            for (Tile t : a) {
                back = t.drawAt(col, row, back);
                row++;
            }
            col++;
        }
        return back;
    }
// handles keystrokes
    public void onKeyEvent(String k) {
 // needs to handle up, down, left, right to move the hole
 // extra: handle "u" to undo moves
    }
    public WorldScene makeScene() {
       WorldScene w = new WorldScene(width, height);
       w.placeImageXY(makeImage(), width/2, height/2);
       return w;
    }
}

class ExampleFifteenGame {
    void testGame(Tester t) {
        FifteenGame g = new FifteenGame();
        g.bigBang(120, 120);
    }
}