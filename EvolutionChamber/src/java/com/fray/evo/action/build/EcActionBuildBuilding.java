package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;

public abstract class EcActionBuildBuilding extends EcActionBuild implements Serializable
{
	public boolean	takesDrone	= true;

	public EcActionBuildBuilding(Building building)
	{
		super(building);
	}

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= getMinerals();
		s.gas -= getGas();
		if (takesDrone)
		{
			s.drones -= 1;
			s.dronesOnMinerals -= 1;
			s.supplyUsed -= 1;
		}
		preExecute(s);
		s.addFutureAction(getTime(), new Runnable()
		{
			@Override
			public void run()
			{
				obtainOne(s, e);
				postExecute(s, e);
			}

		});
	}

	protected void preExecute(EcBuildOrder s)
	{

	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (takesDrone)
			if (s.drones < 1)
				return false;
		return isPossibleResources(s);
	}

	protected abstract void postExecute(EcBuildOrder s, EcEvolver e);

}
