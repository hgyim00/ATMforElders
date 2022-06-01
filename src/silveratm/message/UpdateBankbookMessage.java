package silveratm.message;

import silveratm.ATMState;

/**
 * 통장정리 메시지
 */
public abstract class UpdateBankbookMessage {

	public static final String TYPE = "UpdateBankbook:";

	/**
	 * 통장정리 요청 메시지
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

			return ret;
		}

		@Override
		public void reset() {
			cardNum = null;
			securityCode = null;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.REQUEST);

			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");

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
				}
			}
			return true;
		}
	}

	/**
	 * 통장정리 결과 메시지
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

		@Override
		public void reset() {
			success = false;
			cardNum = null;
			securityCode = null;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.RESULT);

			msg.append("success=").append(success ? "true" : "false").append(",");
			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");

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
				}
			}
			return true;
		}
	}
}
