/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;


/**
 * The singleton instance contains all Zerg Units
 * @author Cyrik
 * 
 * TODO rename this class to ZerUnitLibrary
 */
public class UnitLibrary extends Library<Unit> {

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

    
    /**
     * initializes the lists
     */
    private UnitLibrary() {
        // execute parent constructor to init the lists first
        super();
        libaryList.add(Drone);
        libaryList.add(Zergling);
        libaryList.add(Roach);
        libaryList.add(Queen);
        libaryList.add(Baneling);
        libaryList.add(Mutalisk);
        libaryList.add(Hydralisk);
        libaryList.add(Infestor);
        libaryList.add(Corruptor);
        libaryList.add(Broodlord);
        libaryList.add(Ultralisk);
        libaryList.add(Overlord);
        libaryList.add(Overseer);
        libaryList.add(Larva);

        for (Unit unit : libaryList) {
            idToItemMap.put(unit.getId(), unit);
        }
    }

    
    // has to be at the end of the class to keep the class initialization in order
    final private static UnitLibrary instance = new UnitLibrary();
    final public static UnitLibrary getInstance() {
        return instance;
    }
}
