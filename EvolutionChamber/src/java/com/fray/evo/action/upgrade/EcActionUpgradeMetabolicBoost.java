package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeMetabolicBoost extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.MetabolicBoost);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		// TODO Auto-generated method stub
		super.execute(s, e);
		s.spawningPoolsInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools()-s.spawningPoolsInUse == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
		s.spawningPoolsInUse--;
	}
}