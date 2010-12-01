package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeMissile3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.Missile3);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.missile2 == false)
			return true;
		if (s.missile3 == true)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.missile3 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeMissile2());
		destination.missile2 = true;
		destination.missile1 = true;
		return l;
	}
}