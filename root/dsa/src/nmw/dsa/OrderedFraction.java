package nucleus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class OrderedFraction {

	public static void main(String[] args) {

		String output = "";

//		List<Entry<Integer, Integer>> fractions = new ArrayList<>();

		float tempo = 0, upper = (float) 3 / 7, lower = ((float) 3 / 8), gotIt = lower;

		int limit = 1000000;
		
//		SimpleEntry<Integer, Integer>  finalOutput = null;

		for (int i = limit; i > 0 ; i--) {

			for (int j = limit; j > i && j != i; j--) {

				tempo = (float) i / j;

				if (tempo > lower && tempo < upper && fractionHCF(i, j)) {

					if(tempo > gotIt) {
						
						gotIt = tempo;
						output = i+"/"+j;
					}
				}
			}
		}

		 System.out.println("OUTPUT = " + output + " = " + gotIt );
	}

	private static boolean HCF(int n, int d) {

		int[] prime = { 2, 3, 5, 7, 11, 13 };

		boolean output = false;

		for (int i = 0; i < prime.length && !output; i++) {

			output = isDivisible(n, d, prime[i]);
		}

		return output;
	}

	private static boolean isDivisible(int n, int d, int divisor) {

		boolean output = n % d == 0 || (n % divisor == 0 && d % divisor == 0);

		return output;
	}

	private static void sortFraction(List<Entry<Integer, Integer>> input) {

		CompareFraction comparator = new CompareFraction();

		Collections.sort(input, comparator);

		System.out.println(input);
	}

	private static class CompareFraction implements Comparator<Entry<Integer, Integer>> {

		@Override
		public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {

			Float f1 = (float) o1.getKey() / o1.getValue();
			Float f2 = (float) o2.getKey() / o2.getValue();

			return f1.compareTo(f2);
		}
	}
	
	private static int[] getPrimes(int limit) {
		
		int[] output = new int[] {};
		
		for(int i=0; i <= limit/2; i++) {
			
		}
		
		return output;
	}
	
	private static boolean isPrime(int limit) {
		
		int j = 0;
		
		for(int i=2; i <= limit/2 && j < 2; i++) {
			
			if(limit/i == 0) {
				
				j+=1;
			}
		}
		
		return j == 1;
	}
	
	
	private static boolean fractionHCF(int i, int j) {
		
		int o = 1;
		
		for(int k = 2; o < 2 && (i%k ==0 && j%k == 0) && k <= (i+j)/2; k++) {
			
				o += 1;
		}
		
//		System.out.println(String.format("HCF for %d/%d = %d", i, j, o));
		
		return o == 1;
	}
}
