package Uebung_01.Aufgabe_2;

public class Philosopher implements Runnable{
	private int id;
	private Object stickl;
	private Object stickr;
	
	public Philosopher (int id, Object left, Object right) {
		this.id = id;
		this.stickl = left;
		this.stickr = right;
	}
	
	private void snooze () {
	// lässt den Philosophen eine zufällige Dauer verweilen
		 try {
			int i = (int) (Math.random() * 1000); // generiert [0, 999]
			Thread.sleep(i); // 0 bis 999 Milisekunden verweilen => bis zu einer Sekunde
			System.out.println("Duration of activity " + String.valueOf(i));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void run() {
		while (true) {
			
			// Nachdenken:
			think();
			
			// Essen:
			eat();
						
		}
		
	}
	
	private void think() {
		System.out.println("Philosopher " + String.valueOf(this.id) + " starts thinking at " + System.currentTimeMillis());
		snooze();
		System.out.println("Philosopher " + String.valueOf(this.id) + " finishes thinking at " + System.currentTimeMillis());
	}
	
	private synchronized void eat() {
		
		// Erst linken Stick nehmen
		synchronized(stickl) { // Sperre auf das Objekt, das von hier mittels 'stickl' referenziert wird => ist für alle anderen Philosopher auch gesperrt.
			System.out.println("Philosopher " + String.valueOf(this.id) + " takes up left stick at " + System.currentTimeMillis());
			
			// Dann rechten Stick nehmen
			synchronized(stickr) { // Sperre auf das Objekt, das von hier mittels 'stickr' referenziert wird => ist für alle anderen Philosopher auch gesperrt.
				System.out.println("Philosopher " + String.valueOf(this.id) + " takes up right stick at " + System.currentTimeMillis());

				// Dann essen
				System.out.println("Philosopher " + String.valueOf(this.id) + " starts eating at " + System.currentTimeMillis());
				snooze();
				System.out.println("Philosopher " + String.valueOf(this.id) + " finishes eating at " + System.currentTimeMillis());

				System.out.println("Philosopher " + String.valueOf(this.id) + " puts back right stick at " + System.currentTimeMillis());
			} // Anschließend rechten Stick zurück legen
			
			System.out.println("Philosopher " + String.valueOf(this.id) + " puts back left stick at " + System.currentTimeMillis());
		};// Zuletzt linken Stick zurück legen
		
	}
	
}
