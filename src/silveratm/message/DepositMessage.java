package silveratm.message;

import silveratm.ATMState;

/**
 * 입금 메시지
 */
public abstract class DepositMessage {

	public static final String TYPE = "Deposit:"; 

	/**
	 * 입금 요청 메시지
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
		 * 입금액
		 */
		private int depositAmount = 0;

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
			depositAmount = atmState.getDepositAmount();
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
			ret.setDepositAmount(depositAmount);

			return ret;
		}

		@Override
		public void reset() {
			cardNum = null;
			securityCode = null;
			depositAmount = 0;
		}

		@Override
		public String getMessageString() {
			StringBuffer msg = new StringBuffer();

			msg.append(TYPE);
			msg.append(Message.REQUEST);

			msg.append("cardNum=").append(cardNum).append(",");
			msg.append("securityCode=").append(securityCode).append(",");
			msg.append("depositAmount=").append(depositAmount);

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
				case "depositAmount":
					depositAmount = Integer.parseInt(nameValue[1]);
					break;
				}
			}
			return true;
		}
	}

	/**
	 * 입금 결과 메시지
	 */
	public static class Result implements Message {

		/**
		 * 입금 결과
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
		 * 입금액
		 */
		private int depositAmount = 0;

		/**
		 * 입금 전 잔액
		 */
		private int prevBalance = 0;

		/**
		 * 입금 후 잔액
		 */
		private int balance = 0;

		/**
		 * 수수료
		 */
		private int cummission = 0;

		/**
		 * 
		 */
		public Result() {
			reset();
		}

		/**
		 * 
		 * @param success
		 * @param atmState
		 */
		public Result(boolean success, ATMState atmState) {
			this.success = success;
			cardNum = atmState.getCardNum();
			securityCode = atmState.getSecurityCode();
			depositAmount = atmState.getDepositAmount();
			prevBalance = atmState.getBalance();
			balance = atmState.getBalance();
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

		public int getDepositAmount() {
			return depositAmount;
		}

		public void setDepositAmount(int depositAmount) {
			this.depositAmount = depositAmount;
		}

		public int getPrevBalance() {
			return prevBalance;
		}

		public void setPrevBalance(int prevBalance) {
			this.prevBalance = prevBalance;
		}

		public int getBalance() {
			return balance;
		}

		public void setBalance(int balance) {
			this.balance = balance;
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
			depositAmount = 0;
			prevBalance = 0;
			balance = 0;
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
			msg.append("depositAmount=").append(depositAmount).append(",");
			msg.append("prevBalance=").append(prevBalance).append(",");
			msg.append("balance=").append(balance).append(",");
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
				case "depositAmount":
					depositAmount = Integer.parseInt(nameValue[1]);
					break;
				case "prevBalance":
					prevBalance = Integer.parseInt(nameValue[1]);
					break;
				case "balance":
					balance = Integer.parseInt(nameValue[1]);
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
