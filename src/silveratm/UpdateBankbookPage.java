package silveratm;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * @desciption 통장정리를 하는 중을 나타내는 페이지 (5초의 지속된 페이지를 본다.)
 * @author hgyim00
 *
 */
//화면구성 끝
public class UpdateBankbookPage extends ATMPanel {
	private static final long serialVersionUID = 8057216380514044284L;

	/**
	 * 통장정리 진행 시간 : 5초 
	 */
	public static final long TIMEOUT = 5000;

	ATM atm;

	/**
	 * 통장 정리 중입니다…
	 */
	JLabel ing = null;

	/**
	 * 깜빡이는 작업을 처리하는 객체
	 */
	private Blinker blinker = null;

	private Color blinkFg;
	private Color blinkBg;

	/**
	 * 화면 지속 시간
	 */
	private long liveTime = 0;

	/**
	 * 깜빡임 시작 지연
	 */
	private long blinkDelay = 500;

	/**
	 * 깜빡임 간격
	 */
	private long blinkInterval = 500;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

        Font font = new Font("맑은 고딕", Font.PLAIN, 80);

		// 통장 정리 중입니다…
		ing = new JLabel("통장 정리 중입니다…");
		ing.setFont(font);
		ing.setBounds(0, (ATM.PageHeight - 100) / 2, ATM.PageWidth, 100);
		ing.setHorizontalAlignment(JLabel.CENTER);
		ing.setBackground(Color.LIGHT_GRAY);
		ing.setForeground(Color.BLACK);

		this.add(ing);

		blinkFg = ing.getForeground();
		blinkBg = ing.getBackground();
	}

	/**
	 * 
	 */
	public void onShowPage() {
		liveTime = TIMEOUT;

		blinker = new Blinker(new Blinker.Task() {
			@Override
			public void onBlinkOn() {
				ing.setForeground(blinkBg);
				checkLiveTime();
			}

			@Override
			public void onBlinkOff() {
				ing.setForeground(blinkFg);
				checkLiveTime();
			}

			private void checkLiveTime() {
				liveTime -= blinkInterval;
				if (liveTime < 0) {
					blinker.stopBlink();
					atm.onSuccessUpdateBankbook();
				}
			}
		}, blinkDelay, blinkInterval);
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
