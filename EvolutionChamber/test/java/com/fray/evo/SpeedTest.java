package com.fray.evo;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.logging.LogManager;

import com.fray.evo.util.UnitLibrary;
import com.fray.evo.util.UpgradeLibrary;

/**
 * Runs a number of simulations and calculates the games played per second for
 * each one.
 * 
 * @author mike.angstadt
 * 
 */
public class SpeedTest {
	public static void main(String args[]) throws Exception {
		//turn off logging
		try {
			LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(".level=OFF".getBytes()));
		} catch (Exception e) {
		}

		//run the tests
		double test1 = finalWaypoint();
		double test2 = oneWaypoint();
		double test3 = multipleWaypoints();

		//print the results
		System.out.println("Test 1: " + test1);
		System.out.println("Test 2: " + test2);
		System.out.println("Test 3: " + test3);
	}

	/**
	 * No waypoints, only the final destination contains data.
	 * 
	 * @return the average games played per second
	 * @throws Exception
	 */
	private static double finalWaypoint() throws Exception {
		final int seconds = 20;
		final int processors = 1;

		EcState destination = new EcState();
		destination.targetSeconds = 120 * 60;
		destination.SetUnits(UnitLibrary.Mutalisk, 7);
		destination.AddUpgrade(UpgradeLibrary.FlyerAttacks1);

		return runTest(destination, seconds, processors);
	}

	/**
	 * Contains one waypoint, the final destination is empty.
	 * 
	 * @return the average games played per second
	 * @throws Exception
	 */
	private static double oneWaypoint() throws Exception {
		final int seconds = 20;
		final int processors = 1;

		ArrayList<EcState> waypoints = new ArrayList<EcState>();

		EcState waypoint = new EcState();
		waypoint.targetSeconds = 120 * 60;
		waypoint.SetUnits(UnitLibrary.Mutalisk, 7);
		waypoint.AddUpgrade(UpgradeLibrary.FlyerAttacks1);
		waypoints.add(waypoint);

		EcState destination = new EcState();
		destination.waypoints = waypoints;
		destination.targetSeconds = 120 * 60;

		return runTest(destination, seconds, processors);
	}

	/**
	 * Contains multiple waypoints and a final destination.
	 * 
	 * @return the average games played per second
	 * @throws Exception
	 */
	private static double multipleWaypoints() throws Exception {
		final int seconds = 20;
		final int processors = 1;

		ArrayList<EcState> waypoints = new ArrayList<EcState>();

		EcState waypoint = new EcState();
		waypoint.SetUnits(UnitLibrary.Zergling, 6);
		waypoint.targetSeconds = 3 * 60;
		waypoints.add(waypoint);

		waypoint = new EcState();
		waypoint.SetUnits(UnitLibrary.Roach, 6);
		waypoint.targetSeconds = 6 * 60;
		waypoints.add(waypoint);

		waypoint = new EcState();
		waypoint.SetUnits(UnitLibrary.Hydralisk, 2);
		waypoint.targetSeconds = 9 * 60;
		waypoints.add(waypoint);

		waypoint = new EcState();
		waypoint.SetUnits(UnitLibrary.Overseer, 3);
		waypoint.targetSeconds = 12 * 60;
		waypoints.add(waypoint);

		EcState destination = new EcState();
		destination.targetSeconds = 120 * 60;
		destination.waypoints = waypoints;
		destination.SetUnits(UnitLibrary.Mutalisk, 7);
		destination.AddUpgrade(UpgradeLibrary.FlyerAttacks1);

		return runTest(destination, seconds, processors);
	}

	/**
	 * Runs a simulation.
	 * 
	 * @param destination the final destination (contains the waypoints)
	 * @param seconds the number of seconds to run the simulation for
	 * @param processors the number of processors to use
	 * @return the average games played per second
	 * @throws Exception
	 */
	private static double runTest(final EcState destination, int seconds, int processors) throws Exception {
		EvolutionChamber ec = new EvolutionChamber();
		ec.setDestination(destination);
		ec.setThreads(processors);

		//start EvolutionChamber
		ec.go();

		//wait for X seconds
		Thread.sleep(seconds * 1000);

		//stop EvolutionChamber
		ec.stopAllThreads();

		return (double) ec.getGamesPlayed() / seconds;
	}
}
