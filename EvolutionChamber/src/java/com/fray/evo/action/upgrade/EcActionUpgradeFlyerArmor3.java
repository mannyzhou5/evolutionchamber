package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeFlyerArmor3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.FlyerArmor3);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpire() == 0)
			return true;
		if (s.getHives() == 0)
			return true;
		if (s.isFlyerArmor2() == false)
			return true;
		if (s.isFlyerArmor3() == true)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, GameLog e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.getSpire())
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, GameLog e)
	{
		superAfterTime(s, e);
		s.spiresInUse--;
	}

}