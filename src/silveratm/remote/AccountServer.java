package silveratm.remote;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import silveratm.ATMState;
import silveratm.message.DepositMessage;
import silveratm.message.Message;
import silveratm.message.RemitMessage;
import silveratm.message.UpdateBankbookMessage;
import silveratm.message.ValidateCardMessage;
import silveratm.message.ValidatePINMessage;
import silveratm.message.WithdrawMessage;

/**
 * 서버에 접속된 단말에 대해 서비스 요청을 처리하는 쓰레드.
 */
public class AccountServer extends Thread {
	/**
	 * 쓰레드 작업 계속 진행 여부를 저장한다.
	 */
	public boolean goOn = true;

	/**
	 * 사용자와 접속하고 있는 소켓
	 */
	private Socket connectionSocket = null;

	/**
	 * 소켓에서 입력을 받는 기능을 담당하는 객체
	 */
	private BufferedReader inStream = null;

	/**
	 * 소켓으로 출력하는 기능을 담당하는 객체
	 */
	private DataOutputStream outStream = null;

	/**
	 * 서비스를 처리할 객체
	 */
	private ATMServer atmServer = null;

	/**
	 * 생성자
	 * 
	 * @param connectionSocket
	 *            사용자와 접속하고 있는 소켓
	 */
	public AccountServer(Socket connectionSocket, ATMServer atmServer) {
		this.connectionSocket = connectionSocket;
		this.atmServer = atmServer;
	}

	/**
	 * 쓰레드 실행
	 */
	public void run() {
		// 입출력 준비
		try {
			// 소켓에서 입력 받는 기능을 담당하는 객체를 생성한다.
			this.inStream = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			// 소켓에 출력하는 기능을 담당하는 객체를 생성한다.
			this.outStream = new DataOutputStream(connectionSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// 메시지 한 줄을 읽어서 저장하는 객체
		String msgLine;

		DepositMessage.Request depositMessage_request = new DepositMessage.Request();
		RemitMessage.Request remitMessage_request = new RemitMessage.Request();
		UpdateBankbookMessage.Request updateBankbookMessage_request = new UpdateBankbookMessage.Request();
		ValidateCardMessage.Request validateCardMessage_request = new ValidateCardMessage.Request();
		ValidatePINMessage.Request validatePINMessage = new ValidatePINMessage.Request();
		WithdrawMessage.Request withdrawMessage = new WithdrawMessage.Request();
		ATMState atmState = new ATMState();

		while (goOn) {
			try {
				// 접속한 단말에서 서비스 요청을 받는다.
				msgLine = this.inStream.readLine();

				if (msgLine != null && !msgLine.isEmpty()) {
					//System.out.println(msgLine);
	
					// 매시지를 해석하고 서비스를 처리하는 객체에 전달한다.
					if (depositMessage_request.setMessageString(msgLine)) {
						response(atmServer.deposit(depositMessage_request.toATMState(atmState)));
					} else if (remitMessage_request.setMessageString(msgLine)) {
						response(atmServer.remit(remitMessage_request.toATMState(atmState)));
					} else if (updateBankbookMessage_request.setMessageString(msgLine)) {
						response(atmServer.updateBankbook(updateBankbookMessage_request.toATMState(atmState)));
					} else if (validateCardMessage_request.setMessageString(msgLine)) {
						response(atmServer.validateCard(validateCardMessage_request.toATMState(atmState)));
					} else if (validatePINMessage.setMessageString(msgLine)) {
						response(atmServer.validatePin(validatePINMessage.toATMState(atmState)));
					} else if (withdrawMessage.setMessageString(msgLine)) {
						response(atmServer.withdraw(withdrawMessage.toATMState(atmState)));
					}
				} else {
					goOn = false;
				}
			} catch (IOException e) {
				e.printStackTrace();

				// 접속이 끊기면 종료한다.
				goOn = false;
			}
		}

		try {
			inStream.close();
			outStream.close();
			connectionSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 접속한 단말에 서비스 요청 처리 결과 메시지를 전송한다.
	 * @param msg
	 */
	public void response(Message msg) {
		try {
			String msgStr = msg.getMessageString() + "\n";
			this.outStream.write(msgStr.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}