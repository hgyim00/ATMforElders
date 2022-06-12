package silveratm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import silveratm.message.DepositMessage;
import silveratm.message.RemitMessage;
import silveratm.message.UpdateBankbookMessage;
import silveratm.message.ValidateCardMessage;
import silveratm.message.ValidatePINMessage;
import silveratm.message.WithdrawMessage;

/**
 * @description 서버 대행. 클라이언트와 서버 중재 역할
 */
public class RemoteATMService implements ATMService {

	String host;
	int port;

	/**
	 * 서버 접속
	 */
	Socket socket;

	/**
	 * 서버로 부터 받는 데이터
	 */
	private BufferedReader inStream = null;

	/**
	 * 서버로 보내는 데이터
	 */
	private DataOutputStream outStream = null;

	/**
	 * 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public RemoteATMService(String host, int port) throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
	}

	@Override
	public ValidateCardMessage.Result validateCard(ATMState atmState) {
		ValidateCardMessage.Request reqMsg = new ValidateCardMessage.Request(atmState);
		String reqStr = reqMsg.getMessageString();
		if (reqStr == null) {
			return null;
		}

		try {
			send(reqStr);
			String resStr = receive();

			ValidateCardMessage.Result resMsg = new ValidateCardMessage.Result();
			if (resMsg.setMessageString(resStr)) {
				return resMsg;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ValidatePINMessage.Result validatePin(ATMState atmState) {
		ValidatePINMessage.Request reqMsg = new ValidatePINMessage.Request(atmState);
		String reqStr = reqMsg.getMessageString();
		if (reqStr == null) {
			return null;
		}

		try {
			send(reqStr);
			String resStr = receive();
	
			ValidatePINMessage.Result resMsg = new ValidatePINMessage.Result();
			if (resMsg.setMessageString(resStr)) {
				return resMsg;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public WithdrawMessage.Result withdraw(ATMState atmState) {
		WithdrawMessage.Request reqMsg = new WithdrawMessage.Request(atmState);
		String reqStr = reqMsg.getMessageString();
		if (reqStr == null) {
			return null;
		}

		try {
			send(reqStr);
			String resStr = receive();
	
			WithdrawMessage.Result resMsg = new WithdrawMessage.Result();
			if (resMsg.setMessageString(resStr)) {
				return resMsg;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public DepositMessage.Result deposit(ATMState atmState) {
		DepositMessage.Request reqMsg = new DepositMessage.Request(atmState);
		String reqStr = reqMsg.getMessageString();
		if (reqStr == null) {
			return null;
		}

		try {
			send(reqStr);
			String resStr = receive();
	
			DepositMessage.Result resMsg = new DepositMessage.Result();
			if (resMsg.setMessageString(resStr)) {
				return resMsg;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public RemitMessage.Result remit(ATMState atmState) {
		RemitMessage.Request reqMsg = new RemitMessage.Request(atmState);
		String reqStr = reqMsg.getMessageString();
		if (reqStr == null) {
			return null;
		}

		try {
			send(reqStr);
			String resStr = receive();
	
			RemitMessage.Result resMsg = new RemitMessage.Result();
			if (resMsg.setMessageString(resStr)) {
				return resMsg;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public UpdateBankbookMessage.Result updateBankbook(ATMState atmState) {
		UpdateBankbookMessage.Request reqMsg = new UpdateBankbookMessage.Request(atmState);
		String reqStr = reqMsg.getMessageString();
		if (reqStr == null) {
			return null;
		}

		try {
			send(reqStr);
			String resStr = receive();
	
			UpdateBankbookMessage.Result resMsg = new UpdateBankbookMessage.Result();
			if (resMsg.setMessageString(resStr)) {
				return resMsg;
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void connect() throws UnknownHostException, IOException {
		try {
			socket = new Socket(host, port);
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			disconnect();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
			throw e;
		}
	}

	@Override
	public void disconnect() throws IOException {
		if (inStream != null) {
			inStream.close();
			inStream = null;
		}
		if (outStream != null) {
			outStream.close();
			outStream = null;
		}
		if (socket != null) {
			socket.close();
			socket = null;
		}
	}

	/**
	 * 
	 * @param msg
	 * @throws IOException
	 */
	private void send(String msg) throws IOException {
		this.outStream.writeBytes(msg + '\n');
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private String receive() throws IOException {
		return this.inStream.readLine();
	}
}
