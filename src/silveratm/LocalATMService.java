package silveratm;
/**
 * @description 서버 대행. 클라이언트와 서버 중재 역할. / ATM 서버를 객체로 만들어서 서비스 기능을 바로 실행. 로컬에 있는 파일을 읽고 쓰기.
 * @author hgyim
 *
 */
public class LocalATMService implements ATMService {

	/**
	 * 
	 */
	ATMServer server = new ATMServer();

	@Override
	public boolean validateCard(ATMState atmState) {
		return server.validateCard(atmState);
	}

	@Override
	public boolean validatePin(ATMState atmState) {
		return server.validatePin(atmState);
	}

	@Override
	public boolean withdraw(ATMState atmState) {
		return server.withdraw(atmState);
	}

	@Override
	public boolean deposit(ATMState atmState) {
		return server.deposit(atmState);
	}

	@Override
	public boolean remit(ATMState atmState) {
		return server.remit(atmState);
	}

	@Override
	public void updateBankbook(ATMState atmState) {
		server.updateBankbook(atmState);
	}
}
