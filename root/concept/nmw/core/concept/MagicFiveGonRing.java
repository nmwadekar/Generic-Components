package nmw.dsa;

import java.util.*;

public class MagicFiveGonRing {

	private static Combinator combinor = new Combinator();

	public static void main(String[] args) {

		// List<int[]> intComb = null;
		// Set<String[]> combinations = null;

		Set<String[]> combinations = Combinator.find(0, new String[] { "1", "2", "3", "4", "5", "6" });

		combinor.resetOutput();

		// ArithmeticEvaluator.printx(combinations);

		List<int[]> intComb = convert(combinations);

		// formThreeRing(intComb);

		processThreeRing(combinations);

		// -- FOR 5 RING
		/*
		 * combinations = combinor.find(0, new String[]{"1", "2", "3", "4", "5",
		 * "6", "7", "8", "9", "10"});
		 * 
		 * ArithmeticEvaluator.printx(combinations);
		 * 
		 * intComb = convert(combinations);
		 * 
		 * formFiveRing(intComb);
		 */
	}

	private static void formThreeRing(List<int[]> input) {

		String format = "%d %d %d, %d %d %d, %d %d %d";

		for (int[] i : input) {

			if ((i[0] + i[1] + i[2]) == (i[3] + i[2] + i[4]) && (i[3] + i[2] + i[4]) == i[5] + i[4] + i[1]) {

				System.out.println(String.format(format, i[0], i[1], i[2], i[3], i[2], i[4], i[5], i[4], i[1]));

			}
		}
	}

	private static void processThreeRing(Set<String[]> input) {

		// String format = "%d %d %d, %d %d %d, %d %d %d";
		String format = "%s, %s, %s";

		String[][] resultant/* = new String[3][] */;

		String[] a, b, c;

		Set<String[]> combineSet = Combinator.find(0, new String[] { "0", "1", "2" });

		List<String[]> combineList = new ArrayList<String[]>(combineSet);

		for (String[] i : input) {

			a = new String[] { i[0], i[1], i[2] };
			b = new String[] { i[3], i[2], i[4] };
			c = new String[] { i[5], i[4], i[1] };

			resultant = new String[][] { a, b, c };

			for (String[] s : Collections.unmodifiableCollection(combineList)) {

				permuteThree(resultant[Integer.valueOf(s[0])], resultant[Integer.valueOf(s[1])],
						resultant[Integer.valueOf(s[2])]);
			}

			// permuteThree(a, b, c);
		}
	}

	private static Set<String[][]> permuteThree(String[]... input) {

		// Set<String> resultant = new HashSet<>();
		Set<String[][]> resultant = new HashSet<>();
		String[][] tempo;
		String[] junk;

		String[] a = input[0];
		String[] b = input[1];
		String[] c = input[2];

		List<String[]> aP = new ArrayList<>(Combinator.find(0, a));
		Combinator.resetOutput();

		List<String[]> bP = new ArrayList<>(Combinator.find(0, b));
		Combinator.resetOutput();

		List<String[]> cP = new ArrayList<>(Combinator.find(0, c));
		Combinator.resetOutput();

		for (int i = 0; i < aP.size(); i++) {

			for (int j = 0; j < bP.size(); j++) {

				for (int k = 0; k < cP.size(); k++) {

					if (((Integer.valueOf(aP.get(i)[0]) + Integer.valueOf(aP.get(i)[1])
							+ Integer.valueOf(aP.get(i)[2])) == ((Integer.valueOf(bP.get(j)[0])
									+ Integer.valueOf(bP.get(j)[1]) + Integer.valueOf(bP.get(j)[2]))))
							&& ((Integer.valueOf(cP.get(k)[0]) + Integer.valueOf(cP.get(k)[1])
									+ Integer.valueOf(cP.get(k)[2])) == ((Integer.valueOf(bP.get(j)[0])
											+ Integer.valueOf(bP.get(j)[1]) + Integer.valueOf(bP.get(j)[2]))))) {

						if ((aP.get(i)[2].equals(bP.get(j)[1])) && (cP.get(k)[1].equals(bP.get(j)[2]))
								&& (cP.get(k)[2].equals(aP.get(i)[1]))) {

							junk = combine(aP.get(i), bP.get(j), cP.get(k));

							tempo = new String[][] { aP.get(i), bP.get(j), cP.get(k) };

							// resultant.add(toString(tempo));
							resultant.add(tempo);

							System.out.println("########################## GOT IT " + toString(junk));
						}
					}

				}
			}

		}
		
		fetchUnique(resultant);

		// ArithmeticEvaluator.printSet(resultant);

		return resultant;
	}

	private static void fetchUnique(Set<String> input, int size) {

		List<String> lInput = new ArrayList<String>(input);

		for (int i = 0; i < input.size(); i += size) {

			for (int j = i; j < i + size; j++) {

			}
		}
	}
	
	private static String[][] fetchUnique(Set<String[][]> input){
		
		String[][] output = new String[input.size()][];
		
		for(String[][] s : input) {
			
			for(int i=0; i < s.length; i++) {
				
			}
		}
		
		
		return output;
	}

	private static String toString(String[] input) {

		StringBuilder output = new StringBuilder();

		for (int i = 0; i < input.length; i++) {

			output.append(input[i]);
		}

		return output.toString();
	}

	private static Set<String> concatenateArray(Set<String[]> input) {

		Set<String> output = new HashSet<>();

		for (String[] s : input) {

			output.add(Arrays.toString(s));
		}

		return output;
	}

	private static String[] combine(String[]... input) {

		String[] tempo, output = new String[input.length * input[0].length];

		int n = 0;

		for (int i = 0; i < input.length; i++) {

			tempo = input[i];

			for (int j = 0; j < tempo.length; j++) {

				output[n++] = tempo[j];
			}
		}

		return output;
	}

	private static int[] convert(String[] input) {

		int[] output = new int[input.length];

		for (int i = 0; i < input.length; i++) {

			output[i] = Integer.valueOf(input[i]);
		}

		return output;
	}

	private static void formFiveRing(List<int[]> input) {

		String format = "%d %d %d,%d %d %d,%d %d %d,%d %d %d,%d %d %d";

		for (int[] i : input) {

			// ArithmeticEvaluator.print(i);

			int a = i[0] + i[1] + i[2];
			int b = i[3] + i[2] + i[4];
			int c = i[5] + i[4] + i[6];
			int d = i[7] + i[6] + i[8];
			int e = i[9] + i[8] + i[1];

			if ((a == b) && (b == c) && (c == d) && (d == e)) {

				System.out.println(String.format(format, i[0], i[1], i[2], i[3], i[2], i[4], i[5], i[4], i[6], i[7],
						i[6], i[8], i[9], i[8], i[1]));

			}
		}
	}

	private static List<int[]> convert(Set<String[]> input) {

		List<int[]> output = new ArrayList<>(input.size());

		for (String[] s : input) {

			output.add(convert(s));
		}

		return output;
	}
}
