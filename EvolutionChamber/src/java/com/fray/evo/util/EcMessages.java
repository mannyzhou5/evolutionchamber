package com.fray.evo.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Retrieves string messages from a ResourceBundle properties file for i18n.
 * 
 * @author mike.angstadt
 * 
 */
public class EcMessages {
	private final String bundleName;
	private ResourceBundle messages;
	private Locale locale;

	/**
	 * Constructor.
	 * 
	 * @param bundleName the base name of the resource bundle, a fully qualified
	 * class name
	 */
	public EcMessages(String bundleName) {
		this.bundleName = bundleName;
		messages = Utf8ResourceBundle.getBundle(bundleName);
		locale = messages.getLocale();
		if (locale == null){
			locale = Locale.getDefault();
		}
	}

	/**
	 * Gets a message.
	 * 
	 * @param key the message key
	 * @return the message
	 */
	public String getString(String key) {
		return messages.getString(key);
	}

	/**
	 * Gets a message that has arguments.
	 * 
	 * @param key the message key
	 * @param arguments the arguments to populate the message with
	 * @return the formatted message
	 */
	public String getString(String key, Object... arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
	
	/**
	 * Changes the Locale.
	 * @param locale the new Locale
	 */
	public void changeLocale(Locale locale){
		this.locale = locale;
		messages = Utf8ResourceBundle.getBundle(bundleName, locale);
	}
	
	/**
	 * Gets the current Locale.
	 * @return
	 */
	public Locale getLocale(){
		return locale;
	}
}
