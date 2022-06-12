package silveratm;

public class ATMState {

	/**
	 * 메시지로 받은 경우 메시지 종류를 식별하는 문자열을 저장한다.
	 */
	private String messageType = null;
	
	/**
	 * 카드번호
	 */
	private String cardNum = null;

	/**
	 * Security code
	 */
	private String securityCode = null;

	/**
	 * 사용자명
	 */
	private String myName = null;

	/**
	 * PIN 번호
	 */
	private String pin = null;

	/**
	 * 잔액
	 */
	private int balance = 0;

	/**
	 * 찾을 금액
	 */
	private int withdrawAmount;

	/**
	 * 입금액(돈 넣기 페이지)
	 */
	private int depositAmount;

	/**
	 * 송금 계좌
	 */
	private String remitAccount;

	/**
	 * 송금 계좌 예금주 이름
	 */
	private String remitName;

	/**
	 * 송금액
	 */
	private int remitAmount;

	/**
	 * 수수료
	 */
	private int cummission;

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = checkNull(messageType);
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = checkNull(cardNum);
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = checkNull(securityCode);
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = checkNull(myName);
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = checkNull(pin);
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(int withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int depositAmount) {
		this.depositAmount = depositAmount;
	}

	public int getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(int remitAmount) {
		this.remitAmount = remitAmount;
	}

	public String getRemitName() {
		return remitName;
	}

	public void setRemitName(String remitName) {
		this.remitName = checkNull(remitName);
	}

	public int getCummission() {
		return cummission;
	}

	public void setCummission(int cummition) {
		this.cummission = cummition;
	}

	public String getRemitAccount() {
		return remitAccount;
	}

	public void setRemitAccount(String remitAccount) {
		this.remitAccount = checkNull(remitAccount);
	}

	/**
	 * 
	 */
	public void reset() {
		setMessageType(null);
		cardNum = null;
		securityCode = null;
		myName = null;
		pin = null;
		balance = 0;
		withdrawAmount = 0;
		depositAmount = 0;
		remitAccount = null;
		remitName = null;
		remitAmount = 0;
		cummission = 0;
	}

	/**
	 * 문자열이 null 이거나 "null"이면 null을 리턴하고 아니면 입력한 문자열을 리턴한다.
	 * @param str
	 * @return
	 */
	private String checkNull(String str) {
		if (str == null || "null".equals(str)) {
			return null;
		} else {
			return str;
		}
	}
}
