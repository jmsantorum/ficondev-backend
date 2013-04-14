package es.ficondev.web.modelutil.exceptions;

public class InstanceNotFoundException extends InstanceException {

	private static final long serialVersionUID = -5032013510110419323L;

	public InstanceNotFoundException(Object key, String className) {
		super("Instance not found", key, className);
	}

}