package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildRoachWarren;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeGlialReconstitution extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.GlialReconstitution);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.roachWarrensInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getRoachWarrens()-s.roachWarrensInUse == 0)
			return true;
		if (s.getLairs() == 0 && s.getHives() == 0)
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