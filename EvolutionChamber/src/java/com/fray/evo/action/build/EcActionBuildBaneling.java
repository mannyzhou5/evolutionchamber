package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildBaneling extends EcActionBuildUnit implements Serializable {

    public EcActionBuildBaneling() {
        super(UnitLibrary.Baneling);
    }

}
