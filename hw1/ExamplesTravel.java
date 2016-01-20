// Assignment 1
// Nameth Kyle
// kname5
// Zilbersher Alexander
// zilby

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
}

class Shuttle implements ITransportation{
    SpaceStation from;
    SpaceStation to;
    int fuel;
    int capacity;
}

class SpaceElevator implements ITransportation{
    IHabitation from;
    IHabitation to;
    int tonnage;
}

class ExamplesTravel {
}
