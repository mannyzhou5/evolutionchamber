package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildBaneling extends EcActionBuildUnit implements Serializable {

    public EcActionBuildBaneling() {
        super(UnitLibrary.Baneling);
    }

    @Override
    public void preExecute(final EcBuildOrder s) {
        s.RemoveUnits(UnitLibrary.Zergling, 1);
    }

    @Override
    public boolean isPossible(EcBuildOrder s) {
        if (s.getZerglings() < 1) {
            return false;
        }
        return isPossibleResources(s);
    }

}
