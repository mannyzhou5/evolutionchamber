package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildHydraliskDen extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHydraliskDen()
	{
		super(ZergBuildingLibrary.HydraliskDen);
	}
}
