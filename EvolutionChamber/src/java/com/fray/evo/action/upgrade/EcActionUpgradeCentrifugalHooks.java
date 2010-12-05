package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeCentrifugalHooks extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.CentrifugalHooks);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getBanelingNest() == 0)
			return true;
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, GameLog e)
	{
		superAfterTime(s, e);
	}
}