package silveratm;

import java.io.IOException;
import java.net.UnknownHostException;

import silveratm.message.DepositMessage;
import silveratm.message.RemitMessage;
import silveratm.message.UpdateBankbookMessage;
import silveratm.message.ValidateCardMessage;
import silveratm.message.ValidatePINMessage;
import silveratm.message.WithdrawMessage;

public interface ATMService {
	/**
	 * 
	 * @param atmState 카드 정보
	 * @return
	 */
	ValidateCardMessage.Result validateCard(ATMState atmState);

	/**
	 * 
	 * @param pin 인증 PIN
	 * @return
	 */
	ValidatePINMessage.Result validatePin(ATMState atmState);

	/**
	 * 
	 * @param atmState 출금 정보
	 * @return
	 */
	WithdrawMessage.Result withdraw(ATMState atmState);

	/**
	 * 
	 * @param atmState 입금 정보
	 * @return
	 */
	DepositMessage.Result deposit(ATMState atmState);

	/**
	 * 
	 * @param atmState 송금 정보
	 * @return
	 */
	RemitMessage.Result remit(ATMState atmState);

	/**
	 * 
	 * @param atmState 계좌 정보
	 */
	UpdateBankbookMessage.Result updateBankbook(ATMState atmState);

	/**
	 * 서버에 접속
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	void connect() throws UnknownHostException, IOException;

	/**
	 * 서버 접속 해제
	 * @throws IOException
	 */
	void disconnect() throws IOException;
}
