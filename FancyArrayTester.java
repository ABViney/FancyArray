
public class FancyArrayTester {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FancyArray test = new FancyArray();
		test.addBool(true);						//0
		test.addChar('a');						//1
		test.addDouble(2.562);					//2
		test.addFloat(0.523f);					//3
		test.addInt(50);						//4
		test.addLong(1234567890123L);	//5
		test.addString("I'm walkin' here!");	//6
		
		System.out.println(test.queryType(0));
		System.out.println(test.getBool(0)); //0

		System.out.println(test.queryType(1));
		System.out.println(test.getChar(1)); //1
		
		System.out.println(test.queryType(2));
		System.out.println(test.getDouble(2)); //2
		
		System.out.println(test.queryType(3));
		System.out.println(test.getFloat(3)); //3
		
		System.out.println(test.queryType(4));
		System.out.println(test.getInt(4)); //4
		
		System.out.println(test.queryType(5));
		System.out.println(test.getLong(5)); //5
		
		System.out.println(test.queryType(6));
		System.out.println(test.getString(6)); //6
		System.out.println(test.length());
		
		test.delete(2);
		test.delete(2);
		
		System.out.println(test.length());
		
		System.out.print("Test complete");
	}

}
