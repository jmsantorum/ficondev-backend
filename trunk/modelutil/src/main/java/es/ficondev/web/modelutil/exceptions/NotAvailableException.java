package es.ficondev.web.modelutil.exceptions;

public class NotAvailableException extends InstanceException {

	private static final long serialVersionUID = -5444285346349237669L;

	public NotAvailableException(Object key, String className) {
		super("Not available exception", key, className);
	}

}
