/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo;

import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Cyrik
 */
public final class BuildingCollection implements Serializable{
    private final int[] arr;

    public BuildingCollection(Collection<Building> buildings){
        arr = new int[buildings.size()];
    }
    public BuildingCollection(int size){
        arr = new int[size];
    }

    public void put(Building building, int num){
        arr[building.getId()] = num;
    }

    public int get(Building building){
        return arr[building.getId()];
    }
    public boolean  containsKey(Building building){
        return (arr[building.getId()] != 0);
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
    public HashMap<Building,Integer> toHashMap(){
        HashMap<Building, Integer> result = new HashMap<Building, Integer>();
        for(int i=0;i < arr.length;i++){
            result.put(BuildingLibrary.idToZergBuilding.get(i), arr[i]);
        }
        return result;
    }
    public void putById(int id, int count){
        arr[id] = count;
    }
    @Override
    public BuildingCollection clone(){
        BuildingCollection result = new BuildingCollection(arr.length);
        for(int i = 0; i < arr.length;i++){
            result.putById(i, arr[i]);
        }
        return result;
    }
}
