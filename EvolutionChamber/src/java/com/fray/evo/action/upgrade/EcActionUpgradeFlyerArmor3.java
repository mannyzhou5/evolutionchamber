package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildSpire;
import com.fray.evo.util.UpgradeLibrary;

public class EcActionUpgradeFlyerArmor3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(UpgradeLibrary.FlyerArmor3);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		if (s.hives == 0)
			return true;
		if (s.flyerArmor2 == false)
			return true;
		if (s.flyerArmor3 == true)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerArmor3 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeFlyerArmor2());
		destination.flyerArmor1 = true;
		destination.flyerArmor2 = true;
		return l;
	}
}