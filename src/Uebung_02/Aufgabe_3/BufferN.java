package Uebung_02.Aufgabe_3;

public class BufferN<E> {
	private E[] buffer;

	public BufferN (int n) {
		if (n <= 0){
			throw new IllegalArgumentException();
		} else {
			this.buffer = (E[]) new Object[n];
		}
	}
	
	public synchronized E take() throws InterruptedException {
		// nimmt ersten Eintrag aus dem Array und löscht diesen; sonst suspendiere

		while(this.isEmpty()){
			wait();
		}

		E value = this.buffer[0];

		for (int i = 0; i < this.buffer.length; i++) {
			if (i == this.buffer.length - 1){
				this.buffer[i] = null;
			} else {
				this.buffer[i] = this.buffer[i + 1];
			}
		}
		notifyAll();
		return value;
	}
	
	public synchronized void put(E elem) throws InterruptedException {
		// Füge Wert hinzu, wenn E[] nicht voll; sonst suspendiere
		while(this.isFull()){
			wait();
		}
		for (int i = 0; i < this.buffer.length; i++){
			if(this.buffer[i] == null){
				this.buffer[i] = elem;
			}
		}
		notifyAll();
	}
	
	public synchronized boolean isEmpty() {

		if (this.buffer[0] == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public synchronized boolean isFull(){
		for(E elem : this.buffer){
			if (elem == null) {
				return false;
			}
		}
		return true;
	}
}