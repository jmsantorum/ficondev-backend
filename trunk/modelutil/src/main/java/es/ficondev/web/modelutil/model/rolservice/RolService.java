package es.ficondev.web.modelutil.model.rolservice;

import java.util.List;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.rol.Rol;
import es.ficondev.web.modelutil.model.rol.RolType;

public interface RolService {
	Rol           	saveRol            	 	(Rol rol);
	void          	removeRol          	 	(Long rolId)       throws InstanceNotFoundException;
	Rol           	getRolById         		(Long rolId)       throws InstanceNotFoundException;
	
	List<Account>	getAccountsByRol   		(RolType rolType);
	List<RolType>	getRolByAccountId  		(Long AccountId);
	List<RolType>	getRolByAccountUsername	(String username);
	
	void			accountSetRol			(Long accountId, RolType rolType) throws InstanceNotFoundException;
	void			accountUnsetRol			(Long accountId, RolType rolType) throws InstanceNotFoundException;
}
