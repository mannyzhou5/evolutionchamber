/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.action;

import com.fray.evo.action.build.EcActionBuildBaneling;
import com.fray.evo.action.build.EcActionBuildBanelingNest;
import com.fray.evo.action.build.EcActionBuildBroodLord;
import com.fray.evo.action.build.EcActionBuildCorruptor;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildGreaterSpire;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildHydralisk;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;
import com.fray.evo.action.build.EcActionBuildInfestationPit;
import com.fray.evo.action.build.EcActionBuildInfestor;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildMutalisk;
import com.fray.evo.action.build.EcActionBuildNydusNetwork;
import com.fray.evo.action.build.EcActionBuildNydusWorm;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildOverseer;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildRoach;
import com.fray.evo.action.build.EcActionBuildRoachWarren;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.build.EcActionBuildSpineCrawler;
import com.fray.evo.action.build.EcActionBuildSpire;
import com.fray.evo.action.build.EcActionBuildSporeCrawler;
import com.fray.evo.action.build.EcActionBuildUltralisk;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;
import com.fray.evo.action.build.EcActionBuildZergling;
import com.fray.evo.action.upgrade.*;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.UnitLibrary;
import com.fray.evo.util.UpgradeLibrary;
import java.util.HashMap;

/**
 *
 * @author Cyrik
 */
public final class ActionManager {
    private static HashMap<Buildable,EcAction> buildableToActionMap;

    synchronized static public EcAction getActionFor(Buildable buildable){
        if(buildableToActionMap== null){
            init();
        }
        return buildableToActionMap.get(buildable);
    }

    private static void init() {
        buildableToActionMap = new HashMap<Buildable, EcAction>();
        buildableToActionMap.put(UpgradeLibrary.AdrenalGlands, new EcActionUpgradeAdrenalGlands());
        buildableToActionMap.put(UpgradeLibrary.Armor1, new EcActionUpgradeCarapace1());
        buildableToActionMap.put(UpgradeLibrary.Armor2, new EcActionUpgradeCarapace2());
        buildableToActionMap.put(UpgradeLibrary.Armor3, new EcActionUpgradeCarapace3());
        buildableToActionMap.put(UpgradeLibrary.Burrow, new EcActionUpgradeBurrow());
        buildableToActionMap.put(UpgradeLibrary.CentrifugalHooks, new EcActionUpgradeCentrifugalHooks());
        buildableToActionMap.put(UpgradeLibrary.ChitinousPlating, new EcActionUpgradeChitinousPlating());
        buildableToActionMap.put(UpgradeLibrary.FlyerArmor1, new EcActionUpgradeFlyerArmor1());
        buildableToActionMap.put(UpgradeLibrary.FlyerArmor2, new EcActionUpgradeFlyerArmor2());
        buildableToActionMap.put(UpgradeLibrary.FlyerArmor3, new EcActionUpgradeFlyerArmor3());
        buildableToActionMap.put(UpgradeLibrary.FlyerAttacks1, new EcActionUpgradeFlyerAttacks1());
        buildableToActionMap.put(UpgradeLibrary.FlyerAttacks2, new EcActionUpgradeFlyerAttacks2());
        buildableToActionMap.put(UpgradeLibrary.FlyerAttacks3, new EcActionUpgradeFlyerAttacks3());
        buildableToActionMap.put(UpgradeLibrary.GlialReconstitution, new EcActionUpgradeGlialReconstitution());
        buildableToActionMap.put(UpgradeLibrary.GroovedSpines, new EcActionUpgradeGroovedSpines());
        buildableToActionMap.put(UpgradeLibrary.Melee1, new EcActionUpgradeMelee1());
        buildableToActionMap.put(UpgradeLibrary.Melee2, new EcActionUpgradeMelee2());
        buildableToActionMap.put(UpgradeLibrary.Melee3, new EcActionUpgradeMelee3());
        buildableToActionMap.put(UpgradeLibrary.MetabolicBoost, new EcActionUpgradeMetabolicBoost());
        buildableToActionMap.put(UpgradeLibrary.Missile1, new EcActionUpgradeMissile1());
        buildableToActionMap.put(UpgradeLibrary.Missile2, new EcActionUpgradeMissile2());
        buildableToActionMap.put(UpgradeLibrary.Missile3, new EcActionUpgradeMissile3());
        buildableToActionMap.put(UpgradeLibrary.NeuralParasite, new EcActionUpgradeNeuralParasite());
        buildableToActionMap.put(UpgradeLibrary.PathogenGlands, new EcActionUpgradePathogenGlands());
        buildableToActionMap.put(UpgradeLibrary.PneumatizedCarapace, new EcActionUpgradePneumatizedCarapace());
        buildableToActionMap.put(UpgradeLibrary.TunnelingClaws, new EcActionUpgradeTunnelingClaws());
        buildableToActionMap.put(UpgradeLibrary.VentralSacs, new EcActionUpgradeVentralSacs());

        buildableToActionMap.put(BuildingLibrary.BanelingNest, new EcActionBuildBanelingNest());
        buildableToActionMap.put(BuildingLibrary.EvolutionChamber, new EcActionBuildEvolutionChamber());
        buildableToActionMap.put(BuildingLibrary.Extractor, new EcActionBuildExtractor());
        buildableToActionMap.put(BuildingLibrary.GreaterSpire, new EcActionBuildGreaterSpire());
        buildableToActionMap.put(BuildingLibrary.Hatchery, new EcActionBuildHatchery());
        buildableToActionMap.put(BuildingLibrary.Hive, new EcActionBuildHive());
        buildableToActionMap.put(BuildingLibrary.HydraliskDen, new EcActionBuildHydraliskDen());
        buildableToActionMap.put(BuildingLibrary.InfestationPit, new EcActionBuildInfestationPit());
        buildableToActionMap.put(BuildingLibrary.Lair, new EcActionBuildLair());
        buildableToActionMap.put(BuildingLibrary.NydusNetwork, new EcActionBuildNydusNetwork());
        buildableToActionMap.put(BuildingLibrary.NydusWorm, new EcActionBuildNydusWorm());
        buildableToActionMap.put(BuildingLibrary.RoachWarren, new EcActionBuildRoachWarren());
        buildableToActionMap.put(BuildingLibrary.SpawningPool, new EcActionBuildSpawningPool());
        buildableToActionMap.put(BuildingLibrary.SpineCrawler, new EcActionBuildSpineCrawler());
        buildableToActionMap.put(BuildingLibrary.Spire, new EcActionBuildSpire());
        buildableToActionMap.put(BuildingLibrary.SporeCrawler, new EcActionBuildSporeCrawler());
        buildableToActionMap.put(BuildingLibrary.UltraliskCavern, new EcActionBuildUltraliskCavern());

        buildableToActionMap.put(UnitLibrary.Baneling, new EcActionBuildBaneling());
        buildableToActionMap.put(UnitLibrary.Broodlord, new EcActionBuildBroodLord());
        buildableToActionMap.put(UnitLibrary.Corruptor, new EcActionBuildCorruptor());
        buildableToActionMap.put(UnitLibrary.Drone, new EcActionBuildDrone());
        buildableToActionMap.put(UnitLibrary.Hydralisk, new EcActionBuildHydralisk());
        buildableToActionMap.put(UnitLibrary.Infestor, new EcActionBuildInfestor());
        buildableToActionMap.put(UnitLibrary.Mutalisk, new EcActionBuildMutalisk());
        buildableToActionMap.put(UnitLibrary.Overlord, new EcActionBuildOverlord());
        buildableToActionMap.put(UnitLibrary.Overseer, new EcActionBuildOverseer());
        buildableToActionMap.put(UnitLibrary.Queen, new EcActionBuildQueen());
        buildableToActionMap.put(UnitLibrary.Roach, new EcActionBuildRoach());
        buildableToActionMap.put(UnitLibrary.Ultralisk, new EcActionBuildUltralisk());
        buildableToActionMap.put(UnitLibrary.Zergling, new EcActionBuildZergling());

    }

}
