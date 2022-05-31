package silveratm;

public class ATMState {

	//
	// 사용자가 입력하는 정보들
	
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

	/*
	 * 액션 리스너 지금 동작하는 화면과(현재 프로세스), 다음으로 넘어갈 화면을(다음 프로세스)를 멤버변수로 정의해논다. 작업 진행 상태를 정의
	 */
	/* 각 리스너에서는 그 값을 바꿔놓아야 한다. */
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
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
		this.remitName = remitName;
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
		this.remitAccount = remitAccount;
	}

	/**
	 * 
	 */
	public void init() {
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
}
