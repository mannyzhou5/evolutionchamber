package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeAdrenalGlands extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.AdrenalGlands);
	}

	@Override
	public void execute(EcBuildOrder s, GameLog e)
	{
		super.execute(s, e);
		s.spawningPoolsInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() - s.spawningPoolsInUse == 0)
			return true;
		if (s.getHives() == 0 )
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, GameLog e)
	{
		superAfterTime(s, e);
		s.spawningPoolsInUse--;
	}
}