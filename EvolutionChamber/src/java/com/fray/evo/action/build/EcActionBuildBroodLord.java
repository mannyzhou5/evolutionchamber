package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildBroodLord extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildBroodLord()
	{
		super(UnitLibrary.Broodlord);
	}

    @Override
	protected void preExecute(final EcBuildOrder s)
	{
		s.RemoveUnits((Unit)getConsumes(), 1);
	}
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getCorruptors() < 1)
			return false;
		return isPossibleResources(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getHives() == 0 )
			return true;
		if (s.getGreaterSpire() == 0)
			return true;
		if (!s.hasSupply(2))
			return true;
		return super.isInvalid(s);
	}
}
