/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Cyrik
 */
public class ZergLibrary {
	static private ArrayList<Buildable> createList(Buildable... buildables) {
		return new ArrayList<Buildable>(Arrays.asList(buildables));
	}

	//TODO: Final a better way than setting the ids statically
	//		cannot use a static counter since the array will now start from 0
	//		if we switch races at any point.

	public final static Unit Larva					= new Unit(0, "Larva", 0, 0, 0, 0, null, createList(), null);
	public final static Unit Drone					= new Unit(1, "Drone", 50, 0, 1, 17, Larva, createList(), null);

	public static final Building Hatchery			= new Building(0, "Hatchery", 300, 0, 100, Drone, createList());
	public static final Building Extractor			= new Building(1, "Extractor", 25, 0, 30, Drone, createList());
	public static final Building SpawningPool		= new Building(2, "Spawning Pool", 200, 0, 65, Drone, createList(Hatchery));
	public static final Building Lair				= new Building(3, "Lair", 150, 100, 80, Hatchery, createList(Hatchery, SpawningPool));
	public static final Building InfestationPit		= new Building(4, "Infestation Pit", 100, 100, 50, Drone, createList(Lair));
	public static final Building Hive				= new Building(5, "Hive", 200, 150, 100, Lair, createList(Lair, InfestationPit));
	public static final Building RoachWarren		= new Building(6, "Roach Warren", 150, 0, 55, Drone, createList(SpawningPool));
	public static final Building HydraliskDen		= new Building(7, "Hydralisk Den", 100, 100, 40, Drone, createList(Lair));
	public static final Building BanelingNest		= new Building(8, "Baneling Nest", 100, 50, 60, Drone, createList(SpawningPool));
	public static final Building Spire				= new Building(9, "Spire", 200, 200, 100, Drone, createList(Lair));
	public static final Building GreaterSpire		= new Building(10, "Greater Spire", 100, 150, 100, Spire, createList(Spire, Hive));
	public static final Building UltraliskCavern	= new Building(11, "Ultralisk Cavern", 150, 200, 65, Drone, createList(Hive));
	public static final Building EvolutionChamber	= new Building(12, "Evolution Chamber", 75, 0, 35, Drone, createList(Hatchery));
	public static final Building NydusNetwork		= new Building(13, "Nydus Network", 150, 200, 50, Drone, createList(Lair));
	public static final Building NydusWorm			= new Building(14, "Nydus Worm", 100, 100, 20, null, createList(NydusNetwork));
	public static final Building SpineCrawler		= new Building(15, "Spine Crawler", 100, 0, 50, Drone, createList(SpawningPool));
	public static final Building SporeCrawler		= new Building(16, "Spore Crawler", 75, 0, 30, Drone, createList(EvolutionChamber));

	public static final Unit Zergling				= new Unit(2,"Zergling", 50, 0, 1, 24.0, Larva, createList(SpawningPool),null);
	public static final Unit Roach					= new Unit(3,"Roach", 75, 25, 2, 27, Larva, createList(RoachWarren),null);
	public static final Unit Queen					= new Unit(4,"Queen", 150, 0, 2, 50, null, createList(SpawningPool),Hatchery);
	public static final Unit Baneling				= new Unit(5,"Baneling", 25, 25, 0.5, 20, Zergling, createList(BanelingNest),null);
	public static final Unit Mutalisk				= new Unit(6,"Mutalisk", 100, 100, 2, 33, Larva, createList(Spire),null);
	public static final Unit Hydralisk				= new Unit(7,"Hydralisk", 100, 50, 2, 33, Larva, createList(HydraliskDen),null);
	public static final Unit Infestor				= new Unit(8,"Infestor", 100, 150, 2, 50, Larva, createList(InfestationPit),null);
	public static final Unit Corruptor				= new Unit(9,"Corruptor", 150, 100, 2, 40, Larva, createList(Spire),null);
	public static final Unit Ultralisk				= new Unit(10,"Ultralisk", 300, 200, 6, 70, Larva, createList(UltraliskCavern),null);
	public static final Unit Broodlord				= new Unit(11,"Brood Lord", 150, 150, 4, 34, Corruptor, createList(GreaterSpire),null);
	public static final Unit Overlord				= new Unit(12,"Overlord", 100, 0, 0, 25, Larva, createList(),null);
	public static final Unit Overseer				= new Unit(13,"Overseer", 50, 100, 0, 17, Overlord, createList(Lair),null);
	
	public static final Upgrade MetabolicBoost		= new Upgrade(0, "Metabolic Boost", 100, 100, 110, SpawningPool, createList());
	public static final Upgrade AdrenalGlands		= new Upgrade(1, "Adrenal Glands", 200, 200, 130, SpawningPool, createList(Hive));
	public static final Upgrade GlialReconstitution	= new Upgrade(2, "Glial Reconstitution", 100, 100, 110, RoachWarren, createList(Lair));
	public static final Upgrade TunnelingClaws		= new Upgrade(3, "Tunneling Claws", 150, 150, 110, RoachWarren, createList(Lair));
	public static final Upgrade Burrow				= new Upgrade(4, "Burrow", 100, 100, 100, Hatchery, createList(Lair));
	public static final Upgrade PneumatizedCarapace	= new Upgrade(5, "Pneumatized Carapace", 100, 100, 60, Hatchery, createList(Lair));
	public static final Upgrade VentralSacs			= new Upgrade(6, "Ventral Sacs", 200, 200, 130,Hatchery, createList(Lair));
	public static final Upgrade CentrifugalHooks	= new Upgrade(7, "Centrifugal Hooks", 150, 150, 110, BanelingNest, createList(Lair));
	public static final Upgrade Melee1				= new Upgrade(8, "Melee +1", 100, 100, 160,EvolutionChamber, createList(EvolutionChamber));
	public static final Upgrade Melee2				= new Upgrade(9, "Melee +2", 150, 150, 190,EvolutionChamber, createList(Lair,Melee1));
	public static final Upgrade Melee3				= new Upgrade(10, "Melee +3", 200, 200, 220,EvolutionChamber, createList(Hive,Melee2));
	public static final Upgrade Missile1			= new Upgrade(11, "Missile +1", 100, 100, 160,EvolutionChamber, createList(EvolutionChamber));
	public static final Upgrade Missile2			= new Upgrade(12, "Missile +2", 150, 150, 190,EvolutionChamber, createList(Lair,Missile1));
	public static final Upgrade Missile3			= new Upgrade(13, "Missile +3", 200, 200, 220,EvolutionChamber, createList(Hive,Missile2));
	public static final Upgrade Armor1				= new Upgrade(14, "Carapace +1",150,150,160,EvolutionChamber, createList(EvolutionChamber));
	public static final Upgrade Armor2				= new Upgrade(15, "Carapace +2", 225, 225, 190,EvolutionChamber, createList(Lair,Armor1));
	public static final Upgrade Armor3				= new Upgrade(16, "Carapace +3", 300, 300, 220,EvolutionChamber, createList(Lair,Armor2));
	public static final Upgrade FlyerAttacks1		= new Upgrade(17, "Flyer Attacks +1", 100, 100, 160,Spire, createList(Spire));
	public static final Upgrade FlyerAttacks2		= new Upgrade(18, "Flyer Attacks +2", 175, 175, 190,Spire, createList(Lair,FlyerAttacks1));
	public static final Upgrade FlyerAttacks3		= new Upgrade(19, "Flyer Attacks +3", 250, 250, 220,Spire, createList(Hive,FlyerAttacks2));
	public static final Upgrade FlyerArmor1			= new Upgrade(20, "Flyer Armor +1", 150, 150, 160,Spire, createList(Spire));
	public static final Upgrade FlyerArmor2			= new Upgrade(21, "Flyer Armor +2", 225, 225, 190,Spire, createList(Lair,FlyerArmor1));
	public static final Upgrade FlyerArmor3			= new Upgrade(22, "Flyer Armor +3", 300, 300, 220,Spire, createList(Hive,FlyerArmor2));
	public static final Upgrade GroovedSpines		= new Upgrade(23, "Grooved Spines", 150, 150, 80,HydraliskDen, createList());
	public static final Upgrade NeuralParasite		= new Upgrade(24, "Neural Parasite", 150, 150, 110,InfestationPit, createList());
	public static final Upgrade PathogenGlands		= new Upgrade(25, "Pathogen Glands", 150, 150, 80,InfestationPit, createList());
	public static final Upgrade ChitinousPlating	= new Upgrade(26, "Chitinous Plating", 150, 150, 110,UltraliskCavern, createList());
}
