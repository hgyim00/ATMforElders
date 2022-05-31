package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 9p. PIN 인증 실패 화면.
 */
public class FailNoticePINPage extends ATMPanel{
	private static final long serialVersionUID = -3714006150916530802L;

	ATM atm;

	/**
	 * 비밀번호가 틀렸습니다.
	 * 다시 시도해주세요.
	 */
	JLabel label1 = null;

	/**
	 * 종료(카드 인출) 버튼
	 */
	JButton finishBtn = null;

	/**
	 * 재시도 버튼
	 */
	JButton retry = null;

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

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);

		label1 = new JLabel("<html>비밀번호가 틀렸습니다.<br/>다시 시도해주세요.</html>");
		label1.setFont(font);
		label1.setBounds(50, 50, ATM.PageWidth - 100, 200);

		// '종료(카드 인출)' 버튼
		finishBtn = new JButton("종료(카드 인출)");
		finishBtn.setBounds(100, ATM.PageHeight - 150, 500, 100);
		finishBtn.setFont(fontb);
		finishBtn.setBackground(Color.RED);

		finishBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onEjectCard();
			}
		});
		
		// '재시도' 버튼
		retry = new JButton("재시도");
		retry.setBounds(ATM.PageWidth - 550, ATM.PageHeight - 150, 500, 100);
		retry.setFont(fontb);
		retry.setBackground(Color.GREEN);

		retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atm.onReinputPIN(pinUse);
			}
		});
		
		this.add(label1);
		this.add(finishBtn);
		this.add(retry);
	}

	/**
	 * 입력하는 PIN 사용 용도를 지정한다.
	 * @param pinUse
	 */
	public void setPINUse(ATM.PINUse pinUse) {
		this.pinUse = pinUse;
	}
}