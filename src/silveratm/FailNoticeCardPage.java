package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 4p.삽입된 카드(통장) 인증 실패 화면.
 * @author SWE_Team2
 *
 */
///화면구성 끝
public class FailNoticeCardPage extends ATMPanel{
	private static final long serialVersionUID = -4998960270917970986L;

	ATM atm;

	/**
	 * 일치하는 카드(통장)정보가
	 * 존재하지 않습니다.
	 * 카드를 확인하고 다시 시도해주세요.
	 */
	JLabel label1 = null;

	/**
	 * '다시 입력'버튼
	 */
	JButton reInput = null;

	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 60);
		
		label1 = new JLabel("<html>일치하는 카드(통장)정보가<br/>존재하지 않습니다.<br/>카드를 확인하고 다시 시도해주세요.</html> ");
		label1.setFont(font);
		label1.setBounds(50, 50, ATM.PageWidth - 100, 600);

		// 다시 입력
		reInput = new JButton("다시 입력");
		reInput.setBounds(ATM.PageWidth - 450, ATM.PageHeight - 150, 400, 100);
		reInput.setFont(new Font("맑은 고딕", Font.PLAIN, 80));
		reInput.setBackground(Color.GREEN);
        reInput.setForeground(Color.WHITE);

		reInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onInitialPage();
			}
		});
		
		this.add(label1);
		this.add(reInput);
	}

}