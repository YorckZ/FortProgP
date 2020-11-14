package Codesammlung.Nebenlaeufig;

// Aufgabe: Wert soll erst gedruckt werden, nachdem er gesetzt wurde.
public class PrintState {
	private int state = 0;
//	private boolean changed; //default/inital: false
	private boolean modified = false; // 4. Anlauf
	
	public synchronized void printNewState() throws InterruptedException{
//		while (!changed) {} // solange nichts geändert wurde, soll {} passieren, also nichts => führt wegen synchronized zu Endlosschleife, weil niemand etwas changen kann
		// => Busy-waiting: arbeitet ständig, wartet aber nur darauf, dass sich etwas ändert
//		try { // wait will von try/catch umgeben werden oder ein Exception-Handling bekommen, ist im Vorlesungsbeispiel nicht der Fall
//			wait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println(state);
		
//		// 4. Anlauf
//		while (true) { // läuft immer
//			if (!modified) {
//				wait();
//			}else {
//				System.out.println(state);
//				modified = false;
////				notify();
//				notifyAll(); // Fünfter Anlauf
//			}

		// 6. Anlauf
		while (true) {
			while (!modified) {
				wait();
			}
			System.out.println(state);
			modified = false;
			notifyAll();
			}
		}
	
	public synchronized void setValue(int v) throws InterruptedException{
//		state = v;
//		System.out.println("value set to " + v);
//		changed = true;
//		notify(); // erweckt einen Thread; Position von notify() innerhalb der Methode ist egal, da das Lock erst freigegeben wird, wenn wir die synchronized-Methode verlassen haben
		
		
		// 4. Anlauf
//		notify();
//		state = v;
//		modified = true;
//		System.out.println("value set to " + v);

		// führt jetzt dazu, dass der erste Zustand geändert und nicht ausgegeben wird, erneut geändert und erst dann ausgegeben wird. Die erste Ausgabe geht also verloren. Also:
//		if (modified) { 
//			wait();
//		}
////		notify();
//		notifyAll(); // Fünfter Anlauf
//		state = v;
//		modified = true;
//		System.out.println("value set to " + v);
		
		
		// 6. Anlauf
		while (modified) { 
			wait();
		}
		notifyAll();
		state = v;
		modified = true;
		System.out.println("value set to " + v);
	}
	
	
	
	
	public static void main(String[] args) {
		// Thread 1: printNewState();
		// Thread 2: setValue(42);
		
		
		// Erster Anlauf: beide Methoden synchronized
							// kann verkehrte Reihenfolge haben, reicht nicht für Aufgabenstellung.
		// Zweiter Anlauf: synchronized ersetzt durch boolean changed
							// => busy-waiting, auch nicht gut
		// Dritter Anlauf: synchronized plus wait/notify.
							// wait() => suspendiere und gib Lock frei; notify() => wecke einen suspendierten Thread auf und mache selbst weiter
							// wir sind innerhalb von synchronized Methoden, der geweckte Thread darf nicht direkt weiter machen, sondern bewirbt sich erst einmal um das Lock
							// wait/notify/notifyAll nur innerhalb von synchronized benutzen !!! => zur Design-Time kein Fehler, zur Runtime crashed es, wenn synchronized fehlt.
							// printNewState könnte waiten und nie geweckt werden, also vierter Anlauf
		// Vierter Anlauf: modified eingeführt, if-Schleife in beiden Methoden, jeweils mit wait() wenn false bzw. true
							// immer noch Deadlock möglich, wenn der falsche Thread durch notify() aufgeweckt wird.
							// daher notifyAll() statt notify()
		// Fünfter Anlauf: notifyAll()
							// 1 Print und 3 Set können sich gegenseitig überschreiben. Bsp: print schläft; 1 setted, weckt print, der schläft wieder. 2 setted, weckt 1, 3 setted, weckt print
							// print zeigt den aktuellen Stand, der Stand von 2 wurde nicht ausgegeben. Lösung: nach dem Wecken muss ein Thread erst noch mal prüfen, ob er arbeiten kann.
							// muss nicht unbedingt einen Deadlock erzeugen, kann aber Daten "verschlucken"
		// Sechster Anlauf: while statt if
	
		
		// Objekt erzeugen
		PrintState ps = new PrintState();
		
		// Threads erzeugen
		Thread t1 = new Thread( () -> { 
				try {
					ps.printNewState();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		);
		
//		Thread t2 = new Thread(
//			() -> { ps.setValue(42); }
//		);
		
		Thread t2 = new Thread( () -> { 
				try {
					ps.setValue(42);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		);
		
//		Thread t3 = new Thread(
//			() -> { ps.setValue(99); }
//		);
			
		Thread t3 = new Thread( () -> { 
				try {
					ps.setValue(99);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		);

		
		
		// Threads starten
		// erst t1, dann t2 geht prima
//		t1.start();
//		t2.start();
		
		// erst t2, dann t1 kann schief gehen - dann wird t1 suspendiert, und es gibt keinen t2 mehr, der ihn notified.
//		t2.start();
//		t1.start();

		
		// neuer Anlauf: mit t3
		t2.start();
		t1.start();
		t3.start();

	}

}
