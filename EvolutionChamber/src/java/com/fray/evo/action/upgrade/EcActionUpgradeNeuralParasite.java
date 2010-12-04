package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeNeuralParasite extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.NeuralParasite);
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		// TODO Auto-generated method stub
		super.execute(s, e);
		s.infestationPitInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getInfestationPit()-s.infestationPitInUse == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		superAfterTime(s, e);
		s.infestationPitInUse--;
	}

}