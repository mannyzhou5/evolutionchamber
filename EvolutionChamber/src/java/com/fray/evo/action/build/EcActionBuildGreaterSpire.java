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

public class EcActionBuildGreaterSpire extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildGreaterSpire()
	{
		super(BuildingLibrary.GreaterSpire);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.evolvingSpires += 1;
		s.RemoveBuilding((Building) buildable);
	}
	
	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
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

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getHives() == 0 && s.evolvingHives == 0)
			return true;
		if (s.getSpire() == 0)
			return true;
		if (s.getGreaterSpire() == 1)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionBuildHive());
		return l;
	}

}
