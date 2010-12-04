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
public final class Building extends Buildable {

    public Building(String name, int minerals, int gas, double time, Buildable consumes, ArrayList<Buildable> requirements, int id) {
        super(name,minerals,gas,time,consumes,requirements,id);
    }
    
}
