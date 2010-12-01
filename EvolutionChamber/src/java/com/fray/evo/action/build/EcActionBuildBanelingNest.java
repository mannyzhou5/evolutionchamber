package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildBanelingNest extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildBanelingNest()
	{
		super(BuildingLibrary.BanelingNest);
	}

	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
		s.banelingNest += 1;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.banelingNest == 1)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		l.add(new EcActionBuildExtractor());
		destination.spawningPools = Math.max(destination.spawningPools,1);
		destination.gasExtractors = Math.max(destination.gasExtractors,1);
		return l;
	}
}
