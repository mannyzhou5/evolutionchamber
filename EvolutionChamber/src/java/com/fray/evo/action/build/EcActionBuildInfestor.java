package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildInfestor extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildInfestor()
	{
		super(UnitLibrary.Infestor);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.infestors += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.infestationPit == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
}
