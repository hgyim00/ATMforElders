package silveratm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @descripton 숫자패드
 * @author hgyim00
 *
 */
public class NumPad extends Panel {
	private static final long serialVersionUID = -468437613582683847L;

	public interface Container {
		/**
		 * Numpad에서 확인 버튼을 눌렀음을 처리
		 */
		void onNumPadOK();

		/**
		 * Numpad에서 지우기 버튼을 눌렀음을 처리
		 */
		void onNumPadDelete();

		/**
		 * Numpad에서 숫자 버튼을 눌렀음을 처리
		 */
		void onNumPadNum(int num);
	}

	/**
	 * NumPad를 갖는 화면
	 */
	private Container ctnr;

	/**
	 * 확인 버튼에 표시하는 문자열
	 */
	private static final String okText = "확인";

	/**
	 * 지우기 버튼에 표시하는 문자열
	 */
	private static final String delText = "지우기";

	/**
	 * 
	 * @param ctnr
	 */
	public NumPad(Container ctnr) {
		this.ctnr = ctnr;
	}

	/**
	 * 
	 */
	private class NumAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			String btnTxt = btn.getText();

			if (okText.equals(btnTxt)) {
				ctnr.onNumPadOK();
			} else if (delText.equals(btnTxt)) {
				ctnr.onNumPadDelete();
			} else {
				int num = Integer.parseInt(btnTxt);
				ctnr.onNumPadNum(num);
			}
		}
	}

	public void create() {
		//setSize(400, 350);
		setLayout(new BorderLayout());

		Color numBg = Color.LIGHT_GRAY;
		Color delBg = Color.RED;
		Color okBg = Color.GREEN;
		Color invFg = Color.WHITE;

		Font font = new Font("맑은 고딕", Font.PLAIN, 80);
		Font fonts = new Font("맑은 고딕", Font.PLAIN, 40);

		// JPanel numPadPanel = new JPanel(); // 가운데 부분 패널
		this.setLayout(new BorderLayout());

		JButton btn;
		NumAction numAction = new NumAction();
		this.setLayout(new GridLayout(4, 3));
		for (int i = 1; i <= 9; i++) {
			btn = new JButton(i + "");
			btn.addActionListener(numAction);
			btn.setFont(font);
			btn.setBackground(numBg);
			this.add(btn);
		}

		btn = new JButton(delText);
		btn.addActionListener(numAction);
		btn.setFont(fonts);
		btn.setForeground(invFg);
		btn.setBackground(delBg);
		this.add(btn);

		btn = new JButton("0");
		btn.addActionListener(numAction);
		btn.setFont(font);
		btn.setBackground(numBg);
		this.add(btn);

		btn = new JButton(okText);
		btn.addActionListener(numAction);
		btn.setFont(fonts);
		btn.setForeground(invFg);
		btn.setBackground(okBg);
		this.add(btn);

		setVisible(true);
	}
}