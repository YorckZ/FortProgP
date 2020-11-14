package Codesammlung.Nebenlaeufig;

public class ThreadWithNames extends Thread {

	private String s;
	
	public ThreadWithNames(String s) {
		this.s = s;
	}
	
	public void run() {
		while (true) {
			System.out.println(s + " " + getName() + " "); //getName() ist in Klasse Thread implementiert und wird geerbt; geht nicht bei implements Runnable.
		}
	}
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Thread t1 = new ExampleThread("Hallo");
		t1.setName("Test-Thread-01");
		
		Thread t2 = new ExampleThread("Moin");
		t2.setName("Test-Thread-02");
		
		t1.start();
		t2.start();
		
		try {
			t2.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		Thread t3 = new ExampleThread("Servus");
		t3.setName("Daemon");
		t3.setDaemon(true); // muss for start() erfolgen
		t3.start();
		
		
		t1.interrupt();
		boolean b = t1.isInterrupted();
		boolean d = t3.isDaemon();
		
		
		// TODO: Wie beende ich Threads?
	}

}