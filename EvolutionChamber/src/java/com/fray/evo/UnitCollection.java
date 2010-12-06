/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.fray.evo.util.Race;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;

/**
 *
 * @author Cyrik
 */
public final class UnitCollection implements Serializable {
    private final int[] arr;
    private final Race race;

    public UnitCollection(Collection<Unit> units, Race race){
        this(units.size(),race );
    }
    
    public UnitCollection(int size, Race race){
        arr = new int[size];
        this.race = race;
    }

    public void put(Unit unit, int num){
        arr[unit.getId()] = num;
    }

    public int get(Unit unit){
        return arr[unit.getId()];
    }
    public int getById(int id){
        return arr[id];
    }
    public int getSize(){
        return arr.length;
    }
    public int getCount(){
        int result = 0;
        for(int i = 0; i < arr.length; i++){
            result += arr[i];
        }
        return result;
    }
    public HashMap<Unit,Integer> toHashMap(){
        HashMap<Unit, Integer> result = new HashMap<Unit, Integer>();
        for(int i=0;i < arr.length;i++){
            result.put(ZergUnitLibrary.getInstance().getIdToItemMap().get(i), arr[i]);
        }
        return result;
    }
    public void putById(int id, int count){
        arr[id] = count;
    }
    @Override
    public UnitCollection clone(){
        UnitCollection result = new UnitCollection(arr.length, race);
        for(int i = 0; i < arr.length;i++){
            result.putById(i, arr[i]);
        }
        return result;
    }
}
