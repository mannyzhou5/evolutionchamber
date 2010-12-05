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
}
