package silveratm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

/**
 * @description 실물 현금을 삽입할 수는 없으니, 금액을 입력하는 것으로 대체하는 화면.
 * @author hgyim00
 * wonSymbol 앞에 띄워주고, cashamount로 금액 입력받고, 그 옆에 enter 버튼으로 금액 입력
 */
//화면 구성 끝
public class InsertionMoneyPage extends ATMPanel{
	private static final long serialVersionUID = 7751644357528464458L;

	ATM atm;
    JLabel wonSymbol = null;
    JTextField cashAmount = null;
    JLabel wonErr = null;
    JButton enter = null;

    /**
     * 입금 금액 입력 오류 여부
     */
    private boolean cashAmountErrSta = false;

    public void create(ATM parent) {
        atm = parent;
		setLayout(null);

		Font font = new Font("맑은 고딕", Font.PLAIN, 70);
		Font fontb = new Font("맑은 고딕", Font.PLAIN, 60);
		int posY = (ATM.PageHeight - 200) / 2;

        wonSymbol = new JLabel("₩");
        wonSymbol.setFont(font);
        wonSymbol.setBounds(120, posY, 70, 100);

        cashAmount = new JTextField();
        cashAmount.setFont(font);
        cashAmount.setBounds(200, posY, 800, 100);
        cashAmount.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				wonErr.setText(null);
				if (cashAmountErrSta) {
					// 입력한 입금액 초기화
					cashAmount.setText(null);
					cashAmountErrSta = false;
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}});

        wonErr = new JLabel();
        wonErr.setFont(fontb);
        wonErr.setBounds(200, posY + 100, 800, 100);
        wonErr.setForeground(Color.RED);

        enter = new JButton("확인");
        enter.setBounds(ATM.PageWidth - 350, ATM.PageHeight - 150, 300, 100);
        enter.setFont(fontb);
        enter.setBackground(Color.GREEN);

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
	            	int amount = Integer.parseInt(cashAmount.getText());
	            	atm.onInputDepositAmount(amount);
            	} catch (NumberFormatException err) {
            		wonErr.setText("잘 못 입력했습니다.");
            		cashAmountErrSta = true;
            	}
            }
        });

        this.add(wonSymbol);
        this.add(cashAmount);
        this.add(wonErr);
        this.add(enter);
	}

	@Override
	public void onShowPage() {
		cashAmount.setText(null);
		wonErr.setText(null);
		cashAmountErrSta = false;
   }
}
