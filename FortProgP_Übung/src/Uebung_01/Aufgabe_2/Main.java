package Uebung_01.Aufgabe_2;

public class Main {

	public static void main(String[] args) {
		
		// Annahme: Es wird nur ein Integer als Parameter übergeben
		int n = Integer.parseInt(args[0]);
		Philosopher[] Philosophers = new Philosopher[n];
		Object[] Sticks = new Object[n];
		
		
		// n Philosophen und Stick-Objekte aufbauen
		for (int i=0; i<n; i++) {
			Sticks[i] = new Object();
		}
		
		for (int i=0; i<n; i++) {
			if (i == n-1) {
				// Der letzte Philosoph bekommt den letzten und den ersten Stick
				Philosophers[i] = new Philosopher(i, Sticks[i], Sticks[0]);
			}else {
				// Alle anderen bekommen den aktuellen und den nachfolgenden Stick
				Philosophers[i] = new Philosopher(i, Sticks[i], Sticks[i+1]);
			}
			System.out.println("Philosopher " + i + " erstellt.");
		}
		
		
		// n Philosophen starten
		for (int i=0; i<n; i++) {
			new Thread(Philosophers[i]).start();
			System.out.println("Philosopher " + i + " gestartet.");
		}
		
	}
	
}
