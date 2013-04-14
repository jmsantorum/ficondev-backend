package es.ficondev.web.backend.web.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import es.ficondev.web.modelutil.model.account.Account;

public final class Mail 
{
	private static final Properties properties = new Properties();
	private static final String MAIL_CONF = ConfigParameters.CONFIG_DIR + File.separator + "mail.conf";
	
	static
	{
		try
		{
			FileInputStream propertiesFile = new FileInputStream(MAIL_CONF);
			properties.load(propertiesFile);
		}
        catch (IOException e) 
		{
			System.err.println("Mail configuration file (" + MAIL_CONF + ") cannot be resolved");
			System.exit(ConfigParameters.EXIT_PROPERTIES_ERROR);
		}
	}
	
	private Mail()
	{
		
	}
	
	public synchronized static void sendMail(String subject, String body, Account recipient)
	{
		try
	    {
	        // Prepare session
	        Session session = Session.getDefaultInstance(properties);

	        // Create message
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
	        
        	message.addRecipient( Message.RecipientType.TO, new InternetAddress(recipient.getEmail()));
	    
	        message.setSubject(properties.getProperty("mail.subject.tag") + " " + subject, "UTF-8");
	        
	        message.setText(
	        		String.format(properties.getProperty("mail.header.dear"), recipient.getUsername()) + "<br/><br/>" +
	        		body + "<br/><br/>--</br/><br/>" + 
	        		properties.getProperty("mail.footer.mandatory") + "<br/><br/>" + 
	        		properties.getProperty("mail.footer.confidentiality"),
	        		
	        "UTF-8");
	        
	        message.setHeader("Content-Type", "text/html; charset=UTF-8");

	        // Send
	        Transport transport = session.getTransport("smtp");
	        
	        transport.connect(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.password"));
	        transport.sendMessage(message, message.getAllRecipients());

	        // Close.
	        transport.close();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
}
