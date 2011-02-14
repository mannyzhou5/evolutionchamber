package com.fray.evo.ui.cli;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.fray.evo.EcSettings;
import com.fray.evo.EcState;
import com.fray.evo.util.ZergLibrary;

public class InputFileTest {
	/**
	 * No errors are thrown if no waypoints are defined.
	 */
	@Test
	public void testNoWaypoints() throws Exception {
		StringReader sr = new StringReader("");
		InputFile file = new InputFile(sr);
		EcState destination = file.getDestination();
		Assert.assertTrue(destination.waypoints.isEmpty());
		Assert.assertEquals(1, destination.getEstimatedActions());
	}

	/**
	 * Any amount of whitespace is allowed before the value that follows a keyword.
	 */
	@Test
	public void testParsing() throws Exception {
		StringReader sr = new StringReader("scout-timing\t  2:12");
		InputFile file = new InputFile(sr);
		EcState destination = file.getDestination();
		Assert.assertEquals(132, destination.scoutDrone);
	}

	/**
	 * Comments are ignored.
	 */
	@Test
	public void testComments() throws Exception {
		StringReader sr = new StringReader("waypoint 3:00\n#mutalisk 10\nzergling 6");
		InputFile file = new InputFile(sr);
		EcState destination = file.getDestination();
		Assert.assertEquals(0, destination.getMutalisks());
	}

	/**
	 * Test parsing of the scout-timing field.
	 */
	@Test
	public void testScoutTiming() throws Exception {
		StringReader sr = new StringReader("scout-timing 2:12");
		InputFile file = new InputFile(sr);
		EcState destination = file.getDestination();
		Assert.assertEquals(132, destination.scoutDrone);
	}

	/**
	 * If a count isn't specified with a unit, then a default value is used.
	 * @throws Exception
	 */
	@Test
	public void testDefaultUnitCount() throws Exception {
		StringReader sr = new StringReader("waypoint 3:00\nzergling\nspine-crawler\nmelee");
		InputFile file = new InputFile(sr);
		EcState destination = file.getDestination();
		Assert.assertEquals(1, destination.getZerglings());
		Assert.assertEquals(1, destination.getSpineCrawlers());
		Assert.assertTrue(destination.getUpgrades().contains(ZergLibrary.Melee1));
	}

	/**
	 * An overall test of a waypoint collection.
	 */
	@Test
	public void testWaypoints() throws Exception {
		StringReader sr = new StringReader("waypoint 3:00\nzergling 6\nwaypoint 6:54\nmelee 2\nspine-crawler\nwaypoint 2:00:00\nmutalisk 6\nflyer-attacks 1");
		InputFile file = new InputFile(sr);
		EcState destination = file.getDestination();

		Assert.assertEquals(2, destination.waypoints.size());

		Assert.assertEquals(180, destination.waypoints.get(0).targetSeconds);
		Assert.assertEquals(6, destination.waypoints.get(0).getZerglings());

		Assert.assertEquals(414, destination.waypoints.get(1).targetSeconds);
		Assert.assertTrue(destination.waypoints.get(1).getUpgrades().contains(ZergLibrary.Melee2));
		Assert.assertEquals(1, destination.waypoints.get(1).getSpineCrawlers());

		Assert.assertEquals(7200, destination.targetSeconds);
		Assert.assertEquals(6, destination.getMutalisks());
		Assert.assertTrue(destination.getUpgrades().contains(ZergLibrary.FlyerAttacks1));
	}

	/**
	 * A list of all unknown keywords in the file are thrown in an exception.
	 * @throws Exception
	 */
	@Test
	public void testNonExistantKeyword() throws Exception {
		StringReader sr = new StringReader("ignore-me 34\nscout-timing 2:12\nand-me-too 12");
		try {
			new InputFile(sr);
			Assert.fail();
		} catch (UnknownKeywordException e) {
			Assert.assertEquals(2, e.getKeywords().size());
			Assert.assertTrue(e.getKeywords().contains("ignore-me"));
			Assert.assertTrue(e.getKeywords().contains("and-me-too"));
		}
	}
	
	/**
	 * The default settings defined in EcSettings are used if no settings block is defined in the file.
	 * @throws Exception
	 */
	@Test
	public void testSettingsDefaults() throws Exception {
		StringReader sr = new StringReader("waypoint 3:00\nzergling\nspine-crawler\nmelee");
		InputFile file = new InputFile(sr);
		EcSettings settings = file.getDestination().settings;
		Assert.assertFalse(settings.overDrone);
		Assert.assertFalse(settings.workerParity);
		Assert.assertTrue(settings.useExtractorTrick);
		Assert.assertTrue(settings.pullWorkersFromGas);
		Assert.assertFalse(settings.pullThreeWorkersOnly);
		Assert.assertTrue(settings.avoidMiningGas);
		Assert.assertEquals(200, settings.maximumExtractorTrickSupply);
		Assert.assertEquals(2, settings.minimumPoolSupply);
		Assert.assertEquals(2, settings.minimumExtractorSupply);
		Assert.assertEquals(2, settings.minimumHatcherySupply);
	}
	
	/**
	 * Tests what happens when a settings block with no settings is defined.
	 * @throws Exception
	 */
	@Test
	public void testSettingsNone() throws Exception {
		StringReader sr = new StringReader("settings\nwaypoint 3:00\nzergling\nspine-crawler\nmelee");
		InputFile file = new InputFile(sr);
		EcSettings settings = file.getDestination().settings;
		Assert.assertFalse(settings.overDrone);
		Assert.assertFalse(settings.workerParity);
		Assert.assertFalse(settings.useExtractorTrick);
		Assert.assertFalse(settings.pullWorkersFromGas);
		Assert.assertFalse(settings.pullThreeWorkersOnly);
		Assert.assertFalse(settings.avoidMiningGas);
		Assert.assertEquals(200, settings.maximumExtractorTrickSupply);
		Assert.assertEquals(2, settings.minimumPoolSupply);
		Assert.assertEquals(2, settings.minimumExtractorSupply);
		Assert.assertEquals(2, settings.minimumHatcherySupply);
	}
	
	/**
	 * Settings block must only contain settings keywords.
	 * @throws Exception
	 */
	@Test
	public void testWaypointWordInSettings() throws Exception {
		//settings is before waypoint
		StringReader sr = new StringReader("settings\nzergling 5\nwaypoint 3:00\nzergling\nspine-crawler\nmelee");
		try{
			new InputFile(sr);
			Assert.fail();
		} catch (UnknownKeywordException e){
			Assert.assertEquals(1, e.getKeywords().size());
		}
		
		//settings is after waypoint
		sr = new StringReader("waypoint 3:00\nzergling\nspine-crawler\nmelee\nsettings\nzergling 5");
		try{
			new InputFile(sr);
			Assert.fail();
		} catch (UnknownKeywordException e){
			Assert.assertEquals(1, e.getKeywords().size());
		}
	}
	
	/**
	 * Multiple "settings" blocks are treated as one.
	 * @throws Exception
	 */
	@Test
	public void testSettingsMultipleBlocks() throws Exception {
		StringReader sr = new StringReader("settings\nmax-extractor-trick-supply 10\nuse-extractor-trick\nwaypoint 3:00\nzergling\nspine-crawler\nmelee\nsettings\nmax-extractor-trick-supply 20");
		InputFile file = new InputFile(sr);
		EcSettings settings = file.getDestination().settings;
		Assert.assertTrue(settings.useExtractorTrick);
		Assert.assertEquals(20, settings.maximumExtractorTrickSupply);
	}
	
	/**
	 * Make sure it parses the settings correctly.
	 * @throws Exception
	 */
	@Test
	public void testSettings() throws Exception {
		StringReader sr = new StringReader("settings\nenforce-worker-parity until-saturation\nuse-extractor-trick\npush-pull-workers-gas\npush-pull-in-threes\navoid-unnecessary-extractor\nmax-extractor-trick-supply 10\nmin-pool-supply 11\nmin-extractor-supply 12\nmin-hatchery-supply 13\nwaypoint 3:00\nzergling\nspine-crawler\nmelee");
		InputFile file = new InputFile(sr);
		EcSettings settings = file.getDestination().settings;
		Assert.assertFalse(settings.overDrone);
		Assert.assertTrue(settings.workerParity);
		Assert.assertTrue(settings.useExtractorTrick);
		Assert.assertTrue(settings.pullWorkersFromGas);
		Assert.assertTrue(settings.pullThreeWorkersOnly);
		Assert.assertTrue(settings.avoidMiningGas);
		Assert.assertEquals(10, settings.maximumExtractorTrickSupply);
		Assert.assertEquals(11, settings.minimumPoolSupply);
		Assert.assertEquals(12, settings.minimumExtractorSupply);
		Assert.assertEquals(13, settings.minimumHatcherySupply);
	}
}
