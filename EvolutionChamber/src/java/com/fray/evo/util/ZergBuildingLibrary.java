/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.util;


/**
 * The singleton instance contains all Zerg Buildings
 * @author Cyrik
 * 
 */
public class ZergBuildingLibrary extends Library<Building> {

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

    private ZergBuildingLibrary() {
        libaryList.add(Hatchery);
        libaryList.add(Extractor);
        libaryList.add(Hive);
        libaryList.add(ZergLibrary.Lair);
        libaryList.add(SpawningPool);
        libaryList.add(RoachWarren);
        libaryList.add(HydraliskDen);
        libaryList.add(BanelingNest);
        libaryList.add(GreaterSpire);
        libaryList.add(UltraliskCavern);
        libaryList.add(Spire);
        libaryList.add(InfestationPit);
        libaryList.add(EvolutionChamber);
        libaryList.add(NydusNetwork);
        libaryList.add(NydusWorm);
        libaryList.add(SpineCrawler);
        libaryList.add(SporeCrawler);
        
        for (Building building : libaryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
 // has to be at the end of the class to keep the class initialization in order
    final private static ZergBuildingLibrary instance = new ZergBuildingLibrary();
    final public static ZergBuildingLibrary getInstance() {
        return instance;
    }
}
