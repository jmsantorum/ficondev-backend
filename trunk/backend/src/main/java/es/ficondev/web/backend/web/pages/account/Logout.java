package es.ficondev.web.backend.web.pages.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.accountservice.AccountService;
import es.ficondev.web.modelutil.model.rolservice.RolService;

@RequiresUser
public class Logout 
{
	private Subject currentUser;
	private Session session;
	
	@Inject
	private AccountService accountService;
	
	@Inject
	private RolService rolService;
	
	@InjectPage
	private Login login;
	
	Object onActivate() {
		currentUser = SecurityUtils.getSubject();
		session = currentUser.getSession();
		
		try {
			Long accountId = Long.valueOf((String) session.getAttribute("accountId"));
		    
			try {
				accountService.getAccountById(accountId);
			} catch (InstanceNotFoundException e) {

			}
		}
		catch (Exception e) {
			
		}
		
    	session.stop();
    	currentUser.logout();
    	
		return login;
	}
}
