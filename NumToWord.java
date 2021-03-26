/* Input number, return string that converts the input to word format.
 * I've already created method compatible with any size of int, jumping to use BigDecimal to allow reading of decimal places
 * Theoretically, only cap is the limitation of a string size, as more arrays can be created in sequence
 */

import java.math.BigDecimal;
import java.util.Arrays; //Used for debugging

public class NumToWord { 
	
	static BigDecimal store;
	static final BigDecimal zero = new BigDecimal(0);
	
	final static String[] count = { //1-19
			"",
			"One",
			"Two",
			"Three",
			"Four",
			"Five",
			"Six",
			"Seven",
			"Eight",
			"Nine",
			"Ten",
			"Eleven",
			"Twelve",
			"Thirteen",
			"Fourteen",
			"Fifteen",
			"Sixteen",
			"Seventeen",
			"Eighteen",
			"Nineteen"};
	final static String[] count2 = { //20-90
			"",
			"",
			"Twenty",
			"Thirty",
			"Fourty",
			"Fifty",
			"Sixty",
			"Seventy",
			"Eighty",
			"Ninety"};
	final static String[] count3 = { //Covers hundred's placement, and larger denominations
			"",
			"Hundred",
			"Thousand",
			"Million",
			"Billion",
			"Trillion",
			"Quadrillion",
			"Quintillion",
			"Hextillion",
			"Septillion",
			"Octillion",
			"Nonillion",
			"Decillion",
			"Undecillion",
			"Duodecillion",
			"Tredecillion",
			"Quattuordecillion",
			"Quindecillion",
			"Hexdecillion",
			"Septendecillion",
			"Octodecillion",
			"Vigintillion",
			"Unvigintillion",
			"Duovigintillion",
			"Trevigintillion",
			"Quattourvigintillion",
			"Hexvigintillion",
			"Septenvigintillion",
			"Octovigintillion",
			"Novemvigintillion",
			"Trigintillion",
			"Untrigintillion",
			"Duotrigintillion",
			"Googol"}; //Indefinitely expandable, single array limit on denomination is 10^(Integer.MAX_VALUE*3) I believe. I wouldn't use Java for this.
	
	final static String[] count4 = { //Covers decimal's designation
			"Tenths",
			"Hundreths",
			"Thousandths",
			"Ten-Thousanths",
			"Hundred-Thousandths",
			"Millionths",
			"Ten-Millionths",
			"Hundred-Millionths",
			"Billionths",
			"Ten-Billionths",
			"Hundred-Billionths",
			"Trillionths",
			"Ten-Trillionths",
			"Hundred-Trillionths",
			"Quadrillionths",
			"Ten-Quadriollionths",
			"Hundred-Quadriollionths",}; //Indefinitely expandable, can be merged with count3 with some code modification.
	
	public static String numConvert(String in) {
		in = in.replace(",", ""); //Remove commas from larger numerical values
		try {
			store = new BigDecimal(in); //Assign value to BigDecimal to ascertain if positive, retrieve absolute value, and remove insignificant zeros
		} catch(NumberFormatException ex) {
			return "Error! Ensure your input is a number!";
		}
		if(store.doubleValue() == 0) return "Zero";
		String out = store.compareTo(zero) < 0 ? "Negative " : ""; //Using BigDecimal to compare since doubles are fucky
		store = store.abs();
		in = store.toPlainString();
		boolean hasDecimal = false;
		int places = 0;
		int decimalAt = in.length(); //Only variable that technically isn't redundant
		int placesD = 0;
		//Count whole numbers and if there is--decimal location, and decimal numbers
		for(int i = 0; i < in.length(); i++) {
			if(in.charAt(i) == '.') {
				decimalAt = i;
				hasDecimal = true;
			}
			else if(!hasDecimal) places++;
			else placesD++;
		}
		int denote[] = new int[places%3 == 0 ? (places/3*2) : ((places/3*2)+1)]; //There's possibly a way to organize a simpler formula, MathContext?
		int denoteD[] = new int[placesD%3 == 0 ? (placesD/3*2) : ((placesD/3*2)+1)];
		
		//Separate string into integer arrays
		for(int i = decimalAt-1, r = denote.length-1; i >= 0; i-=3) { //Seek guidance on optimizing this
			if(i-2 >= 0) {
				denote[r] = ((in.charAt(i-1)-'0')*10)+in.charAt(i)-'0';
				denote[r-1] = in.charAt(i-2)-'0';
				r-=2;
			}
			else if(i-1 >= 0) denote[r] = ((in.charAt(i-1)-'0')*10)+in.charAt(i)-'0';
			else denote[r] = in.charAt(i)-'0';
		}
		if(hasDecimal) {
			for(int i = in.length()-1, r = denoteD.length-1; i > decimalAt; i-=3) {
				if(i-2 > decimalAt) {
					denoteD[r] = ((in.charAt(i-1)-'0')*10)+in.charAt(i)-'0';
					denoteD[r-1] = in.charAt(i-2)-'0';
					r-=2;
				}
				else if(i-1 > decimalAt) denoteD[r] = ((in.charAt(i-1)-'0')*10)+in.charAt(i)-'0';
				else denoteD[r] = in.charAt(i)-'0';
			}
		}
		//Read array to string sequentially
		for(int i = 0, c = denote.length, r = (denote.length/2)+(denote.length%2); i < denote.length; i++, c--) {
			if(denote[i]!=0) {
				if(denote[i] < 20) out = out.concat(count[denote[i]]).concat(" ");
				else if(denote[i]%10!=0) out = out.concat(count2[denote[i]/10]).concat("-").concat(count[denote[i]%10]).concat(" ");
				else out = out.concat(count2[denote[i]/10]).concat(" ");
			}
			if(c%2 == 0 && denote[i]!=0) out = out.concat(count3[1]).concat(" ");
			else if(c > 2 && denote[i] != 0){
				out = out.concat(count3[r]).concat(" ");
				r--;
			}
		}
		
		if(hasDecimal) {
			if(denote[0] != 0) out = out.concat("and ");
			for(int i = 0, c = denoteD.length, r = (denoteD.length/2)+(denoteD.length%2); i < denoteD.length; i++, c--) { //find a way to concat hundred if necessary, write a formula or something to figure out c
				if(denoteD[i]!=0) {
					if(denoteD[i] < 20) out = out.concat(count[denoteD[i]]).concat(" ");
					else if(denoteD[i]%10!=0) out = out.concat(count2[denoteD[i]/10]).concat("-").concat(count[denoteD[i]%10]).concat(" ");
					else out = out.concat(count2[denoteD[i]/10]).concat(" ");
				}
				if(c%2 == 0 && denoteD[i]!=0) out = out.concat(count3[1]).concat(" ");
				else if(c > 2 && denoteD[i] != 0){
					out = out.concat(count3[r]).concat(" ");
					r--;
				}
			}
			out = out.concat(count4[placesD-1]);
		}
		return out;
	}	
}
