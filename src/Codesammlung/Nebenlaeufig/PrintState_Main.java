package Codesammlung.Nebenlaeufig;

public class PrintState_Main {

	public static void main(String[] args) {
		PrintState main = new PrintState();
		new PrintState_Set(main).start(); 
		new PrintState_Print(main).start();
	}

}
