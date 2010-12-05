package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeChitinousPlating extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.ChitinousPlating);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getUltraliskCavern() == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, GameLog e)
	{
		superAfterTime(s, e);
	}

}