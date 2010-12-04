package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradePneumatizedCarapace extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.PneumatizedCarapace);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.consumeHatch(this);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getHatcheries() + s.getLairs() + s.getHives() == s.busyMainBuildings)
			return false;
		return super.isPossible(s);
	};

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
            s.unconsumeHatch(this);
		superAfterTime(s, e);
	}

}