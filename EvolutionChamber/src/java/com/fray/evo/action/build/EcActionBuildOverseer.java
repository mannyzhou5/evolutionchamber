package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildOverseer extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildOverseer()
	{
		super(UnitLibrary.Overseer);
	}


    @Override
	protected void preExecute(EcBuildOrder s)
	{
		s.RemoveUnits(UnitLibrary.Overlord, 1);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getOverlords() < 1)
			return false;
		return super.isPossible(s);
	}


}
