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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ATMServer implements ATMService {

	Account.List accountList = new Account.List();

	/**
	 * 계좌 목록 저장 파일
	 */
	private static final String MAIN_DB_FILE = "MainDB.txt";

	/**
	 * 거래 기록 파일
	 */
	private static final String LOG_DB_FILE = "LogDB.txt";

	/**
	 * 통장 기록 파일
	 */
	private static final String BANKBOOK_PREFIX = "BankBook-";
	private static final String BANKBOOK_SUFFIX = ".txt";

	/**
	 * 수수료 요율
	 */
	private static final double CUMMISSION_RATE = 0.001;

	/**
	 * 최소 수수료(원)
	 */
	private static final double CUMMISION_MIN = 100;

	/**
	 * 최대 수수료(원)
	 */
	private static final double CUMMISION_MAX = 1000;


	/**
	 * 거래일시 기록 포맷
	 */
	private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * ATM 소유 은행
	 */
	private static final String BANK_NAME = "가천은행";


	public ATMServer() {
		accountList.load(MAIN_DB_FILE);
	}

	@Override
	public boolean validateCard(ATMState atmState) {
		Account ac = accountList.getAccount(atmState.getCardNum(), atmState.getSecurityCode());
		return (ac != null);
	}

	@Override
	public boolean validatePin(ATMState atmState) {
		Account ac = accountList.getAccount(atmState.getCardNum(), atmState.getSecurityCode());
		if (ac == null) {
			return false;
		}
		if (!ac.getPIN().equals(atmState.getPin())) {
			return false;
		}

		// 계좌 정보 저장
		atmState.setMyName(ac.getAccountHolder());

		// 이체 계좌가 입력되어 있으면 받는 계좌 정보도 저장
		String remitAccount = atmState.getRemitAccount();
		if (remitAccount != null) {
			Account rac = accountList.getAccount(remitAccount);
			if (rac != null) {
				atmState.setRemitAccount(rac.getBankAccountNum());
				atmState.setRemitName(rac.getAccountHolder());
			} else {
				atmState.setRemitAccount(null);
				atmState.setRemitName(null);
			}
		}

		return true;
	}

	@Override
	public boolean withdraw(ATMState atmState) {
		Account ac = accountList.getAccount(atmState.getCardNum(), atmState.getSecurityCode());

		if (ac == null || !ac.getPIN().equals(atmState.getPin())) {
			return false;
		}

		int withdraw = atmState.getWithdrawAmount();

		int cummission = calcCummission(ac, withdraw);
		withdraw += cummission;

		int balance = ac.getBalance();
		if (withdraw > balance) {
			return false;
		}

		balance -= withdraw;
		ac.setBalance(balance);
		atmState.setBalance(balance);
		atmState.setCummission(cummission);

		accountList.save(MAIN_DB_FILE);
		writeLog(ac.getBankAccountNum(), -withdraw, balance);

		return true;
	}

	@Override
	public boolean deposit(ATMState atmState) {
		Account ac = accountList.getAccount(atmState.getCardNum(), atmState.getSecurityCode());
		if (ac == null) {
			return false;
		}

		int balance = ac.getBalance();
		int deposit = atmState.getDepositAmount();

		int cummission;
		if (BANK_NAME.equals(ac.getBankName())) {
			// ATM 소유 은행의 계좌이면 수수료 0
			cummission = 0;
		} else {
			cummission = calcCummission(ac, deposit);
		}
		
		balance += deposit - cummission;
		ac.setBalance(balance);
		atmState.setBalance(balance);
		atmState.setCummission(cummission);

		accountList.save(MAIN_DB_FILE);
		writeLog(ac.getBankAccountNum(), deposit - cummission, balance);

		return true;
	}

	@Override
	public boolean remit(ATMState atmState) {
		Account ac = accountList.getAccount(atmState.getCardNum(), atmState.getSecurityCode());
		if (ac == null) {
			return false;
		}

		Account remitee = accountList.getAccount(atmState.getRemitAccount());
		if (remitee == null) {
			return false;
		}

		int remitAmount = atmState.getRemitAmount(); // 이체할 금액
		int acCummission = calcCummission(ac, remitAmount); // 수수료
		int remiteeCummission = calcCummission(remitee, remitAmount); // 수수료
		int cummission = acCummission + remiteeCummission;
		int withdraw = remitAmount + cummission; // 인출될 금액 계산

		int balance = ac.getBalance();
		if (withdraw > balance) {
			return false;
		}

		// 이체하는 계좌에서 이체 금액과 수수료 차감
		balance -= withdraw;
		ac.setBalance(balance);
		atmState.setBalance(balance);
		atmState.setCummission(cummission);

		// 이체되는 계좌에 이체 금액 추가
		int remiteeBalance = remitee.getBalance();
		remiteeBalance += remitAmount;
		remitee.setBalance(remiteeBalance);

		accountList.save(MAIN_DB_FILE);
		writeLog(ac.getBankAccountNum(), withdraw, balance);
		writeLog(remitee.getBankAccountNum(), remitAmount, remiteeBalance);
		
		return true;
	}

	@Override
	public void updateBankbook(ATMState atmState) {
		Account ac = accountList.getAccount(atmState.getCardNum(), atmState.getSecurityCode());
		if (ac == null) {
			return;
		}

		String accountNum = ac.getBankAccountNum();
		String accountFile = BANKBOOK_PREFIX + accountNum + BANKBOOK_SUFFIX;
		String accountKey = "," + accountNum + ",";

		FileInputStream is = null;
		BufferedReader br = null;
		FileOutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		String line;

		try {
			// 전체 거래 기록 읽기
			is = new FileInputStream(LOG_DB_FILE);
			br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

			// 계좌 거리 기록 저장
			os = new FileOutputStream(accountFile);
			osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
			bw = new BufferedWriter(osw);

			do {
				line = br.readLine();
				if (line != null && line.length() > 0 &&  line.charAt(0) != '#') {
					if (line.indexOf(accountKey) > 0) {
						bw.write(line);
						bw.newLine();
					}
				}
			} while (line != null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
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

	/**
	 * 금액에 따른 수수료를 계산한다.
	 * 금액에 수수료 요율을 곱하고 수수료 최소 이상, 최대 이하가 되도록 조정한다. 
	 * @param amount 금액
	 * @return
	 */
	public int calcCummission(Account ac, int amount) {
		if (BANK_NAME.equals(ac.getBankName())) {
			// ATM 소유 은행의 계좌이면 수수료 0
			return 0;
		} else {
			int cummission = (int) Math.min(CUMMISION_MAX, Math.max(CUMMISION_MIN, amount * CUMMISSION_RATE));
			if (amount > cummission) {
				return cummission;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 거래 기록
	 * @param accountNum 계좌번호
	 * @param amount 금액변화
	 * @param balance 잔액
	 */
	public void writeLog(String accountNum, int amount, int balance) {
		Date now = new Date();
		SimpleDateFormat dateFmt = new SimpleDateFormat();
		dateFmt.applyPattern(DATE_FORMAT);
		String dateStr = dateFmt.format(now);

		FileOutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {
			os = new FileOutputStream(LOG_DB_FILE, true);
			osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
			bw = new BufferedWriter(osw);

			StringBuffer sb = new StringBuffer();
			sb.append(dateStr).append(",");
			sb.append(accountNum).append(",");
			sb.append(amount).append(",");
			sb.append(balance);
			bw.write(sb.toString());
			bw.newLine();

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
	
	public static void main(String[] args) {
		//TODO: 서버로 실행
	}
}
