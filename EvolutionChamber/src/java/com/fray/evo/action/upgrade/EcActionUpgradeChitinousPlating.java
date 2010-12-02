package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeChitinousPlating extends EcActionUpgrade
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
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildUltraliskCavern());
		return l;
	}
}