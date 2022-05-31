package silveratm;

public interface ATMService {
	/**
	 * 
	 * @param atmState 카드 정보
	 * @return
	 */
	boolean validateCard(ATMState atmState);

	/**
	 * 
	 * @param pin 인증 PIN
	 * @return
	 */
	boolean validatePin(ATMState atmState);

	/**
	 * 
	 * @param atmState 출금 정보
	 * @return
	 */
	boolean withdraw(ATMState atmState);

	/**
	 * 
	 * @param atmState 입금 정보
	 * @return
	 */
	boolean deposit(ATMState atmState);

	/**
	 * 
	 * @param atmState 송금 정보
	 * @return
	 */
	boolean remit(ATMState atmState);

	/**
	 * 
	 * @param atmState 계좌 정보
	 */
	void updateBankbook(ATMState atmState);
}
