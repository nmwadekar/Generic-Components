package nmw.dsa;

public class InsertionSort {

	public static void main(String[] args) {
		
		int[] input = new int[]{5,2,4,6,1,3};
		
		print(input);
		
		sort(input);
		
		print(input);

	}

	public static void sort(int[] input) {

		int temp;
		
		for (int i = 1; i < input.length; i++) {

			for(int j = i; j > 0 && input[j] < input[j-1]; j--){
				
				temp = input[j];
				input[j] = input[j-1];
				input[j-1] = temp;
			}
			
		}
	}
	
	private static void print(int[] input){
		
		System.out.println();
		
		for(int i=0; i < input.length; i++){
			
			System.out.print("\t"+input[i]);
		}
	}

}
