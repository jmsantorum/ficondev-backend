package es.ficondev.web.modelutil.model.rol;

import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

public interface RolDao extends GenericDao<Rol,Long> 
{
	Rol				getRolByRolType			(RolType rolType) throws InstanceNotFoundException;
	
	List<RolType>	getRolByAccountId		(Long accountId);
	List<RolType>	getRolByAccountUsername	(String username);
}
