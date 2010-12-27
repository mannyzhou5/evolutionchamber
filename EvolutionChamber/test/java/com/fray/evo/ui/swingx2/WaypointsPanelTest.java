package com.fray.evo.ui.swingx2;

import junit.framework.Assert;

import org.junit.Test;

import com.fray.evo.EcState;
import com.fray.evo.util.ZergLibrary;

public class WaypointsPanelTest {
	@Test
	public void testAddWaypoint(){
		WaypointsPanel panel = new WaypointsPanel();
		WaypointPanel waypoint = panel.addWaypoint("name", 120);
		Assert.assertEquals("name", waypoint.getWaypointName());
		Assert.assertEquals(120, waypoint.getDeadline());
	}
	
	@Test
	public void testBuildDestination(){
		WaypointsPanel panel = new WaypointsPanel();
		
		//add the waypoints out of order to make sure they are sorted
		
		WaypointPanel two = panel.addWaypoint("2", 120);
		two.addTarget(ZergLibrary.MetabolicBoost);
		
		WaypointPanel one = panel.addWaypoint("1", 60);
		one.addTarget(ZergLibrary.Zergling, 6);
		
		WaypointPanel four = panel.addWaypoint("4", 240);
		four.addTarget(ZergLibrary.Roach, 3);
		
		WaypointPanel three = panel.addWaypoint("3", 180);
		three.addTarget(ZergLibrary.SpineCrawler, 1);
		
		EcState destination = panel.buildDestination();
		Assert.assertEquals(240, destination.targetSeconds);
		Assert.assertEquals(3, destination.getUnitCount(ZergLibrary.Roach));
		Assert.assertEquals(3, destination.waypoints.size());
		
		EcState waypoint = destination.waypoints.get(0);
		Assert.assertEquals(60, waypoint.targetSeconds);
		Assert.assertEquals(6, waypoint.getUnitCount(ZergLibrary.Zergling));
		
		waypoint = destination.waypoints.get(1);
		Assert.assertEquals(120, waypoint.targetSeconds);
		Assert.assertTrue(waypoint.getUpgrades().contains(ZergLibrary.MetabolicBoost));
		
		waypoint = destination.waypoints.get(2);
		Assert.assertEquals(180, waypoint.targetSeconds);
		Assert.assertEquals(1, waypoint.getBuildingCount(ZergLibrary.SpineCrawler));
	}
}
