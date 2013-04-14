package es.ficondev.web.backend.web.services;

import java.io.IOException;
import java.util.Calendar;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

public class SessionFilter implements RequestFilter 
{
	@SuppressWarnings("unused")
	private ApplicationStateManager applicationStateManager;
	@SuppressWarnings("unused")
	private Cookies cookies;
	private Subject currentUser;
	
	public SessionFilter(ApplicationStateManager applicationStateManager, Cookies cookies) 
	{
		this.applicationStateManager = applicationStateManager;
		this.cookies = cookies;
	}

	public boolean service(Request request, Response response, RequestHandler handler) throws IOException 
	{
		currentUser = SecurityUtils.getSubject();
		
		//Close session
		if (Calendar.getInstance().getTime().getTime() > currentUser.getSession().getStartTimestamp().getTime() + currentUser.getSession().getTimeout()) 
		{
			response.sendRedirect(request.getContextPath() + "/account/logout");
    		return true;
		}
		
		if ((request.getPath().equals("/account/login") || request.getPath().equals("/client/register")) && currentUser.isAuthenticated())
		{
			response.sendRedirect(request.getContextPath() + "/account/dashboard");
			return true;
		}
		
		handler.service(request, response);

		return true;
	}

}
