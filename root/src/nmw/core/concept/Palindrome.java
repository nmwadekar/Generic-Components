package nmw.core.pet;

public class Palindrome {

	public static void main(String[] args) {
		
		String testNo = "123454322";
		
			for(int i=0, j=testNo.length()-1; j>=0 && i<testNo.length()-1 && i <= j; j--, i++){
				
//				System.out.println("i="+i + ", j= "+j);
//				System.out.println(testNo.toCharArray()[i] + "" + testNo.toCharArray()[j]);
				
				if(testNo.toCharArray()[i] != testNo.toCharArray()[j]){
					
					System.out.println(testNo.toCharArray()[i] + " NOT EQUAL " + testNo.toCharArray()[j] + " AT INDEX - " + j + " FOR INPUT - "+ testNo);
					System.out.println("NOT A PALINDROME, BREAKING");
				}
			}
	}
}
