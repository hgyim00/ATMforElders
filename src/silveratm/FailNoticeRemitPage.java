package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 18p. 잔액이 부족한 경우 송금 실패 화면.
 * @author SWE_Team2
 *
 */
public class FailNoticeRemitPage extends ATMPanel{
	private static final long serialVersionUID = 3486514928224607031L;

	ATM atm;

	/**
	 * 송금할 잔액이 부족합니다. 금액을 다시 입력해주세요.
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
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);

		// 잔액이 부족합니다. 잔액 확인 후 다시 시도해주세요.
		label1 = new JLabel("<html>송금할 잔액이 부족합니다.<br/>금액을 다시 입력해주세요.</html>");
		label1.setFont(font);
		label1.setBounds(50, 50, ATM.PageWidth - 100, 200);

		// 종료(카드 인출) 버튼
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

		// 재시도 버튼
		retry = new JButton("재시도");
		retry.setBounds(ATM.PageWidth - 550, ATM.PageHeight - 150, 500, 100);
		retry.setFont(fontb);
		retry.setBackground(Color.GREEN);

		retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atm.onReinputRemit();
			}
		});

		this.add(label1);
		this.add(finishBtn);
		this.add(retry);
	}

}