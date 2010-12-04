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
public final class Unit extends Buildable {

    private final double supply;
    
    public Unit(int id, String name, int minerals, int gas, double supply, double time, Buildable consumes, ArrayList<Buildable> requirements) {
        super(name, minerals, gas, time, consumes, requirements, id);

        this.supply = supply;
    }

    public double getSupply() {
        return supply;
    }
}
