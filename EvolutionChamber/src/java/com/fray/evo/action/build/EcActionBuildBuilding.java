package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.UnitLibrary;

public abstract class EcActionBuildBuilding extends EcActionBuild implements Serializable
{

	public EcActionBuildBuilding(Building building)
	{
		super(building);
	}

	@Override
	public void execute(final EcBuildOrder s, final GameLog e)
	{
		s.minerals -= getMinerals();
		s.gas -= getGas();
		if (getConsumes() == UnitLibrary.Drone)
		{
			s.RemoveUnits(UnitLibrary.Drone, 1);
			s.dronesOnMinerals -= 1;
			s.supplyUsed -= 1;
		}
		preExecute(s);
		s.addFutureAction(getTime(), new RunnableAction()
		{
			@Override
			public void run(GameLog e)
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
		if (getConsumes() == UnitLibrary.Drone)
			if (s.getDrones() < 1)
				return false;
		return isPossibleResources(s);
	}

	protected void postExecute(EcBuildOrder s, GameLog e){
            s.AddBuilding((Building) buildable);
        };

}
