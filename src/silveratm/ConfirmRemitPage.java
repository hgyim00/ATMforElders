package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @description 16p. 송금 정보 확인
 * @author SWE_Team2
 *
 */
////버튼 추가 완료페이지
public class ConfirmRemitPage extends ATMPanel{
	private static final long serialVersionUID = 8475716272945717269L;

	ATM atm;

	/**
	 * 송금 정보를 확인하세요
	 */
	JLabel label1 = null;

	/**
	 * 상대방 계좌번호, 보낼 계좌 예금주, 보낼 금액, 보내는 계좌 예금주
	 */
	JLabel label2 = null;

	/**
	 * 취소 버튼
	 */
	JButton cancelBtn = null;

	/**
	 * 확인 버튼
	 */
	JButton OKBtn = null;

	/**
	 * 상대방 계좌번호
	 */
	private String remitAccount = null;

	/**
	 * 상대방 계좌 예금주
	 */
	private String remitName = null;

	/**
	 * 보낼 금액
	 */
	private int remitAmount = 0;

	/**
	 * 보내는 계좌 예금주
	 */
	private String myName = null;

	/**
	 * 
	 */
	public void create(ATM parent) {
		atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);
		
		// 송금 정보를 확인하세요
		label1 = new JLabel("송금 정보를 확인하세요");
		label1.setFont(font);
		label1.setBounds(50, 50, ATM.PageWidth - 100, 100);

		// 상대방 계좌번호, 보낼 계좌 예금주, 보낼 금액, 보내는 계좌 예금주
		label2 = new JLabel();
		label2.setFont(font);
		label2.setBounds(50, 200, ATM.PageWidth - 100, 400);

		// 취소 버튼
		cancelBtn = new JButton("취소");
		cancelBtn.setFont(fontb);
		cancelBtn.setBounds(100, ATM.PageHeight - 150, 200, 100);
		cancelBtn.setBackground(Color.RED);
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onEjectCard();
			}
		});
		
		// 확인 버튼
		OKBtn = new JButton("확인");
		OKBtn.setFont(fontb);
		OKBtn.setBounds(ATM.PageWidth - 250, ATM.PageHeight - 150, 200, 100);
		OKBtn.setBackground(Color.GREEN);
		OKBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onConfirmRemit();
			}
		});

		this.add(label1);
		this.add(label2);
		this.add(cancelBtn);
		this.add(OKBtn);
	}

	@Override
	public void onShowPage() {
		label2.setText(getRemitInfo());
	}

	public String getRemitAccount() {
		return remitAccount;
	}

	public void setRemitAccount(String remitAccount) {
		this.remitAccount = remitAccount;
	}

	public String getRemitName() {
		return remitName;
	}

	public void setRemitName(String remitName) {
		this.remitName = remitName;
	}

	public int getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(int remitAmount) {
		this.remitAmount = remitAmount;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	private String getRemitInfo() {
		StringBuffer infos = new StringBuffer();
		infos.append("<html>");
		infos.append("상대방 계좌번호: ").append(remitAccount).append("<br/>"); 
		infos.append("보낼 계좌 예금주: ").append(remitName).append("<br/>"); 
		infos.append("보낼 금액: ").append(remitAmount).append("<br/>"); 
		infos.append("보내는 계좌 예금주: ").append(myName);
		infos.append("</html>");
		return infos.toString();
	}
}
