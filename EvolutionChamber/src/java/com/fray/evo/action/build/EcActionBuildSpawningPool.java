package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildSpawningPool extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildSpawningPool()
	{
		super(BuildingLibrary.SpawningPool);
	}


	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() >= 1)
			return true;
		if(s.supplyUsed < s.settings.minimumPoolSupply)
			return true;
		return super.isInvalid(s);
	}

}
