package com.fray.evo.action.upgrade;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.UpgradeLibrary;

public final class EcActionUpgradeMelee2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.Melee2);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getEvolutionChambers() == 0)
			return true;
		if (s.getLairs() == 0 && s.getHives() == 0 )
			return true;
		if (s.isMelee1() == false)
			return true;
		if (s.isMelee2() == true)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, GameLog e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.getEvolutionChambers())
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, GameLog e)
	{
		superAfterTime(s, e);
		s.evolutionChambersInUse--;
	}

}