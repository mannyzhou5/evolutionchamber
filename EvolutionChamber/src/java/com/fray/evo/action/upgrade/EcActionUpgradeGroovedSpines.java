package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeGroovedSpines extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.GroovedSpines);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getHydraliskDen() == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
	}

}