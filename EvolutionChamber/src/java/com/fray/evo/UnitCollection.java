/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo;

import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Cyrik
 */
public final class UnitCollection implements Serializable {
    private final int[] arr;

    public UnitCollection(Collection<Unit> units){
        arr = new int[units.size()];
    }
    public UnitCollection(int size){
        arr = new int[size];
    }

    public void put(Unit unit, int num){
        arr[unit.getId()] = num;
    }

    public int get(Unit unit){
        return arr[unit.getId()];
    }
    public boolean  containsKey(Unit unit){
        return (arr[unit.getId()] != 0);
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
            result.put(UnitLibrary.idToZergUnit.get(i), arr[i]);
        }
        return result;
    }
    public void putById(int id, int count){
        arr[id] = count;
    }
    @Override
    public UnitCollection clone(){
        UnitCollection result = new UnitCollection(arr.length);
        for(int i = 0; i < arr.length;i++){
            result.putById(i, arr[i]);
        }
        return result;
    }
}
