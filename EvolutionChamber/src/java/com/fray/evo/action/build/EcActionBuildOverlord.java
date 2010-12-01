package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildOverlord extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildOverlord()
	{
		super(UnitLibrary.Overlord);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.overlords += 1;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}
}
