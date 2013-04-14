package es.ficondev.web.backend.web.pages.client;

import java.util.Locale;
import java.util.TimeZone;

import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import es.ficondev.web.backend.web.services.ConfigParameters;
import es.ficondev.web.backend.web.services.Mail;
import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.accountservice.AccountService;

@SuppressWarnings("unused")
@RequiresGuest
public class Register 
{
	private Subject currentUser;
	private Session session;
	
    @Property
    private String username;

    @Property
    private String password;

    @Property
    private String retypePassword;

    @Property
	private String email;
   
    @Property
    private String timeZone;
    
    @Property
    private String name;
    
    @Property
    private String direction;
    
    @Property
    private String phoneNumber;
    
    @Inject
    private Locale currentLocale;
    
    @Inject
    private AccountService userService;

    @Component
    private Form registrationForm;

    @Component(id = "username")
    private TextField usernameField;

    @Component(id = "password")
    private PasswordField passwordField;

	@Component(id= "email")
	private TextField emailField;
	
    @Inject
    private Messages messages;
    
    @Inject
    private PageRenderLinkSource linkSource;
    
    private Account user;
    
    @Property
    private String publicKey = ConfigParameters.RECAPTCHA_PUBLIC_KEY;
    
    @Property
    private String privateKey = ConfigParameters.RECAPTCHA_PRIVATE_KEY;
    
    @InjectPage
    private Registered registered;
        
    void onValidateFromRegistrationForm() {
        if (!registrationForm.isValid()) 
            return;
        
       	if (!password.equals(retypePassword)) {
            registrationForm.recordError(passwordField, messages.get("error-passwordsDontMatch"));
        } else {
            try {
            	//TODO Change
            	user = userService.registerClient(username, password, email, currentLocale, TimeZone.getTimeZone(timeZone), name, direction, phoneNumber); 
            } catch (DuplicateInstanceException e) {
            	registrationForm.recordError(messages.get("error-" + e.getKey() + "-exists"));
            }
        }
    }

    Object onSuccessFromRegistrationForm() {
		Link validateLink = linkSource.createPageRenderLinkWithContext("account/ValidateAccount", user.getActivationCode());
	    
//		Mail.sendMail(
//				messages.get("user-registered-subject"),
//				String.format(messages.get("user-registered-body"), validateLink.toAbsoluteURI()),
//				user);
		
		registered.setEmailContent(String.format(messages.get("user-registered-body"), validateLink.toAbsoluteURI()));
        return registered;
    }
        
}
