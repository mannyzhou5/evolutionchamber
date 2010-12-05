package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildEvolutionChamber extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildEvolutionChamber()
	{
		super(ZergBuildingLibrary.EvolutionChamber);
	}
}
