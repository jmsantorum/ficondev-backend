package es.ficondev.web.modelutil.model.rol;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

@Repository("rolDao")
public class RolDaoHibernate extends GenericDaoHibernate<Rol, Long> implements RolDao {
	@Override
	public Rol getRolByRolType(RolType rolType) throws InstanceNotFoundException {
		Rol rol = (Rol) getSession().createQuery(
				"SELECT r FROM Rol r WHERE r.rolType = :rolType")
				.setParameter("rolType", rolType)
				.uniqueResult();

		if (rol == null) 
			throw new InstanceNotFoundException(rol, Rol.class.getName());
		else 
			return rol;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolType> getRolByAccountId(Long accountId) {
		return getSession().createQuery(
				"SELECT r.rolType FROM Account a, Rol r WHERE a in elements(r.accounts) AND (a.accountId = :accountId) ")
				.setParameter("accountId", accountId)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RolType> getRolByAccountUsername(String username) {
		return getSession().createQuery(
				"SELECT r.rolType FROM Account a, Rol r WHERE a in elements(r.accounts) AND (a.username = :username)")
				.setParameter("username", username)
				.list();
	}
}
