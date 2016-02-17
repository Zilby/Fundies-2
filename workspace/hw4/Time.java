import tester.*;

//Assignment 4
//Nameth Kyle
//kname5
//Zilbersher Alexander
//zilby

// abstract class
abstract class ATime {
    int hour;
    int minute;
    
    ATime(int hour, int minute) {
        
        if (this.validHour(hour)) {
            this.hour = hour;
        }
        else {
            throw new 
            IllegalArgumentException("Invalid hour: " + Integer.toString(hour));
        }
        if (this.validminuteute(minute)) {
            this.minute = minute;
        }
        else {
            throw new IllegalArgumentException("Invalid minute: " + 
                    Integer.toString(minute));
        }
    }
    
 // returns true if the hour is within the proper range (0 - 23)
    boolean validHour(int h) {
        return (0 <= h) && (h <= 23);
    }
    
    // returns true if the minuteute is within the proper range (0 - 59)
    boolean validminuteute(int m) {
        return (0 <= m) && (m <= 59);
    }
}


// the class for time
class Time extends ATime {
    int second;       // ranges from 0 to 59
    
    
    // Main Constructor
    Time(int hour, int minute, int second) {
        super(hour, minute);
        if (this.validsecondond(second)) {
            this.second = second;
        }
        else {
            throw new IllegalArgumentException("Invalid second: " + 
                    Integer.toString(second));
        }
    }
        
    // returns true if the secondond is within the proper range (0 - 59)
    boolean validsecondond(int s) {
        return (0 <= s) && (s <= 59);
    }
    
    // Convenience Constructor
    Time(int hour, int minute) {
        this(hour, minute, 0);
    }
    
    // Constructor containing isAM
    Time(int hour, int minute, boolean isAM) {
        this(hour, minute, 0);
        
        // if the time is AM, the hour provided will be modded by 12
        if (isAM) {
            this.hour = this.hour % 12;
        }
        else {
            // if the time is PM and noon, the hour will be set to 12
            if (this.hour == 12) {
                this.hour = 12;
            }
            // if the time is PM and not noon, the hour will be set to the hour
            // plus 12.
            else {
                this.hour = this.hour + 12;
            }
        }
    }
    
    // returns true if the time is the same as the other time
    boolean sameTime(Time that) {
        return this.hour == that.hour &&
            this.minute == that.minute &&
            this.second == that.second;
    }
    
}


// examples of times
class ExamplesTime {
    Time noon = new Time(12, 00, 00);               // 12:00.00 pm
    Time twothirty = new Time(2, 30, 00);           // 2:30.00 am
    Time fifteenhundred = new Time(15, 00);         // 3:00.30 pm
    Time six = new Time(6, 00);                     // 6:00.00 am
    Time noonb = new Time(12, 00, false);           // 12:00.00 pm
    Time eight = new Time(8, 00, true);             // 8:00.00 am
    
    boolean testsameTime(Tester t) {
        return
                t.checkExpect(noon.sameTime(noonb), true) &&
                t.checkExpect(six.sameTime(eight), false) &&
                t.checkExpect(noon.sameTime(fifteenhundred), false);
                
    }
    
    boolean testInvalidTime(Tester t) {
        return  t.checkConstructorException(
                new IllegalArgumentException("Invalid hour: 90"), 
                "Time", 90, 4, 2) &&
                
                t.checkConstructorException(
                        new IllegalArgumentException("Invalid minute: 90"),
                        "Time", 14, 90, 3) &&
                
                t.checkConstructorException(
                        new IllegalArgumentException("Invalid second: 90"), 
                        "Time", 14, 4, 90);
                
                
    }

}
