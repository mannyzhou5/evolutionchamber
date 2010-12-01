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
public class UpgradeLibrary {
    public static Upgrade MetabolicBoost = new Upgrade("MetabolicBoost", 100, 100, 110);
    public static Upgrade AdrenalGlands = new Upgrade("AdrenalGlands", 200, 200, 130);
    public static Upgrade GlialReconstitution = new Upgrade("GlialReconstitution", 100, 100, 110);
    public static Upgrade TunnelingClaws = new Upgrade("TunnelingClaws", 150, 150, 110);
    public static Upgrade Burrow = new Upgrade("Burrow", 100, 100, 100);
    public static Upgrade PneumatizedCarapace = new Upgrade("PneumatizedCarapace", 100, 100, 60);
    public static Upgrade VentralSacs = new Upgrade("VentralSacs", 200, 200, 130);
    public static Upgrade CentrifugalHooks = new Upgrade("CentrifugalHooks", 150, 150, 110);
    public static Upgrade Melee1 = new Upgrade("Melee1", 100, 100, 160);
    public static Upgrade Melee2 = new Upgrade("Melee2", 150, 150, 190);
    public static Upgrade Melee3 = new Upgrade("Melee3", 200, 200, 220);
    public static Upgrade Missile1 = new Upgrade("Missile1", 100, 100, 160);
    public static Upgrade Missile2 = new Upgrade("Missile2", 150, 150, 190);
    public static Upgrade Missile3 = new Upgrade("Missile3", 200, 200, 220);
    public static Upgrade Armor1 = new Upgrade("Armor1",150,150,160);
    public static Upgrade Armor2 = new Upgrade("Armor2", 225, 225, 190);
    public static Upgrade Armor3 = new Upgrade("Armor3", 300, 300, 220);
    public static Upgrade FlyerAttacks1 = new Upgrade("FlyerAttacks1", 100, 100, 160);
    public static Upgrade FlyerAttacks2 = new Upgrade("FlyerAttacks2", 175, 175, 190);
    public static Upgrade FlyerAttacks3 = new Upgrade("FlyerAttacks3", 250, 250, 220);
    public static Upgrade FlyerArmor1 = new Upgrade("FlyerArmor1", 150, 150, 160);
    public static Upgrade FlyerArmor2 = new Upgrade("FlyerArmor2", 225, 225, 190);
    public static Upgrade FlyerArmor3 = new Upgrade("FlyerArmor3", 300, 300, 220);
    public static Upgrade GroovedSpines = new Upgrade("GroovedSpines", 150, 150, 80);
    public static Upgrade NeuralParasite = new Upgrade("NeuralParasite", 150, 150, 110);
    public static Upgrade PathogenGlands = new Upgrade("PathogenGlands", 150, 150, 80);
    public static Upgrade ChitinousPlating = new Upgrade("chitinousPlating", 150, 150, 110);

    private static ArrayList<Upgrade> allZergUpgrades;
    synchronized public static ArrayList<Upgrade> getAllZergUpgrades(){
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
            allZergUpgrades.add(NeuralParasite);
            allZergUpgrades.add(PathogenGlands);
            allZergUpgrades.add(ChitinousPlating);

        }
        return allZergUpgrades;
    }
 }
