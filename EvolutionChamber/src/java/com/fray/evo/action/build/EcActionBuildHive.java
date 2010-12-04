package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import org.jdesktop.swingx.plaf.BusyLabelUI;

public final class EcActionBuildHive extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHive()
	{
		super(BuildingLibrary.Hive);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{		
		s.busyMainBuildings++;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
            s.RemoveBuilding(BuildingLibrary.Lair);
		s.AddBuilding((Building) buildable);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.getLairs() < 1 || s.getLairs() <= s.busyLairs.size())
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getLairs() == 0)
			return true;
		if (s.getInfestationPit() == 0)
			return true;
		return super.isInvalid(s);
	}

}
