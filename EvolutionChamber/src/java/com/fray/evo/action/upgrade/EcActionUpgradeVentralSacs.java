package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeVentralSacs extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.VentralSacs);
	}

	@Override
	public void execute(EcBuildOrder s, GameLog e)
	{
		super.execute(s, e);
		s.consumeHatch(upgrade.getBuiltIn(),this);
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0  && s.getHives() == 0 )
                    return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s) {
		return s.doesNonBusyExist(upgrade.getBuiltIn()) && super.isPossible(s);
	};

	@Override
	public void afterTime(EcBuildOrder s, GameLog e)
	{
		superAfterTime(s, e);
		s.unconsumeHatch(this);
	}
}