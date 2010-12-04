/**
 * 
 */
package com.fray.evo.util;


/**
 * @author "Beat Durrer"
 *
 * Contains all libraries
 */
public class RaceLibraries {
	
	public static final BuildingLibrary zergBuildingLibrary = BuildingLibrary.getInstance();
	public static final UpgradeLibrary zergUpgradeLibrary = UpgradeLibrary.getInstance();
	public static final UnitLibrary zergUnitLibrary = UnitLibrary.getInstance();

	public static Library<Building> getBuildingLibrary(Race race){
		switch(race){
			case Zerg:
				return zergBuildingLibrary;
			case Terran:
			    throw new RuntimeException("Terran library not yet implemented");
			case Protoss:
			default:
				throw new RuntimeException("Protoss library not yet implemented");
		}
	}
	
	public static Library<Unit> getUnitLibrary(Race race){
		switch(race){
			case Zerg:
				return zergUnitLibrary;
			case Terran:
			    throw new RuntimeException("Terran library not yet implemented");
			case Protoss:
			default:
				throw new RuntimeException("Protoss library not yet implemented");
		}
	}
	
	public static Library<Upgrade> getUpgradeLibrary(Race race){
		switch(race){
			case Zerg:
				return zergUpgradeLibrary;
			case Terran:
			    throw new RuntimeException("Terran library not yet implemented");
			case Protoss:
			default:
				throw new RuntimeException("Protoss library not yet implemented");
		}
	}
}
