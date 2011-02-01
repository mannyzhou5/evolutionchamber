package com.fray.evo.ui.cli;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

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
}
