package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildSpire extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildSpire()
	{
		super(ZergBuildingLibrary.Spire);
	}
}
