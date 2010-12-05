package com.fray.evo.action.upgrade;

import com.fray.evo.util.ZergUpgradeLibrary;

public final class EcActionUpgradeCarapace1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(ZergUpgradeLibrary.Armor1);
	}
}