package nmw.core.concept;

public class SquareRootInteger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int x = 16;
		
		if( x < 0 || ((x&2) != 0) || ((x & 7) == 5) || ((x & 11) == 8) )
		    System.out.println(false);
		if( x == 0 )
		    System.out.println(true);
		

		System.out.println(x&2);
	}

}
