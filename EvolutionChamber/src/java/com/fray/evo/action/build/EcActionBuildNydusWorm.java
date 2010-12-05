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
}
