/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 *
 * @author Cyrik
 */
public class Unit implements Buildable{
    private double supply;
    private String name;
    private int minerals;
    private int gas;
    private double time;

    public Unit(String name, int minerals, int gas, double supply, double time){
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.supply = supply;
        this.time = time;
    }

    public double getSupply(){
        return supply;
    }
    public String getName(){
        return name;
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
}
