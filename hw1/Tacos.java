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

// to represent a filling
class Filled implements ITaco {
    ITaco taco;
    String filling;

    Filled (ITaco taco, String filling) {
	this.taco = taco;
	this.filling = filling;
    }
}

// to represent examples of taco orders
class ExamplesTacos {
    ITaco order1 = new EmptyShell(true);
    Filled carnitas1 = new Filled(order1, "carnitas");
    Filled salsa1 = new Filled(order1, "salsa");
    Filled lettuce1 = new Filled(order1, "lettuce");
    Filled cheddarCheese1 = new Filled(order1, "cheddar cheese");

    ITaco order2 = new EmptyShell(false);
    Filled veggies1 = new Filled(order1, "veggies");
    Filled guacamole1 = new Filled(order1, "guacamole");
    Filled sourCream1 = new Filled(order1, "sourCream");
}
