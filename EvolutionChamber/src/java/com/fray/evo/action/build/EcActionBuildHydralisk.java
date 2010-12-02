package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildHydralisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildHydralisk()
	{
		super(UnitLibrary.Hydralisk);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getHydraliskDen() == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}
}
