package Uebung_02.Aufgabe_2;
//import java.util.concurrent.*;

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



public class Account {

    private double balance;
//    private Semaphore s = new Semaphore(1); // Nur 1 Zugriff zur Zeit

    public Account () {
    	balance = 0.00;
    }
    
    public Account(double initialDeposit) {
        balance = initialDeposit;
    }

    public synchronized void deposit(double amount) { // !!!!
        balance += amount;
    }
    
    public synchronized void withdraw(double amount) { // !!!!
    	balance -= amount;
    }
    
    public double getBalance() {
    	return balance;
    }
    
//    public double getBalance() {
//      s.acquireUninterruptibly(); // P-Operation
//    	double d = balance;
//    	s.release(); // V-Operation
//    	return d;
//    }

    
    
    
    
    public void transfer (int amount, Account target) {
    	// Geld auf ein anderes Konto überweisen
    	
    	// Die Überweisung soll "uno acto" erfolgen;

    	// Überweisung:
    	while (true) {
        	
    		this.withdraw(amount); // in dieser Methode ungeprüft
        	target.deposit(amount); // am Ziel unkritisch
    		
    	}
    }
    
    public void safeTransfer(int amount, Account target) {
    	// Geld auf ein anderes Konto überweisen, wenn ausreichend Deckung
    	
    	while (this.getBalance() < amount || amount <= 0.00 || target == this) { 
    		try {
    			wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		};

    	// Nach dem notify: check again
    	// Wenn mindestens so viel Guthaben besteht wie überwiesen werden soll, und
    	// wenn unsinnige Eingaben abgefangen sind (nichtpositive Beträge, Überweisung an sich selbst)
    	if (this.getBalance() >= amount 
    			&& amount > 0.00
    			&& target != this) {
        	this.withdraw(amount);
        	target.deposit(amount);
    	}
    	
    	
    }
}