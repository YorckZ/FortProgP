package Uebung_02.Aufgabe_3;
public class BufferN<E> {
	private E[] n;

	public BufferN (int capacity) {
		if (capacity <=0) {
			throw new IllegalArgumentException();
			}else {
				this.n = (E[]) new Object[capacity];
			}
	}
	
	public synchronized E take() {
		// nimmt ersten Eintrag aus dem Array und löscht diesen; sonst suspendiere
		return null;
	}
	
	public synchronized void put(E elem) {
		// Füge Wert hinzu, wenn E[] nicht voll; sonst suspendiere
	}
	
	public synchronized boolean isEmpty() {
		// P(E);
		boolean rv;
		if (n.length >0) {
			rv = false;
		}else {
			rv = true;
		}
		// V(E)
		return rv;
	}
}
