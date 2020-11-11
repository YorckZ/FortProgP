package Uebung_02.Aufgabe_2;
import java.util.concurrent.*;

/*
In dieser Aufgabe sollen Sie mit der folgenden Vorlage der Account-Klasse arbeiten (der Code lässt sich per Klick auf Details anzeigen).

1. Erweiteren Sie die Klasse Account um eine Methode transfer, welche Geld von einem auf ein anderes Konto überweist. Definieren Sie auch eine Methode safeTransfer,
	welche nur überweist, falls genügend Geld vorhanden ist.

2. Das Paket java.util.concurrent enthält auch eine Klasse Semaphore (JavaDoc) , welche die Methoden acquireUninterruptibly und release für die Operationen P und V
	zur Verfügung stellt. Ersetzen Sie die synchronized-Methoden durch die Verwendung einer Semaphore zum Schützen der kritischen Bereiche.

3. Vergleichen Sie beide Implementierungen bezüglich ihres Verhaltens. Gibt es Unterschiede bei konkreten Verwendungen der Klasse Account? Wenn ja, erklären Sie die
	Ursache des Unterschieds.

4. Sowohl die Implementierung der Vorlesung (erweitert um Aufgabenteil 1) als auch die Implementierung mit Semaphoren haben Deadlocks. Überlegen Sie sich eine Variante,
	welche frei von Deadlocks ist.
*/


/*
 * Malte fragen:
 * Aufgabe 2: Überweisung wohin?
 * Aufgabenteile 2a und 2b aufeinander aufbauend oder separat?
 * s.acquireUninterruptibly(); und return?
 * 2.4: nur technische Deadlocks?
 */

public class Account {

    private double balance;
    private Semaphore s = new Semaphore(1);

    public Account(double initialDeposit) {
        balance = initialDeposit;
    }

//    public synchronized double getBalance() {
//        return balance;
//    }

    // Malte fragen
    public double getBalance() {
        s.acquireUninterruptibly(); // P-Operation
    	double d = balance;
    	s.release(); // V-Operation
    	return d;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }
    
    
    
    
    public void transfer (int amount, Account target) {
    	// Geld auf ein anderes Konto überweisen
    	
    	
    }
    
    public void safeTransfer(int amount) {
    	// Geld auf ein anderes Konto überweisen, wenn ausreichend Deckung
    	
    }
}