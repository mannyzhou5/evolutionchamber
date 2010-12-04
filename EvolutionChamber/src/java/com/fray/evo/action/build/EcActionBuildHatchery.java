package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildHatchery extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHatchery()
	{
		super(BuildingLibrary.Hatchery);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.hatcheriesBuilding += 1;
		s.addFutureAction(getTime() - 30, new Runnable()
		{
			@Override
			public void run()
			{
			}
		});
		s.addFutureAction(getTime() - 50, new Runnable()
		{
			@Override
			public void run()
			{
			}
		});
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.AddBuilding(BuildingLibrary.Hatchery);
		s.hatcheriesBuilding -= 1;
		s.hatcheryTimes.add(new Integer(s.seconds));
		s.larva.add(1);
		s.larvaProduction.add(1);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.supplyUsed < s.settings.minimumHatcherySupply)
			return true;
		return false;
	}


}