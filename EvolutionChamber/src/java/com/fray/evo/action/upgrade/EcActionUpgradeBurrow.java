package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeBurrow extends EcActionUpgrade
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
		s.consumeHatch(this);
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s) {
		if (s.getHatcheries() + s.getLairs() + s.getHives() == s.busyMainBuildings)
			return false;
		return super.isPossible(s);
	};

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{

		superAfterTime(s, e);
	}
}