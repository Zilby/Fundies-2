
import java.util.*;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

/*
 * My project involves simulating balls bouncing off of various 
 * surfaces curved and slanted surfaces. Given a location and 
 * direction by the user, a ball will fall either straight down 
 * or at an angle, be affected by gravity and reflect off of any 
 * surfaces it touches. The program will also keep track of the 
 * x and y coordinate of the last ball generated and its current 
 * velocity. This project covers concepts such as modelling 2D 
 * spaces, reflections, collisions and the application of 
 * calculus to these concepts. 
 */

// for all of our surfaces
interface Function {
    double apply(double x);
}

// left-most surface
class F1 implements Function {
    public double apply(double x) {
        return ((200 - x) * 1 / 5) + 80;
        // dy/dx = -1/5
    }
}

// mid-left surface
class F2 implements Function {
    public double apply(double x) {
        return (x * 4 / 15) + 80;
        // dy/dx = 4/15
    }
}

// mid-right surface
class F3 implements Function {
    public double apply(double x) {
        return ((200 - x) * (200 - x)) * 3 / 1000;
        // dy/dx = (3x - 600)/500
    }
}

// right-most surface
class F4 implements Function {
    public double apply(double x) {
        return x * x / 1000;
        // dy/dx = x/500
    }
}

// for our balls
class Ball {
    boolean active;
    double x;
    double y;
    double xv;
    double yv;
    Color color;

    Ball(Posn pos) {
        Random r = new Random();
        x = pos.x;
        y = 500 - pos.y;
        xv = 0;
        yv = 0;
        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        active = true;
    }

    // for when we have not yet generated a ball
    // (ie: none are active)
    Ball() {
        x = 0;
        y = 0;
        xv = 0;
        yv = 0;
        color = color.black;
        active = false;
    }
}

// represents our world state
class Math extends World {
    // for determing whether to create balls with an
    // initial left or right velocity
    boolean left;
    boolean right;
    // our current function we're working with
    Function f;
    // our list of balls
    ArrayList<Ball> balls;
    // the current ball
    Ball active;

    Math() {
        balls = new ArrayList<Ball>();
        left = false;
        right = false;
        active = new Ball();
    }

    // draws the world scene
    public WorldScene makeScene() {
        WorldScene w = new WorldScene(1000, 500);
        // generates first slant
        for (double i = 0.0; i < 200.0; i++) {
            f = new F1();
            w.placeImageXY(new CircleImage(1, OutlineMode.SOLID, Color.black), (int) i, (int) (500 - f.apply(i)));
        }
        // generates second slant
        for (double i = 0.0; i < 150.0; i++) {
            f = new F2();
            w.placeImageXY(new CircleImage(1, OutlineMode.SOLID, Color.black), (int) (200 + i),
                    (int) (500 - f.apply(i)));
        }
        // generates first curve
        for (double i = 0.0; i < 200.0; i++) {
            f = new F3();
            w.placeImageXY(new CircleImage(1, OutlineMode.SOLID, Color.black), (int) (350 + i),
                    (int) (500 - f.apply(i)));
        }
        // generates second curve
        for (double i = 0.0; i < 450.0; i++) {
            f = new F4();
            w.placeImageXY(new CircleImage(1, OutlineMode.SOLID, Color.black), (int) (550 + i),
                    (int) (500 - f.apply(i)));
        }
        // places our balls
        for (Ball b : balls) {
            w.placeImageXY(new CircleImage(5, OutlineMode.SOLID, b.color), (int) b.x, (int) (500 - b.y));
        }
        // places our text
        if (active.active) {
            w.placeImageXY(new TextImage("Active Ball:", 30, Color.black), 110, 30);
            w.placeImageXY(new TextImage("Xcor: " + (int) active.x + "  Ycor: " + (int) active.y, 20, active.color),
                    120, 70);
            w.placeImageXY(new TextImage("Velocity: ( " + (int) (20 * active.xv) + ", " + (int) (20 * active.yv) + " )",
                    20, active.color), 120, 100);
        }
        return w;
    }

    public void onTick() {
        double gravity = 0.01;
        for (Ball b : balls) {
            // for bouncing balls off of our first slant
            if (b.x < 200) {
                f = new F1();
                if (b.y <= f.apply(b.x)) {
                    b.y = f.apply(b.x);
                    // x coordinate of perpendicular vector
                    // (derivative of F1 at this point)
                    double px = 1.0 / 5.0;
                    // y coordinate of perpendicular vector
                    double py = 1;
                    // x and y coordinate of new vector
                    // uses w = v - 2Pu(v)
                    b.xv = b.xv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * px);
                    b.yv = b.yv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * py);
                } else {
                    b.yv -= gravity;
                }
                // for bouncing balls off of our second slant
            } else if (b.x < 350) {
                f = new F2();
                if (b.y <= f.apply(b.x - 200)) {
                    b.y = f.apply(b.x - 200);
                    // x coordinate of perpendicular vector
                    // (derivative of F2 at this point)
                    double px = -4.0 / 15.0;
                    // y coordinate of perpendicular vector
                    double py = 1;
                    // x and y coordinate of new vector
                    // uses w = v - 2Pu(v)
                    b.xv = b.xv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * px);
                    b.yv = b.yv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * py);
                } else {
                    b.yv -= gravity;
                }
                // for bouncing balls off of our first curve
            } else if (b.x < 550) {
                f = new F3();
                if (b.y <= f.apply(b.x - 350)) {
                    b.y = f.apply(b.x - 350);
                    // x coordinate of perpendicular vector
                    // (derivative of F3 at this point)
                    double px = -1 * (((3.0 * (b.x - 350)) - 600) / 500);
                    // y coordinate of perpendicular vector
                    double py = 1;
                    // x and y coordinate of new vector
                    // uses w = v - 2Pu(v)
                    b.xv = b.xv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * px);
                    b.yv = b.yv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * py);
                } else {
                    b.yv -= gravity;
                }
                // for bouncing balls off of our second curve
            } else {
                f = new F4();
                if (b.y <= f.apply(b.x - 550)) {
                    b.y = f.apply(b.x - 550);
                    // x coordinate of perpendicular vector
                    // (derivative of F4 at this point)
                    double px = -1 * ((b.x - 550) / 500);
                    // y coordinate of perpendicular vector
                    double py = 1;
                    // x and y coordinate of new vector
                    // uses w = v - 2Pu(v)
                    b.xv = b.xv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * px);
                    b.yv = b.yv - (2 * (((px * b.xv) + (py * b.yv)) / ((px * px) + (py * py))) * py);
                } else {
                    b.yv -= gravity;
                }
            }
            b.x += b.xv;
            b.y += b.yv;
        }
        // bounces our balls off of the walls
        Iterator<Ball> i = balls.iterator();
        while (i.hasNext()) {
            Ball b = i.next();
            if (b.x < 0 || b.x > 1000) {
                b.xv = b.xv * -1;
                b.x += b.xv;
            }
        }
    }

    // generates a new ball on mouse click
    public void onMouseClicked(Posn pos) {
        Ball b = new Ball(pos);
        if (left) {
            b.xv = -1.5;
        } else if (right) {
            b.xv = 1.5;
        }
        balls.add(b);
        this.active = b;
    }

    // sets initial velocity for ball given key event
    public void onKeyEvent(String ke) {
        if (ke.equals("left")) {
            left = true;
            right = false;
        } else if (ke.equals("right")) {
            right = true;
            left = false;
        } else if (ke.equals("down")) {
            right = false;
            left = false;
        }
    }

}

// for testing purposes
class ExamplesWorld {
    // launches the game
    void testGame(Tester t) {
        Math m = new Math();
        m.bigBang(1000, 550, .001);
    }
}
