package nmw.core.concept;

import java.util.HashSet;
import java.util.Set;

public class Combinator {

	private static Set<String[]> finalOutput = new HashSet<>();

	public static void main(String[] args) {

		Set<String[]> output = find(0, new String[] { "1", "2", "3" });

		System.out.println("output size " + output.size() + " : ");
		ArithmeticEvaluator.printx(output);
	}
	
	public static void resetOutput(){
		
		finalOutput = new HashSet<>();
	}

	public static Set<String[]> find(int key, String[] input) {

		if (key >= input.length)
			return null;

		permute(key, input);

		find(key + 1, input);

		return finalOutput;

	}

	public static void permute(int key, String[] input) {

		String[] o = reduceArray(input, key);

		finalOutput.add(build(o, input[key]));

		for (int i = 0; i < o.length - 1; i++) {

			int j = i + 1;

			String swapper = o[i];
			o[i] = o[j];
			o[j] = swapper;

			finalOutput.add(build(o, input[key]));

		}
	}

	public static String[] reduceArray(String[] input, int key) {

		String[] output = new String[input.length - 1];

		int j = 0;

		for (int i = 0; i < input.length; i++) {

			if (key != i)
				output[j] = input[i];
			else {
				continue;
			}

			j++;
		}

		return output;
	}

	private static String[] build(String[] strings, String prefix) {

		String[] sb = new String[strings.length+1];

		sb[0] = (prefix);

		for (int i=0; i < strings.length; i++) {

			sb[i+1] = strings[i];
		}

		return sb;
	}

}
