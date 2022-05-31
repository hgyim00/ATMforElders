package silveratm;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * @description 찾을 금액 액수를 입력하는 화면
 * @author hgyim00
 *
 */
public class InputWithdrawPage extends ATMPanel implements NumPad.Container {
	private static final long serialVersionUID = -5343097617141631903L;

	/**
	 * 금액 입력 단위
	 */
	private static final int AMOUNT_UNIT = 10000;

	ATM atm;
	JLabel label1 = null;
	JLabel label2 = null;
	JLabel withdrawAmount = null;
	NumPad numPad = null;
	int amount = 0;

	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 55);

		label1 = new JLabel("<html>찾을 금액을<br/>입력하세요.</html>");
		label1.setBounds(50, 50, 500, 200);
		label1.setFont(font);

		label2 = new JLabel("<html>출금은 만 원 단위로<br/>가능합니다.</html>");
		label2.setBounds(50, 250, 500, 200);
		label2.setFont(font);
		
		withdrawAmount = new JLabel(getAmountString());
		withdrawAmount.setHorizontalAlignment(JLabel.RIGHT);
		withdrawAmount.setBounds(50, ATM.PageHeight - 150, 500, 100);
		withdrawAmount.setFont(font);

		numPad = new NumPad(this);
		numPad.create();
		numPad.setBounds(ATM.PageWidth - 600, 50, 550, ATM.PageHeight - 100);

		this.add(label1);
		this.add(label2);
		this.add(withdrawAmount);
		this.add(numPad);
	}

	/**
	 * 페이지를 보인 후 실행된다.
	 */
	public void onShowPage() {
		amount = 0;
		withdrawAmount.setText(getAmountString());
	};

	/**
	 * Numpad에서 확인 버튼을 눌렀음을 처리
	 */
	public void onNumPadOK() {
		if (amount > 0) {
			atm.onInputWithdrawAmount(amount * AMOUNT_UNIT);
		}
	}

	/**
	 * Numpad에서 지우기 버튼을 눌렀음을 처리
	 */
	public void onNumPadDelete() {
		amount = amount / 10;
		withdrawAmount.setText(getAmountString());
	}

	/**
	 * Numpad에서 숫자 버튼을 눌렀음을 처리
	 */
	public void onNumPadNum(int num) {
		amount = amount * 10 + num;
		withdrawAmount.setText(getAmountString());
	}

	/**
	 * 금액 표시 문자열을 얻는다.
	 * 
	 * @return
	 */
	private String getAmountString() {
		return String.format("%d 만원", amount);
	}
}