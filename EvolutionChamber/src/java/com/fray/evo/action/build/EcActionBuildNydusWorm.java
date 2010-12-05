package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.GameLog;

public final class EcActionBuildNydusWorm extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildNydusWorm()
	{
		super(BuildingLibrary.NydusWorm);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.nydusNetworkInUse += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
		s.AddBuilding((Building)buildable);
		s.nydusNetworkInUse -= 1;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.nydusNetworkInUse == s.getNydusNetwork())
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		if (s.getNydusNetwork() == 0)
			return true;
		return super.isInvalid(s);
	}

}
