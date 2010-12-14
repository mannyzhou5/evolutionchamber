package com.fray.evo;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.logging.LogManager;

import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;

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
		
		int testRuns = 10;
		int processors = 1;
		int runtimeSec = 20;
		
		double results[][] = new double[testRuns][3];
		
		// do all test multiple times to get the average, 
		// since it might only be luck with a lot of invalid builds (which process faster by aborting early)
		for(int i=0; i < testRuns;i++){

			//run the tests
			double test1 = finalWaypoint(processors, runtimeSec);
			double test2 = oneWaypoint(processors, runtimeSec);
			double test3 = multipleWaypoints(processors, runtimeSec);
	
			//print the results
			System.out.println("Test 1: " + test1);
			System.out.println("Test 2: " + test2);
			System.out.println("Test 3: " + test3);
			
			results[i][0] = test1;
			results[i][1] = test2;
			results[i][2] = test3;
		}
		
		
		double overallTime[] = new double[3];
		
		// list all test results
		for(int i=0;i < testRuns; i++){
			int t=0;
			
			System.out.println("Test Run " + (i+1) + ", Test " + t + ":" + results[i][t]);
			overallTime[t]+=results[i][t];
			
			t++;
			System.out.println("Test Run " + (i+1) + ", Test " + t + ":" + results[i][t]);
			overallTime[t]+=results[i][t];
			
			t++;
			System.out.println("Test Run " + (i+1) + ", Test " + t + ":" + results[i][t]);
			overallTime[t]+=results[i][t];
		}
		
		// list the average results per test
		System.out.println("----------------------------------------------");
		System.out.println("Threads used: " + processors);
		System.out.println("Test duration: " + runtimeSec + " seconds.");
		System.out.println("Test Runs made:" + testRuns);
		System.out.println("The average games/second for Test 1 is " + (overallTime[0] / testRuns));
		System.out.println("The average games/second for Test 2 is " + (overallTime[1] / testRuns));
		System.out.println("The average games/second for Test 3 is " + (overallTime[2] / testRuns));
		
	}

	/**
	 * No waypoints, only the final destination contains data.
	 * 
	 * @return the average games played per second
	 * @throws Exception
	 */
	private static double finalWaypoint(int processors, int seconds) throws Exception {

		EcState destination = new EcState();
		destination.targetSeconds = 120 * 60;
		destination.SetUnits(ZergUnitLibrary.Mutalisk, 7);
		destination.AddUpgrade(ZergUpgradeLibrary.FlyerAttacks1);

		return runTest(destination, seconds, processors);
	}

	/**
	 * Contains one waypoint, the final destination is empty.
	 * 
	 * @return the average games played per second
	 * @throws Exception
	 */
	private static double oneWaypoint(int processors, int seconds) throws Exception {

		ArrayList<EcState> waypoints = new ArrayList<EcState>();

		EcState waypoint = new EcState();
		waypoint.targetSeconds = 120 * 60;
		waypoint.SetUnits(ZergUnitLibrary.Mutalisk, 7);
		waypoint.AddUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
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
	private static double multipleWaypoints(int processors, int seconds) throws Exception {

		ArrayList<EcState> waypoints = new ArrayList<EcState>();

		EcState waypoint = new EcState();
		waypoint.SetUnits(ZergUnitLibrary.Zergling, 6);
		waypoint.targetSeconds = 3 * 60;
		waypoints.add(waypoint);

		waypoint = new EcState();
		waypoint.SetUnits(ZergUnitLibrary.Roach, 6);
		waypoint.targetSeconds = 6 * 60;
		waypoints.add(waypoint);

		waypoint = new EcState();
		waypoint.SetUnits(ZergUnitLibrary.Hydralisk, 2);
		waypoint.targetSeconds = 9 * 60;
		waypoints.add(waypoint);

		waypoint = new EcState();
		waypoint.SetUnits(ZergUnitLibrary.Overseer, 3);
		waypoint.targetSeconds = 12 * 60;
		waypoints.add(waypoint);

		EcState destination = new EcState();
		destination.targetSeconds = 120 * 60;
		destination.waypoints = waypoints;
		destination.SetUnits(ZergUnitLibrary.Mutalisk, 7);
		destination.AddUpgrade(ZergUpgradeLibrary.FlyerAttacks1);

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
