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
    private static int unitCount =0;
    private static int buildingCount = 0;
    private static int upgradeCount = 0;
    public final static Unit Larva = new Unit(unitCount++,"Larva", 0, 0, 0, 0, null, createList(),null);
    public final static Unit Drone = new Unit(unitCount++,"Drone", 50, 0, 1, 17, Larva, createList(),null);

    static final public Building Hatchery = new Building("Hatchery", 300, 0, 100,
            Drone, createList(), buildingCount++);
    static final public Building Extractor = new Building("Extractor", 25, 0, 30,
            Drone, createList(), buildingCount++);
    static final public Building SpawningPool = new Building("Spawning Pool", 200, 0, 65,
            Drone, createList(Hatchery), buildingCount++);
    static final public Building Lair = new Building("Lair", 150, 100, 80, Hatchery,
            createList(Hatchery, SpawningPool), buildingCount++);
    static final public Building InfestationPit = new Building("Infestation Pit", 100, 100, 50,
            Drone, createList(Lair), buildingCount++);
    static final public Building Hive = new Building("Hive", 200, 150, 100, Lair,
            createList(Lair, InfestationPit), buildingCount++);
    static final public Building RoachWarren = new Building("Roach Warren", 150, 0, 55,
            Drone, createList(SpawningPool), buildingCount++);
    static final public Building HydraliskDen = new Building("Hydralisk Den", 100, 100, 40,
            Drone, createList(Lair), buildingCount++);
    static final public Building BanelingNest = new Building("Baneling Nest", 100, 50, 60,
            Drone, createList(SpawningPool), buildingCount++);
    static final public Building Spire = new Building("Spire", 200, 200, 100,
            Drone, createList(Lair), buildingCount++);
    static final public Building GreaterSpire = new Building("Greater Spire", 100, 150, 100, Spire,
            createList(Spire, Hive), buildingCount++);
    static final public Building UltraliskCavern = new Building("Ultralisk Cavern", 150, 200, 65,
            Drone, createList(Hive), buildingCount++);
    static final public Building EvolutionChamber = new Building("Evolution Chamber", 75, 0, 35,
            Drone, createList(Hatchery), buildingCount++);
    static final public Building NydusNetwork = new Building("Nydus Network", 150, 200, 50,
            Drone, createList(Lair), buildingCount++);
    static final public Building NydusWorm = new Building("Nydus Worm", 100, 100, 20, null,
            createList(NydusNetwork), buildingCount++);
    static final public Building SpineCrawler = new Building("Spine Crawler", 100, 0, 50,
            Drone, createList(SpawningPool), buildingCount++);
    static final public Building SporeCrawler = new Building("Spore Crawler", 75, 0, 30,
            Drone, createList(EvolutionChamber), buildingCount++);
        final public static Unit Zergling = new Unit(unitCount++,"Zergling", 50, 0, 1, 24.0, Larva, createList(SpawningPool),null);

    final public static Unit Roach = new Unit(unitCount++,"Roach", 75, 25, 2, 27, Larva, createList(RoachWarren),null);
    final public static Unit Queen = new Unit(unitCount++,"Queen", 150, 0, 2, 50, null, createList(SpawningPool),Hatchery);
    final public static Unit Baneling = new Unit(unitCount++,"Baneling", 25, 25, 0.5, 20, Zergling, createList(BanelingNest),null);
    final public static Unit Mutalisk = new Unit(unitCount++,"Mutalisk", 100, 100, 2, 33, Larva, createList(Spire),null);
    final public static Unit Hydralisk = new Unit(unitCount++,"Hydralisk", 100, 50, 2, 33, Larva, createList(HydraliskDen),null);
    final public static Unit Infestor = new Unit(unitCount++,"Infestor", 100, 150, 2, 50, Larva, createList(InfestationPit),null);
    final public static Unit Corruptor = new Unit(unitCount++,"Corruptor", 150, 100, 2, 40, Larva, createList(Spire),null);
    final public static Unit Ultralisk = new Unit(unitCount++,"Ultralisk", 300, 200, 6, 70, Larva, createList(UltraliskCavern),null);
    final public static Unit Broodlord = new Unit(unitCount++,"Brood Lord", 150, 150, 4, 34, Corruptor, createList(GreaterSpire),null);
    final public static Unit Overlord = new Unit(unitCount++,"Overlord", 100, 0, 0, 25, Larva, createList(),null);
    final public static Unit Overseer = new Unit(unitCount++,"Overseer", 50, 100, 0, 17, Overlord, createList(Lair),null);
    
    final public static Upgrade MetabolicBoost = new Upgrade("Metabolic Boost", 100, 100, 110, SpawningPool, upgradeCount++,
            createList());
    final public static Upgrade AdrenalGlands = new Upgrade("Adrenal Glands", 200, 200, 130, SpawningPool, upgradeCount++,
            createList(Hive));
    final public static Upgrade GlialReconstitution = new Upgrade("Glial Reconstitution", 100, 100, 110, RoachWarren, upgradeCount++,
            createList(Lair));
    final public static Upgrade TunnelingClaws = new Upgrade("Tunneling Claws", 150, 150, 110, RoachWarren, upgradeCount++,
            createList(Lair));
    final public static Upgrade Burrow = new Upgrade("Burrow", 100, 100, 100, Hatchery, upgradeCount++,
            createList(Lair));
    final public static Upgrade PneumatizedCarapace = new Upgrade("Pneumatized Carapace", 100, 100, 60, Hatchery, upgradeCount++,
            createList(Lair));
    final public static Upgrade VentralSacs = new Upgrade("Ventral Sacs", 200, 200, 130,Hatchery, upgradeCount++,
            createList(Lair));
    final public static Upgrade CentrifugalHooks = new Upgrade("Centrifugal Hooks", 150, 150, 110,BanelingNest, upgradeCount++,
            createList(Lair));
    final public static Upgrade Melee1 = new Upgrade("Melee +1", 100, 100, 160,EvolutionChamber, upgradeCount++,
            createList(EvolutionChamber));
    final public static Upgrade Melee2 = new Upgrade("Melee +2", 150, 150, 190,EvolutionChamber, upgradeCount++,
            createList(Lair,Melee1));
    final public static Upgrade Melee3 = new Upgrade("Melee +3", 200, 200, 220,EvolutionChamber, upgradeCount++,
            createList(Hive,Melee2));
    final public static Upgrade Missile1 = new Upgrade("Missile +1", 100, 100, 160,EvolutionChamber, upgradeCount++,
            createList(EvolutionChamber));
    final public static Upgrade Missile2 = new Upgrade("Missile +2", 150, 150, 190,EvolutionChamber, upgradeCount++,
            createList(Lair,Missile1));
    final public static Upgrade Missile3 = new Upgrade("Missile +3", 200, 200, 220,EvolutionChamber, upgradeCount++,
            createList(Hive,Missile2));
    final public static Upgrade Armor1 = new Upgrade("Carapace +1",150,150,160,EvolutionChamber, upgradeCount++,
            createList(EvolutionChamber));
    final public static Upgrade Armor2 = new Upgrade("Carapace +2", 225, 225, 190,EvolutionChamber, upgradeCount++,
            createList(Lair,Armor1));
    final public static Upgrade Armor3 = new Upgrade("Carapace +3", 300, 300, 220,EvolutionChamber, upgradeCount++,
            createList(Lair,Armor2));
    final public static Upgrade FlyerAttacks1 = new Upgrade("Flyer Attacks +1", 100, 100, 160,Spire, upgradeCount++,
            createList(Spire));
    final public static Upgrade FlyerAttacks2 = new Upgrade("Flyer Attacks +2", 175, 175, 190,Spire, upgradeCount++,
            createList(Lair,FlyerAttacks1));
    final public static Upgrade FlyerAttacks3 = new Upgrade("Flyer Attacks +3", 250, 250, 220,Spire, upgradeCount++,
            createList(Hive,FlyerAttacks2));
    final public static Upgrade FlyerArmor1 = new Upgrade("Flyer Armor +1", 150, 150, 160,Spire, upgradeCount++,
            createList(Spire));
    final public static Upgrade FlyerArmor2 = new Upgrade("Flyer Armor +2", 225, 225, 190,Spire, upgradeCount++,
            createList(Lair,FlyerArmor1));
    final public static Upgrade FlyerArmor3 = new Upgrade("Flyer Armor +3", 300, 300, 220,Spire, upgradeCount++,
            createList(Hive,FlyerArmor2));
    final public static Upgrade GroovedSpines = new Upgrade("Grooved Spines", 150, 150, 80,HydraliskDen, upgradeCount++,
            createList());
    final public static Upgrade NeuralParasite = new Upgrade("Neural Parasite", 150, 150, 110,InfestationPit, upgradeCount++,
            createList());
    final public static Upgrade PathogenGlands = new Upgrade("Pathogen Glands", 150, 150, 80,InfestationPit, upgradeCount++,
            createList());
    final public static Upgrade ChitinousPlating = new Upgrade("Chitinous Plating", 150, 150, 110,UltraliskCavern, upgradeCount++,
            createList());
}
