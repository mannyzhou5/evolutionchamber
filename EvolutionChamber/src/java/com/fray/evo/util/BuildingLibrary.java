/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Cyrik
 */
public class BuildingLibrary {
    final public static HashMap<Integer, Building> idToZergBuilding;

    static private ArrayList<Buildable> createList(Buildable... buildables) {
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }
    static final public Building Hatchery = ZergLibrary.Hatchery;
    static final public Building Extractor = ZergLibrary.Extractor;
    static final public Building SpawningPool = ZergLibrary.SpawningPool;
    static final public Building Lair = ZergLibrary.Lair;
    static final public Building InfestationPit = ZergLibrary.InfestationPit;
    static final public Building Hive = ZergLibrary.Hive;
    static final public Building RoachWarren = ZergLibrary.RoachWarren;
    static final public Building HydraliskDen = ZergLibrary.HydraliskDen;
    static final public Building BanelingNest = ZergLibrary.BanelingNest;
    static final public Building Spire = ZergLibrary.Spire;
    static final public Building GreaterSpire = ZergLibrary.GreaterSpire;
    static final public Building UltraliskCavern = ZergLibrary.UltraliskCavern;
    static final public Building EvolutionChamber = ZergLibrary.EvolutionChamber;
    static final public Building NydusNetwork = ZergLibrary.NydusNetwork;
    static final public Building NydusWorm = ZergLibrary.NydusWorm;
    static final public Building SpineCrawler = ZergLibrary.SpineCrawler;
    static final public Building SporeCrawler = ZergLibrary.SporeCrawler;
    static final public ArrayList<Building> allZergBuildings;

    static {
            allZergBuildings = new ArrayList<Building>();
            allZergBuildings.add(Hatchery);
            allZergBuildings.add(Extractor);
            allZergBuildings.add(ZergLibrary.Lair);
            allZergBuildings.add(Hive);
            allZergBuildings.add(SpawningPool);
            allZergBuildings.add(RoachWarren);
            allZergBuildings.add(HydraliskDen);
            allZergBuildings.add(BanelingNest);
            allZergBuildings.add(GreaterSpire);
            allZergBuildings.add(UltraliskCavern);
            allZergBuildings.add(Spire);
            allZergBuildings.add(InfestationPit);
            allZergBuildings.add(EvolutionChamber);
            allZergBuildings.add(NydusNetwork);
            allZergBuildings.add(NydusWorm);
            allZergBuildings.add(SpineCrawler);
            allZergBuildings.add(SporeCrawler);
        idToZergBuilding = new HashMap<Integer, Building>();
        for (Building building : allZergBuildings) {
            idToZergBuilding.put(building.getId(), building);
        }
    }
}
