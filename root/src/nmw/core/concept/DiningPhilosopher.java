package nmw.core.concept;

public class DiningPhilosopher {

	private final static int RESOURCE_COUNT = 5;
	
	public static void main(String[] args) {
		
		/*int output = Integer.numberOfLeadingZeros(12);
		
		System.out.println(output);
		
		System.out.println(""+(10>>>2)+"####"+(10>>2)+"####"+Integer.toBinaryString(12));*/
		
		Philosopher[] p = new Philosopher[RESOURCE_COUNT];
		Thread[] t = new Thread[RESOURCE_COUNT];
		
		DiningPhilosopher dp = new DiningPhilosopher();
		
		Table table = dp.new Table(); 
		
		for(int i=0; i<RESOURCE_COUNT; i++){
			
			p[i] = dp.new Philosopher(i, table);
			t[i] = new Thread(p[i]);
			t[i].start();
			
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean[] initSpoons(){
		
		boolean[] spoons = new boolean[RESOURCE_COUNT];
		
		for(int i=0; i< RESOURCE_COUNT; i++)
			spoons[i] = true;
		
		return spoons;
	}
	
	private static int[] initRicePLate(){
		
		int[] ricePlate = new int[RESOURCE_COUNT];
		
		for(int i=0; i< RESOURCE_COUNT; i++)
			ricePlate[i] = RESOURCE_COUNT;
		
		return ricePlate;
	}
	
	public class Table {
		
		boolean[] spoons = initSpoons();
		int[] rice = initRicePLate();
	}
	
	public class Philosopher implements Runnable {

/*		;
		private boolean[] spoons;
		private int[] rice;*/
		
		private int pid;
		private Table t;
		
		public Philosopher(int pid, Table table) {
			
			super();
			
			this.pid = pid;
			t = table;
		}

		@Override
		public void run() {
			
			for(int i=0; i < RESOURCE_COUNT; i++){
				
				tryEating(pid/*, t*/);
				
				try {
					Thread.sleep(2000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
		}
		
		private void printResources(int pid){
			
		}
		
		private boolean[] fetchSpoons(final int pid){
			
			boolean[] output = new boolean[2];
			
			output[0] = t.spoons[pid];
			
			int secondSpoon = 0;
			
			if(pid-1 < 0)
				secondSpoon = t.spoons.length - 1;
			else 
				secondSpoon = pid-1;
			
			output[1] = t.spoons[secondSpoon];
			
			System.out.println("PHILOSOPHER - "+ pid +" WITH SPOON - "+ pid+output[0] + " & " + secondSpoon+output[1]);
			
			return output;
		}

		private void pickSpoons(final int pid){
			
			t.spoons[pid] = false;
			
			if(pid-1 < 0){
				
				t.spoons[t.spoons.length - 1] = false;
				
			}else{
				
				t.spoons[pid-1] = false;
			}
		}
		
		private void dropSpoons(final int pid){
			
			t.spoons[pid] = true;
			
			if(pid-1 < 0){
				
				t.spoons[t.spoons.length - 1] = true;
				
			}else{
				
				t.spoons[pid-1] = true;
			}
		}
		
		private void eatRice(int pid){
			
			if(t.rice[pid] > 0)
				t.rice[pid] = --t.rice[pid];
			
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private void tryEating(int pid/*, Table t*/){
			
			boolean[] mySpoon = fetchSpoons(pid);
			
			System.out.println(String.format("PHILOSOPHER %s IS TRYING TO EAT",pid));
			
			if(mySpoon[0] == true && true == mySpoon[1]){
				
				pickSpoons(pid);
				
				System.out.println(String.format("PHILOSOPHER %s IS TRYING TO EAT WITH %b & %b AND HAS EATEN RICE %s",pid,mySpoon[0], mySpoon[1], t.rice[pid]));
				
//				if(mySpoon[0] == true && mySpoon[1] == true){
					
					/*try {
						Thread.sleep(2000l);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					
//					mySpoon[0] = true; mySpoon[1] = true;
					
					eatRice(pid);
					
					System.out.println("EATEN RICE BY - "+pid);
					
					dropSpoons(pid);
//				}
				
			}
		}
	}

}
