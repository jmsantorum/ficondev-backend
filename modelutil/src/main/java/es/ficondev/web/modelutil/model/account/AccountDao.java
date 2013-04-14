package es.ficondev.web.modelutil.model.account;


import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

public interface AccountDao extends GenericDao<Account, Long> {
	Account	 		getAccountByEmail			(String email)							throws InstanceNotFoundException;
	Account		 	getAccountByUsername		(String username)						throws InstanceNotFoundException;
	Account			getAccountByActivationCode	(String activationCode)					throws InstanceNotFoundException;
	
	List<Account> 	getAllAccounts				(int startIndex, int count);
	int 			countAllAccounts			();
}
