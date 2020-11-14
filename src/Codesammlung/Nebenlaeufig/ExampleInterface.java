package Codesammlung.Nebenlaeufig;

public class ExampleInterface implements Runnable{ // Wichtig: implements Runnable

	private String s;
	
	public ExampleInterface(String s) {
		this.s = s;
	}
	
	public void run() { // Wichtig: public void run() muss implementiert werden
		while (true) {
			System.out.println(s + " ");
		}
	}
	
	
	public static void main(String[] args) {
		new Thread(new ExampleInterface("Hallo")).start(); // So wird ein "implements Runnable"-Thread gestartet
		
		// Vergleichsweise: Aufruf bei extends Thread
		// new ExampleThread("Hallo").start();
	}

}
