package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildGreaterSpire extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildGreaterSpire()
	{
		super(BuildingLibrary.GreaterSpire);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.evolvingSpires += 1;
		s.RemoveBuilding((Building) getConsumes());
	}
	
	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
		s.AddBuilding((Building) buildable);
		s.evolvingSpires -= 1;
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getSpire() < 1)
			return false;
		return super.isPossible(s);
	}

}
