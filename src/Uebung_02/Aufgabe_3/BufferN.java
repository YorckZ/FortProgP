package Uebung_02.Aufgabe_3;

public class BufferN<E> {
	private E[] buffer;

	public BufferN (int n) {
		if (n <= 0){
			throw new IllegalArgumentException();
		} else {
			this.buffer = (E[]) new Object[n]; // es wird ein Array der Länge n (Kapazität) vom Typ E angelegt
		}
	}
	
	public synchronized E take() throws InterruptedException {
		// liest den ältesten Eintrag aus dem Buffer aus und gibt ihn zurück

		while(this.isEmpty()){ // ist der Buffer leer, suspendiert der Prozess
			wait();
		}

		E value = this.buffer[0];

		for (int i = 0; i < this.buffer.length; i++) { // nach dem Auslesen wird jeder Eintrag im Array "um eine Position nach vorne geschoben" ...
			if (i == this.buffer.length - 1){		   // ... und der letzte Eintrag null gesetzt.
				this.buffer[i] = null;				   // So wird sichergestellt, dass immer der erste Eintrag des Arrays ausgelesen werden kann
			} else {
				this.buffer[i] = this.buffer[i + 1];
			}
		}
		notifyAll();
		return value;
	}
	
	public synchronized void put(E elem) throws InterruptedException {
		// Füge Wert hinzu, wenn E[] nicht voll; sonst suspendiere
		
		while(this.isFull()){ // ist der Buffer voll, suspendiert der Prozess
			wait();
		}
		for (int i = 0; i < this.buffer.length; i++){ // Der Eintrag wird an die erste "leere" Position des Arrays geschrieben
			if(this.buffer[i] == null){
				this.buffer[i] = elem;
				break;
			}
		}
		notifyAll();
	}
	
	public synchronized boolean isEmpty() {
		// Gibt zurück, ob der Buffer leer ist 

		if (this.buffer[0] == null) { // Da immer an die erste leere Position geschrieben und nach dem Entnehmen die Einträge an den Anfang des Buffers verschoben werden, ...
			return true;			  // ... reicht die Feststellung, dass der erste Eintrag im Buffer leer ist
		}else {
			return false;
		}
	}
	
	public synchronized boolean isFull(){
		// Gibt zurück ob der Buffer voll ist
		
		if (buffer[buffer.length - 1] == null) { // Wenn der letzte Eintrag im Buffer null ist, so ist der Buffer nicht voll
			return false;
		} else { // andernfalls ist er voll
		return true;
		}
	}
}