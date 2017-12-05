package core;

public class PascalTriangle {

	/**
	 * @param argsoutput[0]
	 */
	public static void main(String[] args) {

		int[] inpput = new int[1];
		
		inpput[0] = 1;
		
		System.out.println(" "+inpput[0]);
		
			recurrence(inpput);
		}
	
	public static int[] recurrence(int[] input){
			
		if(input.length > 20)
			System.exit(0);
		
		int output[] = new int[input.length+1];
		
		output[0] = input[0];
		System.out.print(" "+input[0] + " ");
		
		for(int i=0; i < input.length-1; i++){
			
			output[i+1] = input[i] + input[i+1];
			
			System.out.print(" "+ output[i+1] + " ");
		}
		
		output[input.length] = input[input.length-1];
		System.out.print(" " + output[input.length] + " \n");
		
		return recurrence(output);
	}

}
