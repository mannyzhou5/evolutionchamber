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
public class BuildingLibrary {
    static private ArrayList<Buildable> createList(Buildable ...buildables){
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }

    static public Building Hatchery = new Building("Hatchery", 300, 0, 100,
            UnitLibrary.Drone, createList());
    static public Building Extractor = new Building("Extractor", 25, 0, 30,
            UnitLibrary.Drone, createList());
    static public Building SpawningPool = new Building("SpawningPool",200,0,65,
            UnitLibrary.Drone, createList(BuildingLibrary.Hatchery));
    static public Building Lair = new Building("Lair",150,100,80, BuildingLibrary.Hatchery,
            createList(BuildingLibrary.Hatchery, BuildingLibrary.SpawningPool));
    static public Building Hive = new Building("Hive",200,150,100, BuildingLibrary.Lair,
            createList(BuildingLibrary.Lair, BuildingLibrary.InfestationPit));
    static public Building RoachWarren = new Building("RoachWarren", 150,0,55,
            UnitLibrary.Drone, createList(BuildingLibrary.SpawningPool));
    static public Building HydraliskDen = new Building("HydraliskDen", 100, 100, 40,
            UnitLibrary.Drone, createList(BuildingLibrary.Lair));
    static public Building BanelingNest = new Building("BanelingNest", 100, 50, 60,
            UnitLibrary.Drone, createList(BuildingLibrary.SpawningPool));
    static public Building Spire = new Building("Spire", 200, 200, 100,
            UnitLibrary.Drone, createList(BuildingLibrary.Lair));
    static public Building GreaterSpire = new Building("GreaterSpire", 100, 150, 100,BuildingLibrary.Spire,
            createList(BuildingLibrary.Spire, BuildingLibrary.Hive));
    static public Building UltraliskCavern = new Building("UltraliskCavern", 150, 200, 65,
            UnitLibrary.Drone, createList(BuildingLibrary.Hive));
    static public Building InfestationPit = new Building("InfestationPit", 100, 100, 50,
            UnitLibrary.Drone, createList(BuildingLibrary.Lair));
    static public Building EvolutionChamber = new Building("EvolutionChamber", 75, 0, 35,
            UnitLibrary.Drone, createList(BuildingLibrary.Hatchery));
    static public Building NydusNetwork = new Building("NydusNetwork", 150, 200, 50,
            UnitLibrary.Drone, createList(BuildingLibrary.Lair));
    static public Building NydusWorm = new Building("NydusWorm", 100, 100, 20,null,
            createList(BuildingLibrary.NydusNetwork));
    static public Building SpineCrawler = new Building("SpineCrawler", 100, 0, 50,
            UnitLibrary.Drone, createList(BuildingLibrary.SpawningPool));
    static public Building SporeCrawler = new Building("SporeCrawler", 75, 0, 30,
            UnitLibrary.Drone, createList(BuildingLibrary.EvolutionChamber));

    static private ArrayList<Building> allZergBuildings;

    synchronized  static public ArrayList<Building> getAllZergBuildings(){
        if(allZergBuildings == null){
            allZergBuildings = new ArrayList<Building>();
            allZergBuildings.add(Hatchery);
            allZergBuildings.add(Extractor);
            allZergBuildings.add(Lair);
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
        }
        return allZergBuildings;
    }
}
