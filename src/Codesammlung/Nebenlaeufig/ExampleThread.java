package Codesammlung.Nebenlaeufig;

public class ExampleThread extends Thread { // Wichtig: extends Thread

	private String s;
	
	public ExampleThread(String s) {
		this.s = s;
	}
	
	public void run() { // Wichtig: public void run() muss implementiert werden
		while (true) {
			System.out.println(s + " ");
		}
	}
	
	
	public static void main(String[] args) {
		new ExampleThread("Hallo").start(); // So wird ein "extends Thread"-Thread gestartet

		// Vergleichsweise: Aufruf bei Interface
		// new Thread(new ExampleInterface("Hallo")).start();
	}

}
