package silveratm.message;

import silveratm.ATMState;

/**
 * PIN 인증 메시지
 */
public abstract class ValidatePINMessage {

	public static final String TYPE = "ValidatePIN:";

	/**
	 * PIN 인증 요청 메시지
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
		private String remitAccount;

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

			return ret;
		}

		@Override
		public void reset() {
			cardNum = null;
			securityCode = null;
			pin = null;
			remitAccount = null;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.REQUEST);

			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");
			msg.append("pin=").append(pin).append(",");
			msg.append("remitAccount=").append(remitAccount);

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
				}
			}
			return true;
		}
	}

	/**
	 * PIN 인증 결과 메시지
	 */
	public static class Result implements Message {

		/**
		 * 인증 결과
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
		 * 사용자명
		 */
		private String myName = null;

		/**
		 * PIN 번호
		 */
		private String pin = null;

		/**
		 * 송금 계좌
		 */
		private String remitAccount;

		/**
		 * 송금 계좌 예금주 이름
		 */
		private String remitName;

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
			myName = atmState.getMyName();
			pin = atmState.getPin();
			remitAccount = atmState.getRemitAccount();
			remitName = atmState.getRemitName();
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

		public String getMyName() {
			return myName;
		}

		public void setMyName(String myName) {
			this.myName = myName;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public String getRemitAccount() {
			return remitAccount;
		}

		public void setRemitAccount(String remitAccount) {
			this.remitAccount = remitAccount;
		}

		public String getRemitName() {
			return remitName;
		}

		public void setRemitName(String remitName) {
			this.remitName = remitName;
		}

		@Override
		public void reset() {
			success = false;
			cardNum = null;
			securityCode = null;
			myName = null;
			pin = null;
			remitAccount = null;
			remitName = null;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.RESULT);

			msg.append("success=").append(success ? "true" : "false").append(",");
			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");
			msg.append("myName=").append(myName).append(",");
			msg.append("pin=").append(pin).append(",");
			msg.append("remitAccount=").append(remitAccount).append(",");
			msg.append("remitName=").append(remitName);

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
				case "myName":
					myName = nameValue[1];
					break;
				case "pin":
					pin = nameValue[1];
					break;
				case "remitAccount":
					remitAccount = nameValue[1];
					break;
				case "remitName":
					remitName = nameValue[1];
					break;
				}
			}
			return true;
		}
	}
}
