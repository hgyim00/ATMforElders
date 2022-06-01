package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 20p. 통장정리 성공 화면
 * @author hgyim00
 *
 */
//화면구성 끝
public class SuccessUpdateBankbookPage extends ATMPanel {
	private static final long serialVersionUID = 2728316039581689846L;

	/**
	 * 화면 유지 시간 : 10초 
	 */
	public static final long TIMEOUT = 10000;

	ATM atm;
	JLabel success;
	JButton exit = null;
	JButton otherTask = null;

	/**
	 * 화면 유지 타이머
	 */
	private Timer timeoutTimer = null;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);

		// 성공메시지
		success = new JLabel("<html>통장 정리가 완료되었습니다.<br/>통장을 가져가세요!</html>");
		success.setFont(font);
		success.setBounds(50, 50, ATM.PageWidth - 100, 200);

		// 종료(카드인출) 버튼
		exit = new JButton("종료(카드 인출)");
		exit.setFont(fontb);
		exit.setBounds(100, ATM.PageHeight - 150, 500, 100);
		exit.setBackground(Color.RED);

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onEjectCard();
			}
		});

		// 다른 업무 진행 버튼
		otherTask = new JButton("다른 업무 진행");
		otherTask.setFont(fontb);
		otherTask.setBounds(ATM.PageWidth - 550, ATM.PageHeight - 150, 500, 100);
		otherTask.setBackground(Color.GREEN);

		otherTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onActivitySelection();
			}
		});

		this.add(success);
		this.add(exit);
		this.add(otherTask);
	}

	/**
	 * 
	 */
	public void onShowPage() {
		// 10초 후 화면 닫도록 타이머 설정
		timeoutTimer = new Timer(getClass().getName());
		timeoutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				atm.onInitialPage();
			}}, TIMEOUT);
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
