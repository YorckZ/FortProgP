package Codesammlung.Nebenlaeufig;

// Aufgabe: Wert soll erst gedruckt werden, nachdem er gesetzt wurde.
public class PrintState {
	private int state = 0;
//	private boolean changed; //default/inital: false
	
	public synchronized void printNewState() {
//		while (!changed) {} // solange nichts geändert wurde, soll {} passieren, also nichts => führt wegen synchronized zu Endlosschleife, weil niemand etwas changen kann
		// => Busy-waiting: arbeitet ständig, wartet aber nur darauf, dass sich etwas ändert
		try { // wait will von try/catch umgeben werden oder ein Exception-Handling bekommen, ist im Vorlesungsbeispiel nicht der Fall
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(state);
	}
	
	public synchronized void setValue(int v) {
		state = v;
		System.out.println("value set to " + v);
//		changed = true;
		notify(); // erweckt einen Thread; Position von notify() innerhalb der Methode ist egal, da das Lock erst freigegeben wird, wenn wir die synchronized-Methode verlassen haben
	}
	
	
	
	
	public static void main(String[] args) {
		// Thread 1: printNewState();
		// Thread 2: setValue(42);
		
		
		// Erster Anlauf: beide Methoden synchronized => kann verkehrte Reihenfolge haben, reicht nicht für Aufgabenstellung.
		// Zweiter Anlauf: synchronized ersetzt durch boolean changed => busy-waiting
		// Dritter Anlauf: synchronized plus wait/notify.
							// wait() => suspendiere und gib Lock frei; notify() => wecke einen suspendierten Thread auf und mache selbst weiter
							// wir sind innerhalb von synchronized Methoden, der geweckte Thread darf nicht direkt weiter machen, sondern bewirbt sich erst einmal um das Lock
		// wait/notify/notifyAll nur innerhalb von synchronized benutzen !!! => zur Design-Time kein Fehler, zur Runtime crashed es, wenn synchronized fehlt.
	}

}
