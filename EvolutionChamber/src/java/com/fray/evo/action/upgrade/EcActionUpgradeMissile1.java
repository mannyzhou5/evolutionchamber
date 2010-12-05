package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeMissile1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.Missile1);
	}


}