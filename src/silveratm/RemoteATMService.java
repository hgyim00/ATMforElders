package silveratm;
/**
 * @description 서버 대행. 클라이언트와 서버 중재 역할 / TCP IP 통신을 이용해서 서버와 통신해서 서비스 실행하도록 구현.
 * @author hgyim00
 *
 */
public class RemoteATMService implements ATMService {

	@Override
	public boolean validateCard(ATMState atmState) {
		//TODO: 인증 구현
		return true;
	}

	@Override
	public boolean validatePin(ATMState atmState) {
		//TODO: 구현
		return true;
	}

	@Override
	public boolean withdraw(ATMState atmState) {
		//TODO: 구현
		return true;
	}

	@Override
	public boolean deposit(ATMState atmState) {
		//TODO: 구현
		return true;
	}

	@Override
	public boolean remit(ATMState atmState) {
		//TODO: 구현
		return true;
	}

	@Override
	public void updateBankbook(ATMState atmState) {
		
	}
}
