package com.fray.evo.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the EcAutoUpdate class.
 * 
 * @author mike.angstadt
 * 
 */
public class EcAutoUpdateTest {
	private File file;

	@After
	public void after() {
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * Tests what happens when there is a newer version available.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdate() throws Exception {
		//version "0013" is an old version so there should be a newer one available
		final String oldVersion = "0013";
		EcAutoUpdate auto = new EcAutoUpdate(oldVersion, new CallbackImpl());
		auto.addPropertyChangeListener(new DisplayDownloadProgress());
		Thread t = auto.findLatestVersion(new EcAutoUpdate.FindLatestVersionCallback(){
			@Override
			public void noUpdateAvailable() {
				Assert.fail();
			}

			@Override
			public void updateAvailable(String version) {
				Assert.assertTrue(Integer.parseInt(oldVersion) < Integer.parseInt(version));
			}
			
		});
		t.join();
		
		String latestVersion = auto.getLatestVersion();
		file = new File("evolutionchamber-version-" + latestVersion + ".jar");

		//download the file
		Assert.assertFalse(auto.isUpdating());
		auto.doInBackground();
		Assert.assertTrue(auto.isUpdating());

		//file should have been downloaded to the current working directory
		Assert.assertTrue(file.exists());
		long lastModified = file.lastModified();

		//run the update again
		//since the file was already downloaded, it should not re-download it
		auto = new EcAutoUpdate(oldVersion, new CallbackImpl());
		auto.doInBackground();
		Assert.assertEquals(lastModified, file.lastModified()); //the file should not have changed

		//run the update again
		//if a file exists with the same name of the JAR file, but is *not* the JAR file, then it should notice this and re-download the JAR file
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.print("This is just some text file that happens to have the same name as the Evolution Chamber JAR.");
		out.close();
		lastModified = file.lastModified();
		auto = new EcAutoUpdate(oldVersion, new CallbackImpl());
		auto.addPropertyChangeListener(new DisplayDownloadProgress());
		auto.doInBackground();
		Assert.assertTrue(lastModified < file.lastModified());
	}

	/**
	 * Tests what happens when the downloaded file does not match the expected
	 * checksum.
	 */
	@Test
	public void testBrokenDownload() throws Exception {
		//create an implementation of the auto-update class which gets the download data from a byte array instead of from the Internet.
		EcAutoUpdate auto = new EcAutoUpdate("0013", new CallbackImpl()) {
			@Override
			protected FileInfo getFileInputStreamAndLength(String version) throws IOException {
				String data = "Some data";
				return new FileInfo(new ByteArrayInputStream(data.getBytes()), data.length());
			}
		};
		String latestVersion = auto.getLatestVersion();
		file = new File("evolutionchamber-version-" + latestVersion + ".jar");

		//"download" the file
		auto.doInBackground();

		//the checksums will not match
		Assert.assertFalse(auto.isChecksumMatches());
	}

	/**
	 * Tests the getExpectedChecksum() method.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetExpectedChecksum() throws Exception {
		Assert.assertEquals("fc1b7cc2aa749362f217392996fdb23070a3aed6", EcAutoUpdate.getExpectedChecksum("0015").toLowerCase());

		try {
			EcAutoUpdate.getExpectedChecksum("0011");
			Assert.fail();
		} catch (IOException e) {
			//this exception should be thrown because there is no version 0011
		}
	}

	/**
	 * Test what happens when no update needs to be performed (the user already
	 * has the most up to date version).
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAlreadyLatest() throws Exception {
		//get the latest version
		EcAutoUpdate auto = new EcAutoUpdate("0000", new CallbackImpl());
		String latestVersion = auto.getLatestVersion();

		//try to update from the latest version
		//there should be no update available
		auto = new EcAutoUpdate(latestVersion, new CallbackImpl());
		Assert.assertFalse(auto.isUpdateAvailable());
	}

	/**
	 * Displays the download progress of an update to stdout.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private class DisplayDownloadProgress implements PropertyChangeListener {
		private boolean first = true;

		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress".equals(evt.getPropertyName())) {
				if (first) {
					first = false;
					System.out.print("Downloading ... ");
				}
				int value = (Integer) evt.getNewValue();
				if (value % 10 == 0) {
					System.out.print(value + "%");
					if (value == 100) {
						System.out.println();
					} else {
						System.out.print(" ... ");
					}
				}
			}
		}
	}

	/**
	 * Callback implementation for the auto-update class.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private class CallbackImpl implements EcAutoUpdate.DownloadCallback {
		@Override
		public void checksumFailed() {
			Assert.fail("File's checksum does not match its expected checksum.");
		}

		@Override
		public void updateCheckFailed() {
			Assert.fail("Could not find the latest version.  Check your internet connection.");
		}
	}
}
