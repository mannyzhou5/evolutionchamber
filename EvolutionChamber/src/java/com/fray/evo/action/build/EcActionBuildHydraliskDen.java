package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildHydraliskDen extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHydraliskDen()
	{
		super(BuildingLibrary.HydraliskDen);
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		if (s.getHydraliskDen() == 1)
			return true;
		return super.isInvalid(s);
	}

}
