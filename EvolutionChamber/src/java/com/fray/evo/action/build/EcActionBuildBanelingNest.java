package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildBanelingNest extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildBanelingNest()
	{
		super(ZergBuildingLibrary.BanelingNest);
	}

}
