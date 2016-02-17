// Assignment 1
// Nameth Kyle
// kname5
// Zilbersher Alexander
// zilby

//to represent a person
class Person {
    String name;
    int yob;
    String state;
    boolean citizen;

    Person(String name, int yob, String state, boolean citizen) {
        this.name = name;
        this.yob = yob;
        this.state = state;
	this.citizen = citizen;
    }
}

//to represent examples of people given their names, yob, state and citizenship
class ExamplesPerson {
    Person jackie = new Person ("Jackie Robinson", 1920, "NY", true);
    Person golda = new Person ("Golda Meir", 1930, "MA", false);
    Person sponge = new Person ("Sponge Bob", 1990, "BB", false);
}
