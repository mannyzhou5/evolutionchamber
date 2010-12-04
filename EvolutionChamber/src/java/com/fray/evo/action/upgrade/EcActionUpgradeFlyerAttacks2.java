package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeFlyerAttacks2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.FlyerAttacks2);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpire() == 0)
			return true;
		if (s.getLairs() == 0 && s.getHives() == 0)
			return true;
		if (s.isFlyerAttack1() == false)
			return true;
		if (s.isFlyerAttack2() == true)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
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
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
		s.spiresInUse--;
	}

}