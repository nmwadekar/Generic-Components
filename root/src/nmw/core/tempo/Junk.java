package nmw.core.tempo;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class Junk {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		for(int i=1; i<11; i++ )		
//			System.out.println(i%20);
		
		System.out.println(0.02f + 0.03f);
		
		 Instant start = Instant.now();
		 
		 try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Duration.between(start, Instant.now()).getSeconds());
	}

}
