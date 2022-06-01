package silveratm;

import java.io.IOException;
import java.net.UnknownHostException;

import silveratm.message.DepositMessage;
import silveratm.message.RemitMessage;
import silveratm.message.UpdateBankbookMessage;
import silveratm.message.ValidateCardMessage;
import silveratm.message.ValidatePINMessage;
import silveratm.message.WithdrawMessage;
import silveratm.remote.ATMServer;

/**
 * @description 서버 대행. 클라이언트와 서버 중재 역할
 *
 */
public class LocalATMService implements ATMService {

	/**
	 * 
	 */
	ATMServer server = new ATMServer();

	@Override
	public ValidateCardMessage.Result validateCard(ATMState atmState) {
		return server.validateCard(atmState);
	}

	@Override
	public ValidatePINMessage.Result validatePin(ATMState atmState) {
		return server.validatePin(atmState);
	}

	@Override
	public WithdrawMessage.Result withdraw(ATMState atmState) {
		return server.withdraw(atmState);
	}

	@Override
	public DepositMessage.Result deposit(ATMState atmState) {
		return server.deposit(atmState);
	}

	@Override
	public RemitMessage.Result remit(ATMState atmState) {
		return server.remit(atmState);
	}

	@Override
	public UpdateBankbookMessage.Result updateBankbook(ATMState atmState) {
		return server.updateBankbook(atmState);
	}

	@Override
	public void connect() throws UnknownHostException, IOException {
		// nothing to do
	}

	@Override
	public void disconnect() throws IOException {
		// nothing to do
	}
}
