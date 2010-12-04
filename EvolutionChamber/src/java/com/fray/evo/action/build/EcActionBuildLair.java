package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildLair extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildLair()
	{
		super(BuildingLibrary.Lair);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.busyMainBuildings++;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
            s.RemoveBuilding(BuildingLibrary.Hatchery);
		s.AddBuilding((Building) buildable);
		s.busyMainBuildings--;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getHatcheries() <= s.queensBuilding)
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getHatcheries() == 0)
			return true;
		if (s.getSpawningPools() == 0)
			return true;
		return super.isInvalid(s);
	}


}
