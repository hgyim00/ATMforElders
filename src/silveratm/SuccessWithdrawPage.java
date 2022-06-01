package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 17p인출 성공 화면.
 * @author hgyim00
 */
public class SuccessWithdrawPage extends ATMPanel {
	private static final long serialVersionUID = -8362401822546499541L;

	ATM atm;
	JLabel success;
	JButton exit = null;
	JButton otherTask = null;

	/**
	 * 인출한 금액
	 */
	private int amount = 0;

	/**
	 * 수수료
	 */
	private int cummission = 0;

	/**
	 * 인출후 잔액
	 */
	private int balance = 0;

	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);

		// 성공 메시지 및 잔액 표시
		success = new JLabel();
		success.setFont(font);
		success.setBounds(50, 50, ATM.PageWidth - 100, 600);

		// 종료(카드인출) 버튼
		exit = new JButton("종료(카드 인출)");
		exit.setBounds(100, ATM.PageHeight - 150, 500, 100);
		exit.setFont(fontb);
		exit.setBackground(Color.RED);

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onEjectCard();
			}
		});

		// 다른 업무 진행 버튼
		otherTask = new JButton("다른 업무 진행");
		otherTask.setBounds(ATM.PageWidth - 550, ATM.PageHeight - 150, 500, 100);
		otherTask.setFont(fontb);
		otherTask.setBackground(Color.GREEN);

		otherTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onActivitySelection();
			}
		});

		this.add(exit);
		this.add(success);
		this.add(otherTask);
		parent.add(this);

	}

	@Override
	public void onShowPage() {
		success.setText(getMsg());
	};

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCummission() {
		return cummission;
	}

	public void setCummission(int cummission) {
		this.cummission = cummission;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * 
	 * @return
	 */
	private String getMsg() {
		StringBuffer infos = new StringBuffer();
		infos.append("<html>");
		infos.append(amount).append("원이<br/>");
		infos.append("성공적으로 인출되었습니다.<br/>");
		infos.append("현금을 가져가세요.<br/><br/>");
		infos.append("수수료: ").append(cummission).append("원<br/>");
		infos.append("현재 잔액: ").append(balance).append("원");
		infos.append("</html>");
		return infos.toString();
	}
}
