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
//TODO: should the units cost the "full" cost or the upgrade cost?
public class UnitLibrary {
    static private ArrayList<Buildable> createList(Buildable ...buildables){
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }
    public static Unit Larva = new Unit("Larva", 0, 0, 0, 0,null, createList());
    public static Unit Zergling = new Unit("Zergling", 50, 0, 1, 24.0,UnitLibrary.Larva,
            createList(BuildingLibrary.SpawningPool));
    public static Unit Drone = new Unit("Drone", 50, 0, 1, 17,UnitLibrary.Larva,
            createList());
    public static Unit Roach = new Unit("Roach", 75, 25, 2, 27,UnitLibrary.Larva,
            createList(BuildingLibrary.RoachWarren));
    public static Unit Queen = new Unit("Queen", 150, 0, 2, 50,UnitLibrary.Larva,
            createList(BuildingLibrary.SpawningPool));
    public static Unit Baneling = new Unit("Baneling", 25, 25, 0.5, 20, UnitLibrary.Zergling,
            createList(BuildingLibrary.BanelingNest));
    public static Unit Mutalisk = new Unit("Mutalisk", 100, 100,  2, 33,UnitLibrary.Larva,
            createList(BuildingLibrary.Spire));
    public static Unit Hydralisk = new Unit("Hydralisk", 100, 50, 2, 33,UnitLibrary.Larva,
            createList(BuildingLibrary.HydraliskDen));
    public static Unit Infestor = new Unit("Infestor", 100, 150, 2, 50,UnitLibrary.Larva,
            createList(BuildingLibrary.InfestationPit));
    public static Unit Corruptor = new Unit("Corruptor", 150, 100, 2, 40,UnitLibrary.Larva,
            createList(BuildingLibrary.Spire));
    public static Unit Ultralisk = new Unit("Ultralisk", 300, 200, 6, 70,UnitLibrary.Larva,
            createList(BuildingLibrary.UltraliskCavern));
    public static Unit Broodlord = new Unit("Broodlord", 150, 150, 4, 34,UnitLibrary.Corruptor,
            createList(BuildingLibrary.GreaterSpire));
    public static Unit Overlord = new Unit("Overlord", 100, 0, 0, 25,UnitLibrary.Larva,
            createList());
    public static Unit Overseer = new Unit("Overseer", 50, 100, 0, 17,UnitLibrary.Overlord,
            createList(BuildingLibrary.Lair));


    private static ArrayList<Unit> zergUnits;
    synchronized public static ArrayList<Unit> getAllZergUnits(){
        if(zergUnits == null){
            zergUnits = new ArrayList<Unit>();
            zergUnits.add(Drone);
            zergUnits.add(Zergling);
            zergUnits.add(Roach);
            zergUnits.add(Queen);
            zergUnits.add(Baneling);
            zergUnits.add(Mutalisk);
            zergUnits.add(Hydralisk);
            zergUnits.add(Infestor);
            zergUnits.add(Corruptor);
            zergUnits.add(Broodlord);
            zergUnits.add(Ultralisk);
            zergUnits.add(Overlord);
            zergUnits.add(Overseer);
            zergUnits.add(Larva);
        }
        return zergUnits;
    };

}
