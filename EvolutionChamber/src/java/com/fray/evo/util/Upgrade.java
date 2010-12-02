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
public class Upgrade implements Buildable{
    private int minerals;
    private int gas;
    private double time;
    private String name;
    private ArrayList<Buildable> requierments;
    private Building builtIn;

    public Upgrade(String name,int minerals, int gas, double time, Building builtIn, ArrayList<Buildable> requierments){
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.time = time;
        this.requierments = requierments;
        this.builtIn = builtIn;
    }

    @Override
    public int getMinerals() {
        return minerals;
    }

    @Override
    public int getGas() {
        return gas;
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int getFullMinerals() {
        return minerals;
    }

    @Override
    public int getFullGas() {
        return gas;
    }

    @Override
    public double getFullTime() {
        return time;
    }

    @Override
    public Buildable getConsumes() {
        return null;
    }

    @Override
    public ArrayList<Buildable> getRequirement() {
        return requierments;
    }

    public Building getBuiltIn(){
        return builtIn;
    }
    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Upgrade other = (Upgrade) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
