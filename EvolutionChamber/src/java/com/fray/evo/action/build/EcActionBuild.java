package com.fray.evo.action.build;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.EcMessages;

public abstract class EcActionBuild extends EcAction implements Serializable {

    protected Buildable buildable;

    public EcActionBuild(Buildable buildable) {
        this.buildable = buildable;
    }

    protected boolean isPossibleResources(EcBuildOrder s) {
        if (s.minerals < getMinerals()) {
            return false;
        }
        if (s.gas < getGas()) {
            return false;
        }
        return true;
    }

    protected void obtainOne(final EcBuildOrder s, EcEvolver e) {
        if (e.debug) {
            e.obtained(s, " " + messages.getString(getName().replace(" ", ".")) + "+1");
        }
    }

    public int getMinerals() {
        return buildable.getMinerals();
    }

    public int getGas() {
        return buildable.getGas();
    }

    public int getTime() {
        return (int) buildable.getTime();
    }

    public String getName() {
        return buildable.getName();
    }
    public Buildable getConsumes(){
        return buildable.getConsumes();
    }
}
