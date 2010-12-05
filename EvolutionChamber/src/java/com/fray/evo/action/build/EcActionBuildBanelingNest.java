package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildBanelingNest extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildBanelingNest()
	{
		super(BuildingLibrary.BanelingNest);
	}

}
