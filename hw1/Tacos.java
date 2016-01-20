// Assignment 1
// Nameth Kyle
// kname5
// Zilbersher Alexander
// zilby

// to represent a taco
interface ITaco { }
 
// to represent an empty shell
class EmptyShell implements ITaco {
    boolean softShell;

    EmptyShell (boolean softShell) {
	this.softShell = softShell;
    }
}

// to represent a filled taco
class Filled implements ITaco {
    ITaco taco;
    String filling;

    Filled (ITaco taco, String filling) {
	this.taco = taco;
	this.filling = filling;
    }
}

class ExamplesTacos {
    ITaco order1 = new EmptyShell(true);
    Filled carnitas = new Filled(order1, "carnitas");
    Filled salsa = new Filled(order1, "salsa");
    Filled lettuce = new Filled(order1, "lettuce");
    Filled cheddarCheese = new Filled(order1, "cheddar cheese");

    ITaco order2 = new EmptyShell(false);
    Filled veggies = new Filled(order1, "veggies");
    Filled guacamole = new Filled(order1, "guacamole");
    Filled sourCream = new Filled(order1, "sourCream");
}
