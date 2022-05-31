package silveratm;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * @description 8p. PIN(계좌비밀번호)을 입력하는 페이지
 * @author hgyim00
 *
 */
public class InputPINPage extends ATMPanel implements NumPad.Container {
	private static final long serialVersionUID = -8514128775500157998L;

	ATM atm;
	
	/**
	 * 계좌 비밀번호를 입력하세요.
	 */
	JLabel label1;
	
	/**
	 * '****'
	 * 입력한 숫자가 실시간으로 보이도록. 단, '****'의 형태로 나타나야 한다.
	 */
	JLabel label2;

	/**
	 * 숫자 입력 화면
	 */
	NumPad numPad;

	/**
	 * 입력한 PIN 저장
	 */
	private StringBuffer pin = new StringBuffer();

	/**
	 * PIN 입력 사용처
	 */
	private ATM.PINUse pinUse;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 55);
		Font fonts = new Font("맑은 고딕", Font.PLAIN, 55);

		/* '계좌 비밀번호를 입력하세요.' */
		label1 = new JLabel("<html>계좌 비밀번호를<br/>입력하세요.</html>");
		label1.setBounds(50, 100, 500, 200);
		label1.setFont(font);

		label2 = new JLabel();
		label2.setBounds(50, 350, 500, 100);
		label2.setFont(fonts);

		numPad = new NumPad(this);
		numPad.create();
		numPad.setBounds(ATM.PageWidth - 600, 50, 550, ATM.PageHeight - 100);

		this.add(label1);
		this.add(label2);
		this.add(numPad);
	}

	/**
	 * 페이지를 보인 후 실행된다.
	 */
	public void onShowPage() {
		pin.setLength(0);
		label2.setText(getEncryptedPIN());
	};

	/**
	 * 입력하는 PIN 사용 용도를 지정한다.
	 * @param pinUse
	 */
	public void setPINUse(ATM.PINUse pinUse) {
		this.pinUse = pinUse;
	}

	/**
	 * Numpad에서 확인 버튼을 눌렀음을 처리
	 */
	public void onNumPadOK() {
		if (pin.length() > 0) {
			atm.onInputPIN(getPIN(), pinUse);
		}
	}

	/**
	 * Numpad에서 지우기 버튼을 눌렀음을 처리
	 */
	public void onNumPadDelete() {
		if (pin.length() > 0) {
			pin.deleteCharAt(pin.length() - 1);
			label2.setText(getEncryptedPIN());
		}
	}

	/**
	 * Numpad에서 숫자 버튼을 눌렀음을 처리
	 */
	public void onNumPadNum(int num) {
		pin.append(String.format("%d", num));
		label2.setText(getEncryptedPIN());
	}

	/**
	 * 
	 * @return
	 */
	private String getPIN() {
		return pin.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String getEncryptedPIN() {
		StringBuffer epin = new StringBuffer();
		int len = pin.length();
		int idx;
		for (idx = 0; idx < len; idx++) {
			epin.append('●');
		}
		return epin.toString();
	}
}