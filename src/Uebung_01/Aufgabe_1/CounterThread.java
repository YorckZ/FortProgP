package Uebung_01.Aufgabe_1;

public class CounterThread extends Thread{
// public class CounterThread implements Runnable{

	private long msec;
	private String name;
	private int value;
	
	public CounterThread(long msec, String name){
		this.msec = msec;
		this.name = name;
	}
	
	public void printName(){
		System.out.print(name);
	}
	
	public void printCounter(){
		System.out.print(value);
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(msec);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			synchronized (System.out){
				printName();
				System.out.print (": ");
				printCounter();
				System.out.println("");
				value++;
			}
		}
	}
}