package es.ficondev.web.modelutil.model.accountservice;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.IncorrectPasswordException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.rol.RolType;

public interface AccountService {
	Account			getAccountById				(Long accountId)						throws InstanceNotFoundException;
	Account	 		getAccountByEmail			(String email)							throws InstanceNotFoundException;
	Account		 	getAccountByUsername		(String username)						throws InstanceNotFoundException;
	
	List<Account> 	getAllAccounts				(int startIndex, int count);
	int 			countAllAccounts			();
	
	void 			activateAccount				(String activationCode) throws InstanceNotFoundException;
	Account 		saveAccount					(Account account);
	void 			removeAccount				(Long accountId)		throws InstanceNotFoundException;
	
	Account 		login						(String username, String password, boolean passwordIsEncrypted) throws InstanceNotFoundException, IncorrectPasswordException;

	Account			registerAdmin				(String username, String password, String email, Locale locale, TimeZone timeZone) throws DuplicateInstanceException;
	Account			registerClient				(String username, String password, String email, Locale locale, TimeZone timeZone, String name, String direction, String phoneNumber) throws DuplicateInstanceException;
	Account			registerEmployee			(String username, String password, String email, Locale locale, TimeZone timeZone, String name, String direction, String phoneNumber, Double salary, RolType rolType)	throws DuplicateInstanceException;
	Account			registerProvider			(String username, String password, String email, Locale locale, TimeZone timeZone, String fullName, String direction, String phoneNumber, String phoneNumber2, String fax, String contactName, String cif, String web)	throws DuplicateInstanceException;
	
	void 			changePassword				(Long accountId, String oldClearPassword, String newClearPassword)	throws IncorrectPasswordException, InstanceNotFoundException;
}
