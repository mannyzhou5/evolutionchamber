package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.BuildingLibrary;

public final class EcActionBuildEvolutionChamber extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildEvolutionChamber()
	{
		super(BuildingLibrary.EvolutionChamber);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getEvolutionChambers() == 3)
			return true;
		return super.isInvalid(s);
	}

}
