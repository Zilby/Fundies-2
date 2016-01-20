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
    ITaco shell1 = new EmptyShell(true);
    ITaco carnitas1 = new Filled(shell1, "carnitas");
    ITaco salsa1 = new Filled(carnitas1, "salsa");
    ITaco lettuce1 = new Filled(salsa1, "lettuce");
    ITaco order1 = new Filled(lettuce1, "cheddar cheese");

    ITaco shell2 = new EmptyShell(false);
    ITaco veggies2 = new Filled(shell2, "veggies");
    ITaco guacamole2 = new Filled(veggies2, "guacamole");
    ITaco order2 = new Filled(guacamole2, "sourCream");
}
