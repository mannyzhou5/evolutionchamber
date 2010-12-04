package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.UnitLibrary;

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
		s.addFutureAction((int)(getTime() - BuildingLibrary.Extractor.getTime()), new Runnable()
		{
			@Override
			public void run()
			{
				// This is a futureaction purely made for wait timing so that
				// you can build a extractor to line up with this hatch.
			}
		});
		s.addFutureAction((int)(getTime() - UnitLibrary.Queen.getTime()), new Runnable()
		{
			@Override
			public void run()
			{
				// This is a futureaction purely made for wait timing so that
				// you can build a queen to line up with this hatch.
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