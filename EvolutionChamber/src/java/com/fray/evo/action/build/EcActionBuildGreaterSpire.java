package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildGreaterSpire extends EcActionBuildBuilding implements Serializable
{
    public EcActionBuildGreaterSpire() {
        super(BuildingLibrary.GreaterSpire);
    }

    @Override
    protected void preExecute(EcBuildOrder s) {
        s.makeBuildingBusy((Building) buildable.getConsumes(), this);
    }

    @Override
    protected void postExecute(EcBuildOrder s, GameLog e) {
        s.makeBuildingNotBusy(this);
        s.AddBuilding((Building) buildable);
    }
}
