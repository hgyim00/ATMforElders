package silveratm;

import java.awt.Panel;

/**
 * 
 */
public abstract class ATMPanel extends Panel{
	private static final long serialVersionUID = 2292583331128116769L;

	/**
	 * 
	 * @param parent
	 */
	public abstract void create(ATM parent);

	/**
	 * 페이지를 보인 후 실행된다.
	 */
	public void onShowPage() { };

	/**
	 * 페이지를 감춘 후 실행된다.
	 */
	public void onHidePage() { };
}
