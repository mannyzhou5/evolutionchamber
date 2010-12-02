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

public class EcActionBuildHive extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHive()
	{
		super(BuildingLibrary.Hive);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.RemoveBuilding(BuildingLibrary.Lair);
		s.evolvingLairs += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.AddBuilding((Building) buildable);
		s.evolvingLairs -= 1;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getLairs() < 1)
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0)
			return true;
		if (s.getInfestationPit() == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}
}
