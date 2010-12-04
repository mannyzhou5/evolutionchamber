/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The singleton instance contains all Zerg Upgrades
 * @author Cyrik
 * 
 * TODO rename this class to ZergUpgradeLibrary
 */
public class UpgradeLibrary extends Library<Upgrade> {    
    static private ArrayList<Buildable> createList(Buildable ...buildables){
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }
    
    private static int buildingCount = 0;
    
    final public static Upgrade MetabolicBoost = new Upgrade("Metabolic Boost", 100, 100, 110, BuildingLibrary.SpawningPool, buildingCount++,
            createList());
    final public static Upgrade AdrenalGlands = new Upgrade("Adrenal Glands", 200, 200, 130, BuildingLibrary.SpawningPool, buildingCount++,
            createList(BuildingLibrary.Hive));
    final public static Upgrade GlialReconstitution = new Upgrade("Glial Reconstitution", 100, 100, 110, BuildingLibrary.RoachWarren, buildingCount++,
            createList(BuildingLibrary.Lair));
    final public static Upgrade TunnelingClaws = new Upgrade("Tunneling Claws", 150, 150, 110, BuildingLibrary.RoachWarren, buildingCount++,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Burrow = new Upgrade("Burrow", 100, 100, 100, BuildingLibrary.Hatchery, buildingCount++,
            createList(BuildingLibrary.Lair));
    final public static Upgrade PneumatizedCarapace = new Upgrade("Pneumatized Carapace", 100, 100, 60, BuildingLibrary.Hatchery, buildingCount++,
            createList(BuildingLibrary.Lair));
    final public static Upgrade VentralSacs = new Upgrade("Ventral Sacs", 200, 200, 130,BuildingLibrary.Hatchery, buildingCount++,
            createList(BuildingLibrary.Lair));
    final public static Upgrade CentrifugalHooks = new Upgrade("Centrifugal Hooks", 150, 150, 110,BuildingLibrary.BanelingNest, buildingCount++,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Melee1 = new Upgrade("Melee +1", 100, 100, 160,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.EvolutionChamber));
    final public static Upgrade Melee2 = new Upgrade("Melee +2", 150, 150, 190,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.Lair,UpgradeLibrary.Melee1));
    final public static Upgrade Melee3 = new Upgrade("Melee +3", 200, 200, 220,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.Hive,UpgradeLibrary.Melee2));
    final public static Upgrade Missile1 = new Upgrade("Missile +1", 100, 100, 160,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.EvolutionChamber));
    final public static Upgrade Missile2 = new Upgrade("Missile +2", 150, 150, 190,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.Lair,UpgradeLibrary.Missile1));
    final public static Upgrade Missile3 = new Upgrade("Missile +3", 200, 200, 220,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.Hive,UpgradeLibrary.Missile2));
    final public static Upgrade Armor1 = new Upgrade("Carapace +1",150,150,160,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.EvolutionChamber));
    final public static Upgrade Armor2 = new Upgrade("Carapace +2", 225, 225, 190,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.Lair,UpgradeLibrary.Armor1));
    final public static Upgrade Armor3 = new Upgrade("Carapace +3", 300, 300, 220,BuildingLibrary.EvolutionChamber, buildingCount++,
            createList(BuildingLibrary.Lair,UpgradeLibrary.Armor2));
    final public static Upgrade FlyerAttacks1 = new Upgrade("Flyer Attacks +1", 100, 100, 160,BuildingLibrary.Spire, buildingCount++,
            createList(BuildingLibrary.Spire));
    final public static Upgrade FlyerAttacks2 = new Upgrade("Flyer Attacks +2", 175, 175, 190,BuildingLibrary.Spire, buildingCount++,
            createList(BuildingLibrary.Lair,UpgradeLibrary.FlyerAttacks1));
    final public static Upgrade FlyerAttacks3 = new Upgrade("Flyer Attacks +3", 250, 250, 220,BuildingLibrary.Spire, buildingCount++,
            createList(BuildingLibrary.Hive,UpgradeLibrary.FlyerAttacks2));
    final public static Upgrade FlyerArmor1 = new Upgrade("Flyer Armor +1", 150, 150, 160,BuildingLibrary.Spire, buildingCount++,
            createList(BuildingLibrary.Spire));
    final public static Upgrade FlyerArmor2 = new Upgrade("Flyer Armor +2", 225, 225, 190,BuildingLibrary.Spire, buildingCount++,
            createList(BuildingLibrary.Lair,UpgradeLibrary.FlyerArmor1));
    final public static Upgrade FlyerArmor3 = new Upgrade("Flyer Armor +3", 300, 300, 220,BuildingLibrary.Spire, buildingCount++,
            createList(BuildingLibrary.Hive,UpgradeLibrary.FlyerArmor2));
    final public static Upgrade GroovedSpines = new Upgrade("Grooved Spines", 150, 150, 80,BuildingLibrary.HydraliskDen, buildingCount++,
            createList());
    final public static Upgrade NeuralParasite = new Upgrade("Neural Parasite", 150, 150, 110,BuildingLibrary.InfestationPit, buildingCount++,
            createList());
    final public static Upgrade PathogenGlands = new Upgrade("Pathogen Glands", 150, 150, 80,BuildingLibrary.InfestationPit, buildingCount++,
            createList());
    final public static Upgrade ChitinousPlating = new Upgrade("Chitinous Plating", 150, 150, 110,BuildingLibrary.UltraliskCavern, buildingCount++,
            createList());


    private UpgradeLibrary() {
        // call parent constructor to init the lists first
        super();
        libaryList.add(MetabolicBoost);
        libaryList.add(AdrenalGlands);
        libaryList.add(GlialReconstitution);
        libaryList.add(TunnelingClaws);
        libaryList.add(Burrow);
        libaryList.add(PneumatizedCarapace);
        libaryList.add(VentralSacs);
        libaryList.add(CentrifugalHooks);
        libaryList.add(Melee1);
        libaryList.add(Melee2);
        libaryList.add(Melee3);
        libaryList.add(Armor1);
        libaryList.add(Armor2);
        libaryList.add(Armor3);
        libaryList.add(FlyerAttacks1);
        libaryList.add(FlyerAttacks2);
        libaryList.add(FlyerAttacks3);
        libaryList.add(FlyerArmor1);
        libaryList.add(FlyerArmor2);
        libaryList.add(FlyerArmor3);
        libaryList.add(GroovedSpines);
        libaryList.add(NeuralParasite);
        libaryList.add(PathogenGlands);
        libaryList.add(ChitinousPlating);
        
        for (Upgrade building : libaryList) {
            idToItemMap.put(building.getId(), building);
        }
    }
    
    
    // has to be at the end of the class to keep the class initialization in order
    final private static UpgradeLibrary instance = new UpgradeLibrary();
    final public static UpgradeLibrary getInstance() {
        return instance;
    }
}
