package es.ficondev.web.modelutil.exceptions;

public class IncorrectPasswordException extends InstanceException 
{	
	private static final long serialVersionUID = -7472993135975102515L;
	
    public IncorrectPasswordException(Object key, String className) {
        super("Incorrect password exception", key, className);	
    }
    
}
