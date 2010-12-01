package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildUltraliskCavern extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildUltraliskCavern()
	{
		super(BuildingLibrary.UltraliskCavern);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.ultraliskCavern += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.ultraliskCavern == 1)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildHive());
		destination.hives = Math.max(destination.hives, 1);
		return l;
	}
}
