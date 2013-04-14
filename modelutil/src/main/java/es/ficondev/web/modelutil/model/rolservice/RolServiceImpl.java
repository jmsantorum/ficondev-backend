package es.ficondev.web.modelutil.model.rolservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.account.AccountDao;
import es.ficondev.web.modelutil.model.rol.Rol;
import es.ficondev.web.modelutil.model.rol.RolDao;
import es.ficondev.web.modelutil.model.rol.RolType;

@Service("rolService")
@Transactional
public class RolServiceImpl implements RolService {
	@Autowired
	private RolDao rolDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public Rol saveRol(Rol rol) {
		rolDao.save(rol);
		
		return rol;
	}
	
	@Override
	public void removeRol(Long rolId) throws InstanceNotFoundException {
		rolDao.remove(rolId);
	}
	
	@Override
	public Rol getRolById(Long rolId) throws InstanceNotFoundException {
		return rolDao.find(rolId);
	}
	
	@Override
	public List<Account> getAccountsByRol(RolType rolType) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try {
			accounts.addAll(rolDao.getRolByRolType(rolType).getAccounts());
		}
		catch (InstanceNotFoundException e) {
			
		}
		
		return accounts;
	}
	
	@Override
	public List<RolType> getRolByAccountId(Long accountId) {
		return rolDao.getRolByAccountId(accountId);
	}

	@Override
	public List<RolType> getRolByAccountUsername(String username) {
		return rolDao.getRolByAccountUsername(username);
	}

	@Override
	public void accountSetRol(Long accountId, RolType rolType) throws InstanceNotFoundException {
		Account account = accountDao.find(accountId);
		Rol rol;
		
		try {
			rol = rolDao.getRolByRolType(rolType);
		}
		catch (InstanceNotFoundException e) {
			rol = new Rol(rolType);
		}

		rol.addAccount(account);
		
		rolDao.save(rol);
	}

	@Override
	public void accountUnsetRol(Long accountId, RolType rolType) throws InstanceNotFoundException {
		Account account = accountDao.find(accountId);
		Rol rol = rolDao.getRolByRolType(rolType);

		rol.removeAccount(account);
		
		rolDao.save(rol);
	}
}
