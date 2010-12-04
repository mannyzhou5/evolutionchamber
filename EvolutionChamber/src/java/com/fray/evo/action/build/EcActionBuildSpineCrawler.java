package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildSpineCrawler extends EcActionBuildBuilding implements Serializable
{

	public EcActionBuildSpineCrawler()
	{
		super(BuildingLibrary.SpineCrawler);
	}

	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		return false;
	}

}
