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
public class Unit implements Buildable {

    private double supply;
    private String name;
    private int minerals;
    private int gas;
    private double time;
    private Buildable consumes;
    private ArrayList<Buildable> requierments;
    private int id;

    public Unit(int id, String name, int minerals, int gas, double supply, double time, Buildable consumes, ArrayList<Buildable> requierments) {
        this.name = name;
        this.minerals = minerals;
        this.gas = gas;
        this.supply = supply;
        this.time = time;
        this.requierments = requierments;
        this.consumes = consumes;
        this.id = id;
    }

    public double getSupply() {
        return supply;
    }

    @Override
    public String getName() {
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
            return consumes.getTime() + time;
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
        final Unit other = (Unit) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    public int getId(){
        return id;
    }
}
