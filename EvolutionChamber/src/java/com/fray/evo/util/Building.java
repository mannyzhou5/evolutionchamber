/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

/**
 *
 * @author Cyrik
 */
public class Building implements Buildable{
    private String name;
    private int minerals;
    private int gas;
    private double time;

    public Building(String name, int minerals, int gas, double time){
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.time = time;
    }

    @Override
    public int getMinerals() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getGas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
