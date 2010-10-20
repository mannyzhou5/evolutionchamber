package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildEvolutionChamber extends EcAction implements Serializable
{

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 75;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.addFutureAction(35, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.obtained(s," Evolution Chamber+1");
				s.evolutionChambers += 1;
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 3)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 75)
			return false;
		if (s.drones < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}
}
