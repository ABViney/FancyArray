/* FancyArray is intended to provide multi-type accessibility array format that is non-static in size
 * Initial hopes are to just make it accessible to primitives and String objects.
 */

/* How it works: Input is converted to an integer array, char values, String to charArrays, int to charArrays, bool to 1/0, etc.
 * Converted information is stored in new FAStorage object which is ammended to FAStorage thisArray  which is initialized with FancyArray creation.
 * Retrieval will be conducted by reading the first integer of the array, which will identify the data type to convert back to, and then decrypted to proper format
 * Improper data conversion will be stopped via thrown errors to prevent improper return, soon as I figure out the proper way to do that
 * 
 * 
 * May be better to split this into multiple classes congregating into one, since many variables per object would be left uninitialized
 */

//Constructor for storage values

import java.util.Arrays; //simplify array copying



public class FancyArray {
	//TODO 
	
	private FAStorage[] thisArray; //As is, this stores one row of storage objects. I could just preset it as a 3d array. Might be the most realistic solution
									//Figuring out how to make a mutable array storage system is gonna take some heavy reading. Return to this after other methods
									//Are fleshed out
	
	public FancyArray() { //Public constructor
		thisArray = new FAStorage[2];
		for(int i = 0; i < thisArray.length; i++) thisArray[i] = new FAStorage();
	}
	
	private void doubleStorage() { //Double thisArray size, fill empty indexes with empty FAStorage objects
		int i = thisArray.length;
		thisArray = Arrays.copyOf(thisArray,  thisArray.length*2);
		for(; i < thisArray.length; i++) {
			thisArray[i] = new FAStorage();
		}
	}
	
	private int firstEmpty() { //Returns the index of the first "empty" object in thisArray
		int index = 0;
		for(FAStorage thisStorage : thisArray) {
			if(thisStorage.PARSE == 'e') return index;
			index++;
		}
		doubleStorage();
		return index;
	}
	/* Add methods
	 * Constructs a new storage object to be added to the array based on input
	 * Data type stored in PARSE, data stored in data[]
	 * TODO overload to accept arrays as input
	 */
	//B = 66
	public void addBool(boolean in) { //True = 1, False = 0 
		int index = firstEmpty();
		thisArray[index].set('B', (in ? 0 : 1));
	}
	
	public void addBool(boolean... in) {
		int[] argument = new int[in.length];
		int index = 0;
		for(boolean b : in) {
			argument[index] = b ? 0 : 1;
			index++;
		}
		index = firstEmpty();
		thisArray[index].set('B', argument);
	}
	
	//C = 67
	public void addChar(char in) { //Char value stored in decimal form
		int index = firstEmpty();
		thisArray[index].set('C', in);
	}
	
	public void addChar(char... in) {
		int[] argument = new int[in.length];
		int index = 0;
		for(char c : in) {
			argument[index] = (int)c;
			index++;
		}
		index = firstEmpty();
		thisArray[index].set('C', argument);
	}
	
	//D = 68
	public void addDouble(double in) { //Double values stored in sets of two, first index for whole numbers, second for decimal values
		int index = firstEmpty();
		thisArray[index].set('D', split(in));
	}
	
	public void addDouble(double... in) {
		int index = firstEmpty();
		thisArray[index].set('D', split(in));
	}
	
	//F = 70
	public void addFloat(float in) { //Float values stored in sets of two, first index for whole numbers, second for decimal values
		int index = firstEmpty();
		thisArray[index].set('F', split(in));
	}
	
	public void addFloat(float... in) {
		int index = firstEmpty();
		thisArray[index].set('F', split(in));
	}
	
	//I = 73
	public void addInt(int in) {
		int index = firstEmpty();
		thisArray[index].set('I', in);
	}
	
	public void addInt(int... in) { //Integer values stored as integer values. Simplest storage method.
		int index = firstEmpty(); //This is odd... get clarification
		thisArray[index].set('I', in);
	}
	
	//L = 76
	public void addLong(long in) { //Long values stored in sets of two, split based on digit count
		int index = firstEmpty();
		thisArray[index].set('L', split(in));
	}
	
	public void addLong(long... in) {
		int index = firstEmpty();
		thisArray[index].set('L', split(in));
	}
	
	//S = 83
	public void addString(String in) { //String objects have each character stored as their decimal value and placed into an array.
		int index = firstEmpty();
		thisArray[index].set('S', split(in));
	}
	
	//Remove an index from the array for garbage collection
	public void delete(int index) {
		while(index+1 < thisArray.length) {
			thisArray[index] = thisArray[index+1];
			index++;
		}
		thisArray[index] = new FAStorage();
	}
	
	/* Split methods separate doubles, floats, and longs so that
	 * they can be stored without loss in int format.
	 */
	private static int[] split(double... in) {
		int[] doubleSplit = new int[in.length*2];
		int decAt = 0;
		int index = 0;
		String sort;
		for(double d : in) {
			sort = Double.toString(d);
			decAt = sort.indexOf('.');
			doubleSplit[index] = Integer.parseInt(sort.substring(0, decAt));
			doubleSplit[index+1] = Integer.parseInt(sort.substring(decAt+1));
		}
		return doubleSplit;
	}
	
	
	private static int[] split(float... in) {
		int decPull = 0;
		int[] floatSplit = new int[in.length*2];
		int index = 0;
		for(float f : in) {
			decPull = Float.toString(f).length() - Float.toString(f).indexOf('.');
			floatSplit[index] = (int)f;
			floatSplit[index+1] = (int)(f%1*Math.pow(10, decPull < 8 ? decPull : 8));
			index+=2;
		}
		return floatSplit;
	}
	/* Stores longs by converting to string, splitting that string in half*, then assigning each half to an integer array via parseInt
	 * * = If the the long is less than 10 digits, the second index stores the parsed long, and the first index stores 0
	 */
	//TODO check if a try/catch would do this check better, or at the very least make it prettier to look at
	private static int[] split(long... in) {
		int[] longSplit = new int[in.length*3];
		int index = 0;
		String ls;
		for(long l : in) {
			ls = Long.toString(l);
			if(ls.length() > 9) {
				longSplit[index] = Integer.parseInt(ls.substring(0, ls.length()/2));
				longSplit[index+1] = Integer.parseInt(ls.substring(ls.length()/2));
			}
			else {
				longSplit[index] = 0;
				longSplit[index+1] = Integer.parseInt(ls);
			}
		}
		return longSplit;
	}
	
	private static int[] split(String in) { //TODO set up a 2d array so multiple string can occupy the same data set
		int[] sIn = new int[in.length()];
		for(int i = 0; i < in.length(); i++) sIn[i] = in.charAt(i);
		return sIn;
	}
	/* Access methods
	 * First method covers receiving the type stored
	 * Remaining methods cover receiving data stored
	 */
	public String queryType(int i) { //Returns a string stating data type stored at index. Can be used for comparison in implementing class.
		switch(thisArray[i].getType()) {
		case 'B':
			return "Boolean";
		case 'C':
			return "Char";
		case 'D':
			return "Double";
		case 'F':
			return "Float";
		case 'I':
			return "Integer";
		case 'L':
			return "Long";
		case 'S':
			return "String";
		}
		return "Empty"; //Should throw an error instead
	}
	
	public boolean getBool(int i) {
		return thisArray[i].getData()[0] == 0 ? true : false;
	}
	
	public char getChar(int i) {
		return (char)thisArray[i].getData()[0];
	}
	
	public double getDouble(int i) {
		int[] doubleSplit = thisArray[i].getData();
		String compile = Integer.toString(doubleSplit[0]) + "." + Integer.toString(doubleSplit[1]);
		return Double.parseDouble(compile);
	}
	
	public float getFloat(int i) {
		int[] floatSplit = thisArray[i].getData();
		String compile = Integer.toString(floatSplit[0]) + "." + Integer.toString(floatSplit[1]);
		return Float.parseFloat(compile);
	}
	
	public int getInt(int i) {
		return thisArray[i].getData()[0];
	}
	
	public long getLong(int i) {
		int[] longSplit = thisArray[i].getData();
		String compile = Integer.toString(longSplit[0]) + Integer.toString(longSplit[1]);
		long out = Long.parseLong(compile);
		return out;
	}
	
	public String getString(int i) {
		int[] data = thisArray[i].getData();
		char[] deci = new char[data.length];
		int index = 0;
		for(int c : data) {
			deci[index] = (char)c;
			index++;
		}
		return new String(deci);
	}
	
	public int length() {		//In order to adhere to block doubling, need a way to identify empty storages. referencing type and checking for 'e' should work
		return thisArray.length - countEmpty();
	}
	
	public int length(int i) {
		return thisArray[i].getData().length;
	}
	
	private int countEmpty() {
		int count = 0;
		for(FAStorage t : thisArray) if(t.PARSE == 'e') count++;
		return count;
	}
	
	private class FAStorage {
		//TODO
		/* initialize storage int[]s
		 * present methods to set object values
		 */ 
		
		private int PARSE;
		private int[] data;
	
		private FAStorage() { //Create an empty storage to be assigned later.
		PARSE = 'e';
		}
		private FAStorage(int type, int... argument) { //Create new storage object, store supplied information
			PARSE = type;
			data = argument;
		}
			
		private void set(int type, int...argument) { //Rewrite currently stored information
			PARSE = type;
			data = argument;
		}
	
		private char getType() { //Return type stored
			return (char)PARSE;
		}
		
		private int[] getData() { //Return data stored
			return data;
		}
			
	}	
		
}
