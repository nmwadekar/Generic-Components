package nmw.dsa;

public class Pivot {

	public static void main(String[] args) {

		int[] pIn = new int[] { 100, 40, 32, 25 };

//		int[] pIn = new int[] { 5,2,4,6,1,3};
		
		System.out.println("PIVOT = " + getPivot(pIn));
	}

	public static int getPivot(int[] input) {

		int x = input[0];
		int y = input[((input.length) / 2)];
		int z = input[input.length - 1];

//		System.out.println("INPUT = " + x + "\t" + y + "\t" + z);

		int pivot = 0;
		int mean = (x + y + z) / 3;

		int[] pIn = new int[] { x, y, z };

//		System.out.println("MEAN = " + mean);

		int difference = Math.abs((pIn[0]) - mean);

		int tempo;

		for (int i = 0; i < 3; i++) {

			tempo = Math.abs((pIn[i]) - mean);

			if (difference > tempo) {

				difference = tempo;
//				pivot = pIn[i]; -- Use this to get actual value
				pivot = i;	// Use this to get index of pivot
			}
		}

		return getIndexFromPivot(pivot, input.length);
	}
	
	private static int getIndexFromPivot(int p, int length){
		
		int index;
		
		switch (p) {
		
		case 0:
			
			index = 0;
			
			break;

		case 1:
			
			index = (length / 2);
			
			break;
			
		case 2:
			
			index = (length - 1);
			
			break;
			
		default:
			
			index = 0;
			
			break;
			
		}
		
		return index;
		
	}

}
