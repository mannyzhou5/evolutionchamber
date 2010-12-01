package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeBurrow extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.Burrow);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.consumeHatch(getTime());
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s) {
		if (s.hatcheries == 0 && s.lairs == 0 && s.hives == 0)
			return false;
		return super.isPossible(s);
	};

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0 && s.evolvingHives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.burrow = true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}