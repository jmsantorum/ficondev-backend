package es.ficondev.web.modelutil.exceptions;

public class DuplicateInstanceException extends InstanceException {

	private static final long serialVersionUID = -3050959677466132954L;

	public DuplicateInstanceException(Object key, String className) {
		super("Duplicate instance", key, className);
	}

}