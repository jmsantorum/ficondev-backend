package es.ficondev.web.backend.web.pages.client;

public class Registered {

	private String emailContent;

	public String getEmailContent() {
		return emailContent;
	}
	
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
    void onActivate(String emailContent) {    	
        this.emailContent = emailContent;
    }
    
    String onPassivate() {
		return emailContent;
	}
	
}
