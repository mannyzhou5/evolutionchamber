package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildDrone extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildDrone()
	{
		super(UnitLibrary.Drone);
	}

	@Override
	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
		s.AddUnits((Unit) buildable, 1);
		s.dronesGoingOnMinerals += 1;
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (s.droneIsScouting == false && s.getDrones() >= e.getDestination().scoutDrone
						&& e.getDestination().scoutDrone != 0)
				{
					s.droneIsScouting = true;
					if (e.debug)
						e.scout(s, " +1 Scouting Drone");
				}
				else
				{
					s.dronesGoingOnMinerals--;
					s.dronesOnMinerals++;
				}
			}
		});
	}

}
