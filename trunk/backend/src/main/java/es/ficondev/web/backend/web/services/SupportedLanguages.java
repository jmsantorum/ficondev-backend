package es.ficondev.web.backend.web.services;

import java.util.HashMap;
import java.util.Map;

public class SupportedLanguages 
{
	private final Map<String, String> options;

	private String codes = "";

	public SupportedLanguages() 
	{			

//		String options_en = "en=English, gl=Galician, es=Spanish";
//		String options_es = "es=Español, gl=Gallego, en=Inglés";
//		String options_gl = "es=Español, gl=Galego, en=Inglés";
		
		options = new HashMap<String, String>();
//		options.put("en", options_en);
//		options.put("es", options_es);
//		options.put("gl", options_gl);

		codes = "en";
		
	}

	public String getCodes() 
	{
		return codes;
	}

	public String getOptions(String languageCode) 
	{
		String languages = options.get(languageCode);

		if (languages != null) 
		{
			return languages;
		}
		else 
		{
			return options.get("en");
		}
	}
	
}
