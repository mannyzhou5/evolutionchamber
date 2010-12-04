package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildRoachWarren extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildRoachWarren()
	{
		super(BuildingLibrary.RoachWarren);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		if (s.getRoachWarrens() >= 1)
			return true;
		return false;
	}


}
