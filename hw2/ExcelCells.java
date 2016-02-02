import tester.*;

// represents the interface for data of a cell
interface IData {
    
    // used in computing the cell's value
    int value();
    
    // used in comupting the number of arguments in a cell
    int countArgument();
    
    // used in computing the number of functions in a cell
    int countFunctions();
    
    // used in computing the depth of a cell
    int formDepth();
    
    // used in forming the String of formulas in the cell
    String getFormula(int depth);
    
}

// represents the interface for the functions
// that are used by Formulas
interface IFunction {
    
    //  returns the function applied to each cell
    int answer(Cell cell1, Cell cell2);
    
    // used in accessing the name of the function
    String getName();
}

// to represent the multiplication function
class Mul implements IFunction {

    // returns the product of the first and second cells
    public int answer(Cell cell1, Cell cell2) {
        return (cell1.data.value() * cell2.data.value());
    }
    
    // returns the String "mul" for the getFormula function
    public String getName() {
        return "mul";    
    }
}

// to represent the modulo function
class Mod implements IFunction {
    
    // returns the remainder of the first cell divided by the second. 
    public int answer(Cell cell1, Cell cell2) {
        return (cell1.data.value() % cell2.data.value());
    }
    
    // returns the String "mod" for the getFormula function
    public String getName() {
        return "mod";
    }
}

// to represent the summation function
class Sum implements IFunction {
    
    // returns the two cells summed together 
    public int answer(Cell cell1, Cell cell2) {
        return (cell1.data.value() + cell2.data.value());
    }
    
    // returns the String "sum" for the getFormula function
    public String getName() {
        return "sum";
    }
}

// to represent a formula potentially used in a Cell
class Formula implements IData {

    // represents the function used by a Formula
    IFunction func;

    // represents the Cells used by a Formula
    Cell cell1;
    Cell cell2;
    
    Formula(IFunction func, Cell cell1, Cell cell2) {
        this.func = func;
        this.cell1 = cell1;
        this.cell2 = cell2;
    }
    
    // returns the answer to the function applied to the two cells
    public int value() {
        return this.func.answer(this.cell1, this.cell2);
    }
   
    // returns the sum of arguments in the two cells
    public int countArgument() {
        return this.cell1.countArgs() + this.cell2.countArgs();
    }
   
    // returns the sum of functions in the two cells
    public int countFunctions() {
        return this.cell1.countFuns() + this.cell2.countFuns() + 1;
    }
   
    // returns how deep the formula is nested in the cell
    public int formDepth() {
        return this.cell1.formulaDepth() + this.cell2.formulaDepth() + 1;
    }
   
    // returns the String of the formula that is used to compute the cell
    public String getFormula(int depth) {
        return (this.func.getName() + "(" + this.cell1.formula((depth - 1))
            + ", " + this.cell2.formula((depth - 1)) + ")");
    }    
    
}
    
class Number implements IData {
    int number;
 
    // returns the number of the cell
    Number(int number) {
        this.number = number;
    }

    // access the number to be used in the value computation
    public int value() {
        return this.number;
    }
    
    // returns 1 for every time there is an argument
    public int countArgument() {
        return 1;
    }
    
    // returns 0 because the number class does not contain a function
    public int countFunctions() {
        return 0;
    }
    
    // returns 0 because the number class does not contain a formula
    public int formDepth() {
        return 0;
    }
    
    // returns the number as a String to be used in the getFormula funciton
    public String getFormula(int depth) {
        return Integer.toString(number);
    }
}

//represents the cell with a row number, column letter and data within
class Cell {

    // represents a Cell's column A-E
    String column;

    // represents a Cell's row 1-5
    int row;

    // represents a Cell's data
    IData data;
 
    Cell(String column, int row, IData data) {
        this.column = column;
        this.row = row;
        this.data = data;
    }
    
    // computes the value of the cell
    int value() {
        return this.data.value();
    }
    
    // counts the arguments in the cell
    int countArgs() {
        return this.data.countArgument();
    }
    
    // counts the functions in the cell
    int countFuns() {
        return this.data.countFunctions();
    }
    
    // counts the depth of the formula
    int formulaDepth() {
        return this.data.formDepth();
    }
    
    // returns the String of the formula that is used to compute the cell
    String formula(int depth) { 
        if (depth == 0) {
            return (this.column + Integer.toString(this.row));
        } 
        else {
            return this.data.getFormula(depth);
        }
    }      
}

// contains all examples for the program
class ExamplesExcelCells {
    
    Cell cellA1 = new Cell("A", 1, (new Number(25)));
    Cell cellB1 = new Cell("B", 1, (new Number(10)));
    Cell cellC1 = new Cell("C", 1, (new Number(1)));
    Cell cellD1 = new Cell("D", 1, (new Number(27)));
    Cell cellE1 = new Cell("E", 1, (new Number(16)));
    Cell cellA3 = new Cell("A", 3, (new Formula(new Mul(), cellA1, cellB1)));
    Cell cellB3 = new Cell("B", 3, (new Formula(new Mod(), cellE1, cellA3)));
    Cell cellC4 = new Cell("C", 4, (new Formula(new Mul(), cellE1, cellD1)));
    Cell cellC2 = new Cell("C", 2, (new Formula(new Sum(), cellA3, cellC1)));
    Cell cellD4 = new Cell("D", 4, (new Formula(new Sum(), cellC4, cellA1)));
    Cell cellE2 = new Cell("E", 2, (new Formula(new Sum(), cellE1, cellD1)));
    Cell cellD2 = new Cell("D", 2, (new Formula(new Mod(), cellC2, cellE2)));
    Cell cellD3 = new Cell("D", 3, (new Formula(new Mul(), cellD2, cellA1)));
    Cell cellC5 = new Cell("C", 5, (new Formula(new Sum(), cellD4, cellB3)));
    Cell cellA5 = new Cell("A", 5, (new Formula(new Mod(), cellD3, cellC5)));

    // tests the value method
    boolean testValue(Tester t) {
        return t.checkExpect(this.cellE2.value(), 43) &&
                t.checkExpect(this.cellC2.value(), 251) &&
                t.checkExpect(this.cellD2.value(), 36);
    }
    
    // tests the countArgs method
    boolean testcountArgs(Tester t) {
        return t.checkExpect(this.cellE2.countArgs(), 2) &&
                t.checkExpect(this.cellC2.countArgs(), 3) &&
                t.checkExpect(this.cellD2.countArgs(), 5);
    }

    // tests the countFunc method
    boolean testcountFunc(Tester t) {
        return t.checkExpect(this.cellE2.countFuns(), 1) &&
                t.checkExpect(this.cellC2.countFuns(), 2) &&
                t.checkExpect(this.cellD2.countFuns(), 4);
    }
    
    // tests the functionDepth method
    boolean testfunctionDepth(Tester t) {
        return t.checkExpect(this.cellE2.formulaDepth(), 1) &&
                t.checkExpect(this.cellC2.formulaDepth(), 2) &&
                t.checkExpect(this.cellD2.formulaDepth(), 4);
    }
    
    // tests the Formula method
    boolean testFormula(Tester t) {
        return t.checkExpect(this.cellE2.formula(2), "sum(16, 27)") &&
                t.checkExpect(this.cellC2.formula(2), "sum(mul(A1, B1), 1)") &&
                t.checkExpect(this.cellD2.formula(3), 
                        "mod(sum(mul(A1, B1), 1), sum(16, 27))");      
    }
}
