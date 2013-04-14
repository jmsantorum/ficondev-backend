package es.ficondev.web.backend.web.pages.account;

import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.accountservice.AccountService;

@RequiresGuest
public class ValidateAccount {
	@Inject
	private AccountService userService;
	
	@InjectPage
	private AccountValidated validated;
	
	@InjectPage
	private Login login;
	
	Object onActivate() {
		return login;
	}
	
	Object onActivate(String activationCode) 
	{
		try {
			userService.activateAccount(activationCode);
			return validated;
		} catch (InstanceNotFoundException e) {
			
		}
		
		return login;
	}
}
