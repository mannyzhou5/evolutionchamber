package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionMineMineral;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildExtractor extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildExtractor()
	{
		super(BuildingLibrary.Extractor);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.extractorsBuilding++;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		if (s.extractorsBuilding == 0)
			throw new RuntimeException("wtf?");
		s.AddBuilding((Building) buildable);
		if (s.settings.pullWorkersFromGas == false)
		{
			s.dronesOnMinerals -= 3;
			s.dronesOnGas += 3;
		}
		s.extractorsBuilding--;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getGasExtractors() + s.extractorsBuilding >= s.extractors())
			return true;
		if (s.supplyUsed < s.settings.minimumExtractorSupply)
			return true;
		return false;
	}

}
