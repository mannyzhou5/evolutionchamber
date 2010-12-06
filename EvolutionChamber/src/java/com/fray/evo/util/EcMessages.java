package com.fray.evo.util;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
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
	private ResourceBundle resourceBundle;
	private Locale locale;
	
	private static EcMessages messages;
	
	public static void init(String bundleName){
		messages = new EcMessages(bundleName);
	}
	
	public static EcMessages getMessages(){
		return messages;
	}
	
	ResourceBundle.Control localeCandidateSelector = new ResourceBundle.Control() {
		@Override
		public List<Locale> getCandidateLocales(String baseName, Locale locale) {
			if (baseName == null)
				throw new NullPointerException();
			
			if (locale.equals(Locale.ROOT)) {
				// look up for english instead the root, since we really hate to maintain the English locale twice
				return Arrays.asList(Locale.ENGLISH, Locale.ROOT);
			}
			List<Locale> defaultCandidates =  super.getCandidateLocales(baseName, locale);
			return defaultCandidates;
		}
	};


	/**
	 * Constructor.
	 * 
	 * @param bundleName the base name of the resource bundle, a fully qualified
	 * class name
	 */
	public EcMessages(String bundleName) {
		this.bundleName = bundleName;
		resourceBundle = Utf8ResourceBundle.getBundle(bundleName, Locale.getDefault(), localeCandidateSelector);
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
		return resourceBundle.getString(key);
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
		resourceBundle = Utf8ResourceBundle.getBundle(bundleName, locale);
	}
	
	/**
	 * Gets the current Locale.
	 * @return
	 */
	public Locale getLocale(){
		return locale;
	}
}
