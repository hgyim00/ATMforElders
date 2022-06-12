package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * @description 1p. 초기 화면. '카드나 통장을 삽입하세요'
 * @author hgyim00
 *
 */
////화면 구성 끝
public class InitialPage extends ATMPanel {
	private static final long serialVersionUID = -580951759308657780L;

	ATM atm;

	/**
	 * '카드나 통장을 삽입하세요' 버튼
	 */
	JButton insertCardBtn = null;

	/**
	 * 깜빡이는 작업을 처리하는 객체
	 */
	private Blinker blinker = null;

	private Color blinkFg;
	private Color blinkBg;

	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

        Font font = new Font("맑은 고딕", Font.PLAIN, 80);

		// '카드나 통장을 삽입하세요' 버튼
		insertCardBtn = new JButton("카드나 통장을 삽입하세요.");
		insertCardBtn.setFont(font);
		insertCardBtn.setBounds(0, 0, ATM.PageWidth, ATM.PageHeight);
		insertCardBtn.setBackground(Color.LIGHT_GRAY);
		insertCardBtn.setForeground(Color.BLACK);

		insertCardBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onInsertCard();
			}
		});

		insertCardBtn.setVisible(true);
		this.add(insertCardBtn);

		blinkFg = insertCardBtn.getForeground();
		blinkBg = insertCardBtn.getBackground();
	}

	/**
	 * 
	 */
	public void onShowPage() {
		blinker = new Blinker(new Blinker.Task() {
			@Override
			public void onBlinkOn() {
				insertCardBtn.setForeground(blinkBg);
			}

			@Override
			public void onBlinkOff() {
				insertCardBtn.setForeground(blinkFg);
			}
		}, 500, 500);
		blinker.startBlink();
	}

	/**
	 * 
	 */
	public void onHidePage() {
		blinker.stopBlink();
		blinker = null;
	}
}
