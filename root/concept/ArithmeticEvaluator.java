package nmw.core.concept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ArithmeticEvaluator {

	private static final char[] OPERATORS = new char[] { '+', '-', '/', '*' };
	
	public static void main(String[] args) {

		Set<String> operatorCombination = Combinator.find(0, OPERATORS);

		System.out.println(operatorCombination);

		System.out.println(roundRobin(4, 3, "+-/*".toCharArray()));
		
		alterPivot('-', "+++".toCharArray());
	}

	public static void combinate(int n, int r, char[] input) {

	}

	public static Set<String> roundRobin(int n, int r, char[] input) {

		for (int i = 0; i < input.length; i++) {

			for (int j = 0; j < r; j++) {

				System.out.print(input[(i + j) % n]);
			}

			System.out.println();
		}
		return null;
	}
	
	public static char[] fill(char c, int n){
		
		char[] output = new char[n];
		
		for(int i=0; i<n ; i++){
			
			output[i] = c;
		}
		
		return output;
	}

	public static List<char[]> alterPivot(char pivot, char[] input) {

		List<char[]> output = new ArrayList<char[]>();
		
		for (int i = 0; i < input.length; i++) {

			char[] o = Arrays.copyOf(input, input.length);
			
			o[i] = pivot;
			
			System.out.println(o);
			
			output.add(o);
		}
		
		return output;
	}
	
	public static void evaluate(){
		
		//TODO
		/*
		
			iterate over i of operators
				fill n times i
					pivot from operators  !current
		
		
		
		*/
	}
}
