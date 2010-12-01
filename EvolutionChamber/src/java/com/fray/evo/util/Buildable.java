/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fray.evo.util;

import java.io.Serializable;

/**
 *
 * @author Cyrik
 */
public interface Buildable extends Serializable {
    public int getMinerals();
    public int getGas();
    public double getTime();
    public String getName();
    public int getFullMinerals();
    public int getFullGas();
    public double getFullTime();
    public Buildable getConsumes();
}
