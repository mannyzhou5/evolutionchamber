/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 * The singleton instance contains all Zerg Upgrades
 * @author Cyrik
 * 
 */
public class ZergUpgradeLibrary extends Library<Upgrade> {
    
    final public static Upgrade MetabolicBoost = ZergLibrary.MetabolicBoost;
    final public static Upgrade AdrenalGlands = ZergLibrary.AdrenalGlands;
    final public static Upgrade GlialReconstitution = ZergLibrary.GlialReconstitution;
    final public static Upgrade TunnelingClaws = ZergLibrary.TunnelingClaws;
    final public static Upgrade Burrow = ZergLibrary.Burrow;
    final public static Upgrade PneumatizedCarapace = ZergLibrary.PneumatizedCarapace;
    final public static Upgrade VentralSacs = ZergLibrary.VentralSacs;
    final public static Upgrade CentrifugalHooks = ZergLibrary.CentrifugalHooks;
    final public static Upgrade Melee1 = ZergLibrary.Melee1;
    final public static Upgrade Melee2 = ZergLibrary.Melee2;
    final public static Upgrade Melee3 = ZergLibrary.Melee3;
    final public static Upgrade Missile1 = ZergLibrary.Missile1;
    final public static Upgrade Missile2 = ZergLibrary.Missile2;
    final public static Upgrade Missile3 = ZergLibrary.Missile3;
    final public static Upgrade Armor1 = ZergLibrary.Armor1;
    final public static Upgrade Armor2 = ZergLibrary.Armor2;
    final public static Upgrade Armor3 = ZergLibrary.Armor3;
    final public static Upgrade FlyerAttacks1 = ZergLibrary.FlyerAttacks1;
    final public static Upgrade FlyerAttacks2 = ZergLibrary.FlyerAttacks2;
    final public static Upgrade FlyerAttacks3 = ZergLibrary.FlyerAttacks3;
    final public static Upgrade FlyerArmor1 = ZergLibrary.FlyerArmor1;
    final public static Upgrade FlyerArmor2 = ZergLibrary.FlyerArmor2;
    final public static Upgrade FlyerArmor3 = ZergLibrary.FlyerArmor3;
    final public static Upgrade GroovedSpines = ZergLibrary.GroovedSpines;
    final public static Upgrade NeuralParasite = ZergLibrary.NeuralParasite;
    final public static Upgrade PathogenGlands = ZergLibrary.PathogenGlands;
    final public static Upgrade ChitinousPlating = ZergLibrary.ChitinousPlating;

    private ZergUpgradeLibrary() {
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
    final private static ZergUpgradeLibrary instance = new ZergUpgradeLibrary();
    final public static ZergUpgradeLibrary getInstance() {
        return instance;
    }
}
