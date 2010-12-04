package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildSpire extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildSpire()
	{
		super(BuildingLibrary.Spire);
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0 && s.evolvingLairs == 0 && s.getHives() == 0 && s.evolvingHives == 0)
			return true;
		if (s.getSpire() == 2)
			return true;
		return super.isInvalid(s);
	}

}
