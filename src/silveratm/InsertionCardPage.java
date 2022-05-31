package silveratm;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @description 2p. 실물 카드를 삽입할 수는 없으니 카드번호와 Security code입력으로 카드(통장)삽입을 대체하는 page.
 * @author hgyim00
 *
 */
//화면구성 끝
public class InsertionCardPage extends ATMPanel{
	private static final long serialVersionUID = -7576375738171045212L;

	/**
	 * 카드 입력 대기 시간 : 1분 
	 */
	public static final long TIMEOUT = 60000;

	ATM atm;

	JLabel card;
	JLabel sc;
	JTextField cardNum;
	JTextField securityCode;
	JButton insertBtn;

	/**
	 * 대기시간 타이머
	 */
	private Timer timeoutTimer = null;

	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 80);

		card = new JLabel("카드번호: ");
		card.setBounds(200, 100, 800, 100);
		card.setFont(font);

		cardNum = new JTextField();
		cardNum.setBounds(200, 200, 900, 100);
		cardNum.setFont(font);

		sc = new JLabel("Security Code: ");
		sc.setFont(font);
		sc.setBounds(200, 400, 800, 100);

		securityCode = new JTextField();
		securityCode.setBounds(200, 500, 900, 100);
		securityCode.setFont(font);

		insertBtn = new JButton("인식");
		insertBtn.setBounds(ATM.PageWidth - 250, ATM.PageHeight - 150, 200, 100);
		insertBtn.setFont(font);
		insertBtn.setBackground(Color.GREEN);
		insertBtn.setForeground(Color.WHITE); 	

		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onValidateCard(cardNum.getText(), securityCode.getText());
			}
		});

		this.add(card);
		this.add(cardNum);
		this.add(sc);
		this.add(securityCode);
		this.add(insertBtn);
	}

	/**
	 * 
	 */
	public void onShowPage() {
		cardNum.setText("");
		securityCode.setText("");

		timeoutTimer = new Timer(getClass().getName());
		timeoutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				atm.onCardInsertionTimeout();
			}
		}, TIMEOUT);
	}

	/**
	 * 
	 */
	public void onHidePage() {
		if (timeoutTimer != null) {
			timeoutTimer.cancel();
			timeoutTimer = null;
		}
	}
}
