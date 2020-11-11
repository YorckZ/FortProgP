package Uebung_01.Aufgabe_1;

public class CounterMain {
	
	public static void main(String[] args){
		if (args.length <1) {
			System.err.println("No counter delay specified");
		}
		
		try{
			for (int i = 0; i < args.length; i++){
				startCounter(i+1, Long.parseLong(args[i]));
			}
		} catch (NumberFormatException e){
			System.err.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
	
	public static void startCounter(int num, long msec){
		
		String name = "Counter " + Integer.toString(num);
		
//		Thread t = new CounterThread(msec, name).start();
		Thread t = new Thread(new CounterThread(msec, name));
		
		t.start();
	}
}