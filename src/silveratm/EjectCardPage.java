package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 9p. PIN 인증 실패 화면.
 * @author SWE_Team2
 *
 */
////화면구성 끝
public class EjectCardPage extends ATMPanel{
	private static final long serialVersionUID = 8226833308928654498L;

	ATM atm;

	/**
	 * 카드를 꺼내십시요.
	 */
	JLabel label1 = null;

	/**
	 * 꺼내기 버튼
	 */
	JButton ejectBtn = null;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);

		label1 = new JLabel("카드를 꺼내세요.");
		label1.setFont(font);
		label1.setBounds(50, 50, ATM.PageWidth - 100, 200);

		// '꺼내기' 버튼
		ejectBtn = new JButton("꺼내기");
		ejectBtn.setBounds(ATM.PageWidth - 350, ATM.PageHeight - 150, 300, 100);
		ejectBtn.setFont(fontb);
		ejectBtn.setBackground(Color.RED);

		ejectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atm.onInitialPage();
			}
		});
		
		this.add(label1);
		this.add(ejectBtn);
	}
}