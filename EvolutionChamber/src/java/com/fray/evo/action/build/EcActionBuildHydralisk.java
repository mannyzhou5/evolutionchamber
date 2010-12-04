package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;
import com.fray.evo.util.Upgrade;

public final class EcActionBuildHydralisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildHydralisk()
	{
		super(UnitLibrary.Hydralisk);
	}

}
