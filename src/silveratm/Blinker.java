package silveratm;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 깜빡임 처리
 */
public class Blinker {

	/**
	 * 깜빡임 작업을 정의한 인터페이스
	 */
	public interface Task {
		void onBlinkOn();
		void onBlinkOff();
	}

	/**
	 * 깜빡임 작업
	 */
	private Task blinkTask = null;

	/**
	 * 깜빡임 시작 지연
	 */
	private long blinkDelay = 500;

	/**
	 * 깜빡임 간격
	 */
	private long blinkInterval = 500;

	/**
	 * 깜빡임 상태
	 */
	private boolean blinkOn = true;

	/**
	 * 깜빡임 타이머
	 */
	private Timer blinkTimer = null;

	/**
	 * 깜빡임 작업 처리
	 */
	public class BlinkTimerTask extends TimerTask {
		@Override
		public void run() {
			if (blinkOn) {
				blinkTask.onBlinkOn();
			} else {
				blinkTask.onBlinkOff();
			}
			blinkOn = !blinkOn;
		}
	}


	/**
	 * 
	 * @param blinkTask 깜빡임 작업을 구현하는 객체
	 */
	public Blinker(Task blinkTask, long blinkDelay, long blinkInterval) {
		this.blinkTask = blinkTask;
		this.blinkDelay = blinkDelay;
		this.blinkInterval = blinkInterval;
	}

	/**
	 * 깜빡임 작업 시작
	 */
	public void startBlink() {
		blinkTimer = new Timer(getClass().getName());
		BlinkTimerTask task = new BlinkTimerTask();
		blinkTimer.schedule(task, blinkDelay, blinkInterval);
	}

	/**
	 * 깜빡임 작업 종료
	 */
	public void stopBlink() {
		if (blinkTimer != null) {
			blinkTimer.cancel();
			blinkTimer = null;
		}
	}
}
