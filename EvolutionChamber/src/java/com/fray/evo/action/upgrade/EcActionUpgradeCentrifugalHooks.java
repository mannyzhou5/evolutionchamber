package com.fray.evo.action.upgrade;

import com.fray.evo.util.ZergUpgradeLibrary;

public final class EcActionUpgradeCentrifugalHooks extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(ZergUpgradeLibrary.CentrifugalHooks);
	}

}