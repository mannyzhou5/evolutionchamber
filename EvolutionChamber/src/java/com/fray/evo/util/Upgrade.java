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
public final class Upgrade extends Buildable{
    private final Building builtIn;

    public Upgrade(String name,int minerals, int gas, double time, Building builtIn, int id, ArrayList<Buildable> requirements){
        super(name,minerals,gas,time,null,requirements, id);

        this.builtIn = builtIn;
    }


    public Building getBuiltIn(){
        return builtIn;
    }
}
