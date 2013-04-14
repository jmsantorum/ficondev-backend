package es.ficondev.web.backend.web.services;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.RequestFilter;
import org.tynamo.security.SecuritySymbols;


/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */

public final class AppModule 
{
	private AppModule(){
	}

	public static void bind(ServiceBinder binder) {
		/* Bind filters. */
		binder.bind(SessionFilter.class);
		binder.bind(SupportedLanguages.class);
//		binder.bind(MyRealm.class);
		
		binder.bind(AuthorizingRealm.class, MyRealm.class).withId(MyRealm.class.getSimpleName());
	}

	public static void contributeApplicationDefaults(
			MappedConfiguration<String, String> configuration,
			SupportedLanguages supportedLanguages) 
	{
		// Contributions to ApplicationDefaults will override any contributions
		// to FactoryDefaults (with the same key). Here we're restricting the
		// supported locales.
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, supportedLanguages.getCodes());
		configuration.add(SecuritySymbols.LOGIN_URL, "/account/login");
		//		configuration.add(SecuritySymbols.SUCCESS_URL, "/user/dashboard");
		configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/account/login");
	}

	public static void contributeRequestHandler(
			OrderedConfiguration<RequestFilter> configuration,
			SessionFilter sessionFilter) 
	{
		/* Add filters to the RequestHandler service. */
		configuration.add("SessionFilter", sessionFilter, "after:*");
	}

	@Contribute(WebSecurityManager.class)
	public static void addRealms(Configuration<Realm> configuration, @InjectService("MyRealm") AuthorizingRealm accountRealm) {
		configuration.add(accountRealm);
	}

	@Contribute(MarkupRenderer.class)
	public static void deactiveDefaultCSS(OrderedConfiguration<MarkupRendererFilter> configuration)
	{
		configuration.override("InjectDefaultStylesheet", null);
	}
}