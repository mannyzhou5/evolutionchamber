package com.fray.evo.ui.swingx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import com.fray.evo.EvolutionChamber;

/**
 * Stores and retrieves the user's personal settings.
 * 
 * @author mike.angstadt
 * 
 */
public class UserSettings {
	private static final Logger logger = Logger.getLogger(UserSettings.class.getName());

	/**
	 * The settings.
	 */
	private Properties properties;

	/**
	 * The properties file that the settings are stored in.
	 */
	private File file;

	/**
	 * Loads a user settings file at the specified location.
	 * @param file the location of the settings file
	 */
	public UserSettings(File file) {
		properties = new Properties();
		this.file = file;
		if (file.exists()) {
			try {
				properties.load(new FileInputStream(file));
			} catch (IOException e) {
				logger.severe("Cannot read user settings.");
			}
		}

		if (getVersion() == null) {
			//if the format of the file changes from version to version, then this property can be used to convert the file to the new format
			setPropertyAndSave("version", EvolutionChamber.VERSION);
		}
	}

	/**
	 * Gets the user's preferred Locale.
	 * 
	 * @return
	 */
	public Locale getLocale() {
		String localeStr = properties.getProperty("locale");
		if (localeStr != null) {
			int pos = localeStr.indexOf('_');
			if (pos >= 0) {
				return new Locale(localeStr.substring(0, pos), localeStr.substring(pos + 1));
			} else if (localeStr.length() > 0) {
				return new Locale(localeStr);
			}
		}
		return null;
	}

	/**
	 * Sets the user's preferred Locale.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		setPropertyAndSave("locale", locale.getLanguage() + "_" + locale.getCountry());
	}

	/**
	 * Gets the version of the file.
	 * @return
	 */
	public String getVersion() {
		return properties.getProperty("version");
	}
	
	/**
	 * Sets a property and saves the file.
	 * @param key
	 * @param value
	 */
	private void setPropertyAndSave(String key, String value){
		properties.setProperty(key, value);
		save();
	}

	/**
	 * Writes the settings to disk.
	 */
	public void save() {
		try {
			properties.store(new FileOutputStream(file), "EvolutionChamber user settings file");
		} catch (IOException e) {
			logger.severe("Cannot save user settings.");
		}
	}
}
