package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.GameLog;

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
		s.consumeHatch((Building)buildable.getConsumes(),this);
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
            s.unconsumeHatch(this);
            s.RemoveBuilding(BuildingLibrary.Lair);
		s.AddBuilding((Building) buildable);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return s.doesNonBusyReallyExist((Building)buildable.getConsumes()) && super.isPossible(s);
	}
}
