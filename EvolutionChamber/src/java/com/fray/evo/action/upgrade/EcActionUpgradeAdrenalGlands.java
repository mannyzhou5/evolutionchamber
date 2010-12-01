package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeAdrenalGlands extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.AdrenalGlands);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spawningPoolsInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools - s.spawningPoolsInUse == 0)
			return true;
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.adrenalGlands = true;
		s.spawningPoolsInUse--;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		l.add(new EcActionBuildHive());
		return l;
	}
}