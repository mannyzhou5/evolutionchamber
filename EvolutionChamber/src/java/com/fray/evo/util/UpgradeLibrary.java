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
public class UpgradeLibrary {
    static private ArrayList<Buildable> createList(Buildable ...buildables){
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }
    final public static Upgrade MetabolicBoost = new Upgrade("Metabolic Boost", 100, 100, 110, BuildingLibrary.SpawningPool,
            createList());
    final public static Upgrade AdrenalGlands = new Upgrade("Adrenal Glands", 200, 200, 130, BuildingLibrary.SpawningPool,
            createList(BuildingLibrary.Hive));
    final public static Upgrade GlialReconstitution = new Upgrade("Glial Reconstitution", 100, 100, 110, BuildingLibrary.RoachWarren,
            createList(BuildingLibrary.Lair));
    final public static Upgrade TunnelingClaws = new Upgrade("Tunneling Claws", 150, 150, 110, BuildingLibrary.RoachWarren,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Burrow = new Upgrade("Burrow", 100, 100, 100, BuildingLibrary.Hatchery,
            createList(BuildingLibrary.Lair));
    final public static Upgrade PneumatizedCarapace = new Upgrade("Pneumatized Carapace", 100, 100, 60, BuildingLibrary.Hatchery,
            createList(BuildingLibrary.Lair));
    final public static Upgrade VentralSacs = new Upgrade("Ventral Sacs", 200, 200, 130,BuildingLibrary.Hatchery,
            createList(BuildingLibrary.Lair));
    final public static Upgrade CentrifugalHooks = new Upgrade("Centrifugal Hooks", 150, 150, 110,BuildingLibrary.BanelingNest,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Melee1 = new Upgrade("Melee +1", 100, 100, 160,BuildingLibrary.EvolutionChamber,
            createList());
    final public static Upgrade Melee2 = new Upgrade("Melee +2", 150, 150, 190,BuildingLibrary.EvolutionChamber,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Melee3 = new Upgrade("Melee +3", 200, 200, 220,BuildingLibrary.EvolutionChamber,
            createList(BuildingLibrary.Hive));
    final public static Upgrade Missile1 = new Upgrade("Missile +1", 100, 100, 160,BuildingLibrary.EvolutionChamber,
            createList());
    final public static Upgrade Missile2 = new Upgrade("Missile +2", 150, 150, 190,BuildingLibrary.EvolutionChamber,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Missile3 = new Upgrade("Missile +3", 200, 200, 220,BuildingLibrary.EvolutionChamber,
            createList(BuildingLibrary.Hive));
    final public static Upgrade Armor1 = new Upgrade("Carapace +1",150,150,160,BuildingLibrary.EvolutionChamber,
            createList());
    final public static Upgrade Armor2 = new Upgrade("Carapace +2", 225, 225, 190,BuildingLibrary.EvolutionChamber,
            createList(BuildingLibrary.Lair));
    final public static Upgrade Armor3 = new Upgrade("Carapace +3", 300, 300, 220,BuildingLibrary.EvolutionChamber,
            createList(BuildingLibrary.Lair));
    final public static Upgrade FlyerAttacks1 = new Upgrade("Flyer Attacks +1", 100, 100, 160,BuildingLibrary.Spire,
            createList());
    final public static Upgrade FlyerAttacks2 = new Upgrade("Flyer Attacks +2", 175, 175, 190,BuildingLibrary.Spire,
            createList(BuildingLibrary.Lair));
    final public static Upgrade FlyerAttacks3 = new Upgrade("Flyer Attacks +3", 250, 250, 220,BuildingLibrary.Spire,
            createList(BuildingLibrary.Hive));
    final public static Upgrade FlyerArmor1 = new Upgrade("Flyer Armor +1", 150, 150, 160,BuildingLibrary.Spire,
            createList());
    final public static Upgrade FlyerArmor2 = new Upgrade("Flyer Armor +2", 225, 225, 190,BuildingLibrary.Spire,
            createList(BuildingLibrary.Lair));
    final public static Upgrade FlyerArmor3 = new Upgrade("Flyer Armor +3", 300, 300, 220,BuildingLibrary.Spire,
            createList(BuildingLibrary.Hive));
    final public static Upgrade GroovedSpines = new Upgrade("Grooved Spines", 150, 150, 80,BuildingLibrary.HydraliskDen,
            createList());
    final public static Upgrade NeuralParasite = new Upgrade("Neural Parasite", 150, 150, 110,BuildingLibrary.InfestationPit,
            createList());
    final public static Upgrade PathogenGlands = new Upgrade("Pathogen Glands", 150, 150, 80,BuildingLibrary.InfestationPit,
            createList());
    final public static Upgrade ChitinousPlating = new Upgrade("Chitinous Plating", 150, 150, 110,BuildingLibrary.UltraliskCavern,
            createList());

    private static ArrayList<Upgrade> allZergUpgrades;
    synchronized final public static ArrayList<Upgrade> getAllZergUpgrades(){
        if(allZergUpgrades == null){
            allZergUpgrades = new ArrayList<Upgrade>();
            allZergUpgrades.add(MetabolicBoost);
            allZergUpgrades.add(AdrenalGlands);
            allZergUpgrades.add(GlialReconstitution);
            allZergUpgrades.add(TunnelingClaws);
            allZergUpgrades.add(Burrow);
            allZergUpgrades.add(PneumatizedCarapace);
            allZergUpgrades.add(VentralSacs);
            allZergUpgrades.add(CentrifugalHooks);
            allZergUpgrades.add(Melee1);
            allZergUpgrades.add(Melee2);
            allZergUpgrades.add(Melee3);
            allZergUpgrades.add(Armor1);
            allZergUpgrades.add(Armor2);
            allZergUpgrades.add(Armor3);
            allZergUpgrades.add(FlyerAttacks1);
            allZergUpgrades.add(FlyerAttacks2);
            allZergUpgrades.add(FlyerAttacks3);
            allZergUpgrades.add(FlyerArmor1);
            allZergUpgrades.add(FlyerArmor2);
            allZergUpgrades.add(FlyerArmor3);
            allZergUpgrades.add(GroovedSpines);
            allZergUpgrades.add(NeuralParasite);
            allZergUpgrades.add(PathogenGlands);
            allZergUpgrades.add(ChitinousPlating);

        }
        return allZergUpgrades;
    }
 }
