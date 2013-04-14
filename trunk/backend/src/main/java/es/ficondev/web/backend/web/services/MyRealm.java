package es.ficondev.web.backend.web.services;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.account.Account;
import es.ficondev.web.modelutil.model.accountservice.AccountService;
import es.ficondev.web.modelutil.model.rol.RolType;
import es.ficondev.web.modelutil.model.rolservice.RolService;

public class MyRealm extends AuthorizingRealm 
{
	private AccountService accountService;
	private RolService rolService;

	public MyRealm(AccountService accountService, RolService rolService) {
		super(new MemoryConstrainedCacheManager());
		setName("localaccounts");

		HashedCredentialsMatcher authenticator = new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME);
		authenticator.setHashIterations(25000);
		setCredentialsMatcher(authenticator);

		this.accountService = accountService;
		this.rolService = rolService;
	}

	@Override
    protected SaltedAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		System.out.println("metodo-1");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        
        // Null username is invalid
        if (username == null) throw new AccountException("Null usernames are not allowed by this realm.");

        Account account = null;
        
        try {
        	account = accountService.getAccountByUsername(username);
        } catch (InstanceNotFoundException e) {
        	try {
				account = accountService.getAccountByEmail(username);
			} catch (InstanceNotFoundException e1) {
        		throw new UnknownAccountException("Not valid account found");
        	}
        }

        if (!account.getActivated()) { throw new LockedAccountException("Account [" + account.getEmail() + "] is locked."); }

        return buildAuthenticationInfo(account.getUsername(), account.getPassword(), account.getSalt());
    }

    protected SaltedAuthenticationInfo buildAuthenticationInfo(String username, String password, String salt) {
    	return new SimpleAuthenticationInfo(username, password,  new SimpleByteSource(salt), getName());
    }
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("metodo-2");
		if (principals == null) throw new AuthorizationException("PrincipalCollection method argument cannot be null.");

		String username = (String) getAvailablePrincipal(principals);

		Set<String> roleNames = getRoleNamesForUser(username);

		Set<String> permissions = getPermissions(username, roleNames);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
		info.setStringPermissions(permissions);

		return info;
	}
	
	protected Set<String> getRoleNamesForUser(String username) {
		Set<String> roleNames = new LinkedHashSet<String>();

		List<RolType> roles = rolService.getRolByAccountUsername(username);

		for(int i=0; i<roles.size(); i++) 
			roleNames.add(roles.get(i).name());

		return roleNames;
	}

	protected Set<String> getPermissions(String username, Collection<String> roleNames) {
		Set<String> permissions = new LinkedHashSet<String>();

		//        List<String> userpermissions = rolService.findPermissionsByAccountUsername(username);
		//        
		//        for(int i=0; i<permisos.size(); i++) 
		//        {
		//        	permissions.add(permisos.get(i));
		//        }

		return permissions;
	}

}
