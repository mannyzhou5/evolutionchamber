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
public class Building implements Buildable {

    private String name;
    private int minerals;
    private int gas;
    private double time;
    private Buildable consumes;
    private ArrayList<Buildable> requierments;

    public Building(String name, int minerals, int gas, double time, Buildable consumes, ArrayList<Buildable> requierments) {
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.time = time;
        this.consumes = consumes;
        this.requierments = requierments;
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
    public String getName() {
        return name;
    }

    @Override
    public int getFullMinerals() {
        if (consumes == null) {
            return minerals;
        } else {

            return consumes.getFullMinerals() + minerals;
        }
    }

    @Override
    public int getFullGas() {
        if (consumes == null) {
            return gas;
        } else {
            return consumes.getFullGas() + gas;
        }

    }

    @Override
    public double getFullTime() {
        if (consumes == null) {
            return time;
        } else {
            return consumes.getFullTime() + time;
        }
    }

    @Override
    public Buildable getConsumes() {
        return consumes;
    }

    @Override
    public ArrayList<Buildable> getRequirement() {
        return requierments;
    }
}
