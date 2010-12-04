package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildBaneling extends EcActionBuildUnit implements Serializable {

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
