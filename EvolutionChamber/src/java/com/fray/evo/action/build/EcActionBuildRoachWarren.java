package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildRoachWarren extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildRoachWarren()
	{
		super(ZergBuildingLibrary.RoachWarren);
	}

}
