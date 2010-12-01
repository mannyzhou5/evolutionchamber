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
//TODO: should the units cost the "full" cost or the upgrade cost?
public class UnitLibrary {
    public static Unit Zergling = new Unit("Zergling", 50, 0, 1, 24.0);
    public static Unit Drone = new Unit("Drone", 50, 0, 1, 17);
    public static Unit Roach = new Unit("Roach", 75, 25, 2, 27);
    public static Unit Queen = new Unit("Queen", 150, 0, 2, 50);
    public static Unit Baneling = new Unit("Baneling", 50, 25, 0.5, 44);
    public static Unit Mutalisk = new Unit("Mutalisk", 100, 100,  2, 33);
    public static Unit Hydralisk = new Unit("Hydralisk", 100, 50, 2, 33);
    public static Unit Infestor = new Unit("Infestor", 100, 150, 2, 50);
    public static Unit Corruptor = new Unit("Corruptor", 150, 100, 2, 40);
    public static Unit Ultralisk = new Unit("Ultralisk", 300, 200, 6, 70);
    public static Unit Broodlord = new Unit("Broodlord", 300, 250, 4, 74);
    public static Unit Overlord = new Unit("Overlord", 100, 0, 0, 25);
    public static Unit Overseer = new Unit("Overseer", 150, 100, 0, 42);

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
        }
        return zergUnits;
    };

}
