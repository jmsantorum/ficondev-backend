package es.ficondev.web.backend.web.pages.chef.plate;

import org.apache.shiro.authz.annotation.RequiresRoles;

@RequiresRoles("CHEF")
public class Notification {

	private String content;

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
    void onActivate(String content) {    	
        this.content = content;
    }
    
    String onPassivate() {
		return this.content;
	}
	
}
