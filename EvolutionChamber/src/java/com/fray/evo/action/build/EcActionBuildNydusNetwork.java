package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildNydusNetwork extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildNydusNetwork()
	{
		super(BuildingLibrary.NydusNetwork);
	}


	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0  && s.getHives() == 0)
			return true;
		if (s.getNydusNetwork() == 2)
			return true;
		return super.isInvalid(s);
	}

}
