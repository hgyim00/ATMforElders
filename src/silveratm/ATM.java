package silveratm;

import javax.swing.JFrame;


/**
 * @description
 * @author SWE_Team2
 * @since 2021.5
 */
public class ATM extends JFrame {
	private static final long serialVersionUID = -1020649619583379796L;

	/**
	 * ATM 화면 가로 크기
	 */
	public static final int PageWidth = 1200;

	/**
	 * ATM 화면 세로 크기
	 */
	public static final int PageHeight = 800;

	public enum PINUse {
		WITHDRAW, // 출금
		REMIT // 송금
	}
	
	// 모든 페이지 선언
	public ActivitySelectionPage activitySelectionPage;
	public ConfirmRemitPage confirmRemitPage;
	public FailNoticeCardPage failNoticeCardPage;
	public FailNoticePINPage failNoticePINPage;
	public FailNoticeRemitPage failNoticeRemitPage;
	public FailNoticeWithdrawPage failNoticeWithdrawPage;
	public InitialPage initialPage;
	public InputAccountNumPage inputAccountNumPage;
	public InputPINPage inputPINPage;
	public InputRemitAmountPage inputRemitAmountPage;
	public InputWithdrawPage inputWithdrawPage;
	public InsertionCardPage insertionCardPage;
	public InsertionMoneyPage insertionMoneyPage;
	public SuccessDepositPage successDepositPage;
	public SuccessRemitPage successRemitPage;
	public SuccessUpdateBankbookPage successUpdateBankbookPage;
	public SuccessWithdrawPage successWithdrawPage;
	public TimeoutEjectPage timeoutEjectPage;
	public UpdateBankbookPage updateBankBookPage;
	public EjectCardPage ejectCardPage;

	private ATMPanel curPanel = null;
	private ATMService atmService = new LocalATMService();
	private ATMState atmState = new ATMState();

	public void create() {
		this.initComponents();

		java.awt.Rectangle bound = getGraphicsConfiguration().getBounds();
		int w = PageWidth + 6;
		int h = PageHeight + 28;
		int x = (bound.x + bound.width / 2) - w / 2;
		int y = (bound.y + bound.height / 2) - h / 2;
		setBounds(x, y, w, h);

	}
	
	private void initComponents() {
		(activitySelectionPage = new ActivitySelectionPage()).create(this);
		(confirmRemitPage = new ConfirmRemitPage()).create(this);
		(failNoticeCardPage = new FailNoticeCardPage()).create(this);
		(failNoticePINPage = new FailNoticePINPage()).create(this);
		(failNoticeRemitPage = new FailNoticeRemitPage()).create(this);
		(failNoticeWithdrawPage = new FailNoticeWithdrawPage()).create(this);
		(initialPage = new InitialPage()).create(this);
		(inputAccountNumPage = new InputAccountNumPage()).create(this);
		(inputPINPage = new InputPINPage()).create(this);
		(inputRemitAmountPage = new InputRemitAmountPage()).create(this);
		(inputWithdrawPage = new InputWithdrawPage()).create(this);
		(insertionCardPage = new InsertionCardPage()).create(this);
		(insertionMoneyPage = new InsertionMoneyPage()).create(this);
		(successDepositPage = new SuccessDepositPage()).create(this);
		(successRemitPage = new SuccessRemitPage()).create(this);
		(successUpdateBankbookPage = new SuccessUpdateBankbookPage()).create(this);
		(successWithdrawPage = new SuccessWithdrawPage()).create(this);
		(timeoutEjectPage = new TimeoutEjectPage()).create(this);
		(updateBankBookPage = new UpdateBankbookPage()).create(this);
		(ejectCardPage = new EjectCardPage()).create(this);
	}

	public void showPage(ATMPanel panel) {
		if (curPanel != null) {
			remove(curPanel);
			curPanel.onHidePage();
		}
		curPanel = panel;
		add(curPanel);
		validate();
		curPanel.onShowPage();
	}

	/**
	 * 카드가 삽입되면 실행된다.
	 */
	public void onInitialPage() {
		// ATM 상태를 초기화 한다.
		atmState.init();
		
		/// 초기 화면으로 전환한다.
		showPage(initialPage);
	}

	/**
	 * 카드가 삽입되면 실행된다.
	 */
	public void onInsertCard() {
		/// PIN 입력 화면으로 전환한다.
		showPage(insertionCardPage);
	}

	/**
	 * 입력된 카드 검증을 실행한다.
	 */
	public void onValidateCard(String cardNum, String securityCd) {
		atmState.setCardNum(cardNum);
		atmState.setSecurityCode(securityCd);
		if(atmService.validateCard(atmState)) {
			showPage(activitySelectionPage);
		}else { //카드 인증 실패
			atmState.init();
			showPage(failNoticeCardPage);
		}
	}

	/**
	 * 작업을 선택한다.
	 */
	public void onActivitySelection() {
		showPage(activitySelectionPage);
	}

	/**
	 * 1분간 압력이 없을 시 초기 화면으로 돌아가기
	 */
	public void onCardInsertionTimeout() {
		showPage(timeoutEjectPage);
	}

	/**
	 * 3p. 카드를 다시 입력하는 창
	 */
	public void onReInputCard() {
		atmState.setCardNum(null);
		atmState.setSecurityCode(null);
		showPage(initialPage);
	}

	/**
	 * 돈찾기 화면으로 넘어가기.
	 */
	public void onOpenWithdraw() {
		showPage(inputWithdrawPage);
	}
	/**
	 * 입금 화면으로 넘어가기.
	 */
	public void onOpenDeposit() {
		showPage(insertionMoneyPage);
	}
	/**
	 * 계좌번호 입력 화면으로 넘어가기.
	 */
	public void onOpenRemit() {
		showPage(inputAccountNumPage);
	}
	/**
	 * 통장 정리 화면으로 넘어가기.
	 */
	public void onOpenUpdateBankbook() {
		showPage(updateBankBookPage);
		atmService.updateBankbook(atmState);
	}
	
	/**
	 * 찾을 금액을 입력받음
	 * @param amount
	 */
	public void onInputWithdrawAmount(int amount) {
		atmState.setWithdrawAmount(amount);
		inputPINPage.setPINUse(PINUse.WITHDRAW);
		showPage(inputPINPage);
	}

	/**
	 * 입금할 금액을 입력받음
	 * @param amount
	 */
	public void onInputDepositAmount(int amount) {
		int prevBalance = atmState.getBalance();

		atmState.setDepositAmount(amount);
		atmService.deposit(atmState);

		successDepositPage.setPrevBalance(prevBalance);
		successDepositPage.setDepositAmount(amount);;
		successDepositPage.setCummission(atmState.getCummission());
		successDepositPage.setCurBalance(atmState.getBalance());
		showPage(successDepositPage);
	}

	/**
	 * 송금할 계좌를 입력받음
	 * @param remitAccount
	 */
	public void onInputRemitAccount(String remitAccount) {
		atmState.setRemitAccount(remitAccount);
		showPage(inputRemitAmountPage);
	}

	/**
	 * 송금할 금액을 입력받음
	 * @param amount
	 */
	public void onInputRemitAmount(int amount) {
		atmState.setRemitAmount(amount);
		inputPINPage.setPINUse(PINUse.REMIT);
		showPage(inputPINPage);
	}

	/**
	 * @description PIN입력 사용처: 출금할떄, 송금할 때
	 * @param pin
	 */
	public void onInputPIN(String pin, PINUse pinUse) {
		atmState.setPin(pin);
		if(atmService.validatePin(atmState)) {
			switch (pinUse) {
			case WITHDRAW:
				onInputWithdrawPIN();
				break;
			case REMIT:
				onInputRemitPIN();
				break;
			}
		}else {
			//핀 번호 오류화면
			failNoticePINPage.setPINUse(pinUse);
			showPage(failNoticePINPage);
		}
	}

	/**
	 * 
	 */
	public void onReinputPIN(PINUse pinUse) {
		inputPINPage.setPINUse(pinUse);
		showPage(inputPINPage);
	}

	/**
	 * 
	 */
	public void onReinputRemit() {
		atmState.setRemitAmount(0);
		showPage(inputRemitAmountPage);
	}

	/**
	 * 
	 */
	public void onEjectCard() {
		atmState.init();
		showPage(ejectCardPage);
	}

	/**
	 * 
	 */
	public void onInputWithdrawPIN() {
		if(atmService.withdraw(atmState)) {
			//성공화면으로 넘어가고
			successWithdrawPage.setAmount(atmState.getWithdrawAmount());
			successWithdrawPage.setCummission(atmState.getCummission());
			successWithdrawPage.setBalance(atmState.getBalance());
			showPage(successWithdrawPage);
		}else {
			//금액 부족화면
			showPage(failNoticeWithdrawPage);
		}
	}

	/**
	 * 
	 */
	public void onInputRemitPIN() {
		confirmRemitPage.setRemitAccount(atmState.getRemitAccount());
		confirmRemitPage.setRemitName(atmState.getRemitName());
		confirmRemitPage.setRemitAmount(atmState.getRemitAmount());
		confirmRemitPage.setMyName(atmState.getMyName());
		showPage(confirmRemitPage);
	}

	/**
	 * 
	 */
	public void onConfirmRemit() {
		if (atmService.remit(atmState)) {
			// 성공화면으로 전환
			successRemitPage.setRemitAmount(atmState.getRemitAmount());
			successRemitPage.setCummission(atmState.getCummission());
			successRemitPage.setBalance(atmState.getBalance());
			showPage(successRemitPage);
		} else {
			// 실패화면으로 전환
			showPage(failNoticeRemitPage);
		}
	}

	/**
	 * 
	 */
	public void onSuccessUpdateBankbook() {
		showPage(successUpdateBankbookPage);
	}

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		ATM atm = new ATM();
		atm.create();
		atm.setResizable(false);
		atm.onInitialPage();
		atm.setVisible(true);
		atm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

