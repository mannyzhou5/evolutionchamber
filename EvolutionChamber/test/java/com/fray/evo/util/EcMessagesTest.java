package com.fray.evo.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.fray.evo.util.EcMessages;

/**
 * Tests the EcMessages class. Also tests to see if all i18n strings referenced
 * in the source code actually exist in a properties file. This prevents run
 * time exceptions from being thrown if a property doesn't exist.
 * 
 * @author mike.angstadt
 * 
 */
public class EcMessagesTest {
	/**
	 * Tests the EcMessages class.
	 */
	@Test
	public void test() {
		EcMessages messages = new EcMessages("com/fray/evo/util/test-messages");
		Assert.assertEquals("My life for Aiur!", messages.getString("no.args"));
		Assert.assertEquals("The Zergling killed the SCV.", messages.getString("two.args", "Zergling", "SCV"));
	}

	/**
	 * Checks the source code to make sure all properties requested during run
	 * time match up to real properties in the properties file.
	 */
	@Test
	public void testAllPropertiesInCode() {
		File dir = new File("src/java");
		EcMessages messages = new EcMessages("com/fray/evo/ui/swingx/messages");
		List<File> exclude = new ArrayList<File>();
		go(dir, messages, new JavaAndDirsFilter(exclude));
	}

	/**
	 * The regex that is used to find calls to the EcMessages.getString() method
	 * in the source code.
	 */
	private final Pattern pattern = Pattern.compile("\\.getString\\(\"(.*?)\"");

	/**
	 * Recursive method that checks to see if all referenced i18n properties
	 * exist in the .properties file.
	 * 
	 * @param dir the current directory
	 * @param messages the object that is used to access the i18n messages
	 * @param filter filters a directory listing so that only the files that
	 * need to be checked are returned
	 */
	private void go(File dir, EcMessages messages, JavaAndDirsFilter filter) {
		File files[] = dir.listFiles(filter);
		for (File f : files) {
			if (f.isDirectory()) {
				go(f, messages, filter);
			} else {
				try {
					//read the file contents into a String
					FileInputStream in = new FileInputStream(f);
					byte bytes[] = new byte[(int) f.length()];
					in.read(bytes);
					in.close();
					String code = new String(bytes);

					//check each property in the code to see if it exists in the properties file
					Matcher m = pattern.matcher(code);
					int found = 0;
					while (m.find()) {
						found++;
						String key = m.group(1);
						try {
							messages.getString(key);
						} catch (MissingResourceException e) {
							Assert.fail("Could not find property \"" + key + "\" in " + f.getAbsolutePath());
						}
					}
					if (found > 0){
						System.out.println(f.getAbsolutePath() + ": " + found + " messages.");
					}
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Filter used to choose while files should be checked to see if they
	 * reference existing i18n properties.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private class JavaAndDirsFilter implements FileFilter {
		private List<File> exclusions;

		public JavaAndDirsFilter(List<File> excludeDirs) {
			this.exclusions = excludeDirs;
		}

		@Override
		public boolean accept(File file) {
			//ignore the file or directory if it is in the exclusion list
			if (exclusions.contains(file)) {
				return false;
			}

			//ignore SVN directories, but accept all other directories
			if (file.isDirectory()) {
				return !file.getName().equals(".svn");
			}

			//only look at .java files
			int dot = file.getName().lastIndexOf('.');
			if (dot > 0) {
				return file.getName().substring(dot + 1).equals("java");
			}
			return false;
		}
	};
}
