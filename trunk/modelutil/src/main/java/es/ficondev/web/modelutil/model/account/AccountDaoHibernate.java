package es.ficondev.web.modelutil.model.account;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

@Repository("accountDao")
public class AccountDaoHibernate extends GenericDaoHibernate<Account, Long> implements AccountDao {
	@Override
	public Account getAccountByEmail(String email) throws InstanceNotFoundException {
		Account account = (Account) getSession().createQuery(
				"SELECT a FROM Account a WHERE a.email = :email")
				.setParameter("email", email)
				.uniqueResult();

		if (account == null) 
			throw new InstanceNotFoundException(email, Account.class.getName());

		return account;
	}

	@Override
	public Account getAccountByUsername(String username) throws InstanceNotFoundException {
		Account account = (Account) getSession().createQuery(
				"SELECT a FROM Account a WHERE a.username = :username")
				.setParameter("username", username)
				.uniqueResult();

		if (account == null) 
			throw new InstanceNotFoundException(username, Account.class.getName());

		return account;
	}

	@Override
	public Account getAccountByActivationCode(String activationCode) throws InstanceNotFoundException {
		Account account = (Account) getSession().createQuery(
				"SELECT a FROM Account a " +
				"WHERE (a.activationCode = :activationCode) AND (a.activated = :activated)")
				.setParameter("activationCode", activationCode)
				.setParameter("activated", false)
				.uniqueResult();

		if (account == null) 
			throw new InstanceNotFoundException(activationCode, Account.class.getName());

		return account;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccounts(int startIndex, int count) {
		return getSession().createQuery(
				"SELECT a FROM Account a " +
				"ORDER BY a.username ASC").
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}

	@Override
	public int countAllAccounts() {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(a) FROM Account a ").
				uniqueResult();

		return (int) count;
	}

}
