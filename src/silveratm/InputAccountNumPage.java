package silveratm;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * @description 14p.입금할 계좌번호를 입력하는 페이지
 * @author hgyim00
 *
 */
////화면 구성 끝
public class InputAccountNumPage extends ATMPanel implements NumPad.Container {
	private static final long serialVersionUID = 6257433371054370512L;

	ATM atm;
	JLabel label1 = null;
	JLabel label2 = null;
	JLabel account = null;
	NumPad numPad = null;

	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 55);

		label1 = new JLabel("<html>상대방 계좌번호를 <br/>입력하세요.</html>");
		label1.setBounds(50, 50, 500, 200);
		label1.setFont(font);

		label2 = new JLabel("계좌번호:");
		label2.setBounds(50, ATM.PageHeight - 300, 500, 100);
		label2.setFont(font);

		account = new JLabel();
		account.setBounds(50, ATM.PageHeight - 200, 500, 100);
		account.setFont(font);

		numPad = new NumPad(this);
		numPad.create();
		numPad.setBounds(ATM.PageWidth - 600, 50, 550, ATM.PageHeight - 100);
		
		this.add(label1);
		this.add(label2);
		this.add(account);
		this.add(numPad);
	}

	/**
	 * 페이지를 보인 후 실행된다.
	 */
	public void onShowPage() {
		account.setText(null);
	};

	/**
	 * Numpad에서 확인 버튼을 눌렀음을 처리
	 */
	public void onNumPadOK() {
		String remitAccount = account.getText();
		if (remitAccount != null && remitAccount.length() > 0) {
			atm.onInputRemitAccount(remitAccount);
		}
	}

	/**
	 * Numpad에서 지우기 버튼을 눌렀음을 처리
	 */
	public void onNumPadDelete() {
		String astr = account.getText();
		if (astr != null && astr.length() > 0) {
			account.setText(astr.substring(0, astr.length() - 1));
		} else {
			account.setText(null);
		}
	}

	/**
	 * Numpad에서 숫자 버튼을 눌렀음을 처리
	 */
	public void onNumPadNum(int num) {
		String astr = account.getText();
		if (astr == null) {
			astr = "";
		}
		astr += num;
		account.setText(astr);
	}
}