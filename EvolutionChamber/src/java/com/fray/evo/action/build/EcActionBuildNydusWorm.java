package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildNydusWorm extends EcActionBuildBuilding implements Serializable
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
	protected void postExecute(EcBuildOrder s, EcEvolver e)
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
		if (s.getLairs() == 0 && s.evolvingLairs == 0 && s.getHives() == 0 && s.evolvingHives == 0)
			return true;
		if (s.getNydusNetwork() == 0)
			return true;
		return super.isInvalid(s);
	}

}
