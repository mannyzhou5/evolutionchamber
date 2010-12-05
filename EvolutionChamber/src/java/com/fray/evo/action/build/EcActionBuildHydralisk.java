package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildHydralisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildHydralisk()
	{
		super(ZergUnitLibrary.Hydralisk);
	}

}
