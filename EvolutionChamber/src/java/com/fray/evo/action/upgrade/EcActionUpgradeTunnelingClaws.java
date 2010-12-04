package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeTunnelingClaws extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.TunnelingClaws);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		// TODO Auto-generated method stub
		super.execute(s, e);
		s.roachWarrensInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getRoachWarrens()-s.roachWarrensInUse == 0)
			return true;
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
		s.roachWarrensInUse--;
	}
}