package Uebung_02.Aufgabe_4;

import java.util.concurrent.TimeoutException;

/**
 * Single element buffer with synchronization.
 *
 * @author FortProg team
 *
 * @param <E>
 *            Type of the element
 */

public class Buffer1<E> {

    // element + empty flag
    private E content;
    private boolean empty;

    // synchronization objects
    private Object r = new Object(); // take thread synchronisation
    private Object w = new Object(); // put  thread synchronisation

    public Buffer1() {
        empty = true;
    }

    public Buffer1(E content) {
        this.content = content;
        empty = false;
    }

    /**
     * take the element from the buffer; suspends on an empty buffer.
     *
     * @return element of the buffer
     * @throws InterruptedException
     */

    public E take() throws InterruptedException {
        synchronized (r) {
            while (empty) {
                r.wait();
            }
            synchronized (w) {
                empty = true;
                w.notify();
                return content;
            }
        }
    }

    /**
     * put an element into the buffer; suspends on a full buffer
     *
     * @param o
     *            Object to put into
     * @throws InterruptedException
     */

    public void put(E o) throws InterruptedException {
        synchronized (w) {
            while (!empty) {
                w.wait();
            }
            synchronized (r) {
                content = o;
                empty = false;
                r.notify();
            }
        }
    }

    /**
     * Return whether the buffer is empty
     *
     * @return true if empty
     */

    public boolean isEmpty() {
        return empty;
    }

    /**
     * Read the element from the buffer without emptying it; suspends on an
     * empty buffer.
     *
     * @return element of the buffer
     * @throws InterruptedException
     */

    public E read() throws InterruptedException { 
        synchronized(r) { // Synchronisation für take-Operationen
            while (empty) {
                r.wait(); // wird durch eine put-Operation geweckt
            }
        }
        return content;
    }

    /**
     * Try to put an element into the buffer; succeeds only for an empty buffer
     *
     * @param elem
     *            Element to put into
     * @return true if successful
     */

    public boolean tryPut(E elem) throws InterruptedException {
        synchronized(w){ // Synchronisation für put- und take-Operationen
        	synchronized(r){
	        	if(empty){ // nur einmalige Prüfung, ob Buffer leer
		            put(elem);
		            empty = false;
		            r.notify(); // wecke einen take-Prozess
		            return true;
		        } else {
		            return false;
		        }
        	}
        }
    }

    /**
     * Overwrite the element in the buffer, even if if the buffer is empty
     *
     * @param elem
     *            Element to overwrite with
     */
    public void overwrite(E elem) {
	    synchronized(w) { // Synchronisation für put- und take-Operationen	
    		synchronized(r){
	    		content = elem;
	    		empty = false; 
	    		r.notify(); // wecke einen take-Prozess
	    	}	
	    }	
    }

    /**
     * take with timeout. The timeout mechanism has to be handcrafted as there
     * is no way to detect whether a wait() was left because of a timeout or a
     * notify().
     *
     * @param timeout
     *            Maximum time to wait in milliseconds
     * @return
     * @throws InterruptedException
     * @throws TimeoutException
     *             if a timeout occurred
     */

    public E take(long timeout) throws InterruptedException, TimeoutException { 
    	synchronized (r) { // Synchronisation für take-Operationen
            if (empty) { // zunächst einmalige Prüfung, ob leer
            	long startTime = System.currentTimeMillis(); // messe die Systemzeit vor dem Warten
                r.wait(timeout); // warte max. die angegebene Zeit
                long endTime = System.currentTimeMillis(); // messe die Systemzeit nach dem Warten
                if (endTime >= startTime + timeout || empty) { // wenn timeout abgelaufen oder Buffer immer noch leer, ...
                	throw new TimeoutException(); // ... dann werfe Exception 
                }
            }
            // wenn gar nicht oder kürzer als timeout gewartet, dann geht es hier weiter
            synchronized (w) { // sync für put-Operationen
                empty = true;
                w.notify(); // wecke einen put-Prozess
                return content;
              
            }
        }
    }
}