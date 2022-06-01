package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @description 4가지 기능을 선택하는 화면.(1.돈찾기 2.돈넣기 3.돈 보내기 4.통장정리)
 * @author SWE_Team2
 *
 */
//// 화면구성 끝
public class ActivitySelectionPage extends ATMPanel {
	private static final long serialVersionUID = 596018313416971897L;

	ATM atm;
	JButton withdrawBtn = null;
	JButton depositBtn = null;
	JButton remitBtn = null;
	JButton updateBankBook = null;

	private static final int btnGap = 20;

	public void create(ATM parent) {
		atm = parent;
		
		GridLayout gridLayout = new GridLayout(2, 2);
		gridLayout.setHgap(btnGap);
		gridLayout.setVgap(btnGap);
		this.setLayout(gridLayout);

		Font font = new Font("맑은 고딕", Font.PLAIN, 100);
		
		withdrawBtn = new JButton("돈 찾기");
		depositBtn = new JButton("돈 넣기");
		remitBtn = new JButton("돈 보내기");
		updateBankBook = new JButton("통장정리");

		withdrawBtn.setFont(font);
		depositBtn.setFont(font);
		remitBtn.setFont(font);
		updateBankBook.setFont(font);

		withdrawBtn.setBackground(Color.LIGHT_GRAY);	withdrawBtn.setForeground(Color.BLACK); 
		depositBtn.setBackground(Color.LIGHT_GRAY);		depositBtn.setForeground(Color.BLACK); 
		remitBtn.setBackground(Color.LIGHT_GRAY);		remitBtn.setForeground(Color.BLACK); 
		updateBankBook.setBackground(Color.LIGHT_GRAY);	updateBankBook.setForeground(Color.BLACK); 

		this.add(withdrawBtn);
		this.add(depositBtn);
		this.add(remitBtn);
		this.add(updateBankBook);

		/* 돈 찾기 버튼 클릭 */
		withdrawBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onOpenWithdraw();
			}
		});

		/* 돈 넣기 버튼 클릭 */
		depositBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onOpenDeposit();
			}
		});

		/* 돈 보내기 버튼 클릭 */
		remitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onOpenRemit();

			}
		});

		/* 통장정리 버튼 클릭 */
		updateBankBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atm.onOpenUpdateBankbook();
			}
		});
	}
}
