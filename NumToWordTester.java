
import java.util.Scanner;
import java.util.Random;

public class NumToWordTester {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String test = "fuck";
		Random send = new Random();
		for(int i = 0; i < 10; i++) {
			test = in.nextLine();
			if(test.equalsIgnoreCase("quit")) break;
			System.out.println(NumToWord.numConvert(test));
		}
		//System.out.println(NumToWord.numConvert("1050.450"));
		in.close();
	}

}
