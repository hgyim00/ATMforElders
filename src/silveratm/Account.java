package silveratm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @description 한 사람의 은행업무 관련 정보를 관리한다.
 * @author hgyim00
 *
 */
public class Account {
	//get,set
	private String bankName;//은행이름
	private String accountHolder; //예금주
	private String bankAccountNum; //계좌번호
	private String cardNum; //카드번호
	private String securityCode; //security code
	private String PIN; //PIN 번호
	private int balance; //잔고

	/**
	 * 계좌 속성항목 개수
	 */
	private static final int ACCOUNT_FIELD_COUNT = 7;

	/**
	 * 계좌 속성항목 저장 순서
	 */
	private static final int ACCOUNT_FIELD_IDX_BANK_NAME = 0;
	private static final int ACCOUNT_FIELD_IDX_HOLDER_NAME = 1;
	private static final int ACCOUNT_FIELD_IDX_ACCOUNT_NUM = 2;
	private static final int ACCOUNT_FIELD_IDX_CARD_NUM = 3;
	private static final int ACCOUNT_FIELD_IDX_SECURITY_CODE = 4;
	private static final int ACCOUNT_FIELD_IDX_PIN = 5;
	private static final int ACCOUNT_FIELD_IDX_BALANCE = 6;

	public Account(String[] fields) {
		bankName = fields[ACCOUNT_FIELD_IDX_BANK_NAME].trim();
		accountHolder = fields[ACCOUNT_FIELD_IDX_HOLDER_NAME].trim();
		bankAccountNum = fields[ACCOUNT_FIELD_IDX_ACCOUNT_NUM].trim();
		cardNum = fields[ACCOUNT_FIELD_IDX_CARD_NUM].trim();
		securityCode = fields[ACCOUNT_FIELD_IDX_SECURITY_CODE].trim();
		PIN = fields[ACCOUNT_FIELD_IDX_PIN].trim();
		balance = Integer.parseInt(fields[ACCOUNT_FIELD_IDX_BALANCE].trim());
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getBankAccountNum() {
		return bankAccountNum;
	}

	public void setBankAccountNum(String bankAccountNum) {
		this.bankAccountNum = bankAccountNum;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getPIN() {
		return PIN;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(bankName).append(",");
		sb.append(accountHolder).append(",");
		sb.append(bankAccountNum).append(",");
		sb.append(cardNum).append(",");
		sb.append(securityCode).append(",");
		sb.append(PIN).append(",");
		sb.append(balance);
		return sb.toString();
	}

	public static String getFieldString() {
		StringBuffer sb = new StringBuffer();
		sb.append("# ");
		sb.append("은행, ");
		sb.append("예금주, ");
		sb.append("계좌번호, ");
		sb.append("카드번호, ");
		sb.append("SecurityCode, ");
		sb.append("PIN, ");
		sb.append("잔고");
		return sb.toString();
	}

	public static class List extends ArrayList<Account>{
		private static final long serialVersionUID = -5365355500271650293L;

		/**
		 * 카드 번호와 security 코드로 계좌를 찾는다.
		 * @param cardNum
		 * @param securityCd
		 * @return
		 */
		public Account getAccount(String cardNum, String securityCd) {
			for (Account ac : this) {
				if (ac.cardNum.equals(cardNum) && ac.securityCode.equals(securityCd)) {
					return ac;
				}
			}
			return null;
		}

		/**
		 * 계좌번호로 계좌를 찾는다.
		 * @param accountNum
		 * @return
		 */
		public Account getAccount(String accountNum) {
			for (Account ac : this) {
				if (ac.bankAccountNum.equals(accountNum)) {
					return ac;
				}
			}
			return null;
		}

		/**
		 * 계좌 목록을 로드한다.
		 * @param dbPath
		 */
		public void load(String dbPath) {
			clear();

			FileInputStream is = null;
			BufferedReader br = null;

			try {
				is = new FileInputStream(dbPath);
				br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				String line;
				String[] fields;

				do {
					line = br.readLine();
					if (line != null && line.length() > 0 &&  line.charAt(0) != '#') {
						fields = line.split(",");

						if (fields.length == ACCOUNT_FIELD_COUNT) {
							add(new Account(fields));
						}
					}
				} while (line != null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				clear();
			} catch (IOException e) {
				e.printStackTrace();
				clear();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 계좌 목록을 저장한다. 지우고 다시쓰기(덮어쓰기 느낌)
		 */
		public void save(String dbPath){
			FileOutputStream os = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;

			try {
				os = new FileOutputStream(dbPath);
				osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
				bw = new BufferedWriter(osw);

				bw.write(getFieldString());
				bw.newLine();

				for (Account ac : this) {
					bw.write(ac.toString());
					bw.newLine();
				}
				bw.close();
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
					if (osw != null) {
						osw.close();
					}
					if (os != null) {
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
