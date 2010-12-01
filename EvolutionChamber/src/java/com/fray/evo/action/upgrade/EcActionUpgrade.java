package com.fray.evo.action.upgrade;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.*;

public abstract class EcActionUpgrade extends EcAction implements Serializable {

    private Upgrade upgrade;

    public EcActionUpgrade() {
        init();
    }

    @Override
    public void execute(final EcBuildOrder s, final EcEvolver e) {
        s.minerals -= getMinerals();
        s.gas -= getGas();
        s.addFutureAction(getTime(), new Runnable() {

            @Override
            public void run() {
                if (e.debug) {
                    e.evolved(s, getName());
                }
                afterTime(s, e);
            }
        });
    }

    @Override
    public boolean isPossible(EcBuildOrder s) {
        if (s.minerals < getMinerals()) {
            return false;
        }
        if (s.gas < getGas()) {
            return false;
        }
        return true;
    }

    public abstract void init();

    protected void init(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public abstract void afterTime(EcBuildOrder s, EcEvolver e);

    public int getMinerals() {
        return upgrade.getMinerals();
    }

    public int getGas() {
        return upgrade.getGas();
    }

    public int getTime() {
        return (int) upgrade.getTime();
    }

    public String getName() {
        return upgrade.getName();
    }
}
