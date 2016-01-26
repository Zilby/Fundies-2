//import tester.*;

// represents the interface for data of a cell
interface IData {
    int getValue();
    int getArgs();
    int getFuns();
    int getDepth();
}

class Formula implements IData {
    int number;
    Cell a;
    Cell b;
    
    Formula (Cell a, Cell b, String f){
	this.a = a;
	this.b = b;
	if(f == "mul"){
	    this.number = (this.a.value() * this.b.value());
	}else if(f == "mod"){
	    this.number = (this.a.value() % this.b.value());
	}else
	    this.number = (this.a.value() + this.b.value());
    }

    public int getValue(){
	return this.number;
    }

    public int getArgs(){
	return (this.a.countArgs() + this.b.countArgs());
    }

    public int getFuns(){
	return (this.a.countFuns() + this.b.countFuns() + 1);
    }

    public int getDepth(){
	if (this.a.formulaDepth() > this.b.formulaDepth())
	    return 1 + this.a.formulaDepth();
	else
	    return 1 + this.b.formulaDepth();
    }
}

class Number implements IData {
    int number;
    
    Number (int number) {
        this.number = number;
    }

    public int getValue(){
	return this.number;
    }

    public int getArgs(){
	return 1;
    }

    public int getFuns(){
	return 0;
    }

    public int getDepth(){
	return 0;
    }
}

// represents the cell with a row number, column letter and data within
class Cell {
    String column;
    int row;
    IData data;
    
    Cell (String column, int row, IData data) {
        this.column = column;
        this.row = row;
        this.data = data;
    }

    int value(){
	return this.data.getValue();
    }

    int countArgs(){
	return this.data.getArgs();
    }

    int countFuns(){
	return this.data.getFuns();
    }

    int formulaDepth(){
	return this.data.getDepth();
    }
}

// contains all examples for the program
class ExamplesExcelCells {

    public static void main(String[] args){
    
	IData a1data = new Number (25);
	Cell cellA1 = new Cell ("A", 1, a1data);
    
	IData b1data = new Number (10);
	Cell cellB1 = new Cell ("B", 1, b1data);
    
	IData c1data = new Number (1);
	Cell cellC1 = new Cell ("C", 1, c1data);
    
	IData d1data = new Number (27);
	Cell cellD1 = new Cell ("D", 1, d1data);
    
	IData e1data = new Number (16);
	Cell cellE1 = new Cell ("E", 1, e1data);

	IData e2data = new Formula (cellE1, cellD1, "sum");
	Cell cellE2 = new Cell ("E", 2, e2data);

	IData a3data = new Formula (cellA1, cellB1, "mul");
	Cell cellA3 = new Cell ("A", 3, a3data);

	IData c2data = new Formula (cellA3, cellC1, "sum");
	Cell cellC2 = new Cell ("C", 2, c2data);

	IData c4data = new Formula (cellE1, cellD1, "mul");
	Cell cellC4 = new Cell ("C", 4, c4data);

	IData b3data = new Formula (cellE1, cellA3, "mod");
	Cell cellB3 = new Cell ("B", 3, b3data);

	IData d2data = new Formula (cellC2, cellE2, "mod");
	Cell cellD2 = new Cell ("D", 2, d2data);

	IData d3data = new Formula (cellD2, cellA1, "mul");
	Cell cellD3 = new Cell ("D", 3, d3data);

	IData d4data = new Formula (cellC4, cellA1, "sum");
	Cell cellD4 = new Cell ("D", 4, d4data);

	IData c5data = new Formula (cellD4, cellB3, "sum");
	Cell cellC5 = new Cell ("C", 5, c5data);
    
	IData a5data = new Formula (cellD3, cellC5, "mod");
	Cell cellA5 = new Cell ("A", 5, a5data);

	/* Example Cells
       
	   IData e4data = new Formula (this.cellD4, this.cellC4, "mul");
	   Cell cellE4 = new Cell ("E", 4, this.e4data);
       
	   IData b5data = new Formula (this.cellC2 this.cellA5, "mod");
	   Cell cellB5 = new Cell ("B", 5, this.b5data);
       
	   IData c3data = new Formula (this.cellE2, this.cellB3, "sum");
	   Cell cellC3 = new Cell ("C", 3, this.c3data);
	*/

	System.out.println(cellD2.value());
	System.out.println(cellB3.value());
	System.out.println(cellD2.countArgs());
	System.out.println(cellB3.countArgs());
	System.out.println(cellD2.countFuns());
	System.out.println(cellB3.countFuns());
	System.out.println(cellD2.formulaDepth());
	System.out.println(cellB3.formulaDepth());
    }
}

/* Prior Tests
// contains all examples for the program
class ExamplesExcelCells {
    
	IData a1data = new Number (25);
	Cell cellA1 = new Cell ("A", 1, this.a1data);
    
	IData b1data = new Number (10);
	Cell cellB1 = new Cell ("B", 1, this.b1data);
    
	IData c1data = new Number (1);
	Cell cellC1 = new Cell ("C", 1, this.c1data);
    
	IData d1data = new Number (27);
	Cell cellD1 = new Cell ("D", 1, this.d1data);
    
	IData e1data = new Number (16);
	Cell cellE1 = new Cell ("E", 1, this.e1data);

	IData e2data = new Formula (this.cellE1, this.cellD1, "sum");
	Cell cellE2 = new Cell ("E", 2, this.e2data);

	IData a3data = new Formula (this.cellA1, this.cellB1, "mul");
	Cell cellA3 = new Cell ("A", 3, this.a3data);

	IData c2data = new Formula (this.cellA3, this.cellC1, "sum");
	Cell cellC2 = new Cell ("C", 2, this.c2data);

	IData c4data = new Formula (this.cellE1, this.cellD1, "mul");
	Cell cellC4 = new Cell ("C", 4, this.c4data);

	IData b3data = new Formula (this.cellE1, this.cellA3, "mod");
	Cell cellB3 = new Cell ("B", 3, this.b3data);

	IData d2data = new Formula (this.cellC2, this.cellE2, "mod");
	Cell cellD2 = new Cell ("D", 2, this.d2data);

	IData d3data = new Formula (this.cellD2, this.cellA1, "mul");
	Cell cellD3 = new Cell ("D", 3, this.d3data);

	IData d4data = new Formula (this.cellC4, this.cellA1, "sum");
	Cell cellD4 = new Cell ("D", 4, this.d4data);

	IData c5data = new Formula (this.cellD4, this.cellB3, "sum");
	Cell cellC5 = new Cell ("C", 5, this.c5data);
    
	IData a5data = new Formula (this.cellD3, this.cellC5, "mod");
	Cell cellA5 = new Cell ("A", 5, this.a5data);

	/* Example Cells
       
	   IData e4data = new Formula (this.cellD4, this.cellC4, "mul");
	   Cell cellE4 = new Cell ("E", 4, this.e4data);
       
	   IData b5data = new Formula (this.cellC2 this.cellA5, "mod");
	   Cell cellB5 = new Cell ("B", 5, this.b5data);
       
	   IData c3data = new Formula (this.cellE2, this.cellB3, "sum");
	   Cell cellC3 = new Cell ("C", 3, this.c3data);
       */
       }
*/


