// Assignment 1
// Nameth Kyle
// kname5
// Zilbersher Alexander
// zilby

//need to add comments describing what each class is for
//and or what interfaces are for
//also write down the units and data definitions for the
//transporter classes

interface IHabitation {}

class Planet implements IHabitation{
    String name;
    int population;
    int spacePorts;

    Planet (String name, int population, int spaceports) {
	this.name = name;
	this.population = population;
	this.spacePorts = spacePorts;
    }
}

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

interface ITransportation {}

class Transporter implements ITransportation{
    IHabitation from;
    IHabitation to;

    Transporter (IHabitation from, IHabitation to) {
	this.from = from;
	this.to = to;
    }
}

class Shuttle implements ITransportation{
    IHabitation from;
    IHabitation to;
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
    IHabitation from;
    IHabitation to;
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
