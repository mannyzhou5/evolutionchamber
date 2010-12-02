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
    public static Unit Larva = new Unit(unitCount++,"Larva", 0, 0, 0, 0, null, createList());
    public static Unit Drone = new Unit(unitCount++,"Drone", 50, 0, 1, 17, Larva, createList());

    static public Building Hatchery = new Building("Hatchery", 300, 0, 100,
            Drone, createList());
    static public Building Extractor = new Building("Extractor", 25, 0, 30,
            Drone, createList());
    static public Building SpawningPool = new Building("SpawningPool", 200, 0, 65,
            Drone, createList(Hatchery));
    static public Building Lair = new Building("Lair", 150, 100, 80, Hatchery,
            createList(Hatchery, SpawningPool));
    static public Building InfestationPit = new Building("InfestationPit", 100, 100, 50,
            Drone, createList(Lair));
    static public Building Hive = new Building("Hive", 200, 150, 100, Lair,
            createList(Lair, InfestationPit));
    static public Building RoachWarren = new Building("RoachWarren", 150, 0, 55,
            Drone, createList(SpawningPool));
    static public Building HydraliskDen = new Building("HydraliskDen", 100, 100, 40,
            Drone, createList(Lair));
    static public Building BanelingNest = new Building("BanelingNest", 100, 50, 60,
            Drone, createList(SpawningPool));
    static public Building Spire = new Building("Spire", 200, 200, 100,
            Drone, createList(Lair));
    static public Building GreaterSpire = new Building("GreaterSpire", 100, 150, 100, Spire,
            createList(Spire, Hive));
    static public Building UltraliskCavern = new Building("UltraliskCavern", 150, 200, 65,
            Drone, createList(Hive));
    static public Building EvolutionChamber = new Building("EvolutionChamber", 75, 0, 35,
            Drone, createList(Hatchery));
    static public Building NydusNetwork = new Building("NydusNetwork", 150, 200, 50,
            Drone, createList(Lair));
    static public Building NydusWorm = new Building("NydusWorm", 100, 100, 20, null,
            createList(NydusNetwork));
    static public Building SpineCrawler = new Building("SpineCrawler", 100, 0, 50,
            Drone, createList(SpawningPool));
    static public Building SporeCrawler = new Building("SporeCrawler", 75, 0, 30,
            Drone, createList(EvolutionChamber));
        public static Unit Zergling = new Unit(unitCount++,"Zergling", 50, 0, 1, 24.0, Larva, createList(SpawningPool));

    public static Unit Roach = new Unit(unitCount++,"Roach", 75, 25, 2, 27, Larva, createList(RoachWarren));
    public static Unit Queen = new Unit(unitCount++,"Queen", 150, 0, 2, 50, Larva, createList(SpawningPool));
    public static Unit Baneling = new Unit(unitCount++,"Baneling", 25, 25, 0.5, 20, Zergling, createList(BanelingNest));
    public static Unit Mutalisk = new Unit(unitCount++,"Mutalisk", 100, 100, 2, 33, Larva, createList(Spire));
    public static Unit Hydralisk = new Unit(unitCount++,"Hydralisk", 100, 50, 2, 33, Larva, createList(HydraliskDen));
    public static Unit Infestor = new Unit(unitCount++,"Infestor", 100, 150, 2, 50, Larva, createList(InfestationPit));
    public static Unit Corruptor = new Unit(unitCount++,"Corruptor", 150, 100, 2, 40, Larva, createList(Spire));
    public static Unit Ultralisk = new Unit(unitCount++,"Ultralisk", 300, 200, 6, 70, Larva, createList(UltraliskCavern));
    public static Unit Broodlord = new Unit(unitCount++,"Broodlord", 150, 150, 4, 34, Corruptor, createList(GreaterSpire));
    public static Unit Overlord = new Unit(unitCount++,"Overlord", 100, 0, 0, 25, Larva, createList());
    public static Unit Overseer = new Unit(unitCount++,"Overseer", 50, 100, 0, 17, Overlord, createList(Lair));
}
