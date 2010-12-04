/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.print.attribute.HashAttributeSet;

/**
 *
 * @author Cyrik
 */
//TODO: should the units cost the "full" cost or the upgrade cost?
public class UnitLibrary {
    static private ArrayList<Buildable> createList(Buildable ...buildables){
        return new ArrayList<Buildable>(Arrays.asList(buildables));
    }
    final public static Unit Larva = ZergLibrary.Larva;
    final public static Unit Zergling = ZergLibrary.Zergling;
    final public static Unit Drone = ZergLibrary.Drone;
    final public static Unit Roach = ZergLibrary.Roach;
    final public static Unit Queen = ZergLibrary.Queen;
    final public static Unit Baneling = ZergLibrary.Baneling;
    final public static Unit Mutalisk = ZergLibrary.Mutalisk;
    final public static Unit Hydralisk = ZergLibrary.Hydralisk;
    final public static Unit Infestor = ZergLibrary.Infestor;
    final public static Unit Corruptor = ZergLibrary.Corruptor;
    final public static Unit Ultralisk = ZergLibrary.Ultralisk;
    final public static Unit Broodlord = ZergLibrary.Broodlord;
    final public static Unit Overlord = ZergLibrary.Overlord;
    final public static Unit Overseer = ZergLibrary.Overseer;

    final public static HashMap<Integer, Unit> idToZergUnit;
    public static ArrayList<Unit> zergUnits;
    static {
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
        idToZergUnit = new HashMap<Integer, Unit>();
        for(Unit unit: zergUnits){
            idToZergUnit.put(unit.getId(), unit);
        }
    };



}
