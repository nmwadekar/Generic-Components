package nmw.dsa;

public class QuickSort {

	private static int PIVOT_INDEX = 0;
	
	public static void main(String[] args) {

		int[] input = { 54, 26, 93, 17, 77, 31, 44, 55, 20 };

		// int[] input = {5,2,4,6,1,3};

		process(input);

		print(input, "OUTPUT");
	}

	public static int[] process(int[] input) {

		if (input.length < 2) {

			return input;

		}
		
		PIVOT_INDEX = Pivot.getPivot(input);

		sort(input);
		
		System.out.println("Pivot Index after sort = " + PIVOT_INDEX);

		int[][] splitted = divide(input, PIVOT_INDEX);

		int[] part_1 = splitted[0];
		int[] part_2 = splitted[1];

		print(part_1, "PART_1");
		print(part_2, "PART_2");
		
		process(part_1);
		process(part_2);
		
		int[] output =join(part_1, part_2, input[PIVOT_INDEX]);
		
		print(output, "FINAL INCREMENTAL");
		
		return output;

	}

	private static int[] sort(int[] input) {

		int pivot = input[PIVOT_INDEX];

		int l = 0, r = (input.length - 1), left, right, tempo;

		boolean l_swap = false, r_swap = false;

		print(input, "INPUT");

		System.out.println("\n PIVOT = " + pivot);

		for (int i = 0; i < input.length; i++) {

			left = input[l];
			right = input[r];

			print(input, "INPUT");
			System.out.println("VALUES = " + pivot + "\t" + left + "\t" + right);
			System.out.println("INDEX  i = " + i + " l=" + l + " r= " + r);

			if (l == r) {

				tempo = input[r];
				input[r] = input[PIVOT_INDEX];
				input[PIVOT_INDEX] = tempo;

				PIVOT_INDEX = r;

				System.out.println("BREAKING at pivot index - " + PIVOT_INDEX);

				break;
			}

			if (left > pivot) {

				l_swap = true;

			} else {
				++l;
			}

			if (right < pivot) {

				r_swap = true;

			} else {

				r--;
			}

			if (l_swap && r_swap) {

				tempo = input[r];
				input[r] = input[l];
				input[l] = tempo;
			}
		}

		print(input, "intermediatory output");

		return input;
	}

	private static int[][] divide(int[] input, int pivotIndex) {

		int part_1_length = Math.abs(pivotIndex);
		int part_2_length = Math.abs((input.length - pivotIndex) - 1);

		int[] part_1 = new int[part_1_length];
		int[] part_2 = new int[part_2_length];

		fillArray(input, part_1, 0, part_1_length);
		fillArray(input, part_2, pivotIndex + 1, input.length);

		int[][] output = { part_1, part_2 };

		return output;
	}
	
	private static int[] join(int[] part_1, int[] part_2, int pivot){
		
		int total_length = part_1.length + part_2.length + 1;
		
		int[] output = new int[total_length];

		fillArray(part_1, output, 0);
		output[part_1.length] = pivot;
		fillArray(part_2, output, part_1.length+1);
		
		return output;
	}

	private static void fillArray(int[] input, int[] part, int start, int end) {

		for (int i = start; i < end; i++) {

			part[i - start] = input[i];
		}
	}
	
	private static void fillArray(int[] input, int[] output, int start) {

		for (int i = start; i < (start+input.length); i++) {

			output[i] = input[i- start];
		}
	}

	private static void print(int[] input, String text) {

		System.out.print("\n " + text);

		for (int i = 0; i < input.length; i++) {

			System.out.print("\t" + input[i]);
		}

		System.out.println();
	}

}
