package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * 
 * @description 13p. 입금 성공 화면.
 * @author hgyim00
 * 
 */
// 화면구성 끝
public class SuccessDepositPage extends ATMPanel {
	private static final long serialVersionUID = 3767917639373382724L;

	ATM atm;
	JLabel success = null;
	JLabel info = null;
	JLabel currentBalance = null;
	JButton exit = null;
	JButton otherTask = null;

	/**
	 * 기존 잔액
	 */
	private int prevBalance = 0;

	/**
	 * 입금액
	 */
	private int depositAmount = 0;

	/**
	 * 현재 잔액
	 */
	private int curBalance = 0;

	/**
	 * 수수료
	 */
	private int cummission = 0;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);

		// 성공메시지
		success = new JLabel("성공적으로 입금되었습니다");
		success.setFont(font);
		success.setBounds(50, 50, ATM.PageWidth - 100, 100);

		// 기존잔액, 입금액, 수수료 (검정색으로 표시)
		info = new JLabel();
		info.setFont(font);
		info.setHorizontalAlignment(JLabel.RIGHT);
		info.setBounds(50, 200, ATM.PageWidth - 100, 300);

		/* 현재 잔액(파란색으로 표시) */
		currentBalance = new JLabel();
		currentBalance.setFont(font);
		currentBalance.setHorizontalAlignment(JLabel.RIGHT);
		currentBalance.setBounds(50, 500, ATM.PageWidth - 100, 100);
		currentBalance.setForeground(Color.BLUE);

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
		this.add(info);
		this.add(currentBalance);
		this.add(exit);
		this.add(otherTask);
	}

	@Override
	public void onShowPage() {
		StringBuffer infoMsgs = new StringBuffer();
		infoMsgs.append("<html>");
		infoMsgs.append("기존 잔액: ").append(prevBalance).append("원<br/>");
		infoMsgs.append("입금액: ").append(depositAmount).append("원<br/>");
		infoMsgs.append("수수료: ").append(cummission).append("원<br/>");
		infoMsgs.append("</html>");
		info.setText(infoMsgs.toString());

		currentBalance.setText(String.format("현재 잔액: %d원", curBalance));
	}

	public int getPrevBalance() {
		return prevBalance;
	}

	public void setPrevBalance(int prevBalance) {
		this.prevBalance = prevBalance;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int depositAmount) {
		this.depositAmount = depositAmount;
	}

	public int getCurBalance() {
		return curBalance;
	}

	public void setCurBalance(int curBalance) {
		this.curBalance = curBalance;
	}

	public int getCummission() {
		return cummission;
	}

	public void setCummission(int cummission) {
		this.cummission = cummission;
	};
}
