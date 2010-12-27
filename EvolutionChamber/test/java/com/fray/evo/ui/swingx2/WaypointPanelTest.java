package com.fray.evo.ui.swingx2;

import org.junit.Assert;
import org.junit.Test;

import com.fray.evo.EcState;
import com.fray.evo.util.ZergLibrary;

public class WaypointPanelTest {
	@Test
	public void testBuildEcState(){
		WaypointPanel panel = new WaypointPanel("name", 70);
		panel.addTarget(ZergLibrary.Drone, 1);
		panel.addTarget(ZergLibrary.Hydralisk, 2);
		panel.addTarget(ZergLibrary.SpineCrawler, 3);
		panel.addTarget(ZergLibrary.MetabolicBoost);
		
		EcState state = panel.buildEcState();
		Assert.assertEquals(70, state.targetSeconds);
		Assert.assertEquals(1, state.getUnitCount(ZergLibrary.Drone));
		Assert.assertEquals(2, state.getUnitCount(ZergLibrary.Hydralisk));
		Assert.assertEquals(0, state.getUnitCount(ZergLibrary.Queen));
		Assert.assertEquals(3, state.getBuildingCount(ZergLibrary.SpineCrawler));
		Assert.assertTrue(state.getUpgrades().contains(ZergLibrary.MetabolicBoost));
		Assert.assertFalse(state.getUpgrades().contains(ZergLibrary.PathogenGlands));
	}
}
