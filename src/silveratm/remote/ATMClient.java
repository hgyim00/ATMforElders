package silveratm.remote;

import silveratm.ATM;

public class ATMClient {
	public static void main(String[] args) {
		String[] connectionInfo = { "127.0.0.1", "6487" };
		ATM.main(connectionInfo);
	}
}
