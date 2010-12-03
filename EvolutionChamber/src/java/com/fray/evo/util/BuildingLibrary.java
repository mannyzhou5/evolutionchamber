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
    public static HashMap<Integer, Building> idToZergBuilding;

    static private ArrayList<Buildable> createList(Buildable... buildables) {
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }
    static public Building Hatchery = ZergLibrary.Hatchery;
    static public Building Extractor = ZergLibrary.Extractor;
    static public Building SpawningPool = ZergLibrary.SpawningPool;
    static public Building Lair = ZergLibrary.Lair;
    static public Building InfestationPit = ZergLibrary.InfestationPit;
    static public Building Hive = ZergLibrary.Hive;
    static public Building RoachWarren = ZergLibrary.RoachWarren;
    static public Building HydraliskDen = ZergLibrary.HydraliskDen;
    static public Building BanelingNest = ZergLibrary.BanelingNest;
    static public Building Spire = ZergLibrary.Spire;
    static public Building GreaterSpire = ZergLibrary.GreaterSpire;
    static public Building UltraliskCavern = ZergLibrary.UltraliskCavern;
    static public Building EvolutionChamber = ZergLibrary.EvolutionChamber;
    static public Building NydusNetwork = ZergLibrary.NydusNetwork;
    static public Building NydusWorm = ZergLibrary.NydusWorm;
    static public Building SpineCrawler = ZergLibrary.SpineCrawler;
    static public Building SporeCrawler = ZergLibrary.SporeCrawler;
    static public ArrayList<Building> allZergBuildings;

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
