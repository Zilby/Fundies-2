import tester.*;

/**
 * HtDC Labs
 * Lab 2: Methods for unions of classes
 * 
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */

//to represent a person
class Person {
    String name;
    ILoPet pets;
    int age;
    
    Person(String name, ILoPet pets, int age) {
        this.name = name;
        this.pets = pets;
        this.age = age;
    }
    
    /* 
     TEMPLATE
     Fields: 
     ... this.name ...    -- String
     ... this.pets ...    -- ILoPet
     ... this.age ...     -- int
     
     Methods:
     ... this.isOlder(Person) ...      -- boolean
     ... this.sameNamePet(String) ...  -- boolean
     
     Methods for Fields:
     ... this.pet.sameName(String) ... -- boolean
     */
    
    //is this person older than the given person?
    boolean isOlder(Person other) {
        return this.age > other.age;
    }
    
    //does the name of this person's pet match the given name?
    boolean hasPetNamed(String petName) {
        return this.pets.hasName(petName);
    }
}

//to represent a pet
interface Pet { 
    
    //does the name of this person's pet match the given name?
    boolean sameName(String name);
}

//to represent a pet cat
class Cat implements Pet {
    String name;
    String kind; 
    boolean longhaired;
    
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }
    
    /* 
     TEMPLATE
     Fields: 
     ... this.name ...        -- String
     ... this.kind ...        -- String
     ... this.longhaired ...  -- boolean
     
     Methods:
     ... this.sameName(String) ...  -- boolean
     */
    
    //does the name of this pet match the given name?
    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

//to represent a pet dog
class Dog implements Pet{
    String name;
    String kind; 
    boolean male;
    
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }
    
    /* 
     TEMPLATE
     Fields: 
     ... this.name ...  -- String
     ... this.kind ...  -- String
     ... this.male ...  -- boolean
     
     Methods:
     ... this.sameName(String) ...  -- boolean
     */
    
    //does the name of this pet match the given name?
    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

//to represent the absence of any pet
class NoPet implements Pet {
    NoPet() { }
    
    //does the name of this pet match the given name?
    public boolean sameName(String name) {
        return false;
    }
}

// to represent a list of pets
interface ILoPet {
    
    // its there a pet with the given name in this list?
    public boolean hasName(String name);
}

// to represent an empty list of pets
class MtLoPet implements ILoPet {
    MtLoPet() { }
    
    // its there a pet with the given name in this list?
    public boolean hasName(String name) {
        return false;
    }
}

//to represent an nonempty list of pets
class ConsLoPet implements ILoPet {
    Pet first;
    ILoPet rest;
    
    ConsLoPet(Pet first, ILoPet rest){
        this.first = first;
        this.rest = rest;
    }
    
    // its there a pet with the given name in this list?
    public boolean hasName(String name) {
        return this.first.sameName(name) ||
        this.rest.hasName(name);
    }
}

// to represent examples of pet owners and pets
class ExamplesPets {
    
    Pet kitty = new Cat("Boots", "tabby", false);
    Pet minny = new Cat("Minny", "angora", true);
    Pet spot = new Dog("Spot", "terrier", true);
    Pet tasha = new Dog("Tasha", "mutt", false);
    
    Pet nopet = new NoPet();
    
    ILoPet mtpet = new MtLoPet();
    
    ILoPet km = 
    new ConsLoPet(this.kitty, new ConsLoPet(this.minny, this.mtpet));
    ILoPet s = new ConsLoPet(this.spot, this.mtpet);
    ILoPet t = new ConsLoPet(this.tasha, this.mtpet);
    
    Person ann = new Person ("Ann", this.km, 74);
    Person pat = new Person ("Pat", this.s, 13);
    Person kim = new Person ("Kim", this.t, 18);
    
    Person dan = new Person("Dan", this.mtpet, 23);
    
    // test the method isOlder in the class Person
    boolean testIsOlder(Tester t) {
        return
        t.checkExpect(this.kim.isOlder(this.ann), false) &&
        t.checkExpect(this.kim.isOlder(this.pat), true) &&
        t.checkExpect(this.ann.isOlder(this.kim), true);
    } 
    
    // test the method sameName in the class Pet
    boolean testSameName(Tester t) {
        return
        t.checkExpect(this.kitty.sameName("Minny"), false) &&
        t.checkExpect(this.minny.sameName("Minny"), true) &&
        t.checkExpect(this.spot.sameName("Spot"), true) &&
        t.checkExpect(this.kitty.sameName("Spot"), false);
    }
    
    // test the method sameName in the class Pet
    boolean testHasPetNamed(Tester t) {
        return
        t.checkExpect(this.ann.hasPetNamed("Minny"), true) &&
        t.checkExpect(this.ann.hasPetNamed("Boots"), true) &&
        t.checkExpect(this.kim.hasPetNamed("Tasha"), true) &&
        t.checkExpect(this.pat.hasPetNamed("Spot"), true) &&
        t.checkExpect(this.kim.hasPetNamed("Spot"), false) &&
        t.checkExpect(this.dan.hasPetNamed("Spot"), false);
    }
}

