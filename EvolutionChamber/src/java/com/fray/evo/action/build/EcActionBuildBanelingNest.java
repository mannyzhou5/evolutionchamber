package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcActionBuildBanelingNest extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildBanelingNest()
	{
		super(BuildingLibrary.BanelingNest);
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		if (s.getBanelingNest() == 1)
			return true;
		return false;
	}

}
