package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildSporeCrawler extends EcActionBuildBuilding implements Serializable
{

	public EcActionBuildSporeCrawler()
	{
		super(BuildingLibrary.SporeCrawler);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getEvolutionChambers() == 0)
			return true;
		return false;
	}

}
