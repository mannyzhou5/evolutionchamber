package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildLair extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildLair()
	{
		super(BuildingLibrary.Lair);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.RemoveBuilding(BuildingLibrary.Hatchery);
		s.evolvingHatcheries += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.AddBuilding((Building) buildable);
		s.evolvingHatcheries -= 1;
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
