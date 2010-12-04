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
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		if (s.getBanelingNest() == 1)
			return true;
		return false;
	}

}
