package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildInfestationPit extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildInfestationPit()
	{
		super(BuildingLibrary.InfestationPit);
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		if (s.getInfestationPit() == 2)
			return true;
		return super.isInvalid(s);
	}

}
