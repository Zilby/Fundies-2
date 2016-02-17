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

/*
  to represent a transporter Constraints: Can only transport to or from	  	
  spacestations if the spacestation has a transporterpad. Can travel between 	
  planets	  	
*/
class Transporter implements ITransportation{
    IHabitation from; 
    IHabitation to; 

    Transporter (IHabitation from, IHabitation to) {
	this.from = from;
	this.to = to;
    }
}

/*
  to represent a Shuttle Constraints: Shuttles must be concerned about fuel
  (kg) and capacity (number of passengers); must travel to or from planets with	
  spaceports, but can travel to any spacestation.	  	
*/
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

/*
  to represent a SpaceElevator Constraints: must have different types of
  habitation for travel to and from (cannot go to and from a planet or
  spaceStation), has a limited amount of tonnage that can be carried.	  	
*/
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
    IHabitation nausicant = new Planet ("Nausicant", 6000000, 8);
    IHabitation earth = new Planet ("Earth", 9000000, 14);
    IHabitation remus = new Planet ("Remus", 18000000, 23);
    IHabitation watcherGrid = new SpaceStation ("WatcherGrid", 1, 0);
    IHabitation deepSpace42 = new SpaceStation ("Deep Space 42", 7, 8);
    IHabitation deathStar = new SpaceStation ("Death Star", 10, 30);

    ITransportation transporter1 = new Transporter(nausicant, deathStar);
    ITransportation transporter2 = new Transporter(deepSpace42, earth);
    ITransportation shuttle1 = new Shuttle(watcherGrid, deepSpace42, 100, 150);
    ITransportation shuttle2 = new Shuttle(deathStar, earth, 50, 80);
    ITransportation elevator1 = new SpaceElevator(remus, deepSpace42, 4);
    ITransportation elevator2 = new SpaceElevator(deathStar, earth, 8);
}
