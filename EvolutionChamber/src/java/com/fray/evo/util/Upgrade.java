/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 *
 * @author Cyrik
 */
public class Upgrade implements Buildable{
    private int minerals;
    private int gas;
    private double time;
    private String name;
    public Upgrade(String name,int minerals, int gas, double time){
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.time = time;
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

    public String getName(){
        return name;
    }

}
