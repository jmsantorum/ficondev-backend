package es.ficondev.web.backend.web.pages.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.ficondev.web.backend.web.services.ConfigParameters;
import es.ficondev.web.modelutil.exceptions.IncorrectPasswordException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.accountservice.AccountService;

@RequiresGuest
public class Login 
{
	private Subject currentUser;
	private Session session;
	
    @Property
    private String username;

    @Property
    private String password;

    @Property
    private boolean rememberMyPassword;
    
    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Inject
    private AccountService userService;

    @InjectPage
    private Dashboard dashboard;
    
    private Account user = null;
	
    void onValidateFromLoginForm() {
    	if (!loginForm.isValid()) 
            return;

        try {
            user = userService.login(username, password, false);
            session.setAttribute("accountId", user.getAccountId().toString());
        } catch (InstanceNotFoundException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        } catch (IncorrectPasswordException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        }
    }

    void onPrepare() {
    	currentUser = SecurityUtils.getSubject();
    	session = currentUser.getSession();
    }
        
    Object onSuccessFromLoginForm() {
    	if (!currentUser.isAuthenticated()) 
    		setSessionToken();
        
        return dashboard;
    }
    
    private void setSessionToken() {
    	UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	    token.setRememberMe(rememberMyPassword);
	    currentUser.login(token);
	    
	    currentUser.getSession().setTimeout(ConfigParameters.SESSION_TIMEOUT * 1000);
    }
}
