package com.fray.evo.ui.swingx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.fray.evo.EvolutionChamber;

/**
 * Tests the UserSettings class
 * 
 * @author mike.angstadt
 * 
 */
public class UserSettingsTest {
	private File file;

	@After
	public void after() {
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * Tests what happens when the settings file doesn't exist.
	 * @throws Exception
	 */
	@Test
	public void testNonExistantFile() throws Exception{
		file = new File("test.properties");
		UserSettings settings = new UserSettings(file);
		Assert.assertNull(settings.getLocale()); //there was no file, so there was no locale information
		Assert.assertEquals(EvolutionChamber.VERSION, settings.getVersion());
		Assert.assertTrue(file.exists()); //it should create the file
		
		//check the contents of the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
	}

	/**
	 * Tests what happens when the settings file is empty.
	 * @throws Exception
	 */
	@Test
	public void testEmptyFile() throws Exception {
		file = new File("test.properties");
		file.createNewFile();
		UserSettings settings = new UserSettings(file);
		Assert.assertNull(settings.getLocale()); //the file was empty, so there was no locale information
		Assert.assertEquals(EvolutionChamber.VERSION, settings.getVersion());
		
		//check the contents of the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
	}

	/**
	 * Tests what happens when the settings file already exists.
	 * @throws Exception
	 */
	@Test
	public void testExistingFile() throws Exception {
		file = new File("test.properties");
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println("locale=en_US");
		out.close();

		UserSettings settings = new UserSettings(file);
		Assert.assertEquals(new Locale("en", "US"), settings.getLocale());
		Assert.assertEquals(EvolutionChamber.VERSION, settings.getVersion()); //it should create the "version" property if it doesn't exist
		
		//check the contents of the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals("en_US", properties.getProperty("locale"));
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
	}

	/**
	 * Tests what happens when the Locale is set.
	 * @throws Exception
	 */
	@Test
	public void testSetLocale() throws Exception {
		file = new File("test.properties");
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println("locale=en_US");
		out.close();

		UserSettings settings = new UserSettings(file);
		Locale newLocale = new Locale("ko", "KR");
		settings.setLocale(newLocale);
		Assert.assertEquals(newLocale, settings.getLocale());
		
		//check the contents of the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals("ko_KR", properties.getProperty("locale")); //it should save the file every time a property is changed
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
	}
}
