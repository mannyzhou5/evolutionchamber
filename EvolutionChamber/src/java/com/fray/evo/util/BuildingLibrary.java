/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

import java.util.ArrayList;

/**
 *
 * @author Cyrik
 */
public class BuildingLibrary {
    static public Building Hatchery = new Building("Hatchery", 300, 0, 100);
    static public Building Extractor = new Building("Extractor", 25, 0, 30);
    static public Building Lair = new Building("Lair",150,100,80);
    static public Building Hive = new Building("Hive",200,150,100);
    static public Building SpawningPool = new Building("SpawningPool",200,0,65);
    static public Building RoachWarren = new Building("RoachWarren", 150,0,55);
    static public Building HydraliskDen = new Building("HydraliskDen", 100, 100, 40);
    static public Building BanelingNest = new Building("BanelingNest", 100, 50, 60);
    static public Building GreaterSpire = new Building("GreaterSpire", 300, 350, 200);
    static public Building UltraliskCavern = new Building("UltraliskCavern", 150, 200, 65);
    static public Building Spire = new Building("Spire", 200, 200, 100);
    static public Building InfestationPit = new Building("InfestationPit", 100, 100, 50);
    static public Building EvolutionChamber = new Building("EvolutionChamber", 75, 0, 35);
    static public Building NydusNetwork = new Building("NydusNetwork", 150, 200, 50);
    static public Building NydusWorm = new Building("NydusWorm", 100, 100, 20);
    static public Building SpineCrawler = new Building("SpineCrawler", 100, 0, 50);
    static public Building SporeCrawler = new Building("SporeCrawler", 75, 0, 30);

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
