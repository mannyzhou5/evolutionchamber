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
public final class Building implements Buildable {

    private final String name;
    private final int minerals;
    private final int gas;
    private final double time;
    private final Buildable consumes;
    private final ArrayList<Buildable> requierments;
    private final int id;

    public Building(String name, int minerals, int gas, double time, Buildable consumes, ArrayList<Buildable> requierments, int id) {
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.time = time;
        this.consumes = consumes;
        this.requierments = requierments;
        this.id = id;
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
        final Building other = (Building) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public int getId(){
        return id;
    }
}
