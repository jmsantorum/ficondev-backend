package es.ficondev.web.backend.web.pages.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.accountservice.AccountService;

@RequiresAuthentication
public class Dashboard {
	
	@Inject
	private AccountService accountService;
	
	@Property
	private Account account;
	
	private Subject currentUser;
	private Session session;
	private Long accountId;
	
	public void setupRender() {
		currentUser = SecurityUtils.getSubject();
    	session = currentUser.getSession();
    	    	
    	try {
    		accountId = Long.valueOf((String) session.getAttribute("accountId"));
    		
			account = accountService.getAccountById(accountId);
		} 
		catch (Exception e) {

		}
	}
}
