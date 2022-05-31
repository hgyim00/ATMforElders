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
 * @description 시간 초과(60초)시 첫 화면으로 돌아가는 화면 (10초간 표시)
 * @author hgyim00
 *
 */
// 화면구성 끝
public class TimeoutEjectPage extends ATMPanel {
	private static final long serialVersionUID = -4405203323218312417L;

	/**
	 * 카드 입력 대기 시간 : 10초 
	 */
	public static final long TIMEOUT = 10000;

	ATM atm;
	JLabel msg;;
	JButton exit = null;

	/**
	 * 대기시간 타이머
	 */
	private Timer timeoutTimer = null;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 80);

		// 메시지 표시
		msg = new JLabel("<html>입력 시간이 초과되었습니다.<br />처음부터 다시 진행해주세요.<br /><br />카드가 반환됩니다.</html>");
		msg.setFont(font);
		msg.setBounds(50, 50, ATM.PageWidth - 100, 600);

		// 처음으로 버튼
		exit = new JButton("처음으로");
		exit.setBounds(ATM.PageWidth - 450, ATM.PageHeight - 150, 400, 100);
		exit.setFont(font);
		exit.setBackground(Color.RED);

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onInitialPage();
			}
		});

		this.add(msg);
		this.add(exit);
	}

	/**
	 * 
	 */
	public void onShowPage() {
		timeoutTimer = new Timer(getClass().getName());
		timeoutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				atm.onInitialPage();
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
