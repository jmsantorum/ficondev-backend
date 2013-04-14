package es.ficondev.web.modelutil.model.accountservice;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.IncorrectPasswordException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.account.AccountDao;
import es.ficondev.web.modelutil.model.account.physical.Client;
import es.ficondev.web.modelutil.model.account.physical.Employee;
import es.ficondev.web.modelutil.model.account.physical.legal.provider.Provider;
import es.ficondev.web.modelutil.model.rol.RolType;
import es.ficondev.web.modelutil.model.rolservice.RolService;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;

	@Autowired
	private RolService rolService;

	@Override
	@Transactional (readOnly = true)
	public Account getAccountById(Long accountId) throws InstanceNotFoundException {
		return accountDao.find(accountId);
	}

	@Override
	@Transactional (readOnly = true)
	public Account getAccountByEmail(String email) throws InstanceNotFoundException {
		return accountDao.getAccountByEmail(email);
	}

	@Override
	@Transactional (readOnly = true)
	public Account getAccountByUsername(String username) throws InstanceNotFoundException {
		return accountDao.getAccountByUsername(username);
	}

	@Override
	@Transactional (readOnly = true)
	public List<Account> getAllAccounts(int startIndex, int count) {
		return accountDao.getAllAccounts(startIndex, count);
	}

	@Override
	public int countAllAccounts() {
		return accountDao.countAllAccounts();
	}

	@Override
	public void activateAccount(String activationCode) throws InstanceNotFoundException {
		Account account = accountDao.getAccountByActivationCode(activationCode);

		account.setActivated(true);

		accountDao.save(account);
	}

	@Override
	public Account saveAccount(Account account) {
		accountDao.save(account);

		return account;
	}

	@Override
	public void removeAccount(Long accountId) throws InstanceNotFoundException {
		Account account = accountDao.find(accountId);

		account.setActivated(false);

		accountDao.save(account);
	}

	@Override
	public Account login(String username, String password, boolean passwordIsEncrypted) throws InstanceNotFoundException, IncorrectPasswordException {
		Account account;

		try {
			account = accountDao.getAccountByUsername(username);
		} catch(InstanceNotFoundException e) {
			account = accountDao.getAccountByEmail(username);
		}

		if (!account.getActivated()) {
			throw new InstanceNotFoundException("activated", Account.class.getName());
		} else {
			String storedPassword = account.getPassword();
			String storedSalt = account.getSalt();

			if (passwordIsEncrypted) {
				if (!password.equals(storedPassword)) 
					throw new IncorrectPasswordException(username, Account.class.getName());
			} else {
				String oldHashedPasswordBase64 = new Sha512Hash(password, storedSalt, 25000).toString();

				if (!oldHashedPasswordBase64.equals(storedPassword)) 
					throw new IncorrectPasswordException(account.getUsername(), Account.class.getName());
			}
		}

		return account;
	}

	@Override
	public Account registerAdmin(String username, String password, String email, Locale locale, TimeZone timeZone) throws DuplicateInstanceException {
		try {
			accountDao.getAccountByEmail(email);

			throw new DuplicateInstanceException("email", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			//The email doesn't exists
		}

		try {
			accountDao.getAccountByUsername(username);

			throw new DuplicateInstanceException("username", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toString();		
			String hashedPasswordBase64 = new Sha512Hash(password, salt, 25000).toString();

			Account account = new Account(username, hashedPasswordBase64, salt, email, locale, timeZone);

			accountDao.save(account);

			try {
				rolService.accountSetRol(account.getAccountId(), RolType.ADMIN);
			} catch (InstanceNotFoundException e1) {

			}

			return account;
		}
	}

	@Override
	public Account registerClient(String username, String password, String email, Locale locale, TimeZone timeZone, String name, String direction, String phoneNumber) throws DuplicateInstanceException {
		try {
			accountDao.getAccountByEmail(email);

			throw new DuplicateInstanceException("email", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			//The email doesn't exists
		}

		try {
			accountDao.getAccountByUsername(username);

			throw new DuplicateInstanceException("username", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toString();		
			String hashedPasswordBase64 = new Sha512Hash(password, salt, 25000).toString();

			Account account = new Client(username, hashedPasswordBase64, salt, email, locale, timeZone, name, direction, phoneNumber);

			accountDao.save(account);

			try {
				//TODO Change to RolType.CLIENT
				//rolService.accountSetRol(account.getAccountId(), RolType.CLIENT);
				rolService.accountSetRol(account.getAccountId(), RolType.CHEF);
			} catch (InstanceNotFoundException e1) {

			}

			return account;
		}
	}

	@Override
	public Account registerEmployee(String username, String password, String email, Locale locale, TimeZone timeZone, String name, String direction, String phoneNumber, Double salary, RolType rolType)	throws DuplicateInstanceException {
		try {
			accountDao.getAccountByEmail(email);

			throw new DuplicateInstanceException("email", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			//The email doesn't exists
		}

		try {
			accountDao.getAccountByUsername(username);

			throw new DuplicateInstanceException("username", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toString();		
			String hashedPasswordBase64 = new Sha512Hash(password, salt, 25000).toString();

			Account account = new Employee(username, hashedPasswordBase64, salt, email, locale, timeZone, name, direction, phoneNumber, salary);

			accountDao.save(account);

			try {
				rolService.accountSetRol(account.getAccountId(), rolType);
			} catch (InstanceNotFoundException e1) {

			}

			return account;
		}
	}

	@Override
	public Account registerProvider(String username, String password, String email, Locale locale, TimeZone timeZone, String fullName, String direction, String phoneNumber, String phoneNumber2, String fax, String contactName, String cif, String web) throws DuplicateInstanceException {
		try {
			accountDao.getAccountByEmail(email);

			throw new DuplicateInstanceException("email", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			//The email doesn't exists
		}

		try {
			accountDao.getAccountByUsername(username);

			throw new DuplicateInstanceException("username", Account.class.getName());
		} catch (InstanceNotFoundException e) {
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toString();		
			String hashedPasswordBase64 = new Sha512Hash(password, salt, 25000).toString();

			Account account = new Provider(username, hashedPasswordBase64, salt, email, locale, timeZone, fullName, direction, phoneNumber, phoneNumber2, fax, contactName, cif, web);

			accountDao.save(account);

			try {
				rolService.accountSetRol(account.getAccountId(), RolType.PROVIDER);
			} catch (InstanceNotFoundException e1) {

			}

			return account;
		}
	}

	@Override
	public void changePassword(Long accountId, String oldClearPassword, String newClearPassword) throws IncorrectPasswordException, InstanceNotFoundException {
		Account account = accountDao.find(accountId);

		String storedPassword = account.getPassword();
		String storedSalt = account.getSalt();

		String oldHashedPasswordBase64 = new Sha512Hash(oldClearPassword, storedSalt, 25000).toString();

		if (!oldHashedPasswordBase64.equals(storedPassword)) 
			throw new IncorrectPasswordException(account.getUsername(), Account.class.getName());

		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toString();		
		String newHashedPasswordBase64 = new Sha512Hash(newClearPassword, salt, 25000).toString();

		account.setSalt(salt);
		account.setPassword(newHashedPasswordBase64);
	}

}
