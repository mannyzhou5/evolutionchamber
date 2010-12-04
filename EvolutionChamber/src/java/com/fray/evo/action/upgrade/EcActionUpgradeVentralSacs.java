package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeVentralSacs extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.VentralSacs);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.consumeHatch(getTime());
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.evolvingLairs == 0 && s.getHives() == 0 && s.evolvingHives == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s) {
		if (s.getHatcheries() == 0 && s.getLairs() == 0 && s.getHives() == 0)
			return false;
		return super.isPossible(s);
	};

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
	}
}