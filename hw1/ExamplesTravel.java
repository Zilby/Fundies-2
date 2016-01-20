// Assignment 1
// Nameth Kyle
// kname5
// Zilbersher Alexander
// zilby

//to represent a habitation
interface IHabitation {}

//to represent a planet
class Planet implements IHabitation{
    String name; 
    int population; //in thousands of people
    int spacePorts; 

    Planet (String name, int population, int spaceports) {
	this.name = name;
	this.population = population;
	this.spacePorts = spacePorts;
    }
}

//to represent a space-station
class SpaceStation implements IHabitation{
    String name;
    int population;
    int transporterPads;

    SpaceStation (String name, int population, int transporterPads) {
	this.name = name;
	this.population = population;
	this.transporterPads = transporterPads;
    }
}

//to represent a method of transportation
interface ITransportation {}

//to represent a transporter
class Transporter implements ITransportation{
    IHabitation from; //habitations must either be a planet 
    IHabitation to;   //or have a transporter-pad

    Transporter (IHabitation from, IHabitation to) {
	this.from = from;
	this.to = to;
    }
}

class Shuttle implements ITransportation{
    IHabitation from; //habitations must either be a 
    IHabitation to;   //space-station or have a spaceport
    int fuel;
    int capacity;

    Shuttle (IHabitation from, IHabitation to, int fuel, int capacity) {
	this.from = from;
	this.to = to;
	this.fuel = fuel;
	this.capacity = capacity;
    }
}

class SpaceElevator implements ITransportation{
    IHabitation from; //can only travel from a planet
    IHabitation to;   //to a space station or vice versa
    int tonnage;

    
    SpaceElevator (IHabitation from, IHabitation to, int tonnage) {
	this.from = from;
	this.to = to;
	this.tonnage = tonnage;
    }
}

class ExamplesTravel {
    Planet nausicant = new Planet ("Nausicant", 6000000, 8);
    Planet earth = new Planet ("Earth", 9000000, 14);
    Planet remus = new Planet ("Remus", 18000000, 23);
    SpaceStation watcherGrid = new SpaceStation ("WatcherGrid", 1000, 0);
    SpaceStation deepSpace42 = new SpaceStation ("Deep Space 42", 7000, 8);
    SpaceStation deathStar = new SpaceStation ("Death Star", 10000, 30);

    Transporter transporter1 = new Transporter(nausicant, deathStar);
    Transporter transporter2 = new Transporter(deepSpace42, earth);
    Shuttle shuttle1 = new Shuttle(watcherGrid, deepSpace42, 100, 150);
    Shuttle shuttle2 = new Shuttle(deathStar, earth, 50, 80);
    SpaceElevator elevator1 = new SpaceElevator(remus, deepSpace42, 4);
    SpaceElevator elevator2 = new SpaceElevator(deathStar, earth, 8);
}
