package silveratm.message;

import silveratm.ATMState;

/**
 * 이체 메시지
 */
public abstract class RemitMessage {

	public static final String TYPE = "Remit:"; 

	/**
	 * 이체 요청 메시지
	 */
	public static class Request implements Message {

		/**
		 * 카드번호
		 */
		private String cardNum = null;

		/**
		 * Security code
		 */
		private String securityCode = null;

		/**
		 * PIN 번호
		 */
		private String pin = null;

		/**
		 * 송금 계좌
		 */
		private String remitAccount = null;

		/**
		 * 송금액
		 */
		private int remitAmount = 0;

		/**
		 * 
		 */
		public Request() {
			reset();
		}

		/**
		 * 
		 * @param atmState
		 */
		public Request(ATMState atmState) {
			cardNum = atmState.getCardNum();
			securityCode = atmState.getSecurityCode();
			pin = atmState.getPin();
			remitAccount = atmState.getRemitAccount();
			remitAmount = atmState.getRemitAmount();
		}

		/**
		 * 
		 * @param ret
		 * @return
		 */
		public ATMState toATMState(ATMState ret) {
			if (ret == null) {
				ret = new ATMState();
			} else {
				ret.reset();
			}

			ret.setCardNum(cardNum);
			ret.setSecurityCode(securityCode);
			ret.setPin(pin);
			ret.setRemitAccount(remitAccount);
			ret.setRemitAmount(remitAmount);

			return ret;
		}

		@Override
		public void reset() {
			cardNum = null;
			securityCode = null;
			pin = null;
			remitAccount = null;
			remitAmount = 0;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.REQUEST);

			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");
			msg.append("pin=").append(pin).append(",");
			msg.append("remitAccount=").append(remitAccount).append(",");
			msg.append("remitAmount=").append(remitAmount);

			return msg.toString();
		}

		@Override
		public boolean setMessageString(String msg) {
			reset();

			String type = TYPE + Message.REQUEST;
			if (!msg.startsWith(type)) {
				return false;
			}

			String[] params = msg.substring(type.length()).split(",");
			String[] nameValue;
			for (String param : params) {
				nameValue = param.split("=");
				switch (nameValue[0]) {
				case "cardNum":
					cardNum = nameValue[1];
					break;
				case "securityCode":
					securityCode = nameValue[1];
					break;
				case "pin":
					pin = nameValue[1];
					break;
				case "remitAccount":
					remitAccount = nameValue[1];
					break;
				case "remitAmount":
					remitAmount = Integer.parseInt(nameValue[1]);
					break;
				}
			}
			return true;
		}
	}

	/**
	 * 이체 결과 메시지
	 */
	public static class Result implements Message {

		/**
		 * 이체 결과
		 */
		boolean success = false;

		/**
		 * 카드번호
		 */
		private String cardNum = null;

		/**
		 * Security code
		 */
		private String securityCode = null;

		/**
		 * 잔액
		 */
		private int balance = 0;

		/**
		 * 송금액
		 */
		private int remitAmount;

		/**
		 * 수수료
		 */
		private int cummission;

		/**
		 * 
		 */
		public Result() {
			reset();
		}

		/**
		 * 
		 * @param atmState
		 */
		public Result(boolean success, ATMState atmState) {
			this.success = success;
			cardNum = atmState.getCardNum();
			securityCode = atmState.getSecurityCode();
			balance = atmState.getBalance();
			remitAmount = atmState.getRemitAmount();
			cummission = atmState.getCummission();
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
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

		public int getBalance() {
			return balance;
		}

		public void setBalance(int balance) {
			this.balance = balance;
		}

		public int getRemitAmount() {
			return remitAmount;
		}

		public void setRemitAmount(int remitAmount) {
			this.remitAmount = remitAmount;
		}

		public int getCummission() {
			return cummission;
		}

		public void setCummission(int cummission) {
			this.cummission = cummission;
		}

		@Override
		public void reset() {
			success = false;
			cardNum = null;
			securityCode = null;
			balance = 0;
			remitAmount = 0;
			cummission = 0;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.RESULT);

			msg.append("success=").append(success ? "true" : "false").append(",");
			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");
			msg.append("balance=").append(balance).append(",");
			msg.append("remitAmount=").append(remitAmount).append(",");
			msg.append("cummission=").append(cummission);

			return msg.toString();
		}

		@Override
		public boolean setMessageString(String msg) {
			reset();

			String type = TYPE + Message.RESULT;
			if (!msg.startsWith(type)) {
				return false;
			}

			String[] params = msg.substring(type.length()).split(",");
			String[] nameValue;
			for (String param : params) {
				nameValue = param.split("=");
				switch (nameValue[0]) {
				case "success":
					success = "true".equals(nameValue[1]) ? true : false;
					break;
				case "cardNum":
					cardNum = nameValue[1];
					break;
				case "securityCode":
					securityCode = nameValue[1];
					break;
				case "balance":
					balance = Integer.parseInt(nameValue[1]);
					break;
				case "remitAmount":
					remitAmount = Integer.parseInt(nameValue[1]);
					break;
				case "cummission":
					cummission = Integer.parseInt(nameValue[1]);
					break;
				}
			}
			return true;
		}
	}
}
