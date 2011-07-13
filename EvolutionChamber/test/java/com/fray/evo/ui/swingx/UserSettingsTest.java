package com.fray.evo.ui.swingx;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.LogManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void beforeClass() {
		//turn off logging
		try {
			LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(".level=OFF".getBytes()));
		} catch (Exception e) {
		}
	}

	@After
	public void after() {
		if (file != null) {
			if(file.delete()) {
				System.out.println("Failed to delete properties after a test!");
			}
		}
	}

	/**
	 * Tests what happens when the settings file doesn't exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNonExistantFile() throws Exception {
		file = new File("DOES_NOT_EXIST.properties");
		Assert.assertFalse(file.exists());
		UserSettings settings = new UserSettings(file);
		Assert.assertNull(settings.getLocale());
		Assert.assertNull(settings.getWindowSize());
		Assert.assertNull(settings.getWindowExtensionState());
		Assert.assertEquals(EvolutionChamber.VERSION, settings.getVersion());
		Assert.assertTrue(file.exists()); //it should create the file

		//check the contents of the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
		Assert.assertNull(properties.getProperty("locale"));
		Assert.assertNull(properties.getProperty("gui.windowExtendedState"));
		Assert.assertNull(properties.getProperty("gui.windowHeight"));
		Assert.assertNull(properties.getProperty("gui.windowWidth"));
	}

	/**
	 * Tests what happens when the settings file is empty.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEmptyFile() throws Exception {
		file = new File("EMPTY.properties");
		Assert.assertFalse(file.exists());
		file.createNewFile();
		UserSettings settings = new UserSettings(file);
		Assert.assertNull(settings.getLocale());
		Assert.assertNull(settings.getWindowSize());
		Assert.assertNull(settings.getWindowExtensionState());
		Assert.assertEquals(EvolutionChamber.VERSION, settings.getVersion());

		//check the contents of the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
		Assert.assertNull(properties.getProperty("locale"));
		Assert.assertNull(properties.getProperty("gui.windowExtendedState"));
		Assert.assertNull(properties.getProperty("gui.windowHeight"));
		Assert.assertNull(properties.getProperty("gui.windowWidth"));
	}

	/**
	 * Tests what happens when the settings file already exists.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExistingFile() throws Exception {
		file = new File("test.properties");
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println("locale=en_US\ngui.windowExtendedState=1\ngui.windowHeight=100\ngui.windowWidth=500");
		out.close();

		UserSettings settings = new UserSettings(file);
		Assert.assertEquals(new Locale("en", "US"), settings.getLocale());
		Assert.assertEquals(new Integer(1), settings.getWindowExtensionState());
		Assert.assertEquals(new Dimension(500, 100), settings.getWindowSize());
		Assert.assertEquals(EvolutionChamber.VERSION, settings.getVersion()); //it should create the "version" property if it doesn't exist
	}

	/**
	 * Tests what happens when the Locale is set.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetLocale() throws Exception {
		file = new File("test.properties");
		file.createNewFile();

		//check a Locale that has a language and country
		UserSettings settings = new UserSettings(file);
		Locale newLocale = new Locale("ko", "KR");
		settings.setLocale(newLocale);
		Assert.assertEquals(newLocale, settings.getLocale());

		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals("ko_KR", properties.getProperty("locale")); //it should save the file every time a property is changed
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));

		//check a Locale that just has a language
		newLocale = new Locale("en");
		settings.setLocale(newLocale);
		Assert.assertEquals(newLocale, settings.getLocale());

		properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals("en", properties.getProperty("locale")); //it should save the file every time a property is changed
	}

	/**
	 * Tests what happens when the window size is set.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetWindowSize() throws Exception {
		file = new File("test.properties");
		file.createNewFile();

		UserSettings settings = new UserSettings(file);
		Dimension dim = new Dimension(500, 100);
		settings.setWindowSize(dim);
		Assert.assertEquals(dim, settings.getWindowSize());

		//setting the property should automatically save the file
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		Assert.assertEquals("100", properties.getProperty("gui.windowHeight"));
		Assert.assertEquals("500", properties.getProperty("gui.windowWidth"));
		Assert.assertEquals(EvolutionChamber.VERSION, properties.getProperty("version"));
	}
}
